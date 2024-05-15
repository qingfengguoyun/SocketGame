package com.myPokeGame.cotroller;

import com.myPokeGame.entity.NativeFile;
import com.myPokeGame.exceptions.NativeException;
import com.myPokeGame.mapper.NativeFileMapper;
import com.myPokeGame.models.vo.NativeFileVo;
import com.myPokeGame.service.imageService.NativeFileService;
import com.myPokeGame.utils.CommonUtils;
import com.myPokeGame.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/file")
@Api(tags = "文件接口")
public class FileController {

    @Value("${app-env.fileStorage}")
    private String fileStore;

    @Value("${app-env.defaultPage}")
    private Integer defaultPage;

    @Value("${app-env.defaultPageSize}")
    private Integer defaultPageSize;

    @Autowired
    private NativeFileService nativeFileService;

    @Autowired
    private NativeFileMapper nativeFileMapper;

    @Autowired
    HttpServletResponse response;

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public Result saveUpLoadFile(MultipartFile file){
        NativeFile nativeFile = nativeFileService.saveFile(file);
        if(!ObjectUtils.isEmpty(nativeFile)){
            return Result.success(nativeFile);
        }
        return Result.fail("文件上传失败");

    }
    @ApiOperation("批量上传文件")
    @PostMapping(value = "/uploadByBatch" ,headers = "content-type=multipart/form-data")
    public Result saveUpLoadFile(@RequestParam("file") MultipartFile[] files){
        List<NativeFile> resList=new LinkedList<>();
        for (MultipartFile file:files){
            NativeFile nativeFile = nativeFileService.saveFile(file);
            resList.add(nativeFile);
        }
        return Result.success(resList);
    }

    @ApiOperation("根据ID获取图片")
    @GetMapping("/getImage/{imageId}")
    public void getImage(@PathVariable("imageId") Long imageId){
        try {
            NativeFile nativeFile = nativeFileMapper.selectById(imageId);
            if(!ObjectUtils.isEmpty(nativeFile)){
                BufferedImage image = ImageIO.read(new FileInputStream(fileStore+ nativeFile.getFileUrl()));
                response.setContentType("image/"+nativeFile.getFileSuffix());
                ServletOutputStream outputStream = response.getOutputStream();
                ImageIO.write(image,nativeFile.getFileSuffix(),outputStream);
            }

        } catch (Exception e) {
            if( e instanceof FileNotFoundException){
                throw new NativeException("图片未找到");
            }
        }
    }

    @ApiOperation("分页获取文件")
    @GetMapping("/getFilesByPage/{currentPage}/{pageSize}")
    public Result getFilesByPage(@PathVariable(required = false) Integer currentPage,
                               @PathVariable(required = false) Integer pageSize){
        if(ObjectUtils.isEmpty(currentPage)){
            currentPage=defaultPage;
        }
        if(ObjectUtils.isEmpty(pageSize)){
            pageSize=defaultPageSize;
        }
        List<NativeFileVo> nativeFileVos = nativeFileService.queryFilesByPage(currentPage, pageSize);
        return Result.success(nativeFileVos);
    }

}
