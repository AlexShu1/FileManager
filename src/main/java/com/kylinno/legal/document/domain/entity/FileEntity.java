package com.kylinno.legal.document.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "file_info")
public class FileEntity implements Serializable {

    private static final long serialVersionUID = -3258839839160856613L;

    @Id
    private String id;
    private String path;
    private String realPath;

    public FileEntity() {
    }

    public FileEntity(String id, String path, String realPath) {
        this.id = id;
        this.path = path;
        this.realPath = realPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", realPath='" + realPath + '\'' +
                '}';
    }
}
