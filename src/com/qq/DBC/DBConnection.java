package com.qq.DBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {		//工具类之  数据库连接
	
	private String name = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1/QQ";
	private String user = "root";
	private String password = "root";
	
	public Connection conn = null;  
    public PreparedStatement pst = null;  
	
	/*public Connection getConnection() throws SQLException, ClassNotFoundException{
		Class.forName(name);
		Connection con = DriverManager.getConnection(url, user, password);
		return con;
	}*/
	
	
    public DBConnection(String sql) {  
        try {  
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
            pst = conn.prepareStatement(sql);//准备执行语句  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    } 
}
