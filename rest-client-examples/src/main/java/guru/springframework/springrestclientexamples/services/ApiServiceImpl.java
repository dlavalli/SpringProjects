package guru.springframework.springrestclientexamples.services;

import guru.springframework.api.domain.User;
import guru.springframework.api.domain.UserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    private RestTemplate restTemplate;
    private final String api_url;

    public ApiServiceImpl(RestTemplate restTemplate,
                          @Value("${api.url}") String api_url) {
        this.restTemplate = restTemplate;
        this.api_url = api_url;
    }

    @Override
    public List<User> getUsers(Integer limit) {
        /*
        // To enable NO CORS
        HttpHeaders headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin","*");
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<UserData> userData = restTemplate.exchange("https://jsonplaceholder.typicode.com/users?_limit=" + limit, HttpMethod.GET, entity, UserData.class);
         */

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(api_url)
                .queryParam("_limit", limit);

        List<User> userData = restTemplate.getForObject(uriComponentsBuilder.toUriString(), List.class);

        return userData;
    }


}
