package com.myPokeGame.service;

import com.myPokeGame.entity.User;
import com.myPokeGame.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public User saveUser(User user) {
        userMapper.insert(user);
        return user;
    }
}
