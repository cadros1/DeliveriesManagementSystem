package com.gaohongsen;

import java.io.*;
import java.net.*;
import java.util.*;

/*
 * 此类为客户端，用于与服务端通信，发送请求并接收服务端的数据
 * 在目前的设计下请求应被整合为一条长字符串，并在串首添加一个int值为请求的类型
 */
public class Client {
    public static String sendRequest(String request)throws IOException{
        Socket sock=new Socket("localhost",6666);
        String answer;
        try(InputStream input=sock.getInputStream()){
            try(OutputStream output=sock.getOutputStream()){
                answer=handle(input,output,request);
            }
        }
        sock.close();
        return answer;
    }

    private static String handle(InputStream input,OutputStream output,String request)throws IOException{
        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(output));
        BufferedReader reader=new BufferedReader(new InputStreamReader(input));
        Scanner scanner=new Scanner(System.in);
        System.out.println("[server] " +reader.readLine());
        System.out.print(">>>");
        String s=scanner.nextLine();
        writer.write(s);
        writer.newLine();
        writer.flush();
        return reader.readLine();
    }
}
