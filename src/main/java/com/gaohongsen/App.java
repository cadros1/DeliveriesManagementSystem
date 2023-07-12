package com.gaohongsen;

import java.sql.*;

public class App 
{
    public static void main ( String[] args ){
        try{
            Database.readUsersName();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
