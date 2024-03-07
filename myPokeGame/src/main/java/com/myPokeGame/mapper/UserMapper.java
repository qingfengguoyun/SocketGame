package com.myPokeGame.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myPokeGame.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

//    public Integer addUser(User user);

    public List<String> queryAllUserName();

    public User checkNameAndPass(User user);

}
