package ru.iu3.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		// Пометка - чтобы исправить отсутствие плагина в pom.xml, добавьте строку <version>2.6.4</version> сразу
		// после него

		SpringApplication.run(BackendApplication.class, args);
	}

}
