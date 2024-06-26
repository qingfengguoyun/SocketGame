package com.myPokeGame.service.fileService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myPokeGame.entity.NativeFile;
import com.myPokeGame.entity.NativeFileSource;
import com.myPokeGame.entity.Tag;
import com.myPokeGame.entity.User;
import com.myPokeGame.mapper.NativeFileMapper;
import com.myPokeGame.mapper.NativeFileSourceMapper;
import com.myPokeGame.mapper.TagMapper;
import com.myPokeGame.mapper.UserMapper;
import com.myPokeGame.models.pojo.NativeFileQueryPojo;
import com.myPokeGame.models.vo.NativeFileVo;
import com.myPokeGame.models.vo.UserVo;
import com.myPokeGame.relationEntity.NFileTagRelation;
import com.myPokeGame.relationMapper.NFileTagRelationMapper;
import com.myPokeGame.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
    NativeFileSourceService nativeFileSourceService;

    @Autowired
    NativeFileSourceMapper nativeFileSourceMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TagMapper tagMapper;


    @Autowired
    ConvertUtils convertUtils;

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
            List<NativeFileSource> nativeFileSources = nativeFileSourceService.queryByMd5(md5);
            List<NativeFile> nativeFiles = nativeFileMapper.queryByMd5(md5);
            nativeFileInfo.setDate(new Date());
            if(ObjectUtils.isEmpty(nativeFileSources)){

                nativeFileInfo.setFileName(CommonUtils.getFileNameWithoutSuffix(file.getOriginalFilename()));
                String url=CommonUtils.getRandomUuid()+"_"+file.getOriginalFilename();
                File savedFile=new File(fileStore,url);
                FileOutputStream fileOutputStream = new FileOutputStream(savedFile);
                fileOutputStream.write(file.getBytes());
                fileOutputStream.close();

                //生成NativeFileSource记录
                NativeFileSource source=new NativeFileSource();
                source.setMd5(md5);
                source.setFileUrl(url);
                //如果是图片则生成预览图
                if(ImageUtils.isImage(url)){
                    String previewImageUrl = ImageUtils.getCompressImage(savedFile,filePreviewStore);
//                            ImageUtils.getPreviewImage(savedFile, filePreviewStore, 198);
                    log.info("previewImageUrl:"+previewImageUrl);
                    nativeFileInfo.setFilePreviewUrl(previewImageUrl);
                    source.setFilePreviewUrl(previewImageUrl);
                }
                //在t_native_file_source表中添加记录
                nativeFileSourceMapper.insert(source);
                nativeFileInfo.setMd5(md5);
                nativeFileInfo.setFileUrl(url);
                nativeFileInfo.setFileSuffix(CommonUtils.getSuffix(file.getOriginalFilename()));
                saveFile(nativeFileInfo);
            }else{
                nativeFileInfo.setFileName(CommonUtils.getFileNameWithoutSuffix(file.getOriginalFilename()));
                nativeFileInfo.setFileUrl(nativeFileSources.get(0).getFileUrl());
                nativeFileInfo.setFilePreviewUrl(nativeFileSources.get(0).getFilePreviewUrl());
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
    public NativePage<NativeFileVo> queryFilesByPage(Integer currentPage, Integer pageSize, NativeFileQueryPojo pojo){
        IPage<NativeFile> page=new Page<>(currentPage,pageSize);
        //TODO: 添加条件查询文件的方法
        Map<String,Object> param=new HashMap<>();
        if (!ObjectUtils.isEmpty(pojo)){
            param.put("tagIds",ObjectUtils.isEmpty(pojo.getTagIds())?new LinkedList<Long>():pojo.getTagIds());
            param.put("fileName",ObjectUtils.isEmpty(pojo.getFileName())?"":pojo.getFileName());
            if(!ObjectUtils.isEmpty(pojo.getTagIds())){
                param.put("count",pojo.getTagIds().size());
            }
        }
        nativeFileMapper.queryAllByConditions(param,page);
//        nativeFileMapper.queryAll(page);
        List<NativeFile> records = page.getRecords();
        List<NativeFileVo> resList=new LinkedList<>();
        Map<Long, User> userMap=new HashMap<>();
        for(NativeFile file:records){
            NativeFileVo vo=new NativeFileVo();
            if(!ObjectUtils.isEmpty(file.getUploaderId())){
                if(!ObjectUtils.isEmpty(userMap.get(file.getUploaderId()))){
                    User user = userMap.get(file.getUploaderId());
                    vo = convertUtils.convert(file,user);
//                    vo = NativeFileVo.convert(file, user);

                }else{
                    User user = userMapper.selectById(file.getUploaderId());
                    userMap.put(user.getId(),user);
                    vo = convertUtils.convert(file,user);
//                    vo = NativeFileVo.convert(file, user);
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

    @Override
    public void downLoadFile(Long fileId, HttpServletResponse response){
        NativeFile file = nativeFileMapper.selectById(fileId);
//        ClassPathResource resource = new ClassPathResource(fileStore+file.getFileUrl());
        File resourceFile=new File(fileStore+file.getFileUrl());
        //设置响应头
        response.setContentType(CommonUtils.getContentType(file.getFileSuffix()));
        try {
            //响应首部 Access-Control-Expose-Headers 为控制暴露的开关，列出哪些首部可以作为响应的一部分暴露给外部
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getFileName()+
                    (ObjectUtils.isEmpty(file.getFileSuffix())?"":"."+file.getFileSuffix()),"UTF-8"));
            InputStream inputStream = new FileInputStream(resourceFile);
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] buffer=new byte[1024];
            int len;
            while((len=inputStream.read(buffer))!=-1){
                outputStream.write(buffer,0,len);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
