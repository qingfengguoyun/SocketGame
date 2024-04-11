package com.myPokeGame.service.userService;

import com.myPokeGame.entity.User;

import java.util.List;

public interface UserService {

    public User saveUser(User user);

    public User signUser(User user);

    public User login(User user);

    public List<String> queryAllUserName();

    public List<User> queryAllUsers();

    public User queryUserById(Long userId);
}