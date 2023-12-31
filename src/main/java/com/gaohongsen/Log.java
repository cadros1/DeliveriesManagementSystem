package com.gaohongsen;

/**
 * 此类对log进行了封装<p>
 * 实例化对象时需根据需求选择合适的构造方法传入参数，构造方法选择不当将会导致异常
 * 
 * @author 高洪森
 * @param id 日志id，为自增主键
 * @param name 用户姓名
 * @param account 用户账号
 * @param datetime 行为时间
 * @param type 行为类型，0为登入，1为登出，2为注册，3为修改密码
 */
public class Log implements java.io.Serializable{
    private final int id;
    private String name;
    private String account;
    private String datetime;
    private int type;

    public Log(int id,String name,String account,String datetime,int type) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.datetime = datetime;
        this.type = type;
    }

    public Log(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public String getDatetime() {
        return datetime;
    }

    //将type转换为String
    //提供给持有对象的情况下使用
    public String getTypeString(){
        switch(this.type){
            case 0:
                return "登入";
            case 1:
                return "登出";
            case 2:
                return "注册";
            case 3:
                return "修改密码";
            case 4:
                return "新建物流";
            case 5:
                return "修改物流";
            case 6:
                return "删除物流";
            case 7:
                return "注销账号";
            default:
                return "未知";
        }
    }

    //将type转换为String
    //提供给不持有对象的情况下使用
    public static String turnToString(int type){
        switch(type){
            case 0:
                return "登入";
            case 1:
                return "登出";
            case 2:
                return "注册";
            case 3:
                return "修改密码";
            case 4:
                return "新建物流";
            case 5:
                return "修改物流";
            case 6:
                return "删除物流";
            case 7:
                return "注销账号";
            default:
                return "未知";
        }
    }
}
