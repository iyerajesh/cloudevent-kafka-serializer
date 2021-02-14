package com.xylia.platform.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudEventKafkaSerializerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudEventKafkaSerializerApplication.class, args);
	}

}
