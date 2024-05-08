package com.kn.initialmusic.controller;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;


@RestController
@CrossOrigin
@RequestMapping("/file")
public class fileController {

    @PostMapping("/download")
    public void download(@RequestBody String filepath, HttpServletResponse response) throws IOException {
        /*if (filepath != null) {
            File file = new File(filepath);
            if (file.exists()) {
                // 取得文件名。
                String filename = file.getName();
                // 取得文件的后缀名。
                String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
                String name = filename + ext;
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "utf-8"));
                FileInputStream inputStream = new FileInputStream(file);//创建文件输入流对象
                ServletOutputStream outputStream = response.getOutputStream();//创建文件输出流对象
                byte[] buf = new byte[1024]; //定义一个字节数组作为缓冲区，并指定一次读取1024个字节
                int len; //记住读入缓冲区的字节长度
                while ((len = inputStream.read(buf)) != -1) { //判断文件是否读完（读完后会返回-1）
                    //注意不要用output.write(buf); 否则可能导致最终写入的文件比原文件大，如果文件是图片的话，那么就会失真
                    outputStream.write(buf, 0, len); //从buf缓冲区中读取数据，从第一个字节开始读，一次读取len，并将读取出来的数据写入文件输出流对象中
                }
                inputStream.close();
                outputStream.close();
            }
        }*/
    }
}
