package com.alejandromo.springboot.fundamentos.caseuse;

import com.alejandromo.springboot.fundamentos.entity.User;
import com.alejandromo.springboot.fundamentos.service.UserService;

import java.util.List;

public class GetUserImplement implements  GetUser{

    private UserService userService;

    public GetUserImplement(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<User> getAll() {
        return userService.getAllUsers();
    }
}