package com.myPokeGame.service;

import com.myPokeGame.entity.User;
import com.myPokeGame.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public User saveUser(User user) {
        if(user.getUserId()==null){
            userMapper.insert(user);
            return user;
        }else{
            User u = userMapper.selectById(user.getUserId());
            if(u==null){
                userMapper.insert(user);
                return user;
            }else{
                userMapper.updateById(user);
            }
        }
        return user;
    }

    @Override
    public User signUser(User user) {
        return null;
    }

    @Override
    public User Login(User user) {
        return null;
    }

    @Override
    public List<String> queryAllUserName() {
        List<String> names = userMapper.queryAllUserName();
        names.forEach(t->{
            System.out.println(t);
        });
        return names;
    }
}
