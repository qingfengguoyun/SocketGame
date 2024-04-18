package com.myPokeGame.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myPokeGame.entity.UnReadMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UnReadMessageMapper extends BaseMapper<UnReadMessage> {

    UnReadMessage queryBySendUserIdAndReceiveUserId(@Param("sendUserId") Long sendUserId ,
                                                          @Param("receiveUserId")Long receiveUserId);
}