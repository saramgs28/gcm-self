package com.andresrcb.gcmbackend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.json.simple.JSONObject;



public class FileRecord {
    @Id
    Long id;

    private String type;
    private String fileName;
    private String url;
    private String createdAt;

    public String getType(){
        return type;
    }
    public String getFileName(){
        return fileName;
    }
    public String getUrl(){
        return url;
    }
    public String getCreatedAt(){
        return createdAt;
    }
    public void setType(String t){
        this.type = t;
    }
    public void setCreatedAt(String c){
        this.createdAt = c;
    }
    public void setUrl(String u){
        this.url = u;
    }
    public void setFileName(String f){
        this.fileName = f;
    }
}
