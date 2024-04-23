package com.myPokeGame.service.messageService;

import com.myPokeGame.entity.Message;
import com.myPokeGame.models.dto.UnReadMessageCountDto;
import com.myPokeGame.models.pojo.MessagePojo;
import com.myPokeGame.models.vo.MessageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageService {

    public Message insertMessage(MessagePojo messagePojo);

    public Message insertMessage(Message message);

    public List<Message> queryLastestMessages(Integer num);

    public Message sendPrivateMessage(MessagePojo pojo);


    /**
     * 私聊界面查询全部记录
     * @param connectUserId 私聊对象Id
     * @param num 显示条数
     * @return
     */
    public List<MessageVo> queryLatestPrivteMessageVos(Long connectUserId,Integer num);

    /**
     * 查询用户未读的私聊消息数量
     * @param userId
     * @return
     */
    public List<UnReadMessageCountDto> queryUnReadMessageCountByUserId( Long userId);
}
