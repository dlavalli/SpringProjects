package guru.springframework.spring_6_di;

import guru.springframework.spring_6_di.controllers.MyController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"EN","dev"})
class Spring6DiApplicationTests {

	@Autowired
	ApplicationContext context;

	@Autowired
	MyController myController;

	@Test
	void testAutowireOfController() {
		assertNotNull(myController);
		System.out.println(STR."In main method: \{myController.sayHello()}");
	}

	@Test
	void contextLoads() {
		assertNotNull(context);
		MyController controller = context.getBean(MyController.class);
		assertNotNull(controller);

		// STR is enabled for java 21 through Project Structure -> Modules -> Source -> Language Level (x - preview)
		System.out.println(STR."In main method: \{controller.sayHello()}");
	}
}
