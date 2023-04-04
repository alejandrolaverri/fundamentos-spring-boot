package com.alejandromo.springboot.fundamentos.caseuse;

import com.alejandromo.springboot.fundamentos.entity.User;

import java.util.List;

public interface GetUser {
    List<User> getAll();
}
