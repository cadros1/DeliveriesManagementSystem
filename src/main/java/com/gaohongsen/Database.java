package com.gaohongsen;

import java.sql.*;

public class Database {
    
    public static void readUsersName ()throws SQLException{
        String JDBC_URL = "jdbc:mysql://localhost:3306/DataMiningProject?useSSL=false&characterEncoding=utf8";
        String JDBC_USER = "root";
        String JDBC_PASSWORD = "800618";

        try(Connection conn=DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD)){
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
}
