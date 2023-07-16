package com.gaohongsen;

import java.io.*;
import java.net.*;

/*
 * 此类为服务端，用于与客户端通信，接收客户端的请求并返回请求的数据
 * 作为服务端，这个类需要保持长时间运行
 * 在目前的设计下，发送来的请求为一个封装后的Request对象，包含了请求类型与带有数据的对象
 * 完成请求后将返回一个封装后的Reply对象，包含了是否成功的布尔值，可能还有带有数据的对象，如果不成功，将会返回一个异常
 * 请求类型如下：0-登入，1-登出，2-注册，3-修改密码，4-添加一条物流信息,5-按单号查找一条物流信息,6-按id删除一条物流信息
 * 
 * @author 高洪森
 */
public class Server {
    public static void main(String[] args)throws IOException{
        ServerSocket ss=new ServerSocket(6666);
        System.out.println("Server is running...");
        for(;;){
            Socket sock=ss.accept();
            System.out.println("Connected from "+sock.getRemoteSocketAddress());
            Thread t=new Handler(sock);
            t.start();
        }
    }
}


class Handler extends Thread{
    Socket sock;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public Handler(Socket sock){
        this.sock=sock;
    }

    public void run(){
        try(InputStream is=this.sock.getInputStream()){
            try(OutputStream os=this.sock.getOutputStream()){
                handle(is,os);
            }
        }catch(Exception e){
            try{
                this.sock.close();
            }catch(IOException ioe){
                System.out.println("client disconnected.");
            }
        }
    }

    private void handle(InputStream input,OutputStream output)throws IOException,ClassNotFoundException{
        ois=new ObjectInputStream(input);
        oos=new ObjectOutputStream(output);
        for(;;){
            final Request request=(Request)ois.readObject();
            switch(request.getType()){
                //请求为登入
                //将会传入包含account和password的user对象
                case 0:
                    try{
                        this.logIn((User)request.getItem());
                        break;
                    }catch(Exception e){
                        oos.writeObject(new Reply(false,e));
                        oos.flush();
                        break;
                    }
                
                //请求为登出
                //将会传入包含account的user对象
                case 1:
                    try{
                        this.logOut((User)request.getItem());
                        break;
                    }catch(Exception e){
                        oos.writeObject(new Reply(false,e));
                        oos.flush();
                        break;
                    }
                
                //请求为注册
                //将会传入包含account、password、name和permission的user对象
                case 2:
                    try{
                        this.register((User)request.getItem());
                        break;
                    }catch(Exception e){
                        oos.writeObject(new Reply(false,e));
                        break;
                    }
                
                //请求为修改密码
                //将会传入包含account、password和updatedPassword的user对象
                case 3:
                    try{
                        this.changePassword((User)request.getItem());
                        break;
                    }catch(Exception e){
                        oos.writeObject(new Reply(false,e));
                        oos.flush();
                        break;
                    }
                
                //请求为添加一条物流信息
                //将会传入包含sendplace、receiveplace、sender、receiver、situation的delivery对象
                case 4:
                    try{
                        this.addDelivery((Delivery)request.getItem());
                        break;
                    }catch(Exception e){
                        oos.writeObject(new Reply(false,e));
                        oos.flush();
                        break;
                    }

                //请求为按单号查找物流信息
                //将会传入包含id的delivery对象
                case 5:
                    try{
                        this.getDeliveryInfoById((Delivery)request.getItem());
                        break;
                    }catch(Exception e){
                        oos.writeObject(new Reply(false,e));
                        oos.flush();
                        break;
                    }

                case 6:
                    try{
                        this.deleteDeliveryById((Delivery)request.getItem());
                    }catch(Exception e){
                        oos.writeObject(new Reply(false,e));
                        oos.flush();
                        break;
                    }
            }
        }
    }


    //登入
    private void logIn(User user)throws Exception{
        Database.passwordCheck(user);
        User replyUser=Database.getUserInfo(user);
        Database.addLog(replyUser,0);
        oos.writeObject(new Reply(true,replyUser));
        oos.flush();
    }

    //登出
    private void logOut(User user)throws Exception{
        Database.addLog(Database.getUserInfo(user),1);
        oos.writeObject(new Reply(true,null));
        oos.flush();
    }

    //注册
    private void register(User user)throws Exception{
        Database.addUser(user);
        Database.addLog(user,2);
        oos.writeObject(new Reply(true,null));
        oos.flush();
    }

    //修改密码
    private void changePassword(User user)throws Exception{
        Database.passwordCheck(user);
        Database.updatePassword(user);
        oos.writeObject(new Reply(true,Database.getUserInfo(user)));
        oos.flush();
    }

    //添加一条物流信息
    private void addDelivery(Delivery delivery)throws Exception{
        oos.writeObject(new Reply(true,Database.addDelivery(delivery)));
        oos.flush();
    }

    //按单号查找物流信息
    private void getDeliveryInfoById(Delivery delivery)throws Exception{
        oos.writeObject(new Reply(true,Database.getDeliveryInfo(delivery)));
        oos.flush();
    }

    //按id删除一条物流信息
    private void deleteDeliveryById(Delivery delivery)throws Exception{
        Database.deleteDelivery(delivery);
        oos.writeObject(new Reply(true,null));
    }
}
