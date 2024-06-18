package com.example.restapi;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Messages {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer idMessage;

    private Integer idSender;

    private Integer idReceiver;

    private String messageContent;

    //private Integer idGroup;
    //This is may be no need because if it is a group, then id will be the Group ID

    private String time;

    public Messages(Integer idSender, Integer idReceiver, String messageContent, String time)
    {
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.messageContent = messageContent
        this.time = time;
    }

    public Messages() { }

    public Integer getId()
    {
        return idMessage;
    }

    public Integer getIdSender()
    {
        return idSender;
    }

    public Integer getIdReceiver()
    {
        return idReceiver;
    }

    public String getMessageContent()
    {
        return messageContent;
    }

    public String getTime;
    {
        return time;
    }

    public void setIdMessage(Integer idMessage)
    {
        this.idMessage = idMessage;
    }

    public void setIdSender(Integer idSender)
    {
        this.idSender = idSender;
    }

    public void setIdReceiver(Integer idReceiver)
    {
        this.idReceiver = idReceiver;
    }

    public void setMesageContent(String messageContent)
    {
        this.messageContent = messageContent;
    }

    public void setTime(String time)
    {
        this.time = time ;
    }
}