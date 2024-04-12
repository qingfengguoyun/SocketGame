package com.myPokeGame.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myPokeGame.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageMapper extends BaseMapper<Message> {

    public List<Message> queryLatestMessagesByDate(Integer num);


}
