package com.andresrcb.gcmtest;

public class ChatMessage {
    public boolean left;
    public String message;
    public String filetype;

    public ChatMessage(boolean left, String message, String filetype) {
        super();
        this.left = left;
        this.message = message;
        this.filetype= filetype;
    }

    public String getFileType()
    {
        return filetype;
    }
}

