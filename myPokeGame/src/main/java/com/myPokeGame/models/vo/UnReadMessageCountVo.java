package com.myPokeGame.models.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnReadMessageCountVo {

    Long id;

    String userName;

    Integer UnReadMessageCount = 0;

}
