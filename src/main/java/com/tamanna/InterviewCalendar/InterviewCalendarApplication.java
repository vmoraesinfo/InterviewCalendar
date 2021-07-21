package com.tamanna.InterviewCalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.*;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class InterviewCalendarApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewCalendarApplication.class, args);
	}

}
