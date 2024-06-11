package com.myPokeGame.service.nFileTagRelationService;

import java.util.Collection;
import java.util.List;

public interface NFileTagRelationService {


    public List<Long> queryFileIdsByTagIds(Collection<Long> tagIds);
}
