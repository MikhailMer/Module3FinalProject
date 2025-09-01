package com.codegym.servletTask.model;

public abstract class AbstractEntity {
    private Integer id;

    private String content;

    public AbstractEntity(Integer id, String content){
        this.id = id;
        this.content = content;
    }

    public  Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
