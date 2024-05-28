package com.myPokeGame.utils;

import cn.hutool.core.io.FileUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.poi.hpsf.Thumbnail;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ImageUtils {

    static List<String> imageSuffix=Arrays.asList("jpg","jpeg","png");

    public static String getPreviewImage(File orgImage,String storageFolder,Integer previewWidth){
        File storage=new File(storageFolder);
        if(!storage.exists()){
            storage.mkdirs();
        }
        String orgImageName = orgImage.getName();
        String[] strs = orgImageName.split("\\.");
        String outputImageFileName=strs[0]+"_preview."+strs[1];
        String outputImagePath=storageFolder+strs[0]+"_preview."+strs[1];
        try {
            // 读取原始图像
            File inputFile = orgImage;
            System.out.println(inputFile.exists());
            BufferedImage inputImage = ImageIO.read(inputFile);

            Integer scaledWidth=inputImage.getWidth();
            Integer scaledHeight=inputImage.getHeight();

            // 如果inputImage 的width>198px 则进行压缩
            if(scaledWidth>previewWidth){
                scaledHeight=(Integer)(scaledHeight*previewWidth/scaledWidth);
                scaledWidth=previewWidth;
            }

            // 创建一个新的缩放后的图像缓冲区
            BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());
            // 使用 Graphics2D 绘制重新缩放的图像
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.dispose();
            // 提取扩展名
            String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);
            // 将缩放后的图像保存到输出文件
            ImageIO.write(outputImage, formatName, new File(outputImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputImageFileName;
    }

    public static String getCompressImage(File orgImage,String storageFolder){
        File storage=new File(storageFolder);
        if(!storage.exists()){
            storage.mkdirs();
        }
        String orgImageName = orgImage.getName();
        String[] strs = orgImageName.split("\\.");
        String suffix=strs[1];
        if("gif".equals(suffix)){
            String outputImageFileName=strs[0]+"_preview."+strs[1];
            String outputImagePath=storageFolder+strs[0]+"_preview."+strs[1];
            File preivewFile=new File(outputImagePath);
            FileUtil.copy(orgImage,preivewFile,false);
            return outputImageFileName;
        }
        else if("png".equals(suffix)){
            String outputImageFileName=strs[0]+"_preview."+"jpg";
            String outputImagePath=storageFolder+strs[0]+"_preview."+"jpg";
            try {
                Thumbnails.of(orgImage).scale(1f).toFile(outputImagePath);
                Thumbnails.of(orgImage).scale(1f).outputQuality(0.2f).toFile(outputImagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return outputImageFileName;
        }
        else{
            String outputImageFileName=strs[0]+"_preview."+strs[1];
            String outputImagePath=storageFolder+strs[0]+"_preview."+strs[1];
            try {
                Thumbnails.of(orgImage).scale(1).outputQuality(0.2).toFile(outputImagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return outputImageFileName;
        }
    }

    public static Boolean isImage(String fileName){
        String suffix = CommonUtils.getSuffix(fileName);
        return imageSuffix.contains(suffix);
    }
}
