package com.myPokeGame.service.messageService;

import com.myPokeGame.entity.Message;
import com.myPokeGame.models.pojo.MessagePojo;

import java.util.List;

public interface MessageService {

    public Message insertMessage(MessagePojo messagePojo);

    public Message insertMessage(Message message);

    public List<Message> queryLastestMessages(Integer num);
}
