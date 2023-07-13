package com.gaohongsen;

import java.io.*;
import java.net.*;

/*
 * 此类为服务端，用于与客户端通信，接收客户端的请求并返回请求的数据
 * 作为服务端，这个类需要保持长时间运行
 * 在目前的设计下，发送来的请求为一条长字符串，解析后第一个参数为请求的类型，应为int，后面的参数为请求的参数
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
        BufferedReader reader=new BufferedReader(new InputStreamReader(input));
        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(output));
        for(;;){
            String request=reader.readLine();
            String[] arr=request.split("/");
            int type=Integer.parseInt(arr[0]);
            switch(type){
                //请求为登入
                case 0:
                    String info;
                    try{
                        Database.loginCheck(arr[1], arr[2]);
                        info=Database.getUserInfo(arr[1]);
                    }catch(Exception e){
                        writer.write("0"+"/"+e.getMessage());
                        writer.newLine();
                        writer.flush();
                        break;
                    }
                    writer.write("1"+"/"+info);
                    break;
                
                //请求为登出
                case 1:
                
                //请求为注册
                case 2:
                    try{
                        String account=arr[1];
                        String password=arr[2];
                        String name=arr[3];
                        int permission=Integer.parseInt(arr[4]);
                        Database.addUser(account, password,name,permission);
                        writer.write("1");
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
}
