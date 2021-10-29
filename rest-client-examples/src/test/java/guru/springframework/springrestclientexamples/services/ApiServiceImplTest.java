package guru.springframework.springrestclientexamples.services;

import guru.springframework.api.domain.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiServiceImplTest extends TestCase {

    public static final int LIMIT = 3;

    @Autowired
    ApiService apiService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    // See https://github.com/springframeworkguru/spring-rest-client-examples/issues/6  and
    // https://github.com/raphaeltesttest/spring-rest-client-examples


    @Test
    public void testGetUsers() throws Exception {
        List<User> users = apiService.getUsers(LIMIT);
        assertEquals(LIMIT, users.size());
    }
}