package com.myPokeGame.cotroller;

import com.myPokeGame.entity.User;
import com.myPokeGame.models.vo.UserVo;
import com.myPokeGame.service.userService.UserService;
import com.myPokeGame.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
@Api(tags ="用户接口")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    Map<Long, Date> onlineUserMap;

    @ApiOperation(value = "保存用户")
    @PostMapping("/save")
    public Result saveUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        log.info(user1.toString());
        return Result.success(user1);
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/sign")
    public Result signUser(@RequestBody User user){
        User user1 = userService.signUser(user);
        log.info(user1.toString());
        return Result.success(user1);
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result userLogin(@RequestBody User user){
        User user1 = userService.login(user);
        log.info(user1.toString());
        return Result.success(user1);
    }

    @ApiOperation(value = "查询所有用户名")
    @PostMapping("/queryAllName")
    public Result queryAllName(){
        List<String> names = userService.queryAllUserName();
        return Result.success(names);
    }

    @ApiOperation(value = "查询所有用户和登录状态")
    @PostMapping("/queryAllUserStatus")
    public Result queryAllUserStatus(){
//        List<String> names = userService.queryAllUserName();
        List<User> users = userService.queryAllUsers();
        List<UserVo> resList=new LinkedList<>();
        Set<Long> longs = onlineUserMap.keySet();
        users.stream().forEach(t->{
            UserVo vo = UserVo.builder().userId(t.getUserId()).userName(t.getUserName()).build();
            if(longs.contains(t.getUserId())){
                vo.setIsOnline(true);
            };

            resList.add(vo);
        });
        return Result.success(resList);
    }
}
