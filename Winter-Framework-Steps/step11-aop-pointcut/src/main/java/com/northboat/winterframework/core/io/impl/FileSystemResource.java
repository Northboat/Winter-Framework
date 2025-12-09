package com.northboat.winterframework.core.io.impl;

import com.northboat.winterframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileSystemResource implements Resource {

    private final File file;
    private final String path;


    public FileSystemResource(File file) {
        this.file = file;
        path = file.getPath();
    }

    public FileSystemResource(String path) {
        this.path = path;
        file = new File(path);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    public final String getPath() {
        return path;
    }
}
