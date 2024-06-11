package com.myPokeGame.service.nFileTagRelationService;

import com.myPokeGame.relationMapper.NFileTagRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NFileTagRelationServiceImpl implements NFileTagRelationService {


    @Autowired
    NFileTagRelationMapper nFileTagRelationMapper;

    /**
     * 通过标签id筛选文件id
     * @param tagIds
     * @return
     */
    @Override
    public List<Long> queryFileIdsByTagIds(Collection<Long> tagIds) {
        Map<String,Object> params=new HashMap<>();
        params.put("tagIds",tagIds);
        params.put("count",tagIds.size());
        List<Long> fileIds = nFileTagRelationMapper.queryFileIdsByTagIds(params);
        return fileIds;
    }
}
