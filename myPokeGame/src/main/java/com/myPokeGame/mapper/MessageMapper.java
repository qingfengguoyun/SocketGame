package com.myPokeGame.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myPokeGame.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageMapper extends BaseMapper<Message> {

    public List<Message> queryLatestMessagesByDate(Integer num);

    public List<Message> queryPrivateMessagesByUnReadMessageId(@Param("sendUserId") Long sendUserId,
                                                               @Param("receiveUserId") Long receiveUserId,
                                                               @Param("messageId") Long messageId,
                                                               @Param("num")Integer num);

    public List<Message> queryPrivateMessages(@Param("sendUserId") Long sendUserId,
                                              @Param("receiveUserId") Long receiveUserId,
                                              @Param("num") Integer num);

}
