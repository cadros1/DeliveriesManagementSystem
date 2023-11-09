package com.gaohongsen;

/**
 * 此类封装了一个请求类型与一个Object对象，便于客户端与服务端之间的通讯
 * 
 * @author 高洪森
 * @param type 请求的类型，0为登入，1为注册，2为登出，3为修改密码
 * @param item 请求的内容，为一个对象，具体内容由type决定
 */
public class Request implements java.io.Serializable{
    private final int type;
    private final Object item;

    public Request(int type,Object item){
        this.type=type;
        this.item=item;
    }

    public int getType(){
        return this.type;
    }

    public Object getItem(){
        return this.item;
    }
}