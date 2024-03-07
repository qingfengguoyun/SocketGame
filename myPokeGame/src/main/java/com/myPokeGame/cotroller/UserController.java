package com.myPokeGame.cotroller;

import com.myPokeGame.entity.User;
import com.myPokeGame.service.UserService;
import com.myPokeGame.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
@Api(tags ="用户接口")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "保存用户")
    @PostMapping("/save")
    public Result saveUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        log.info(user1.toString());
        return Result.success(user1);
    }

    @ApiOperation(value = "保存用户")
    @PostMapping("/save")
    public Result signUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        log.info(user1.toString());
        return Result.success(user1);
    }

    @ApiOperation(value = "保存用户")
    @PostMapping("/save")
    public Result userLogin(@RequestBody User user){
        User user1 = userService.saveUser(user);
        log.info(user1.toString());
        return Result.success(user1);
    }

    @ApiOperation(value = "查询所有用户名")
    @PostMapping("/queryAllName")
    public Result queryAllName(){
        List<String> names = userService.queryAllUserName();
        return Result.success(names);
    }
}
