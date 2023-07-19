package com.gaohongsen;

import java.io.*;
import java.net.*;

/*
 * 此类为服务端，用于与客户端通信，接收客户端的请求并返回请求的数据
 * 作为服务端，这个类需要保持长时间运行
 * 在目前的设计下，发送来的请求为一个封装后的Request对象，包含了请求类型与带有数据的对象
 * 完成请求后将返回一个封装后的Reply对象，包含了是否成功的布尔值，可能还有带有数据的对象，如果不成功，将会返回一个异常
 * 请求类型如下：0-登入，1-登出，2-注册，3-修改密码，4-添加一条物流信息,5-按单号查找一条物流信息,
 *              6-按id删除一条物流信息,7-修改一条物流信息,8-显示最近30条物流信息,9-显示最近30条日志信息
 *              10-导出物流信息,11-导出日志信息
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

//创建一个线程来处理与客户端的通信
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
        User currentUser=null;

        ois=new ObjectInputStream(input);
        oos=new ObjectOutputStream(output);
        for(;;){
            final Request request=(Request)ois.readObject();
            switch(request.getType()){
                //请求为登入
                //将会传入包含account和password的user对象
                case 0:
                    try{
                        Database.passwordCheck((User)request.getItem());
                        currentUser=Database.getUserInfo((User)request.getItem());
                        Database.addLog(currentUser,0);
                        oos.writeObject(new Reply(true,currentUser));
                        oos.flush();
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
                        Database.addLog(Database.getUserInfo((User)request.getItem()),1);
                        currentUser=null;
                        oos.writeObject(new Reply(true,null));
                        oos.flush();
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
                        Database.addUser((User)request.getItem());
                        Database.addLog((User)request.getItem(),2);
                        oos.writeObject(new Reply(true,null));
                        oos.flush();
                        break;
                    }catch(Exception e){
                        oos.writeObject(new Reply(false,e));
                        break;
                    }
                
                //请求为修改密码
                //将会传入包含account、password和updatedPassword的user对象
                case 3:
                    try{
                        Database.passwordCheck((User)request.getItem());
                        Database.updatePassword((User)request.getItem());
                        Database.addLog(currentUser,3);
                        Database.addLog(currentUser,1);
                        oos.writeObject(new Reply(true,currentUser));
                        oos.flush();
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
                        Delivery d=Database.addDelivery((Delivery)request.getItem());
                        Database.addLog(currentUser,4);
                        oos.writeObject(new Reply(true,d));
                        oos.flush();
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
                        oos.writeObject(new Reply(true,Database.getDeliveryInfo((Delivery)request.getItem())));
                        oos.flush();
                        break;
                    }catch(Exception e){
                        oos.writeObject(new Reply(false,e));
                        oos.flush();
                        break;
                    }

                //请求为删除一条物流信息
                //将会传入包含id的delivery对象
                case 6:
                    try{
                        Database.deleteDelivery((Delivery)request.getItem());
                        Database.addLog(currentUser,5);
                        oos.writeObject(new Reply(true,null));
                        break;
                    }catch(Exception e){
                        oos.writeObject(new Reply(false,e));
                        oos.flush();
                        break;
                    }

                //请求为修改一条物流信息
                //将会传入包含id、sendplace、receiveplace、sender、receiver、situation的delivery对象
                case 7:
                    try{
                        Database.updateDelivery((Delivery)request.getItem());
                        Database.addLog(currentUser,5);
                        oos.writeObject(new Reply(true,null));
                        break;
                    }catch(Exception e){
                        oos.writeObject(new Reply(false,e));
                        oos.flush();
                        break;
                    }
                
                //请求为显示最近30条物流信息
                //不传入对象
                case 8:
                    try{
                        oos.writeObject(new Reply(true,Database.displayDeliveries()));
                        break;
                    }catch(Exception e){
                        oos.writeObject(new Reply(false,e));
                        oos.flush();
                        break;
                    }
                
                //请求为显示最近30条日志
                //不传入对象
                case 9:
                    try{
                        oos.writeObject(new Reply(true,Database.displayLogs()));
                        break;
                    }catch(Exception e){
                        oos.writeObject(new Reply(false,e));
                        oos.flush();
                        break;
                    }

                //请求为导出物流信息
                //不传入对象
                case 10:
                    try{
                        Database.outputDeliveries();
                        oos.writeObject(new Reply(true,null));
                        break;
                    }catch(Exception e){
                        oos.writeObject(new Reply(false,e));
                        oos.flush();
                        break;
                    }
                
                //请求为导出日志
                //不传入对象
                case 11:
                    try{
                        Database.outputLogs();
                        oos.writeObject(new Reply(true,null));
                        break;
                    }catch(Exception e){
                        oos.writeObject(new Reply(false,e));
                        oos.flush();
                        break;
                    }
            }
        }
    }
}
