package mx.uam.izt.archinautInterface;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ArchinautInterfaceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ArchinautInterfaceApplication.class, args);
	}

}
