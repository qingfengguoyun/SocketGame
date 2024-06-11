package com.myPokeGame.service.tagService;

import com.myPokeGame.entity.Tag;

import java.util.Collection;
import java.util.List;

public interface TagService {

    public Tag insertTag(Tag tag);

    public List<Tag> queryTagsByFileId(Long fileId);

    public List<Long> updateTagsByFileId(Long fileId, Collection<Long> tagIds);
}
