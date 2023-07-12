package com.gaohongsen;

import java.sql.*;
import javax.sql.*;
import com.zaxxer.hikari.*;

/*
 * 这个类提供了对数据库的访问功能，目前包括：新增用户，登录检查
 * 所有方法均为静态方法，不需要实例化对象
 */
public class Database {

    private static HikariConfig config=new HikariConfig();
    private static DataSource ds;
    private static boolean hasInitialized=false;

    //不允许实例化对象
    private Database(){}


    //此方法用于初始化数据库连接池，只可调用一次，后续调用无效果
    private static void initialize(){
        if(hasInitialized){
            return;
        }

        config.setJdbcUrl("jdbc:mysql://localhost:3306/DataMiningProject?useSSL=false&characterEncoding=utf8");
        config.setUsername("root");
        config.setPassword("800618");
        config.addDataSourceProperty("connectionTimeout", "1000"); // 连接超时：1秒
        config.addDataSourceProperty("idleTimeout", "60000"); // 空闲超时：60秒
        config.addDataSourceProperty("maximumPoolSize", "10"); // 最大连接数：10
        ds=new HikariDataSource(config);

        hasInitialized=true;
    }
    
    
    public static void readUsersName()throws SQLException{
        //示例功能，请勿使用
        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("SELECT name FROM users")){
                try(ResultSet rs=ps.executeQuery()){
                    while(rs.next()){
                        String name=rs.getString("name");
                        System.out.println(name);
                    }
                }
            }
        }
    }


    //此方法用于在users表中新增一个用户，需要传入account,password,name,permission四个参数
    //如果传入的account已经存在，则抛出一个SQLException
    public static void createUser(String account,String password,String name,int permission)throws SQLException{
        //如果未初始化，则进行一次初始化
        if(!hasInitialized){
            initialize();
        }

        //校验账号是否存在
        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("SELECT account FROM users WHERE account=?")){
                ps.setString(1,account);
                try(ResultSet rs=ps.executeQuery()){
                    if(rs.next()){
                        throw new SQLException("用户已存在");
                    }
                }
            }

            //新增用户
            try(PreparedStatement ps=conn.prepareStatement("INSERT INTO users(account,password,name,permission) VALUES(?,?,?,?)")){
                ps.setString(1,account);
                ps.setString(2,password);
                ps.setString(3,name);
                ps.setInt(4,permission);
                ps.executeUpdate();
            }
        }
    }


    //此方法用于在登陆时检查用户是否存在以及密码是否正确，需要传入account,password两个参数
    //如果传入的account不存在或者account与password不匹配，则抛出一个SQLException
    public static int loginCheck(String account,String password)throws SQLException{
        //如果未初始化，则进行一次初始化
        if(!hasInitialized){
            initialize();
        }

        //校验账号是否存在，若存在，校验密码是否正确
        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("SELECT password FROM users WHERE account=?")){
                ps.setString(1,account);
                try(ResultSet rs=ps.executeQuery()){
                    if(!rs.next()){
                        throw new SQLException("用户不存在");
                    }
                    if(!password.equals(rs.getString("password"))){
                        throw new SQLException("密码错误");
                    }
                    int id=rs.getInt("id");
                    return id;
                }
            }
        }
    }
}
