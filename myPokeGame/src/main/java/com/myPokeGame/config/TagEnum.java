package com.myPokeGame.config;

import com.myPokeGame.entity.Tag;

import java.util.LinkedList;
import java.util.List;

public enum TagEnum {

    CARTOON("动漫",true),
    SCENERY("风景",true),
    FILE("文件",true),
    TRAVEL("旅行",true);

    public String tagName;

    public Boolean isBasic;

    TagEnum(String tagName, Boolean isBasic) {
        this.tagName = tagName;
        this.isBasic = isBasic;
    }

    public static List<Tag> getTagNames(){
        List<Tag> res=new LinkedList<>();
        for (TagEnum tagEnum:TagEnum.values()){
            Tag tag = Tag.builder().tagName(tagEnum.tagName).isBasic(tagEnum.isBasic).build();
            res.add(tag);
        }
        return res;
    }
}
