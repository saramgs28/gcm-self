package com.andresrcb.gcmbackend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.json.simple.JSONObject;

/** The Objectify object model for device registrations we are persisting */
@Entity
public class MessageRecord {

    @Id
    Long id;

    @Index
    private String toPhone;
    private String fromPhone;
    private String textMessage;

    // you can add more fields...

    public MessageRecord() {
    }
    public String getToPhone(){
        return toPhone;
    }
    public String getFromPhone(){
        return fromPhone;
    }
    public String getTextMessage(){return textMessage;}
    public void setToPhone(String p){
        this.toPhone = p;
    }
    public void setFromPhone(String p){
        this.fromPhone = p;
    }
    public void setTextMessage(String m){
        this.textMessage = m;
    }

}