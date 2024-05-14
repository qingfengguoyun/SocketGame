package com.myPokeGame.service.imageService;

import com.myPokeGame.entity.NativeFile;
import org.springframework.web.multipart.MultipartFile;

public interface NativeFileService {

    public NativeFile saveFile(NativeFile nativeFileInfo);

    public NativeFile saveFile(MultipartFile file);
}
