package com.gaohongsen;

import java.io.*;
import java.net.*;

/*
 * 此类为服务端，用于与客户端通信，接收客户端的请求并返回请求的数据
 * 作为服务端，这个类需要保持长时间运行
 * 在目前的设计下，发送来的请求为一条长字符串，解析后第一个参数为请求的类型，应为int，后面的参数为请求的参数
 * 请求类型如下：0-登入，1-登出，2-注册，3-修改密码
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
    BufferedReader reader;
    BufferedWriter writer;

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

    private void handle(InputStream input,OutputStream output)throws IOException{
        reader=new BufferedReader(new InputStreamReader(input));
        writer=new BufferedWriter(new OutputStreamWriter(output));
        for(;;){
            final String request=reader.readLine();
            final String[] arr=request.split("/");
            int type=Integer.parseInt(arr[0]);
            switch(type){
                //请求为登入
                case 0:
                    try{
                        this.logIn(arr);
                        break;
                    }catch(Exception e){
                        writer.write("0"+"/"+e.getMessage());
                        writer.newLine();
                        writer.flush();
                        break;
                    }
                
                //请求为登出
                case 1:
                    try{
                        this.logOut(arr);
                        break;
                    }catch(Exception e){
                        writer.write("0/"+e.getMessage());
                        writer.newLine();
                        writer.flush();
                        break;
                    }
                
                //请求为注册
                case 2:
                    try{
                        this.register(arr);
                        break;
                    }catch(Exception e){
                        writer.write("0/"+e.getMessage());
                        writer.newLine();
                        writer.flush();
                        break;
                    }
                
                //请求为修改密码
                case 3:
                    try{
                        this.changePassword(arr);
                        break;
                    }catch(Exception e){
                        writer.write("0"+"/"+e.getMessage());
                        writer.newLine();
                        writer.flush();
                        break;
                    }
                }
        }
    }


    //登入
    private void logIn(String[] arr)throws Exception{
        Database.passwordCheck(arr[1], arr[2]);
        String info=Database.getUserInfo(arr[1]);
        Database.addLog(Database.getUserName(arr[1]),arr[1],0);
        writer.write("1"+"/"+info);
        writer.newLine();
        writer.flush();
    }

    //登出
    private void logOut(String[] arr)throws Exception{
        Database.addLog(Database.getUserName(arr[1]),arr[1],1);
        writer.write("1");
        writer.newLine();
        writer.flush();
    }

    //注册
    private void register(String[] arr)throws Exception{
        String account=arr[1];
        String password=arr[2];
        String name=arr[3];
        int permission=Integer.parseInt(arr[4]);
        Database.addUser(account, password,name,permission);
        Database.addLog(name,account,2);
        writer.write("1");
        writer.newLine();
        writer.flush();
    }

    //修改密码
    private void changePassword(String[] arr)throws Exception{
        String account=arr[1];
        String oldPassword=arr[2];
        String newPassword=arr[3];
        Database.passwordCheck(account, oldPassword);
        Database.updatePassword(account, newPassword);
        writer.write("1");
        writer.newLine();
        writer.flush();
    }
}
