package com.gaohongsen;

/*
 * 此类对delivery进行了封装
 * 实例化对象时请根据需求选择合适的构造方法传入参数，构造方法选择不当将会导致异常
 * 
 * @author 高洪森
 * @param id 物流单号，同时也是自增主键，由数据库分配
 * @param sendPlace 发货地
 * @param receivePlace 收货地
 * @param sender 发货人
 * @param receiver 收货人
 * @param situation 物流状态，0为未发货，1为已揽收，2为已发货，3为已到货，4为已签收
 */
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
