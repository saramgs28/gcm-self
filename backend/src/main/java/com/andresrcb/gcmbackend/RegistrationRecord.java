package com.andresrcb.gcmbackend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/** The Objectify object model for device registrations we are persisting */
@Entity
public class RegistrationRecord {

    @Id
    Long id;

    @Index
    private String regId;
    private String name;
    private String phone;
    private String username;
    // you can add more fields...

    public RegistrationRecord() {}

    public String getRegId() {
        return regId;
    }

    public String getName() {
        return name;
    }
    public String getPhone(){ return phone; }
    public String getUsername(){ return username; }
    public void setRegId(String regId) {
        this.regId = regId;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPhone(String p){ this.phone = p;}
    public void setUsername(String u){this.username = u;}
}