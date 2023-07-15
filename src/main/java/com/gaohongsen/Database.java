package com.gaohongsen;

import java.sql.*;
import javax.sql.*;
import com.zaxxer.hikari.*;

/*
 * 此类提供了对数据库的访问功能，目前包括：按账号查询名字，按账号查询用户所有信息，新增用户，登录检查，新增日志，更改密码
 * 所有方法均为静态方法，不需要实例化对象
 * 使用了Hikari连接池
 * 
 * @author 高洪森
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
    

    //此方法根据账号查询用户姓名，并返回姓名。需要传入account参数
    //如果出现错误如查询出错、账号不存在等，均抛出SQLException
    public static String getUserName(final String account)throws SQLException{
        String name;
        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("SELECT name FROM users WHERE account=?")){
                ps.setString(1,account);
                try(ResultSet rs=ps.executeQuery()){
                    if(rs.next()){
                        name=rs.getString("name");
                    }else{
                        throw new SQLException("账号不存在");
                    }
                }
            }
        }
        return name;
    }


    //此方法根据账号查询用户信息，并返回id/name/permission的字符串。需要传入account参数
    //如果出现错误如查询出错、账号不存在等，均抛出SQLException
    public static String getUserInfo(final String account)throws SQLException{
        int id;
        String name;
        int permission;

        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("SELECT * FROM users WHERE account=?")){
                ps.setString(1,account);
                try(ResultSet rs=ps.executeQuery()){
                    rs.next();
                    id=rs.getInt("id");
                    name=rs.getString("name");
                    permission=rs.getInt("permission");
                }
            }
        }
        return id+"/"+name+"/"+permission;
    }


    //此方法用于在users表中新增一个用户，需要传入account,password,name,permission四个参数
    //如果传入的account已经存在，则抛出一个SQLException
    public static void addUser(final String account,final String password,final String name,final int permission)throws SQLException{
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


    //此方法用于检查用户是否存在以及密码是否正确，需要传入account,password两个参数
    //如果传入的account不存在或者account与password不匹配，则抛出一个SQLException
    public static void passwordCheck(final String account,final String password)throws SQLException{
        //如果未初始化，则进行一次初始化
        if(!hasInitialized){
            initialize();
        }

        //校验账号是否存在，若存在，校验密码是否正确
        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("SELECT * FROM users WHERE account=?")){
                ps.setString(1,account);
                try(ResultSet rs=ps.executeQuery()){
                    if(rs.next()){
                        if(!rs.getString("password").equals(password)){
                            throw new SQLException("密码错误");
                        }
                    }else{
                        throw new SQLException("用户不存在");
                    }
                }
            }
        }
    }


    //此方法向logs表中添加一条日志，内容为name,account,time,type，需要传入name,account,type三个参数，time由程序获取系统时间填充
    //如果数据库操作出错，则抛出SQLException
    public static void addLog(final String name,final String account,final int type)throws SQLException{
        //如果未初始化，则进行一次初始化
        if(!hasInitialized){
            initialize();
        }

        //添加日志
        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("INSERT INTO logs(name,account,datetime,type) VALUES(?,?,now(),?)")){
                ps.setString(1,name);
                ps.setString(2,account);
                ps.setInt(3,type);
                ps.executeUpdate();
            }
        }
    }

    //此方法用于更新用户密码，需要传入account,password两个参数
    //如果出错，则抛出一个SQLException
    public static void updatePassword(final String account,final String password)throws SQLException{
        //如果未初始化，则进行一次初始化
        if(!hasInitialized){
            initialize();
        }

        //更新密码
        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("UPDATE users SET password=? WHERE account=?")){
                ps.setString(1,password);
                ps.setString(2,account);
                ps.executeUpdate();
            }
        }
    }
}