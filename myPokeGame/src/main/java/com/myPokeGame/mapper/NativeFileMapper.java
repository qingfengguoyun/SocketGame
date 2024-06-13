package com.myPokeGame.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myPokeGame.entity.NativeFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface NativeFileMapper extends BaseMapper<NativeFile> {

    public List<NativeFile> queryByMd5(String md5);

    public IPage<NativeFile> queryAll(IPage<NativeFile> page);

    public IPage<NativeFile> queryAllByConditions(@Param("params") Map<String,Object> param, IPage<NativeFile> page);

}
