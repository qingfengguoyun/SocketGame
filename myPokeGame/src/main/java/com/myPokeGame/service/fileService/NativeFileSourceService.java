package com.myPokeGame.service.fileService;

import com.myPokeGame.entity.NativeFileSource;

import java.util.List;

public interface NativeFileSourceService {

    public List<NativeFileSource> queryByMd5(String md5);
}
