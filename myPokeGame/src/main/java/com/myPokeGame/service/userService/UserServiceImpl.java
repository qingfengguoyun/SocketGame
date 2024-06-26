package com.myPokeGame.service.userService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.myPokeGame.entity.User;
import com.myPokeGame.exceptions.NativeException;
import com.myPokeGame.mapper.UserMapper;
import com.myPokeGame.models.vo.UserVo;
import com.myPokeGame.service.socketIoService.SocketIoService;
import com.myPokeGame.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    Map<Long, Date> onlineUserMap;

    @Autowired
    SocketIoService socketIoService;

    @Autowired
    JwtUtils jwtUtils;

    @PostConstruct
    public void InitAdmin(){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,"admin");
        User user = userMapper.selectOne(wrapper);
        if(ObjectUtils.isEmpty(user)){
            log.info("初始化管理员角色");
            User admin = User.builder().userName("admin").password("123").date(new Date()).build();
            userMapper.insert(admin);
            log.info("管理员初始化完成");
        }else{
            log.info("已存在管理员角色");
        }
    }


    @Override
    @Transactional
    public User saveUser(User user) {
        if(user.getId()==null){
            userMapper.insert(user);
            return user;
        }else{
            User u = userMapper.selectById(user.getId());
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
    @Transactional
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
    @Transactional
    public User login(User user, HttpServletRequest request, HttpServletResponse response) {
        User loginUser = userMapper.checkNameAndPass(user);
        if(!ObjectUtils.isEmpty(loginUser)){
            if(!ObjectUtils.isEmpty(onlineUserMap.get(loginUser.getId()))){
                Date date = onlineUserMap.get(loginUser.getId());
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                log.info("用户上次登录时间："+sdf.format(date));
                onlineUserMap.put(loginUser.getId(),new Date());
//                throw new NativeException("不能重复登录,上次登录时间："+sdf.format(date));
                String token = JwtUtils.createToken(loginUser.getId(), loginUser.getUserName());
                response.setHeader("Authorization",token);
                return loginUser;
            }else{
                onlineUserMap.put(loginUser.getId(),new Date());
                System.out.println(String.format("欢迎用户 %s ",loginUser.getUserName()));
                String token = JwtUtils.createToken(loginUser.getId(), loginUser.getUserName());
                response.setHeader("Authorization",token);
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

    @Override
    public List<User> queryAllUsers() {
        List<User> users = userMapper.selectList(null);
        return users;
//        List<UserVo> resList=new LinkedList<>();
//        Set<Long> longs = onlineUserMap.keySet();
//        users.stream().forEach(t->{
//            UserVo vo = UserVo.builder().userId(t.getUserId()).userName(t.getUserName()).build();
//            if(longs.contains(t.getUserId()));
//            vo.setIsOnline(true);
//            resList.add(vo);
//        });
//        return resList;
    }

    @Override
    public List<User> queryAllUsersButAdmin() {

        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.ne(User::getUserName,"admin");
        List<User> users = userMapper.selectList(wrapper);
        return users;
    }

    @Override
    public User queryUserById(Long userId) {
        User user = userMapper.selectById(userId);
        return user;
    }

    @Override
    public User updateUser(User user) {
        UpdateWrapper<User> wapper=new UpdateWrapper<User>();
        wapper.eq("id",user.getId());
        wapper.set("user_name",user.getUserName())
                .set("password",user.getPassword())
                .set("user_image_id",user.getUserImageId())
                .set("user_default_image",user.getUserDefaultImage());
        int i = userMapper.update(user, wapper);
//        int i = userMapper.updateById(user);
        if(i!=0){
            return userMapper.selectById(user.getId());
        }
        return user;
    }
}
