package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.print("""
						Чтобы проверить открывай: http://localhost:8080 
						
						логин/пароль для проверки:
						admin/admin - все доступные действия (редактирование, удаление) 
						user/user - просто просмотр
						""");
	}

}
