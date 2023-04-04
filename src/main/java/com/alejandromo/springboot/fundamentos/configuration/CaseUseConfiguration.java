package com.alejandromo.springboot.fundamentos.configuration;

import com.alejandromo.springboot.fundamentos.caseuse.GetUser;
import com.alejandromo.springboot.fundamentos.caseuse.GetUserImplement;
import com.alejandromo.springboot.fundamentos.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaseUseConfiguration {

    @Bean
    GetUser getUser(UserService userService) {
        return new GetUserImplement(userService);
    }
}
