package com.myPokeGame.service;

import com.myPokeGame.entity.User;
import com.myPokeGame.exceptions.NativeException;
import com.myPokeGame.mapper.UserMapper;
import com.myPokeGame.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    Map<User, Date> onlineUserMap;

    @Autowired
    SocketIoService socketIoService;


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
        List<String> names = userMapper.queryAllUserName();
        if(names.contains(user.getUserName())){
           throw new NativeException("用户名重复");
        }else{
            userMapper.insert(user);
            log.info("欢迎新用户： "+user.getUserName());
        }
        return user;
    }

    @Override
    public User login(User user) {
        User res = userMapper.checkNameAndPass(user);
        if(!ObjectUtils.isEmpty(res)){
            if(!ObjectUtils.isEmpty(onlineUserMap.get(res))){
                throw new NativeException("不能重复登录");
            }else{
                onlineUserMap.put(res,new Date());
                String.format("欢迎用户 %s ",res.getUserName());
                return res;
            }
        }
        throw new NativeException("用户未注册");
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
