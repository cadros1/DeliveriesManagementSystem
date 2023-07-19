package com.gaohongsen;

/*
 * 此类对user进行了封装
 * 实例化对象时请根据需求选择合适的构造方法传入参数，构造方法选择不当将会导致异常
 *
 * @author 高洪森
 * @param id 用户id，为自增主键
 * @param account 用户账号
 * @param password 用户密码
 * @param updatedPassword 用户修改后的密码
 * @param name 用户姓名
 * @param permission 用户权限，0为客户，1为普通员工，2为管理员
 */

public class User implements java.io.Serializable {
    private int id;
    private  String account;
    private String password;
    private String updatedPassword;
    private String name;
    private int permission;

    //登入时用此构造方法
    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    //登出时用此构造方法
    public User(String account) {
        this.account = account;
    }

    //注册时用此构造方法
    public User(String account, String password, String name, int permission) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.permission = permission;
    }

    //修改密码时用此构造方法
    //使用时请将旧密码传入password，新密码传入updatedPassword
    public User(String account, String password, String updatedPassword) {
        this.account = account;
        this.password = password;
        this.updatedPassword = updatedPassword;
    }

    //提供给getUserInfo方法使用
    public User(int id, String account, String name, int permission) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.permission = permission;
    }

    public int getId() {
        return this.id;
    }

    public String getAccount() {
        return this.account;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUpdatedPassword() {
        return this.updatedPassword;
    }

    public String getName() {
        return this.name;
    }

    public int getPermission() {
        return this.permission;
    }

    public String getPermissionString() {
        switch(this.permission) {
            case 0:
                return "客户";
            case 1:
                return "普通员工";
            case 2:
                return "管理员";
            default:
                return "未知";
        }
    }
}
