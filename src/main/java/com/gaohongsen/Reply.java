package com.gaohongsen;

/*
 * 此类封装了一个表示是否成功的布尔值与一个Object对象，便于客户端与服务端之间的通讯
 * 
 * @author 高洪森
 * @param hasSuccess 表示是否成功的布尔值
 * @param item 传输的对象
 */
public class Reply implements java.io.Serializable{
    boolean hasSuccess=false;
    Object item;

    public Reply(boolean hasSuccess,Object item){
        this.hasSuccess=hasSuccess;
        this.item=item;
    }

    public boolean hasSuccess(){
        return hasSuccess;
    }

    public Object getItem(){
        return item;
    }
}
