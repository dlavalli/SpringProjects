package com.lavalliere.daniel.spring.httpinterface;

import com.lavalliere.daniel.spring.httpinterface.service.TodoService;
import com.lavalliere.daniel.spring.httpinterface.service.TraditionalTodoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class HttpInterfaceApplication {


//	// Traditional way of binding service in Spring Boot 3
//	@Bean
//	TodoService todoService(RestClient.Builder restClientBuilder ) {
//		RestClient client =  restClientBuilder.baseUrl("http://jsonplaceholder.typicode.com").build();
//		RestClientAdapter restClientAdapter = RestClientAdapter.create(client);
//		HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
//		return httpServiceProxyFactory.createClient(TodoService.class);
//	}

	public static void main(String[] args) {
		SpringApplication.run(HttpInterfaceApplication.class, args);
	}

}
