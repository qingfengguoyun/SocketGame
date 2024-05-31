package com.myPokeGame.models.pojo;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class QueryHistoryPrivateMessagePojo {

    Long msgId;

    List<Long> userIds=new LinkedList<>();
}
