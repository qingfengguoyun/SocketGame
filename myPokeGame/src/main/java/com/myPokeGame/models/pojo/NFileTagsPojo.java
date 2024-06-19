package com.myPokeGame.models.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NFileTagsPojo {

    Long fileId;

    List<Long> fileIds;

    List<Long> tagIds;
}
