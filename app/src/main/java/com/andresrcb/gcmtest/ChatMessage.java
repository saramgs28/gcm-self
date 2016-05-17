package com.andresrcb.gcmtest;

public class ChatMessage {
    public boolean left;
    public String message;
    public String filetype;
    public String fileUrl;

    public ChatMessage(boolean left, String message, String filetype, String fileUrl) {
        super();
        this.left = left;
        this.message = message;
        this.filetype= filetype;
        this.fileUrl = fileUrl;
    }

    public String getFileType()
    {
        return filetype;
    }
    public String getFileUrl(){
        return fileUrl;
    }
}

