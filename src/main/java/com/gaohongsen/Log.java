package com.gaohongsen;

public class Log implements java.io.Serializable{
    private int id;
    private String name;
    private String account;
    private String datetime;
    private int type;

    public Log(int id,String name,String account,String datetime,int type) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.datetime = datetime;
        this.type = type;
    }

    public Log(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public String getDatetime() {
        return datetime;
    }

    public int getType() {
        return type;
    }
}
