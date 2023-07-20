package com.gaohongsen;

import java.io.FileOutputStream;
import java.sql.*;
import java.util.*;
import javax.sql.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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


    //此方法用于初始化Hikari连接池，只可调用一次，后续调用无效果
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
                if(ps.executeUpdate()!=1){
                    throw new SQLException("更新密码出错！");
                }
            }
        }
    }


    //此方法用于添加一条快递信息，需要传入带sendplace,receiveplace,sender,receiver,situation的delivery对象
    public static Delivery addDelivery(final Delivery delivery)throws SQLException{
        if(!hasInitialized){
            initialize();
        }

        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("INSERT INTO deliveries(sendplace,receiveplace,sender,receiver,situation) VALUES(?,?,?,?,?)")){
                ps.setString(1,delivery.getSendPlace());
                ps.setString(2,delivery.getReceivePlace());
                ps.setString(3,delivery.getSender());
                ps.setString(4,delivery.getReceiver());
                ps.setInt(5,delivery.getSituation());
                ps.executeUpdate();
                try(PreparedStatement ps2=conn.prepareStatement("SELECT id FROM deliveries order by id desc limit 1")){
                    try(ResultSet rs=ps2.executeQuery()){
                        rs.next();
                        Delivery d=new Delivery(rs.getInt("id"),delivery.getSendPlace(),delivery.getReceivePlace(),delivery.getSender(),delivery.getReceiver(),delivery.getSituation());
                        return d;
                    }
                }
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
                        Delivery d=new Delivery(rs.getInt("id"),rs.getString("sendplace"),rs.getString("receiveplace"),rs.getString("sender"),rs.getString("receiver"),rs.getInt("situation"));
                        return d;
                    }else{
                        throw new SQLException("此单号不存在");
                    }
                }
            }
        }
    }


    //此方法用于修改一条物流的信息，需要传入带id的delivery对象
    public static void updateDelivery(final Delivery delivery)throws SQLException{
        if(!hasInitialized){
            initialize();
        }

        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("UPDATE deliveries SET sendplace=?,receiveplace=?,sender=?,receiver=?,situation=? WHERE id=?")){
                ps.setString(1,delivery.getSendPlace());
                ps.setString(2,delivery.getReceivePlace());
                ps.setString(3,delivery.getSender());
                ps.setString(4,delivery.getReceiver());
                ps.setInt(5,delivery.getSituation());
                ps.setInt(6,delivery.getId());
                ps.executeUpdate();
            }
        }
    }


    //此方法用于删除一条物流的信息，需要传入带id的delivery对象
    public static void deleteDelivery(final Delivery delivery)throws SQLException{
        if(!hasInitialized){
            initialize();
        }

        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("DELETE FROM deliveries WHERE id=?")){
                ps.setInt(1,delivery.getId());
                ps.executeUpdate();
            }
        }
    }


    //此方法用于获取最近30条所有物流信息，将会返回一个Vector<Delivery>对象
    public static Vector<Delivery> displayDeliveries()throws SQLException{
        Vector<Delivery> deliveries=new Vector<Delivery>();
        if(!hasInitialized){
            initialize();
        }

        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("SELECT * FROM deliveries order by id desc limit 30")){
                try(ResultSet rs=ps.executeQuery()){
                    while(rs.next()){
                        deliveries.add(new Delivery(rs.getInt("id"),rs.getString("sendplace"),rs.getString("receiveplace"),rs.getString("sender"),rs.getString("receiver"),rs.getInt("situation")));
                    }
                    return deliveries;
                }
            }        
        }
    }


    //此方法用于获取最近30条所有日志信息，将会返回一个Vector<Log>对象
    public static Vector<Log> displayLogs()throws SQLException{
        Vector<Log> logs=new Vector<Log>();
        if(!hasInitialized){
            initialize();
        }

        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("SELECT * FROM logs order by id desc limit 30")){
                try(ResultSet rs=ps.executeQuery()){
                    while(rs.next()){
                        logs.add(new Log(rs.getInt("id"),rs.getString("name"),rs.getString("account"),rs.getString("datetime"),rs.getInt("type")));
                    }
                    return logs;
                }
            }        
        }
    }


    //此方法用于导出最近30条物流信息到Excel表格
    public static void outputDeliveries()throws Exception{
        if(!hasInitialized){
            initialize();
        }

        String path="C:\\Users\\16272\\Desktop\\";

        Workbook workbook=new XSSFWorkbook();
        Sheet sheet=workbook.createSheet("物流信息");
        Row row0=sheet.createRow(0);
        row0.createCell(0).setCellValue("单号");
        row0.createCell(1).setCellValue("发货地");
        row0.createCell(2).setCellValue("收货地");
        row0.createCell(3).setCellValue("发货人");
        row0.createCell(4).setCellValue("收货人");
        row0.createCell(5).setCellValue("状态");

        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("SELECT * FROM deliveries order by id desc limit 30")){
                try(ResultSet rs=ps.executeQuery()){
                    int row=1;
                    while(rs.next()){
                        Row r=sheet.createRow(row);
                        r.createCell(0).setCellValue(rs.getInt("id"));
                        r.createCell(1).setCellValue(rs.getString("sendplace"));
                        r.createCell(2).setCellValue(rs.getString("receiveplace"));
                        r.createCell(3).setCellValue(rs.getString("sender"));
                        r.createCell(4).setCellValue(rs.getString("receiver"));
                        r.createCell(5).setCellValue(Delivery.turnToString(rs.getInt("situation")));
                        row++;
                    }
                }
            }
        }
        FileOutputStream fos=new FileOutputStream(path+"物流信息.xlsx");
        workbook.write(fos);
        fos.close();
        workbook.close();
    }


    //此方法用于导出最近30条日志信息到Excel表格
    public static void outputLogs()throws Exception{
        if(!hasInitialized){
            initialize();
        }

        String path="C:\\Users\\16272\\Desktop\\";

        Workbook workbook=new XSSFWorkbook();
        Sheet sheet=workbook.createSheet("日志信息");
        Row row0=sheet.createRow(0);
        row0.createCell(0).setCellValue("编号");
        row0.createCell(1).setCellValue("姓名");
        row0.createCell(2).setCellValue("账号");
        row0.createCell(3).setCellValue("时间");
        row0.createCell(4).setCellValue("类型");

        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("SELECT * FROM logs order by id desc limit 30")){
                try(ResultSet rs=ps.executeQuery()){
                    int row=1;
                    while(rs.next()){
                        Row r=sheet.createRow(row);
                        r.createCell(0).setCellValue(rs.getInt("id"));
                        r.createCell(1).setCellValue(rs.getString("name"));
                        r.createCell(2).setCellValue(rs.getString("account"));
                        r.createCell(3).setCellValue(rs.getString("datetime"));
                        r.createCell(4).setCellValue(Log.turnToString(rs.getInt("type")));
                        row++;
                    }
                }
            }
        }
        FileOutputStream fos=new FileOutputStream(path+"日志信息.xlsx");
        workbook.write(fos);
        fos.close();
        workbook.close();
    }


    //此方法用于注销账号
    public static void deleteUser(final User user)throws SQLException{
        if(!hasInitialized){
            initialize();
        }

        try(Connection conn=ds.getConnection()){
            try(PreparedStatement ps=conn.prepareStatement("DELETE FROM users WHERE id=?")){
                ps.setInt(1,user.getId());
                ps.executeUpdate();
            }
        }
    }
}