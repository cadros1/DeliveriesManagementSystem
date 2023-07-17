package com.gaohongsen;

public class Delivery implements java.io.Serializable{
    private int id=0;
    private String sendPlace=null;
    private String receivePlace=null;
    private String sender=null;
    private String receiver=null;
    private int situation=0;

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

    public String getSituationString() {
        switch (situation) {
            case (1):
                return "未发货";
            case (2):
                return "已揽收";
            case (3):
                return "已发货";
            case (4):
                return "已到货";
            case (5):
                return "已签收";
        }
        return "无可查询状态";
    }
}
