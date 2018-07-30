package com.zhixie.catalog.model;

public class Catalogs {
    private int id;
    private int pid;
    private String name;
    private String content;
    private String code;

    @Override
    public String toString() {
        return "Catalogs{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
