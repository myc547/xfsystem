package com.myc.file.model;

import lombok.Data;

/**
 * @author myc
 * @version 1.0, 2017/12/9
 */
@Data
public class UploadInfo {

    private String md5;
    private String chunks;
    private String chunk;
    private String path;
    private String fileName;
    private String ext;

    public UploadInfo() {
    }

    public UploadInfo(String md5, String chunks, String chunk, String path, String fileName, String ext) {
        this.md5 = md5;
        this.chunks = chunks;
        this.chunk = chunk;
        this.path = path;
        this.fileName = fileName;
        this.ext = ext;
    }

}
