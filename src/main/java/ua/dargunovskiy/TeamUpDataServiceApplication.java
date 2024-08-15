package ua.dargunovskiy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TeamUpDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamUpDataServiceApplication.class, args);
	}

}
