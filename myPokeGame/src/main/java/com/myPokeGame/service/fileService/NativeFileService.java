package com.myPokeGame.service.fileService;

import com.myPokeGame.entity.NativeFile;
import com.myPokeGame.models.vo.NativeFileVo;
import com.myPokeGame.utils.NativePage;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface NativeFileService {

    public NativeFile saveFile(NativeFile nativeFileInfo);

    public NativeFile saveFile(MultipartFile file);

    public NativePage<NativeFileVo> queryFilesByPage(Integer currentPage, Integer pageSize);

    public void downLoadFile(Long fileId, HttpServletResponse response);
}
