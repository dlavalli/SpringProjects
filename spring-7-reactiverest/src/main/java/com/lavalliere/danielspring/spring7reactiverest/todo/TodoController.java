package com.lavalliere.danielspring.spring7reactiverest.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;


    /*
     * Important NOTE:
     * What would be the consequence of changing MediaType.TEXT_EVENT_STREAM to MediaType.APPLICATION_JSON in this case ?
     *
     * With APPLICATION_JSON, Spring/WebFlux will typically serialize
     * the Flux<Todo> as one JSON array (e.g. [ {...}, {...} ]) and the client usually gets it when completed, not as incremental SSE events.
     * So consequences:
     *
     *   TEXT_EVENT_STREAM
     *       Response is SSE stream (text/event-stream)
     *       Items can arrive one-by-one continuously
     *       Good for live updates / long-lived connections
     *
     *   APPLICATION_JSON
     *       Response is normal JSON (application/json)
     *       Usually buffered/aggregated as a finite payload (often an array)
     *       Better for “get all once” use cases, not live push
     *
     */

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)  // MediaType.TEXT_EVENT_STREAM_VALUE
    public ResponseEntity<Flux<Todo>> getAllTodos() {
        return ResponseEntity.ok()
            .header(HttpHeaders.CACHE_CONTROL, "no-cache")
            .body(todoService.getAllTodos());
   }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<Todo>> getTodoById(@PathVariable Long id) {
        return ResponseEntity.ok()
            .header(HttpHeaders.CACHE_CONTROL, "no-cache")
            .body(todoService.getTodoById(id));
    }
}
