package com.mycompany.myapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class FileUploadConfiguration implements WebMvcConfigurer {
    @Value("${file.vpath}")
    String vpath;
    @Value("${file.diskpath}")
    String diskpath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler(this.vpath+"/**")
            .addResourceLocations("file:"+this.diskpath+"/");
    }
}
