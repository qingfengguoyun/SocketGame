package com.myPokeGame.service.imageService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myPokeGame.entity.NativeFile;
import com.myPokeGame.entity.User;
import com.myPokeGame.mapper.NativeFileMapper;
import com.myPokeGame.mapper.UserMapper;
import com.myPokeGame.models.vo.NativeFileVo;
import com.myPokeGame.models.vo.UserVo;
import com.myPokeGame.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class NativeFileServiceImpl implements NativeFileService {

    @Value("${app-env.fileStorage}")
    private String fileStore;

    @Value("${app-env.filePreviewStorage}")
    private String filePreviewStore;

    @Autowired
    NativeFileMapper nativeFileMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtils jwtUtils;


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
            NativeFile nativeFileInfo =new NativeFile();
            // 依据Authority添加用户id
            UserVo userVo = jwtUtils.validateToken();
            nativeFileInfo.setUploaderId(userVo.getUserId());
            //验证MD5是否重复，若重复则不执行保存，仅添加数据库记录
            String md5 = CommonUtils.calcMd5(file.getInputStream());
            List<NativeFile> nativeFiles = nativeFileMapper.queryByMd5(md5);
            nativeFileInfo.setDate(new Date());
            if(ObjectUtils.isEmpty(nativeFiles)){
                nativeFileInfo.setFileName(file.getOriginalFilename());
                String url=CommonUtils.getRandomUuid()+"_"+file.getOriginalFilename();
                File savedFile=new File(fileStore,url);
                FileOutputStream fileOutputStream = new FileOutputStream(savedFile);
                fileOutputStream.write(file.getBytes());
                fileOutputStream.close();
                //如果是图片则生成预览图
                if(ImageUtils.isImage(url)){
                    String previewImageUrl = ImageUtils.getCompressImage(savedFile,filePreviewStore);
//                            ImageUtils.getPreviewImage(savedFile, filePreviewStore, 198);
                    log.info("previewImageUrl:"+previewImageUrl);
                    nativeFileInfo.setFilePreviewUrl(previewImageUrl);
                }
                nativeFileInfo.setMd5(md5);
                nativeFileInfo.setFileUrl(url);
                nativeFileInfo.setFileSuffix(CommonUtils.getSuffix(file.getOriginalFilename()));
                saveFile(nativeFileInfo);
            }else{
                nativeFileInfo.setFileName(file.getOriginalFilename());
                nativeFileInfo.setFileUrl(nativeFiles.get(0).getFileUrl());
                nativeFileInfo.setFilePreviewUrl(nativeFiles.get(0).getFilePreviewUrl());
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

    @Override
    public NativePage<NativeFileVo> queryFilesByPage(Integer currentPage,Integer pageSize){
        IPage<NativeFile> page=new Page<>(currentPage,pageSize);
        nativeFileMapper.queryAll(page);
        List<NativeFile> records = page.getRecords();
        List<NativeFileVo> resList=new LinkedList<>();
        Map<Long, User> userMap=new HashMap<>();
        for(NativeFile file:records){
            NativeFileVo vo=new NativeFileVo();
            if(!ObjectUtils.isEmpty(file.getUploaderId())){
                if(!ObjectUtils.isEmpty(userMap.get(file.getUploaderId()))){
                    User user = userMap.get(file.getUploaderId());
                    vo = NativeFileVo.convert(file, user);

                }else{
                    User user = userMapper.selectById(file.getUploaderId());
                    userMap.put(user.getId(),user);
                    vo = NativeFileVo.convert(file, user);
                }
            }else{
                vo=NativeFileVo.convert(file);
            }
            resList.add(vo);
        }
        NativePage<NativeFileVo> nativePage=NativePage.convertPageInfo(page);
        nativePage.setData(resList);
        return nativePage;
    }

}
