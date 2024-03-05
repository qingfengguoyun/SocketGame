package com.project_02_28.controller;

import com.project_02_28.utils.DocumentExtraction;
import com.project_02_28.utils.Result;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/api/testController")
public class testController {

    @GetMapping("/test")
    public Result test(){
        return Result.success("test");
    }

    @GetMapping("/readFile")
    public Result readFile(){
        try {
            ClassPathResource resource = new ClassPathResource("files/txtFile1.txt");
            File file = resource.getFile();
            String out = DocumentExtraction.txt2String(file);
            System.out.println(out);
            return Result.success(out);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(501,e.getMessage());
        }
    }
}
