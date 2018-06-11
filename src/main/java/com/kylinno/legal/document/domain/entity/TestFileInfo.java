package com.kylinno.legal.document.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Test_file")
public class TestFileInfo {

    @Id
    private String id;
    private String name;
    private String type;
    private TestFileInfo[] file;

    public TestFileInfo() {
    }

    public TestFileInfo(String name, String type, TestFileInfo[] file) {
        this.name = name;
        this.type = type;
        this.file = file;
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

    public TestFileInfo[] getFile() {
        return file;
    }

    public void setFile(TestFileInfo[] file) {
        this.file = file;
    }
}
