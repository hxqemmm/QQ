package com.qq.daoimp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Vector;

import com.qq.base.Cmd;
import com.qq.base.DBConn;
import com.qq.bean.ID;
import com.qq.dao.IDDao;

public class IDdaoImp implements IDDao{
	
	static String sql = null;  
    static DBConn db1 = null;  
    static ResultSet ret = null;  
	
	private int getQQnumber(){
		int qqnumber = 0;
		Random r = new Random();
		Boolean isExist = true;
		/**
		 * 随机生成一个五位数
		 * */
		while(isExist){
			qqnumber = r.nextInt(89999)+10000;
			String sql = "select * From ID where qqnumber=?";
			Connection con = DBConn.openDB();
			try {
				PreparedStatement p = con.prepareStatement(sql);
				p.setInt(1,qqnumber);
				ResultSet rs = p.executeQuery();
				if(!rs.next()){
					isExist = false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return qqnumber;      
	}
	
	public String save(ID a) {
		Connection con = DBConn.openDB();
		int qqnumber = 0;
		
		String sql = "insert into ID values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement p = con.prepareStatement(sql);
			qqnumber  = getQQnumber();
			p.setInt(1,qqnumber);
			p.setString(2,a.getNickname());
			p.setString(3,a.getHead());
			p.setString(4,a.getPassword());
			p.setString(6, a.getSex());
			p.setString(5,  a.getYear()+"-"+a.getMonth()+"-"+a.getDay());
			p.setString(7, a.getNation());
			p.setString(8,a.getBlood());
			p.setString(9,a.getHobby());
			p.setString(10, a.getIP());
			p.setInt(11,1111);
			p.setString(12,"离线");
			p.setString(13, a.getSignature());
			p.executeUpdate();    
			p.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Integer(qqnumber).toString();
	}
	
	private int getPort(){
		int port = 0;
		Random r = new Random();
		Boolean isExist = true;
		/**
		 * 随机生成一个端口号
		 * */
		while(isExist){
			port = r.nextInt(65535-1024)+1024;
			String sql = "select * from ID where port=?";
			Connection con = DBConn.openDB();
			try {
				PreparedStatement p = con.prepareStatement(sql);
				p.setInt(1,port);
				ResultSet rs = p.executeQuery();
				if(!rs.next()){
					isExist = false;
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return port;  
	}
	
	public ID longin(ID a){
		Connection con=DBConn.openDB();
		ID id=null;
		String sql="select * from ID where qqnumber=? and password=?";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1,a.getQqnumber());
			ps.setString(2, a.getPassword());
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()){
				id=new ID();
				id.setQqnumber(rs.getInt("qqnumber"));
				id.setNickname(rs.getString("nickname"));
				id.setHead(rs.getString("head"));
				id.setPassword(rs.getString("password"));
				id.setBirthday(rs.getString("birthday"));
				id.setSex(rs.getString("sex"));
				id.setNation(rs.getString("nation"));
				id.setBlood(rs.getString("blood"));
				id.setHobby(rs.getString("hobby"));
				id.setSignature(rs.getString("signature"));
				//获取当前IP
				InetAddress ip = InetAddress.getLocalHost();
				id.setIP(ip.getHostAddress());
				// * 给登入用户分配一个端口号
				int port = getPort();
				id.setPort(port);
				id.setState("在线");	//更改状态
				String sqlupdate="update ID set IP=?, port=?,state=? where qqnumber=? ";
				PreparedStatement pu=con.prepareStatement(sqlupdate);
				pu.setString(1, id.getIP());
				pu.setInt(2, id.getPort());
				pu.setString(3, id.getState());
				pu.setInt(4, id.getQqnumber());
				pu.executeUpdate();
				
				pu.close();		//操作对象关闭
				rs.close();		//结果集
				ps.close();
				con.close();	//连接关闭
			}else{
				id=null;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return id;
		
	}

	public Vector<ID> getMyFriend(int qqnumber) {
		String sql = "select a.*,f.groupname from ID a inner join Friend f on a.qqnumber=f.yid where f.mid=?";
		Vector<ID> vmyfirend=new Vector<ID>();
		Connection conn=DBConn.openDB();
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1,qqnumber);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				ID id = new ID();
				id.setQqnumber(rs.getInt("qqnumber"));
				id.setNickname(rs.getString("nickname").trim());
				id.setHead(rs.getString("head").trim());
				id.setPassword(rs.getString("password"));
				id.setBirthday(rs.getString("birthday").trim());
				id.setSex(rs.getString("sex").trim());
				id.setNation(rs.getString("nation").trim());
				id.setBlood(rs.getString("blood").trim());
				id.setHobby(rs.getString("hobby").trim());
				id.setIP(rs.getString("IP"));
				id.setPort(rs.getInt("port"));
				id.setState(rs.getString("state"));
				id.setSignature(rs.getString("signature"));	
				id.setGroupname(rs.getString("groupName").trim());
				vmyfirend.add(id);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vmyfirend;
	}

	//修改列表实现分组
	public void moveGroup(int qqnumber, int friendnumber, String groudname) {
		String sql ="update friend set groupname=? where mid=? and yid=?";
		try{
			Connection conn = DBConn.openDB();
			PreparedStatement ps = conn.prepareStatement(sql);
			int i=1;
			ps.setString(i++,groudname);
			ps.setInt(i++, qqnumber);
			ps.setInt(i++, friendnumber);
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//更改数据库中QQ的状态
	public void changeStatus(int qqnumber, String state) {
		String sql ="update ID set state=? where qqnumber=?";
		Connection conn = DBConn.openDB();
		try{
			int i=1;
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(i++, state);
			ps.setInt(i++, qqnumber);
			i=ps.executeUpdate();
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector<Vector> findFriend(String sql) {
		Vector<Vector> vData = new Vector<Vector>();
		Connection conn = DBConn.openDB();
		try{
			Statement ps = conn.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				Vector rowData = new Vector();
				rowData.addElement(rs.getString("head").trim());
				rowData.addElement(rs.getInt("qqnumber"));
				rowData.addElement(rs.getString("nickname").trim());
				rowData.addElement(rs.getString("birthday").trim());
				rowData.addElement(rs.getString("sex").trim());
				rowData.addElement(rs.getString("nation").trim());
				rowData.addElement(rs.getString("blood").trim());
				rowData.addElement(rs.getString("hobby").trim());
				rowData.addElement(rs.getString("state").trim());
				rowData.addElement(rs.getString("signature"));
				vData.addElement(rowData);
			}
			ps.close();
		}catch(Exception e){	
			e.printStackTrace();
		}
		return vData;
	}

	public ID getSelectedFriend(int myqqnumber) {
		String sql = "select * from ID where qqnumber=?";
		ID id = new ID(); 
		Connection conn = DBConn.openDB();  
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, myqqnumber);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				id.setQqnumber(rs.getInt("qqnumber"));
				id.setNickname(rs.getString("nickname").trim());
				id.setHead(rs.getString("head").trim());
				id.setPassword(rs.getString("password"));
				id.setBirthday(rs.getString("birthday").trim());
				id.setSex(rs.getString("sex").trim());
				id.setNation(rs.getString("nation").trim());
				id.setBlood(rs.getString("blood").trim());
				id.setHobby(rs.getString("hobby").trim());
				id.setIP(rs.getString("IP"));
				id.setPort(rs.getInt("port"));
				id.setState(rs.getString("state"));
				id.setSignature(rs.getString("signature"));	
				//id.setGroupname(rs.getString("groupName").trim());
			}
			ps.close();
		}catch(Exception e){	
			e.printStackTrace();
		}
		return id;
	}

	public boolean isFriend(int myqqnumber, int friendqqnumber) {
		String sql = "select * from Friend where mid=? and yid=?";
		boolean isok=false;
		Connection conn = DBConn.openDB();
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, myqqnumber);
			pstmt.setInt(2, friendqqnumber);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				isok=true;
			}
			pstmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return isok;
	}

	public void addFriend(int myqqnumber, int friendqqnumber) {
		try{
			Connection conn = DBConn.openDB();
			String sql ="insert into Friend(mid,yid,groupname) values(?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, myqqnumber);
			pstmt.setInt(2, friendqqnumber);
			pstmt.setString(3, Cmd.GROUPNAME[0]);
			pstmt.executeUpdate();
			pstmt.setInt(1, friendqqnumber);
			pstmt.setInt(2, myqqnumber);
			pstmt.setString(3, Cmd.GROUPNAME[0]);
			pstmt.executeUpdate();  
			pstmt.close();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(myqqnumber+","+friendqqnumber+","+Cmd.GROUPNAME[0]);
		}
	}

	@Override
	public ID updateID(ID id) {
		String sql ="update ID set nickname=?,head=?,password=?,birthday=?,sex=?,blood=?,nation=?,hobby=?,signature=? where qqnumber=?";
		Connection conn = DBConn.openDB();
		try{
			int i=1;
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(i++, id.getNickname());
			pstmt.setString(i++, id.getHead());
			pstmt.setString(i++, id.getPassword());
			//pstmt.setInt(i++, id.getAge());
			pstmt.setString(i++,  id.getYear()+"-"+id.getMonth()+"-"+id.getDay());
			pstmt.setString(i++, id.getSex());
			//pstmt.setString(i++, id.getStar());
			pstmt.setString(i++, id.getBlood());
			pstmt.setString(i++, id.getNation());
			pstmt.setString(i++, id.getHobby());
			pstmt.setString(i++, id.getSignature());
			pstmt.setInt(i++, id.getQqnumber());
			i=pstmt.executeUpdate();
			pstmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}

	public ID getDelFriend(int mid, int yid) {
		String sql="delete from Friend where (mid=? and yid=?) or (mid=? and yid=?)";
		Connection conn = DBConn.openDB();
		try{
			int i=1;
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(i++,mid);
			ps.setInt(i++,yid);
			ps.setInt(i++,yid);
			ps.setInt(i++,mid);
			i=ps.executeUpdate();  
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(mid+","+yid);
		}
		return null;
	}
	
}
