package com.myPokeGame.controller;

import com.myPokeGame.entity.NativeFile;
import com.myPokeGame.entity.ProfilePhoto;
import com.myPokeGame.entity.Tag;
import com.myPokeGame.exceptions.NativeException;
import com.myPokeGame.mapper.NativeFileMapper;
import com.myPokeGame.mapper.ProfilePhotoMapper;
import com.myPokeGame.models.pojo.NFileTagsPojo;
import com.myPokeGame.models.vo.NativeFileVo;
import com.myPokeGame.service.fileService.NativeFileService;
import com.myPokeGame.service.tagService.TagService;
import com.myPokeGame.utils.NativePage;
import com.myPokeGame.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/file")
@Api(tags = "文件接口")
public class FileController {

    @Value("${app-env.fileStorage}")
    private String fileStore;

    @Value("${app-env.filePreviewStorage}")
    private String filePreviewStore;

    @Value("${app-env.profilePhotoStorage}")
    private String profilePhotoStore;

    @Value("${app-env.defaultPage}")
    private Integer defaultPage;

    @Value("${app-env.defaultPageSize}")
    private Integer defaultPageSize;

    @Autowired
    private NativeFileService nativeFileService;

    @Autowired
    private NativeFileMapper nativeFileMapper;

    @Autowired
    private ProfilePhotoMapper profilePhotoMapper;

    @Autowired
    private TagService tagService;

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
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(nativeFile.getFileName(),"UTF-8"));
                ServletOutputStream outputStream = response.getOutputStream();
                ImageIO.write(image,nativeFile.getFileSuffix(),outputStream);
            }

        } catch (Exception e) {
            if( e instanceof FileNotFoundException){
                throw new NativeException("图片未找到");
            }
        }
    }

    @ApiOperation("根据ID获取预览图片")
    @GetMapping("/getPreviewImage/{imageId}")
    public void getPreviewImage(@PathVariable("imageId") Long imageId){
        try {
            NativeFile nativeFile = nativeFileMapper.selectById(imageId);
            if(!ObjectUtils.isEmpty(nativeFile)){
                BufferedImage image = ImageIO.read(new FileInputStream(filePreviewStore+ nativeFile.getFilePreviewUrl()));

                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(nativeFile.getFileName(),"UTF-8"));
                if("png".equals(nativeFile.getFileSuffix())){
                    ServletOutputStream outputStream = response.getOutputStream();
                    response.setContentType("image/"+"jpg");
                    ImageIO.write(image,"jpg",outputStream);
                }else{
                    ServletOutputStream outputStream = response.getOutputStream();
                    response.setContentType("image/"+nativeFile.getFileSuffix());
                    ImageIO.write(image,nativeFile.getFileSuffix(),outputStream);
                }
            }

        } catch (Exception e) {
            if( e instanceof FileNotFoundException){
                throw new NativeException("图片未找到");
            }
        }
    }

    @ApiOperation("根据ID获取用户头像")
    @GetMapping("/getProfilePhoto/{imageId}")
    public void getProfilePhoto(@PathVariable("imageId") Long imageId){
        try {
//            NativeFile nativeFile = nativeFileMapper.selectById(imageId);
            ProfilePhoto profilePhoto = profilePhotoMapper.selectById(imageId);
            if(!ObjectUtils.isEmpty(profilePhoto)){
                BufferedImage image = ImageIO.read(new FileInputStream(profilePhotoStore+ profilePhoto.getProfilePhotoUrl()));
                response.setContentType("image/"+profilePhoto.getProfilePhotoSuffix());
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(profilePhoto.getProfilePhotoUrl(),"UTF-8"));
                ServletOutputStream outputStream = response.getOutputStream();
                ImageIO.write(image,profilePhoto.getProfilePhotoSuffix(),outputStream);
            }
        } catch (Exception e) {
            if( e instanceof FileNotFoundException){
                throw new NativeException("图片未找到");
            }
        }
    }


    /**
     * @GetMapping 和 @PostMapping 可以传递url对象，同时匹配多个路径
     * 用于解决 @PathVariable(required = false) ，不传递参数提示找不到路径的问题
     */
    @ApiOperation("分页获取文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页码", dataType = "Integer", paramType = "path", defaultValue = "1",required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", dataType = "Integer", paramType = "path", defaultValue = "5",required = false)
    })
    @GetMapping({"/getFilesByPage/{currentPage}/{pageSize}","/getFilesByPage/{currentPage}","/getFilesByPage"})
    public Result getFilesByPage(@PathVariable(required = false) Integer currentPage,
                               @PathVariable(required = false) Integer pageSize){
        if(ObjectUtils.isEmpty(currentPage)){
            currentPage=defaultPage;
        }
        if(ObjectUtils.isEmpty(pageSize)){
            pageSize=defaultPageSize;
        }
        NativePage<NativeFileVo> nativePage = nativeFileService.queryFilesByPage(currentPage, pageSize);
        return Result.success(nativePage);
    }

    @ApiOperation("下载文件")
    @GetMapping("/downloadFile")
    public void downloadFileById(Long fileId,HttpServletResponse response){
        nativeFileService.downLoadFile(fileId,response);
    }

    @ApiOperation("更新文件标签")
    @PostMapping("/updateFileTags")
    public Result updateFileTags(@RequestBody NFileTagsPojo pojo){
        List<Long> tagIds = tagService.updateTagsByFileId(pojo.getFileId(), pojo.getTagIds());
        return Result.success(tagIds);
    }

}