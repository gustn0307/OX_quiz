package com.my.ox_quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA의 CRUD를 감시(main 함수가 있는 클래스에 달아준다.)
@SpringBootApplication
public class OxQuizApplication {

	public static void main(String[] args) {
		SpringApplication.run(OxQuizApplication.class, args);
	}

}
