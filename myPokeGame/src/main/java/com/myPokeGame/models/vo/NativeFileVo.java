package com.myPokeGame.models.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.myPokeGame.entity.NativeFile;
import com.myPokeGame.entity.Tag;
import com.myPokeGame.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class NativeFileVo {

    Long fileId;

    String fileName;

    UserVo userVo;

    String fileSuffix;

    String fileUrl;

    String filePreviewUrl;

    String fileType;

    //文件对应的标签
    List<Tag> tags;

    Date date;

    public static NativeFileVo convert(NativeFile nativeFile, User user){
        NativeFileVo vo = new NativeFileVo();
        UserVo userVo = new UserVo();
        userVo.setUserId(user.getId());
        userVo.setUserName(user.getUserName());
        vo.setUserVo(userVo);
        vo.setFileId(nativeFile.getId());
        vo.setFileName(nativeFile.getFileName());
        vo.setFileSuffix(nativeFile.getFileSuffix());
        vo.setFileType(nativeFile.getFileType());
        vo.setFileUrl(nativeFile.getFileUrl());
        vo.setFilePreviewUrl(nativeFile.getFilePreviewUrl());
        vo.setDate(nativeFile.getDate());
        return vo;
    }

    public static NativeFileVo convert(NativeFile nativeFile){
        NativeFileVo vo = new NativeFileVo();
//        UserVo userVo = new UserVo();
//        userVo.setUserId(user.getId());
//        userVo.setUserName(user.getUserName());
//        vo.setUserVo(userVo);
        vo.setFileId(nativeFile.getId());
        vo.setFileName(nativeFile.getFileName());
        vo.setFileSuffix(nativeFile.getFileSuffix());
        vo.setFileType(nativeFile.getFileType());
        vo.setFileUrl(nativeFile.getFileUrl());
        vo.setFilePreviewUrl(nativeFile.getFilePreviewUrl());
        vo.setDate(nativeFile.getDate());
        return vo;
    }
}
