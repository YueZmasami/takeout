package com.yueZhai.reggie.controller;

import com.yueZhai.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file)  {
       log.info("获取文件：{}", file.toString()) ;
       //判断当前目录是否存在，不存在则创建
        File file1 = new File(basePath);

        if(!file1.exists()){
            file1.mkdirs();
        }

//获取原来的文件名称
        String fileName = file.getOriginalFilename();
        //获取.jpg
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //使用UUID获取文件名字
       String newFileName = UUID.randomUUID()+suffix;


        //将文件转存在桌面
        try {
            file.transferTo(new File(basePath+newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  R.success(newFileName);
    }
    /**
     * 下载图片
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        //通过输入流读取文件内容
        try {
            FileInputStream fis=new FileInputStream((basePath+name));
            ServletOutputStream os = response.getOutputStream();
            int len;
            byte[] buffer=new byte[1024];
            while((len=fis.read(buffer))!=-1){
                //把读取的文件写到浏览器上面
                os.write(buffer, 0, len);
            }
            fis.close();
            os.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
