# 基于Swing的数据挖掘项目

本项目使用Maven构建，src文件夹中存放源代码，target文件夹中存放编译好的文件，pom.xml用于管理依赖。

请先阅读各个类的注释。

## 对于数据库

目前存在users和logs、logistics三个表。

users用于储存用户信息，有id/INT、account/VARCHAR(20)、password/VARCHAR(20)、name/VARCHAR(20)、permission/TINYINT五个字段，其中id为自增主键，permission为用户权限。

logs用于储存日志信息，有id/INT、name/VARCHAR(20)、account/VARCHAR(20)、datetime/DATETIME、type/TINYINYT五个字段，其中id为自增主键，type为操作类型（0-登入，1-登出，2-注册）

deliveries用于储存物流信息，有id/INT、sendplace/VAHRCHAR(30)、receiveplace/VARCHAR(30)、sender(20)、receiver(20)、situation/TINYINT七个字段，其中id为自增主键，number为物流单号，situation为物流状态（0-未发货，1-以揽收，2-已发货，3-已到货，4-已签收）
