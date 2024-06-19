package com.myPokeGame.config;

import com.myPokeGame.entity.Tag;

import java.util.LinkedList;
import java.util.List;

public enum TagEnum {

    CARTOON("动漫",true,false),
    PICTURE("图片",true,false),
    ORIGIN_QUALITY("原画",true,false),
    MID_QUALITY("中质量",true,false),
    PROFILE_PHOTO("头像",true,false),
    HIGH_QUALITY("高质量",true,false),
    SCENERY("风景",true,false),
    FILE("文件",true,false),
    TRAVEL("旅行",true,false),
    NSFW("NSFW",true,true),
    ;

    public String tagName;

    public Boolean isBasic;

    public Boolean isLimited;

    TagEnum(String tagName, Boolean isBasic,Boolean isLimited) {
        this.tagName = tagName;
        this.isBasic = isBasic;
        this.isLimited= isLimited;
    }

    public static List<Tag> getTagNames(){
        List<Tag> res=new LinkedList<>();
        for (TagEnum tagEnum:TagEnum.values()){
            Tag tag = Tag.builder().tagName(tagEnum.tagName).isBasic(tagEnum.isBasic).isLimited(tagEnum.isLimited).build();
            res.add(tag);
        }
        return res;
    }
}
