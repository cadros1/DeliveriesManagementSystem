package com.gaohongsen;

public class Delivery implements java.io.Serializable{
    private int id;
    private String sendPlace;
    private String receivePlace;
    private String sender;
    private String receiver;
    private int situation;

    //新增时使用此构造方法
    public Delivery(String sendPlace, String receivePlace, String sender, String receiver, int situation) {
        this.sendPlace = sendPlace;
        this.receivePlace = receivePlace;
        this.sender = sender;
        this.receiver = receiver;
        this.situation = situation;
    }

    //修改时使用此构造方法
    public Delivery(int id,String sendPlace, String receivePlace, String sender, String receiver, int situation) {
        this.id = id;
        this.sendPlace = sendPlace;
        this.receivePlace = receivePlace;
        this.sender = sender;
        this.receiver = receiver;
        this.situation = situation;
    }

    //删除或查询时使用此构造方法
    public Delivery(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSendPlace() {
        return sendPlace;
    }

    public String getReceivePlace() {
        return receivePlace;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public int getSituation() {
        return situation;
    }
}
