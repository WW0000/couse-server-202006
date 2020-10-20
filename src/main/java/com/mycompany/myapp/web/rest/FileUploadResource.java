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
    @ApiOperation("文件上传接口")
    @PostMapping("/upload/file")
    public ResponseEntity upload(MultipartFile file){
        String originName=file.getOriginalFilename();
        String extName=originName.substring(originName.lastIndexOf(".")+1);
        Long size=file.getSize();
        String newFileName= UUID.randomUUID()+"."+extName;

        String filePath=diskpath+newFileName;
        File serverFile=new File(filePath);
        try{
            file.transferTo(serverFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String serverFilePath=vpath+"/"+newFileName;
        FileDTO result=new FileDTO();
        result.setOriginFileName(originName);
        result.setSize(size);
        result.setExtName(extName);
        result.setFileName(newFileName);
        result.setPath(serverFilePath);
        return ResponseEntity.ok(result);
    }
}
