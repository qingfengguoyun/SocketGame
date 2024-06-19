package com.myPokeGame.service.fileService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.myPokeGame.entity.NativeFileSource;
import com.myPokeGame.mapper.NativeFileSourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NativeFileSourceServiceImpl implements NativeFileSourceService {

    @Autowired
    NativeFileSourceMapper nativeFileSourceMapper;
    @Override
    public List<NativeFileSource> queryByMd5(String md5) {

        LambdaQueryWrapper<NativeFileSource> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(NativeFileSource::getMd5,md5);
        List<NativeFileSource> nativeFileSources = nativeFileSourceMapper.selectList(wrapper);
        return nativeFileSources;
    }
}
