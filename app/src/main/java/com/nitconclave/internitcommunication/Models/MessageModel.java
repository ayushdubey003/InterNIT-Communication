package com.nitconclave.internitcommunication.Models;

import java.util.Date;

public class MessageModel {
    public String userName;
    public String message;
    public String imageUrl;
    public Date date;
    public Boolean isRead;
    public Boolean isSender;

    public MessageModel(){

    }

    public MessageModel(String userName, String message, String imageUrl, Date date){
        this.userName = userName;
        this.message = message;
        this.imageUrl = imageUrl;
        this.date = date;
        this.isRead = false;
    }

    public String getMessage() {
        return message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Date getDate() {
        return date;
    }

    public Boolean getRead() {
        return isRead;
    }

    public Boolean getSender() {
        return isSender;
    }

    public String getUserName() {
        return userName;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public void setSender(Boolean sender) {
        isSender = sender;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
