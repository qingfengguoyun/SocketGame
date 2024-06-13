package com.myPokeGame.service.tagService;

import com.myPokeGame.entity.Tag;
import com.myPokeGame.models.pojo.TagQueryPojo;

import java.util.Collection;
import java.util.List;

public interface TagService {

    public Tag insertTag(Tag tag);

    public List<Tag> queryTagsByFileId(Long fileId);

    public List<Long> updateTagsByFileId(Long fileId, Collection<Long> tagIds);

    /**
     * 标签条件查询
     * @param pojo
     * @return
     */
    public List<Tag> queryTagsByConditions(TagQueryPojo pojo);
}
