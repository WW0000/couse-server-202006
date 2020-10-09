package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.dto.FileDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class FileUploadResource {
    @Value("${file.vpath}")
    String vpath;
    @Value("${file.diskpath}")
    String diskpath;

    @ApiOperation("文件上传")
    @PostMapping("/upload/file")//名字是/upload/file
    public ResponseEntity upload(MultipartFile file) {
        //将file存入刚刚写的目录里面
        //1.接收文件，解析文件名
        String originName = file.getOriginalFilename();
        String extName = originName.substring(originName.lastIndexOf(".") + 1);
        Long size = file.getSize();
        //2.使用uuid生成新的文件名
        String newFileName = UUID.randomUUID() + "." + extName;
        //3.使用新的文件名，将文件写入指定目录
        String filePath = diskpath + newFileName;
        File serverFile = new File(filePath);
        try {
            file.transferTo(serverFile);//传送到serverFile
        } catch (IOException e) {
            e.printStackTrace();
        }
        //4.构成服务器访问地址返回
        String serverFilePath = vpath + "/" + newFileName;
        FileDTO result = new FileDTO();
        result.setOriginFileName(originName);
        result.setSize(size);
        result.setFileName(newFileName);
        result.setExtName(extName);
        result.setPath(serverFilePath);
        return ResponseEntity.ok(result);
    }
}
