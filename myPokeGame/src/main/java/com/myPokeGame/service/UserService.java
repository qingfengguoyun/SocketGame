package com.myPokeGame.service;

import com.myPokeGame.entity.User;

import java.util.List;

public interface UserService {

    public User saveUser(User user);

    public User signUser(User user);

    public User Login(User user);

    public List<String> queryAllUserName();
}
