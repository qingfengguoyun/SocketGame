package com.myPokeGame.cotroller;

import com.myPokeGame.entity.ProfilePhoto;
import com.myPokeGame.entity.User;
import com.myPokeGame.models.vo.UserVo;
import com.myPokeGame.service.messageService.MessageService;
import com.myPokeGame.service.profilePhotoService.ProfilePhotoService;
import com.myPokeGame.service.userService.UserService;
import com.myPokeGame.utils.ConvertUtils;
import com.myPokeGame.utils.JwtUtils;
import com.myPokeGame.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
@Api(tags ="用户接口")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ProfilePhotoService profilePhotoService;

    @Autowired
    Map<Long, Date> onlineUserMap;

    @Autowired
    ConvertUtils convertUtils;

    @Autowired
    JwtUtils jwtUtils;

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
    public Result userLogin(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
        User user1 = userService.login(user,  request,  response);
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
            UserVo vo = new UserVo();
            convertUtils.convert(vo,t);
            if(longs.contains(t.getId())){
                vo.setIsOnline(true);
                vo.setLastOnLineTime(onlineUserMap.get(t.getId()));
            };
            resList.add(vo);
        });
        return Result.success(resList);
    }

    @ApiOperation(value = "更新用户信息")
    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody User user){
        User updatedUser = userService.updateUser(user);
        return Result.success(updatedUser);
    }

    @ApiOperation(value = "上传用户自定义头像")
    @PostMapping(value="/uploadUserProfilePhoto" ,headers = "content-type=multipart/form-data")
    public Result uploadUserProfilePhoto(@RequestParam("file") MultipartFile multipartFile){
        ProfilePhoto profilePhoto = profilePhotoService.ChangeProfilePhoto(multipartFile);
        return Result.success(profilePhoto);
    }

    @ApiOperation(value = "通过Id查询用户个人信息")
    @PostMapping("/getUserInfoById")
    public Result getUserInfoById(@RequestBody Long userId){
        User user = userService.queryUserById(userId);
        UserVo vo=new UserVo();
        convertUtils.convert(vo,user);
        return Result.success(vo);
    }
}
