package com.lavalliere.daniel.spring.elksample.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.truncate;


// See https://www.baeldung.com/spring-onceperrequestfilter
// OncePerRequestFilter is a Filter base class that aims to guarantee a single execution per request dispatch, on any servlet container
// Zero Configuration: Spring Boot's web auto-configuration scans for any bean implementing jakarta.servlet.Filter (including OncePerRequestFilter) and registers it globally.
// Execution Scope: By default, it intercepts all incoming paths (/*) during standard request dispatches.
// When You Might Need Extra Configuration:
// - Targeted Paths: Use FilterRegistrationBean if you want the filter to run on specific URL patterns only, rather than every single endpoint
//   See the FilterConfig in config for an example
// - Filter Ordering: Use the @Order annotation or Ordered interface if you have multiple filters and need them to execute in a strict sequence.
// - Async/Error Dispatches: Override shouldNotFilterAsyncDispatch() or shouldNotFilterErrorDispatch() if you also need to capture asynchronous requests or error page forwards
@Slf4j
@RequiredArgsConstructor
// @Component  // Required to apply to all endpoints. In this case, we are customizing it in FilterConfig
public class HttpTrafficLoggingFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper;
    private static final int MAX_PAYLOAD_LENGTH = 10_000; // truncate very large bodies
    private static final int MAX_CACHE_LIMIT = 1024 * 1024;
    private static final Set<String> SENSITIVE_HEADERS = Set.of(
        "authorization", "proxy-authorization", "cookie", "set-cookie", "x-api-key"
    );
    private static final Set<String> SENSITIVE_JSON_FIELDS = Set.of(
        "password", "pass", "pwd", "secret", "token", "access_token", "refresh_token",
        "client_secret", "authorization", "apiKey", "ssn", "creditCard", "cvv", "pin"
    );

    // Returning true to avoid filtering of the given request. The default implementation always returns false.
    // See also https://www.baeldung.com/spring-onceperrequestfilter#conditionally-skipping-requests
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Optional: skip actuator, static, health, etc.
        String path = request.getRequestURI();
        // Spring Boot 2.x and higher: Uses the /actuator base path by default for all web-exposed endpoints.
        return path.startsWith("/actuator");
    }

    // capture all incoming http traffic to your API endpoint and the corresponding
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        // HttpServletRequest wrapper that caches all content read from the input stream and reader, and allows this content
        // to be retrieved via a byte array. This class acts as an interceptor that only caches content as it is being read
        // but otherwise does not cause content to be read. That means if the request content is not consumed,
        // then the content is not cached, and cannot be retrieved via getContentAsByteArray().
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request, MAX_CACHE_LIMIT);

        // HttpServletResponse wrapper that caches all content written to the output stream and writer, and allows this content
        // to be retrieved via a byte array.
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        // Calculate start time: ie time to execute the request
        Instant start = Instant.now();
        try {
            // Pass the filter down the line for further processing
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            // Calculate end time: ie time to execute the request
            long tookMs = Duration.between(start, Instant.now()).toMillis();

            // Logging of JSON event for current request/response
            emitStructuredLog(wrappedRequest, wrappedResponse, tookMs);

            // IMPORTANT: Copy the complete cached body content to the response.
            //            without this, response body may not be sent to client
            // REASON:    You must call copyBodyToResponse() because ContentCachingResponseWrapper holds data in a private buffer
            //            instead of writing it directly to the client, meaning the client gets an empty response unless you explicitly
            //            copy the cached bytes back. (Note: Spring Data Elasticsearch is part of the service/data layer
            //            and does not directly change how servlet response wrappers handle HTTP output streams).
            wrappedResponse.copyBodyToResponse();
        }
    }

    private void emitStructuredLog(ContentCachingRequestWrapper req,
                                   ContentCachingResponseWrapper res,
                                   long durationMs) {

        // Create the request/response logging event
        Map<String, Object> event = new LinkedHashMap<>();
        event.put("http", Map.of("durationMs", durationMs));

        // Retrieve the request content type
        String reqContentType = Optional.ofNullable(req.getContentType()).orElse("");
        event.put("eventType", "http_traffic");
        event.put("timestamp", Instant.now().toString());
        event.put("request", Map.of(
            "method", req.getMethod(),
            "uri", req.getRequestURI(),
            "query", Optional.ofNullable(req.getQueryString()).orElse(""),
            "contentType", reqContentType,
            "headers", maskHeaders(getRequestHeadersMap(req)),
            "body", maskBodyIfNeeded(getRequestBody(req), reqContentType)
        ));

        // Retrieve the response content type
        String resContentType = Optional.ofNullable(res.getContentType()).orElse("");
        event.put("response", Map.of(
            "status", res.getStatus(),
            "contentType", resContentType,
            "headers", maskHeaders(getResponseHeadersMap(res)),
            "body", maskBodyIfNeeded(getResponseBody(res), resContentType)
        ));

        // message is human-readable, kv is structured JSON for logstash/logback encoder
        log.info("HTTP traffic", StructuredArguments.kv("event", event));
    }

    // Extract the request/response provided charset else assume default UTF-8
    private Charset getCharsetOrDefault(String encoding) {
        if (StringUtils.hasText(encoding)) {
            try {
                return Charset.forName(encoding);
            } catch (Exception ignored) { }
        }
        return StandardCharsets.UTF_8;
    }

    // Make sure the payload does not exceed the max allowed size
    private String truncate(String payload) {
        if (payload == null) return "";
        return payload.length() > MAX_PAYLOAD_LENGTH
            ? payload.substring(0, MAX_PAYLOAD_LENGTH) + "...(truncated)"
            : payload;
    }

    // Extract the request/response and convert to requested charset and max length
    private String getBody(byte[] buf, String encoding) {
        if (buf.length == 0) return "";
        Charset cs = getCharsetOrDefault(encoding);
        return truncate(new String(buf, cs));
    }

    // Extract the request body and convert to requested charset and max length
    private String getRequestBody(ContentCachingRequestWrapper request) {
        return getBody(request.getContentAsByteArray(), request.getCharacterEncoding());
    }

    // Extract the response body and convert to requested charset and max length
    private String getResponseBody(ContentCachingResponseWrapper response) {
        return getBody(response.getContentAsByteArray(), response.getCharacterEncoding());
    }

    // Retrieve a map of all provided HTTP request headers
    private Map<String, String> getRequestHeadersMap(
        ContentCachingRequestWrapper request  // HttpServletRequest request
    ) {
        // Retrieve all provided HTTP header names
        Enumeration<String> names = request.getHeaderNames();
        if (names == null) return Collections.emptyMap();

        // Store all request headers in a map for easier access
        Map<String, String> headers = new LinkedHashMap<>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            headers.put(name, request.getHeader(name));
        }
        return headers;
    }

    // Retrieve a map of all provided HTTP response headers
    private Map<String, List<String>> getResponseHeadersMap(
        ContentCachingResponseWrapper response  // HttpServletResponse response
    ) {
        Map<String, List<String>> headers = new LinkedHashMap<>();
        for (String name : response.getHeaderNames()) {
            headers.put(name, new ArrayList<>(response.getHeaders(name)));
        }
        return headers;
    }

    // Validate if the current field should be masked
    private boolean isSensitiveField(String name) {
        String n = name.toLowerCase(Locale.ROOT);
        return SENSITIVE_JSON_FIELDS.stream()
            .map(s -> s.toLowerCase(Locale.ROOT))
            .anyMatch(n::equals);
    }

    // @SuppressWarnings("unchecked")
    // Mask Map and List types if required
    private Object maskAny(Object any) {
        return switch(any) {
            case Map<?, ?> map -> {
                Map<String, Object> out = new LinkedHashMap<>();
                map.forEach((k, v) -> {
                    String key = String.valueOf(k);  // Convert the Key capture to a String since specified ?
                    if (isSensitiveField(key)) out.put(key, "***MASKED***");
                    else out.put(key, maskAny(v));
                });
                yield out;
            }
            case List<?> list -> list.stream()
                .map(this::maskAny)
                .collect(Collectors.toList());
            default -> any;
        };
    }

    private void maskJsonNode(ObjectNode node) {
        // Convert field names to Iterator<String>
        Iterator<String> it = node.fieldNames();
        List<String> names = new ArrayList<>();
        it.forEachRemaining(names::add);

        for (String name : names) {
            if (isSensitiveField(name)) {
                node.put(name, "***MASKED***");
            } else if (node.get(name).isObject()) {
                maskJsonNode((ObjectNode) node.get(name));
            } else if (node.get(name).isArray()) {
                for (var child : node.get(name)) {
                    if (child.isObject()) maskJsonNode((ObjectNode) child);
                }
            }
        }
    }

    // Mask sensitive headers for logging
    private Map<String, ?> maskHeaders(Map<String, ?> headers) {
        Map<String, Object> masked = new LinkedHashMap<>();
        headers.forEach((k, v) -> {
            if (k != null && SENSITIVE_HEADERS.contains(k.toLowerCase(Locale.ROOT))) {
                masked.put(k, "***MASKED***");
            } else {
                masked.put(k, v);
            }
        });
        return masked;
    }

    private String maskBodyIfNeeded(String body, String contentType) {
        if (!StringUtils.hasText(body)) return body;

        // Only attempt JSON masking for JSON payloads
        if (contentType != null && contentType.toLowerCase(Locale.ROOT).contains("application/json")) {
            try {
                ObjectNode root = (ObjectNode) mapper.readTree(body);
                maskJsonNode(root);
                return truncate(mapper.writeValueAsString(root));
            } catch (Exception ignored) {
                // if not valid JSON object, fallback to raw/truncated
            }

            try {
                List<Object> list = mapper.readValue(body, new TypeReference<List<Object>>() {});
                Object masked = maskAny(list);
                return truncate(mapper.writeValueAsString(masked));
            } catch (Exception ignored) {
            }
        }

        return truncate(body);
    }
}
