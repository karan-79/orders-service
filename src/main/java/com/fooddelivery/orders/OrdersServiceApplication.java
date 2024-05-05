package com.fooddelivery.orders;

import com.fooddelivery.orders.service.OrderService;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class OrdersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersServiceApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(OrderService service) {
		return (args) -> {
			var id = UUID.randomUUID();
			var store = UUID.randomUUID();
			service.create(id, store);

			var ords =service.getAllOrders(id);
			System.out.println(ords);
		};
	}

}
