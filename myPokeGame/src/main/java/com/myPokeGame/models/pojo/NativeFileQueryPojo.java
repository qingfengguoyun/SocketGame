package com.myPokeGame.models.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 文件条件查询参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NativeFileQueryPojo {

    List<Long> tagIds;

}
