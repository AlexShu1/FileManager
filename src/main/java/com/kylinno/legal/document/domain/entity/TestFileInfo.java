package com.kylinno.legal.document.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "Test_file")
public class TestFileInfo implements Serializable {

    private static final long serialVersionUID = -3258839839160856613L;

    @Id
    private String id;
    private String name;
    private String type;
    private List<TestFileInfo> files = new ArrayList<>();
    private String saveServerPath;

    public TestFileInfo() {
    }

    public TestFileInfo(String name) {
        this.name = name;
    }

    public TestFileInfo(String name, String saveServerPath) {
        this.name = name;
        this.saveServerPath = saveServerPath;
    }

    public TestFileInfo(String id, String name, String type, List<TestFileInfo> files, String saveServerPath) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.files = files;
        this.saveServerPath = saveServerPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TestFileInfo> getFiles() {
        return files;
    }

    public void setFiles(List<TestFileInfo> file) {
        this.files = file;
    }

    public String getSaveServerPath() {
        return saveServerPath;
    }

    public void setSaveServerPath(String saveServerPath) {
        this.saveServerPath = saveServerPath;
    }
}
