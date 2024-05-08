package com.myPokeGame.service.imageService;

import com.myPokeGame.entity.Image;
import com.myPokeGame.mapper.ImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageMapper imageMapper;


    @Override
    public Image saveImage(Image imageInfo) {
        int res = imageMapper.insert(imageInfo);
        return imageInfo;
    }
}
