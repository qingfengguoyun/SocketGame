package com.myPokeGame.models.pojo;

import lombok.Data;

/**
 * Tag条件查询参数
 */
@Data
public class TagQueryPojo {

    Boolean isBasic=true;

    String tagName;
}
