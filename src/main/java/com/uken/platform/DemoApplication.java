package com.uken.platform;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
	
	@Bean
	public String hostname() {
		String hostname;
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			hostname = UUID.randomUUID().toString();
		}
        return hostname;
    }

	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
