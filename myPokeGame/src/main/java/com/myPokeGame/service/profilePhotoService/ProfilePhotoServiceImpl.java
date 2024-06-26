package com.myPokeGame.service.profilePhotoService;

import com.myPokeGame.entity.ProfilePhoto;
import com.myPokeGame.entity.User;
import com.myPokeGame.mapper.ProfilePhotoMapper;
import com.myPokeGame.mapper.UserMapper;
import com.myPokeGame.models.vo.UserVo;
import com.myPokeGame.utils.CommonUtils;
import com.myPokeGame.utils.JwtUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfilePhotoServiceImpl implements ProfilePhotoService {

    @Value("${app-env.profilePhotoStorage}")
    private String profilePhotoStorage;

    @Autowired
    ProfilePhotoMapper profilePhotoMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtils jwtUtils;

    public ProfilePhoto ChangeProfilePhoto(MultipartFile file){
        ProfilePhoto profilePhoto = saveProFilePhoto(file);
        return profilePhoto;
    }

    public ProfilePhoto saveProFilePhoto(MultipartFile file) {
        if(file.isEmpty()){
            return null;
        }
        try {
            ProfilePhoto profilePhoto=new ProfilePhoto();
            // 查询用户id
            UserVo userVo = jwtUtils.validateToken();
            profilePhoto.setUserId(userVo.getUserId());
            //验证MD5是否重复，若重复则不执行保存，仅添加数据库记录
            String md5 = CommonUtils.calcMd5(file.getInputStream());
            List<ProfilePhoto> profilePhotos = profilePhotoMapper.queryProfilePhotoByMd5(md5);
            profilePhoto.setDate(new Date());
            if(ObjectUtils.isEmpty(profilePhotos)){
                //为未上传的图片
                String uuid=CommonUtils.getRandomUuid();
                //头像url
                String url=uuid+"_"+file.getOriginalFilename();
                //原图url
                String orgUrl=uuid+"_origin_"+file.getOriginalFilename();
                //保存原图
                File savedFile=new File(profilePhotoStorage,orgUrl);
                FileOutputStream fileOutputStream = new FileOutputStream(savedFile);
                fileOutputStream.write(file.getBytes());
                fileOutputStream.close();
                //使用Thumbnails压缩图片，生成缩略图作为头像
                File compressFile=new File(profilePhotoStorage,url);
                Thumbnails.of(savedFile).size(128,128).toFile(compressFile);
                profilePhoto.setMd5(md5);
                profilePhoto.setProfilePhotoUrl(url);
                profilePhoto.setProfilePhotoOrgUrl(orgUrl);
                profilePhoto.setProfilePhotoSuffix(CommonUtils.getSuffix(file.getOriginalFilename()));
                //移除profilePhoto表中userId为当前用户的记录
                List<ProfilePhoto> records = profilePhotoMapper.queryProfilePhotoByUserId(userVo.getUserId());
                List<Long> ids = records.stream().map(t -> t.getId()).collect(Collectors.toList());
                if(!ObjectUtils.isEmpty(ids)){
                    profilePhotoMapper.deleteBatchIds(ids);
                }

                //插入新记录
                profilePhotoMapper.insert(profilePhoto);
                //更新user表记录
                User user = userMapper.selectById(userVo.getUserId());
                user.setUserImageId(profilePhoto.getId().toString());
                userMapper.updateById(user);
            }else{
                profilePhoto.setProfilePhotoUrl(profilePhotos.get(0).getProfilePhotoUrl());
                profilePhoto.setProfilePhotoSuffix(profilePhotos.get(0).getProfilePhotoSuffix());
                profilePhoto.setProfilePhotoOrgUrl(profilePhotos.get(0).getProfilePhotoOrgUrl());
                profilePhoto.setMd5(md5);
                //移除profilePhoto表中userId为当前用户的记录
                List<ProfilePhoto> records = profilePhotoMapper.queryProfilePhotoByUserId(userVo.getUserId());
                List<Long> ids = records.stream().map(t -> t.getId()).collect(Collectors.toList());
                profilePhotoMapper.deleteBatchIds(ids);
                //插入新记录
                profilePhotoMapper.insert(profilePhoto);
                //更新user表记录
                User user = userMapper.selectById(userVo.getUserId());
                user.setUserImageId(profilePhoto.getId().toString());
                userMapper.updateById(user);
            }
            return profilePhoto;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
