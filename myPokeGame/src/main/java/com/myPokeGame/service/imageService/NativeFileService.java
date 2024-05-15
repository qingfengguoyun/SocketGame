package com.myPokeGame.service.imageService;

import com.myPokeGame.entity.NativeFile;
import com.myPokeGame.models.vo.NativeFileVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NativeFileService {

    public NativeFile saveFile(NativeFile nativeFileInfo);

    public NativeFile saveFile(MultipartFile file);

    public List<NativeFileVo> queryFilesByPage(Integer currentPage, Integer pageSize);
}
