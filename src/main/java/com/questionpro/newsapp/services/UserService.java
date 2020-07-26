package com.questionpro.newsapp.services;

import com.questionpro.newsapp.model.User;

public interface UserService {
    User getByUsername(String username);
}
