package com.andresrcb.gcmtest;

public class Contact {

    public String name;
    public String phone;

    public Contact(String name, String phone){

        this.name = name;
        this.phone = phone;
    }
    public Contact(String name){

        this.name = name;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPhone(String phone){
        this.phone = phone;}

    public String getName(){return name;}
    public String getPhone(){return phone;}

}

