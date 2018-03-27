package com.qq.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {		//工具类之  数据库连接
	
	private static String drive = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://127.0.0.1/QQ";
	private static String user = "root";
	private static String password = "root";
	private static Connection con=null;
	
	static{			//静态代码块
		try {
			Class.forName(drive);
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection openDB(){		//连接数据库
		try {
			if(con.isClosed()){
			con = DriverManager.getConnection(url, user, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Connection con=DBConn.openDB();
	}
		
}
