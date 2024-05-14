package com.imgServer.controller;

import com.imgServer.exceptions.NativeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

@RequestMapping("/api/img")
@RestController
@Slf4j
public class ImgController {

    @Autowired
    HttpServletResponse response;

    @GetMapping("/getImageDemo/{imgUrl}")
    public void getImageDemo(@PathVariable String imgUrl){
        try {
            BufferedImage image = ImageIO.read(new ClassPathResource("public/" + imgUrl).getInputStream());
            response.setContentType("image/jpg");
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"jpg",outputStream);
        } catch (Exception e) {
           if( e instanceof FileNotFoundException){
               throw new NativeException("图片未找到");
           }
        }
    }

    @GetMapping("/test/{i}")
    public String test(@PathVariable Integer i){
        return ""+i;
    }
}
