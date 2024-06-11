package com.myPokeGame.service.tagService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myPokeGame.config.TagEnum;
import com.myPokeGame.entity.Tag;
import com.myPokeGame.mapper.NativeFileMapper;
import com.myPokeGame.mapper.TagMapper;
import com.myPokeGame.relationEntity.NFileTagRelation;
import com.myPokeGame.relationMapper.NFileTagRelationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.awt.dnd.DragSource;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Autowired
    NativeFileMapper nativeFileMapper;

    @Autowired
    NFileTagRelationMapper nFileTagRelationMapper;

    // 初始化/更新 t_tag 表
    @PostConstruct
    public void initBasicTag(){
        List<Tag> res = tagMapper.selectAll();
        List<String> existTagNames = res.stream().map(t -> t.getTagName()).collect(Collectors.toList());
        List<Tag> tags = TagEnum.getTagNames();
        List<Tag> newTagNames = tags.stream().filter(t -> {
            return !existTagNames.contains(t.getTagName());
        }).collect(Collectors.toList());
        if(ObjectUtils.isEmpty(newTagNames)){
            log.info("初始化/更新标签表");
            newTagNames.stream().forEach(t->{
                tagMapper.insert(t);
            });
            log.info("tag表初始化/更新完成");
        }
    }

    @Override
    public Tag insertTag(Tag tag) {
        tagMapper.insert(tag);
        return tag;
    }


    /**
     * 通过文件id查找标签
     * @param fileId
     * @return
     */
    @Override
    public List<Tag> queryTagsByFileId(Long fileId) {

//        nFileTagRelationMapper.s
        LambdaQueryWrapper<NFileTagRelation> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(NFileTagRelation::getNativeFileId,fileId);
        List<NFileTagRelation> nFileTagRelations = nFileTagRelationMapper.selectList(wrapper);
        Set<Long> tagIds = nFileTagRelations.stream().map(t -> {
            return t.getTagId();
        }).collect(Collectors.toSet());
        List<Tag> tags = new LinkedList<>();
        if(!ObjectUtils.isEmpty(tagIds)){
            tags=tagMapper.selectBatchIds(tagIds);
        }

        return tags;
    }

    /**
     * 更新文件-标签关系表
     * @param fileId
     * @param tagIds
     * @return
     */
    @Override
    public List<Long> updateTagsByFileId(Long fileId, Collection<Long> tagIds) {
        LambdaQueryWrapper<NFileTagRelation> deleteWrapper=new LambdaQueryWrapper<>();
        deleteWrapper.eq(NFileTagRelation::getNativeFileId,fileId);
        int delete = nFileTagRelationMapper.delete(deleteWrapper);
        tagIds.stream().forEach(t->{
            NFileTagRelation nFileTagRelation = NFileTagRelation.builder().NativeFileId(fileId).TagId(t).build();
            nFileTagRelationMapper.insert(nFileTagRelation);
        });

        return tagIds.stream().collect(Collectors.toList());
    }
}
