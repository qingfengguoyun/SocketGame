package com.myPokeGame.service.imageService;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.myPokeGame.entity.NativeFile;
import com.myPokeGame.mapper.NativeFileMapper;
import com.myPokeGame.utils.CommonUtils;
import com.myPokeGame.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class NativeFileServiceImpl implements NativeFileService {

    @Value("${app-env.fileStorage}")
    private String fileStore;

    @Autowired
    NativeFileMapper nativeFileMapper;


    @Override
    public NativeFile saveFile(NativeFile nativeFileInfo) {
        int res = nativeFileMapper.insert(nativeFileInfo);
        return nativeFileInfo;
    }

    @Override
    public NativeFile saveFile(MultipartFile file) {
        if(file.isEmpty()){
            return null;
        }
        try {
            //验证MD5是否重复，若重复则不执行保存，仅添加数据库记录
            String md5 = CommonUtils.calcMd5(file.getInputStream());
            NativeFile nativeFileInfo =new NativeFile();
            List<NativeFile> nativeFiles = nativeFileMapper.queryByMd5(md5);
            if(ObjectUtils.isEmpty(nativeFiles)){
                nativeFileInfo.setFileName(file.getOriginalFilename());
                String url=CommonUtils.getRandomUuid()+"_"+file.getOriginalFilename();
                File savedFile=new File(fileStore,url);
                FileOutputStream fileOutputStream = new FileOutputStream(savedFile);
                fileOutputStream.write(file.getBytes());
                fileOutputStream.close();
                nativeFileInfo.setMd5(md5);
                nativeFileInfo.setFileUrl(url);
                nativeFileInfo.setFileSuffix(CommonUtils.getSuffix(file.getOriginalFilename()));
                saveFile(nativeFileInfo);
            }else{
                nativeFileInfo.setFileName(file.getOriginalFilename());
                nativeFileInfo.setFileUrl(nativeFiles.get(0).getFileUrl());
                nativeFileInfo.setMd5(md5);
                nativeFileInfo.setFileSuffix(CommonUtils.getSuffix(file.getOriginalFilename()));
                saveFile(nativeFileInfo);
            }
            return nativeFileInfo;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
