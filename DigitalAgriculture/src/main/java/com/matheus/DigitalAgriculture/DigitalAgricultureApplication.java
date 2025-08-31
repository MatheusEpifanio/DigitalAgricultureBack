package com.matheus.DigitalAgriculture;

import com.matheus.DigitalAgriculture.dto.request.UsersRequestDTO;
import com.matheus.DigitalAgriculture.model.Fields;
import com.matheus.DigitalAgriculture.model.Users;
import com.matheus.DigitalAgriculture.repository.FieldsRepository;
import com.matheus.DigitalAgriculture.services.AuthenticationUsersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

@SpringBootApplication
public class DigitalAgricultureApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalAgricultureApplication.class, args);
	}

    @Bean
    @Profile("dev")
    CommandLineRunner initDataBase(AuthenticationUsersService authenticationUsersService, FieldsRepository fieldsRepository) {
        return args -> {
            Users user = new Users();
            user.setId(1L);
            user.setName("matheus");
            user.setEmail("teste@teste.com1");
            user.setPassword("123456");

            authenticationUsersService.register(new UsersRequestDTO(user.getEmail(), user.getName(), user.getPassword()));

            Fields fields = new Fields();
            fields.setName("Campo norte");
            fields.setCrop("soja");
            fields.setAreaHectares(120);
            fields.setLatitude(new BigDecimal(-47.9287));
            fields.setLongitude(new BigDecimal(-15.7801));
            fields.setUsers(user);
            fieldsRepository.save(fields);
        };
    }


}
