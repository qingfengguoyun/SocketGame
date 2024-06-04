package com.myPokeGame.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myPokeGame.entity.ProfilePhoto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProfilePhotoMapper extends BaseMapper<ProfilePhoto> {

    public List<ProfilePhoto> queryProfilePhotoByUserId(Long userId);

    public List<ProfilePhoto> queryProfilePhotoByMd5(String md5);
}
