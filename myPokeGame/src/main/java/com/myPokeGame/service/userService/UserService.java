package com.myPokeGame.service.userService;

import com.myPokeGame.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {

    public User saveUser(User user);

    public User signUser(User user);

    public User login(User user, HttpServletRequest request, HttpServletResponse response);

    public List<String> queryAllUserName();

    public List<User> queryAllUsers();

    public List<User> queryAllUsersButAdmin();

    public User queryUserById(Long userId);

    public User updateUser(User user);
}
