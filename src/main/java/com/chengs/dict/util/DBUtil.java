package com.chengs.dict.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 数据库工具类
 */
public class DBUtil {

    // 获取数据库连接
	public static Connection getConnection(String url,String user,String password) {
	    String driverClassName = "com.mysql.jdbc.Driver";
	    Connection conn = null;
	    try {
	        Class.forName(driverClassName);
	        conn = DriverManager.getConnection(url, user, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}

	// 关闭所有
    public static void closeAll(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            rs = null;
            stmt = null;
            conn = null;
        }
    }
}
