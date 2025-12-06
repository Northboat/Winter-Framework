package com.northboat.winterframework.core.io;

import cn.hutool.core.lang.Assert;
import com.northboat.winterframework.core.io.impl.ClassPathResource;
import com.northboat.winterframework.core.io.impl.FileSystemResource;
import com.northboat.winterframework.core.io.impl.UrlResource;

import java.net.MalformedURLException;
import java.net.URL;

public class DefaultResourceLoader implements ResourceLoader{
    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null");
        if(location.startsWith(CLASSPATH_URL_PREFIX)){
            // 将本地 classpath 传入并创建 ClassPathResource
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            // 酱紫写逻辑原则上是不允许的
            try {
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e){
                return new FileSystemResource(location);
            }
        }
    }
}
