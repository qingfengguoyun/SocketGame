package com.project_02_28.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.extractor.ExcelExtractor;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

public class DocumentExtraction {

    /**
     * 取微软记事本中的内容，以字符串的形式返回。记事本中内容的编码为GBK
     *
     * @param file
     * @return
     */
    public static String txt2String(File file) {
        StringBuffer buffer = new StringBuffer();
        try {
            InputStreamReader input = new InputStreamReader(new FileInputStream(file), "utf8");
            BufferedReader reader = new BufferedReader(input);

            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append('\n');
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 取Excel xlsx中的内容，以字符串的形式返回。
     *
     * @param file
     * @return
     */
    @SuppressWarnings("resource")
    public static String xlsx2String(File file) {

        XSSFWorkbook xlsxwb = new XSSFWorkbook();
        try {
            OPCPackage pkg = OPCPackage.open(new FileInputStream(file));
            xlsxwb = new XSSFWorkbook(pkg);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExcelExtractor extractor = new XSSFExcelExtractor(xlsxwb);
        extractor.setFormulasNotResults(true);
        extractor.setIncludeSheetNames(false);

        return extractor.getText();
    }

    /**
     * 取Excel xls中的内容，以字符串的形式返回。
     *
     * @param file
     * @return
     */
    @SuppressWarnings("resource")
    public static String xls2String(File file) {

        HSSFWorkbook xlswb = new HSSFWorkbook();
        try {
            POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(file));
            xlswb = new HSSFWorkbook(fileSystem);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExcelExtractor extractor = new org.apache.poi.hssf.extractor.ExcelExtractor(xlswb);
        extractor.setFormulasNotResults(true);
        extractor.setIncludeSheetNames(false);

        return extractor.getText();
    }

    /**
     * 取Word doc中的内容，以字符串的形式返回。
     *
     * @param file
     * @return
     */
    public static String doc2String(File file) {
        StringBuffer buffer = new StringBuffer();

        WordExtractor extractor = null;
        POIFSFileSystem fileSystem;
        try {
            fileSystem = new POIFSFileSystem(new FileInputStream(file));
            extractor = new WordExtractor(fileSystem);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] paragraphText = extractor.getParagraphText();
        for (String paragraph : paragraphText) {
            buffer.append(paragraph);
        }

        return buffer.toString();
    }

    /**
     * 取Word docx中的内容，以字符串的形式返回。
     *
     * @param file
     * @return
     */
    public static String docx2String(File file) {

        XWPFWordExtractor extractor = null;
        try {
            OPCPackage pkg = OPCPackage.open(new FileInputStream(file));
            extractor = new XWPFWordExtractor(pkg);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XmlException e) {
            e.printStackTrace();
        } catch (OpenXML4JException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractor.getText();
    }

}
