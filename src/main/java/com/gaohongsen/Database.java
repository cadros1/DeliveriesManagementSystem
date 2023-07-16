package com.gaohongsen;

import java.sql.*;
import javax.sql.*;
import com.zaxxer.hikari.*;

/*
 * 此类提供了对数据库的访问功能
 * 当前功能：按账号查询名字，按账号查询用户所有信息，新增用户，登录检查，新增日志，更改密码，添加一条物流信息，修改物流信息
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

        config.setJdbcUrl("jdbc:mysql://localhost:3306/dataminingproject?useSSL=false&characterEncoding=utf8");
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
    public static String getUserName(final User user)throws SQLException{
        String name;
        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("SELECT name FROM users WHERE account=?")){
                ps.setString(1,user.getAccount());
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


    //此方法根据账号查询用户信息，并返回带有id/account/name/permission的user对象。需要传入带有account的user对象
    //如果出现错误如查询出错、账号不存在等，均抛出SQLException
    public static User getUserInfo(final User user)throws SQLException{
        int id;
        String name;
        int permission;

        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("SELECT * FROM users WHERE account=?")){
                ps.setString(1,user.getAccount());
                try(ResultSet rs=ps.executeQuery()){
                    rs.next();
                    id=rs.getInt("id");
                    name=rs.getString("name");
                    permission=rs.getInt("permission");
                }
            }
        }
        return new User(id,user.getAccount(),name,permission);
    }


    //此方法用于在users表中新增一个用户，需要传入带account,password,name,permission的user对象
    //如果传入的account已经存在，则抛出一个SQLException
    public static void addUser(final User user)throws SQLException{
        //如果未初始化，则进行一次初始化
        if(!hasInitialized){
            initialize();
        }

        //校验账号是否存在
        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("SELECT account FROM users WHERE account=?")){
                ps.setString(1,user.getAccount());
                try(ResultSet rs=ps.executeQuery()){
                    if(rs.next()){
                        throw new SQLException("用户已存在");
                    }
                }
            }

            //新增用户
            try(PreparedStatement ps=conn.prepareStatement("INSERT INTO users(account,password,name,permission) VALUES(?,?,?,?)")){
                ps.setString(1,user.getAccount());
                ps.setString(2,user.getPassword());
                ps.setString(3,user.getName());
                ps.setInt(4,user.getPermission());
                ps.executeUpdate();
            }
        }
    }


    //此方法用于检查用户是否存在以及密码是否正确，需要传入带account,password的user对象
    //如果传入的account不存在或者account与password不匹配，则抛出一个SQLException
    public static void passwordCheck(final User user)throws SQLException{
        //如果未初始化，则进行一次初始化
        if(!hasInitialized){
            initialize();
        }

        //校验账号是否存在，若存在，校验密码是否正确
        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("SELECT * FROM users WHERE account=?")){
                ps.setString(1,user.getAccount());
                try(ResultSet rs=ps.executeQuery()){
                    if(rs.next()){
                        if(!rs.getString("password").equals(user.getPassword())){
                            throw new SQLException("密码错误");
                        }
                    }else{
                        throw new SQLException("用户不存在");
                    }
                }
            }
        }
    }


    //此方法向logs表中添加一条内容为name,account,time,type的日志，需要传入带name,account的user对象和type，time由程序获取系统时间填充
    //如果数据库操作出错，则抛出SQLException
    public static void addLog(final User user,int type)throws SQLException{
        //如果未初始化，则进行一次初始化
        if(!hasInitialized){
            initialize();
        }

        //添加日志
        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("INSERT INTO logs(name,account,datetime,type) VALUES(?,?,now(),?)")){
                ps.setString(1,user.getName());
                ps.setString(2,user.getAccount());
                ps.setInt(3,type);
                ps.executeUpdate();
            }
        }
    }

    //此方法用于更新用户密码，需要传入带account,password，updatedPassword的user对象
    //如果出错，则抛出一个SQLException
    public static void updatePassword(final User user)throws SQLException{
        //如果未初始化，则进行一次初始化
        if(!hasInitialized){
            initialize();
        }

        //更新密码
        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("UPDATE users SET password=? WHERE account=?")){
                ps.setString(1,user.getUpdatedPassword());
                ps.setString(2,user.getAccount());
                ps.executeUpdate();
            }
        }
    }

    //此方法用于添加一条快递信息，需要传入带sendplace,receiveplace,sender,receiver,situation的delivery对象
    //如果出错，则抛出一个SQLException
    public static void addDelivery(final Delivery delivery)throws SQLException{
        if(!hasInitialized){
            initialize();
        }

        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("INSERT INTO delivaries(sendplace,receiveplace,sender,receiver,situation) VALUES(?,?,?,?,?)")){
                ps.setString(1,delivery.getSendPlace());
                ps.setString(2,delivery.getReceivePlace());
                ps.setString(3,delivery.getSender());
                ps.setString(4,delivery.getReceiver());
                ps.setInt(5,delivery.getSituation());
                ps.executeUpdate();
            }
        }
    }

    //此方法用于获取快递信息，需要传入带id的delivery对象
    //将会返回带id,sendplace,receiveplace,sender,receiver,situation的delivery对象
    //如果出错，则抛出一个SQLException
    public static Delivery getDeliveryInfo(final Delivery delivery)throws SQLException{
        if(!hasInitialized){
            initialize();
        }

        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("SELECT * FROM deliveries WHERE id=?")){
                ps.setInt(1,delivery.getId());
                try(ResultSet rs=ps.executeQuery()){
                    if(rs.next())
                    {
                        return new Delivery(rs.getInt("id"),rs.getString("sendplace"),rs.getString("receiveplace"),rs.getString("sender"),rs.getString("receiver"),rs.getInt("situation"));
                    }else{
                        throw new SQLException("此单号不存在");
                    }
                }
            }
        }
    }

    public static void updateDelivery(final Delivery delivery)throws SQLException{
        if(!hasInitialized){
            initialize();
        }

        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("UPDATE deliveries SET situation=? WHERE id=?")){
                ps.setInt(1,delivery.getSituation());
                ps.setInt(2,delivery.getId());
                ps.executeUpdate();
            }
        }
    }
}