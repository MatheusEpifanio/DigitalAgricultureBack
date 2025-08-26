package com.matheus.DigitalAgriculture;

import com.matheus.DigitalAgriculture.dto.request.UsersRequestDTO;
import com.matheus.DigitalAgriculture.model.Users;
import com.matheus.DigitalAgriculture.services.AuthenticationUsersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DigitalAgricultureApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalAgricultureApplication.class, args);
	}

    @Bean
    CommandLineRunner initDataBase(AuthenticationUsersService authenticationUsersService) {
        return args -> {
            Users user = new Users();
            user.setName("matheus");
            user.setEmail("teste@teste.com1");
            user.setPassword("123456");

            authenticationUsersService.register(new UsersRequestDTO(user.getEmail(), user.getName(), user.getPassword()));
        };
    }


}
