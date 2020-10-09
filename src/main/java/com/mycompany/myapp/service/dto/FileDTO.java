package com.mycompany.myapp.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("文本信息实体")
public class FileDTO {
    @ApiModelProperty(value="原始文件名")
    String originFileName;
    @ApiModelProperty(value="上传后文件名")
    String fileName;
    @ApiModelProperty(value="文件大小")
    Long size;
    @ApiModelProperty(value="文件路径")
    String path;
    @ApiModelProperty(value="文件的扩展名")
    String extName;

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }
}
