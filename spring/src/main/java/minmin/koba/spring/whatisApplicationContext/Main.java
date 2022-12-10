package minmin.koba.spring.whatisApplicationContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import minmin.koba.spring.whatisDI.models.User;
import minmin.koba.spring.whatisDI.services.UserService;

public class Main {
    public static void main(String[] args) {
		// SpringApplication.run(SpringPracticeApplication.class, args);
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		UserService userService = context.getBean(UserService.class);

		userService.register(new User(), "password");

		((AnnotationConfigApplicationContext) context).close();
    }
}
