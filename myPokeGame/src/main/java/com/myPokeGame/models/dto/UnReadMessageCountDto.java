package com.myPokeGame.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnReadMessageCountDto {

    Long id;

    String userName;

    Integer UnReadMessageCount=0;
}
