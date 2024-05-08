package com.myPokeGame.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myPokeGame.entity.Image;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Mapper
@Repository
public interface ImageMapper extends BaseMapper<Image> {

    public List<Image> queryByMd5(String md5);

}
