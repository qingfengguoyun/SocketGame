package com.myPokeGame.controller;

import com.myPokeGame.entity.Tag;
import com.myPokeGame.models.pojo.TagQueryPojo;
import com.myPokeGame.service.tagService.TagService;
import com.myPokeGame.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
@Api(tags = "标签接口")
public class TagController {

    @Autowired
    TagService tagService;


    @ApiOperation("条件查询所有标签")
    @PostMapping("/queryAllTagsByConditions")
    public Result queryAllTagsByConditions(@RequestBody(required = false) TagQueryPojo pojo){
        List<Tag> tags = tagService.queryTagsByConditions(pojo);
        return Result.success(tags);
    }
}
