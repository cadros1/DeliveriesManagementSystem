package com.gaohongsen;

/*
 * 此类对user进行了封装，封装数据不包含password，因为password只应在登入时被校验，此后便不应再保存于内存中
 * 
 * @author 高洪森
 * @param id 用户id，为自增主键
 * @param account 用户账号
 * @param name 用户姓名
 * @param permission 用户权限，0为客户，1为普通员工，2为管理员
 */

public class User {
    private int id;
    private String account;
    private String name;
    private int permission;

    public User(int id,String account,String name,int permission) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.permission = permission;
    }

    public int getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public int getPermission() {
        return permission;
    }

    public String getPermissionString() {
        if (permission==1)
            return "用户";
        else if (permission==2)
            return "工作人员";
        else
            return "管理员";
    }
}
