package guru.springframework.springrestclientexamples.services;

import guru.springframework.api.domain.User;
import guru.springframework.api.domain.UserData;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    private RestTemplate restTemplate;

    public ApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<User> getUsers(Integer limit) {
        /*
        // To enable NO CORS
        HttpHeaders headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin","*");
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<UserData> userData = restTemplate.exchange("https://apifaketory.com/api/user?limit=" + limit, HttpMethod.GET, entity, UserData.class);
         */

        List<User> userData = restTemplate.getForObject("https://jsonplaceholder.typicode.com/users?_limit=" +
                limit, List.class);  // What class the data to bind to
        return userData;
    }
}
