# 基于Swing的数据挖掘项目

本项目使用Maven构建，src文件夹中存放源代码，target文件夹中存放编译好的文件，pom.xml用于管理依赖。

请先阅读各个类的注释。

## 对于数据库

目前存在users和log两个表。

users用于储存用户信息，有id/INT、account/VARCHAR(20)、password/VARCHAR(20)、name/VARCHAR(20)、permission/TINYINT五个字段，其中id为自增主键，permission为用户权限。

log用于储存日志信息，有id/INT、account/VARCHAR(20)、name/VARCHAR(20)、time/DATETIME、type/TINYINYT五个字段，其中id为自增主键，type为操作类型（0为登出，1为登入）
