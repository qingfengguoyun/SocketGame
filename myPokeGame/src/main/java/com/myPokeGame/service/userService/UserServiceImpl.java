package com.myPokeGame.service.userService;

import com.myPokeGame.entity.User;
import com.myPokeGame.exceptions.NativeException;
import com.myPokeGame.mapper.UserMapper;
import com.myPokeGame.service.socketIoService.SocketIoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    Map<Long, Date> onlineUserMap;

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
            log.info("新用户： "+user.getUserName()+"注册成功");
        }
        return user;
    }

    @Override
    public User login(User user) {
        User loginUser = userMapper.checkNameAndPass(user);
        if(!ObjectUtils.isEmpty(loginUser)){
            if(!ObjectUtils.isEmpty(onlineUserMap.get(loginUser.getUserId()))){
                Date date = onlineUserMap.get(loginUser.getUserId());
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                log.info("用户上次登录时间："+sdf.format(date));
                onlineUserMap.put(loginUser.getUserId(),new Date());
//                throw new NativeException("不能重复登录,上次登录时间："+sdf.format(date));
                return loginUser;
            }else{
                onlineUserMap.put(loginUser.getUserId(),new Date());
                System.out.println(String.format("欢迎用户 %s ",loginUser.getUserName()));
                return loginUser;
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
