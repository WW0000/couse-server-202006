package com.mycompany.myapp.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

    @ApiModel("文件信息实体")
    public class FileDTO {
        @ApiModelProperty(value = "原始文件名")
        String originFileName;
        @ApiModelProperty(value = "服务器文件名")
        String fileName;
        @ApiModelProperty(value = "文件大小")
        Long size;
        @ApiModelProperty(value = "服务器文件上访问路径")
        String path;
        @ApiModelProperty(value = "文件后缀名")
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

