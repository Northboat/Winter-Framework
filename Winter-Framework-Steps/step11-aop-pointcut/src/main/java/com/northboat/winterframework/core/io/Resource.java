package com.northboat.winterframework.core.io; // 这个包主要用于处理资源加载流

import java.io.InputStream;

// 提供获取 InputStream 流的方法
public interface Resource {

    InputStream getInputStream() throws Exception;
}
