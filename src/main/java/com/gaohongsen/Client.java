package com.gaohongsen;

import java.io.*;
import java.net.*;

/**
 * 此类为客户端，用于与服务端通信，发送请求并接收服务端的数据<p>
 * 在目前的设计下，客户端与服务端之间的通信以对象的形式进行<p>
 * 客户端将实例化一个包含请求信息的Request对象并发送给服务端进行处理<p>
 * 服务端将返回一个包含处理结果的Reply对象,其中可能包含一个带有信息的数据对象或一个Exception
 * 
 * @author 高洪森
 */
public class Client {

    static Socket sock=null;
    static boolean isInitialized=false;
    static ObjectOutputStream oos=null;
    static ObjectInputStream ois=null;

    public static Object sendRequest(final Request request)throws IOException,ClassNotFoundException{
        if(!isInitialized){
            initialize();
        }

        Reply reply=handle(request);

        if(request.getType()==1||request.getType()==12){
            disconnected();
        }
        return reply;
    }

    private static void initialize()throws IOException{
        sock=new Socket("localhost",6666);
        oos=new ObjectOutputStream(sock.getOutputStream());
        ois=new ObjectInputStream(sock.getInputStream());
        isInitialized=true;
    }

    private static void disconnected()throws IOException{
        sock.close();
        isInitialized=false;
    }

    /* 暂时无用功能，可与服务端之间以字符串形式通讯
    private static String sendType(InputStream input,OutputStream output,int type)throws IOException{
        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(output));
        BufferedReader reader=new BufferedReader(new InputStreamReader(input));
        writer.write(type);
        writer.newLine();
        writer.flush();
        String s=reader.readLine();
        return s;
    }
    */

    private static Reply handle(final Request request)throws IOException,ClassNotFoundException{
        oos.writeObject(request);
        oos.flush();
        return (Reply)ois.readObject();
    }
}
