package guru.springframework.sfgdi;

import guru.springframework.sfgdi.controllers.MyController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SfgDiApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SfgDiApplication.class, args);

		// By default when Spring creates beans, the string name of the bean
		// is going to be the class name but beginning with a lower case
		MyController myController = (MyController)ctx.getBean("myController");
		String greeting = myController.sayHello();
		System.out.println(greeting);
	}

}
