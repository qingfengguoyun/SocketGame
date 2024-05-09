package com.myPokeGame.cotroller;

import com.myPokeGame.entity.NativeFile;
import com.myPokeGame.mapper.NativeFileMapper;
import com.myPokeGame.service.imageService.NativeFileService;
import com.myPokeGame.utils.CommonUtils;
import com.myPokeGame.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/file")
@Api(tags = "文件接口")
public class FileController {

    @Value("${app-env.fileStorage}")
    private String fileStore;

    @Autowired
    private NativeFileService nativeFileService;

    @Autowired
    private NativeFileMapper nativeFileMapper;

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public Result saveUpLoadFile(MultipartFile file){
        NativeFile nativeFile = nativeFileService.saveFile(file);
        if(!ObjectUtils.isEmpty(nativeFile)){
            return Result.success(nativeFile);
        }
        return Result.fail("文件上传失败");

    }
    @ApiOperation("批量上传文件")
    @PostMapping(value = "/uploadByBatch" ,headers = "content-type=multipart/form-data")
    public Result saveUpLoadFile(MultipartFile[] files){
        List<NativeFile> resList=new LinkedList<>();
        for (MultipartFile file:files){
            NativeFile nativeFile = nativeFileService.saveFile(file);
            resList.add(nativeFile);
        }
        return Result.success(resList);
    }

}
