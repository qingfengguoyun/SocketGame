package com.myPokeGame.relationMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myPokeGame.relationEntity.NFileTagRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface NFileTagRelationMapper extends BaseMapper<NFileTagRelation> {

    public List<Long> queryFileIdsByTagIds(Map<String,Object> params);
}
