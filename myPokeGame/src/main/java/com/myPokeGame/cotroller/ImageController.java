package com.myPokeGame.cotroller;

import ch.qos.logback.classic.spi.TurboFilterList;
import com.myPokeGame.entity.Image;
import com.myPokeGame.mapper.ImageMapper;
import com.myPokeGame.service.imageService.ImageService;
import com.myPokeGame.utils.CommonUtils;
import com.myPokeGame.utils.Result;
import io.swagger.annotations.Api;
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
import java.util.List;

@RestController
@RequestMapping("/api/image")
@Api(tags = "图片接口")
public class ImageController {

    @Value("${app-env.imageStorage}")
    private String imageStore;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageMapper imageMapper;

    @ApiOperation("上传图片")
    @PostMapping("/upload")
    public Result saveUpLoadImage(MultipartFile file){
        if(file.isEmpty()){
            return Result.success("上传图片为空");
        }
        try {
            //验证MD5是否重复，若重复则不执行保存，仅添加数据库记录
            String md5 = CommonUtils.calcMd5(file.getInputStream());
            Image imageInfo=new Image();
            List<Image> images = imageMapper.queryByMd5(md5);
            if(ObjectUtils.isEmpty(images)){
                imageInfo.setImageName(file.getOriginalFilename());
                String url=CommonUtils.getRandomUuid()+"_"+file.getOriginalFilename();
                File savedFile=new File(imageStore,url);
                FileOutputStream fileOutputStream = new FileOutputStream(savedFile);
                fileOutputStream.write(file.getBytes());
                fileOutputStream.close();
                imageInfo.setMd5(md5);
                imageInfo.setImageUrl(url);
                imageInfo.setImageSuffix(CommonUtils.getSuffix(file.getOriginalFilename()));
                imageService.saveImage(imageInfo);
            }else{
                imageInfo.setImageName(file.getOriginalFilename());
                imageInfo.setImageUrl(images.get(0).getImageUrl());
                imageInfo.setMd5(md5);
                imageInfo.setImageSuffix(CommonUtils.getSuffix(file.getOriginalFilename()));
                imageService.saveImage(imageInfo);
            }
            return Result.success(imageInfo);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
