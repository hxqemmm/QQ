package com.qq.dao;

import java.util.Vector;
import com.qq.bean.ID;

public interface  IDDao {
	public String save(ID a);
	public ID longin(ID a);
	public Vector<ID> getMyFriend(int qqnumber);
	public void moveGroup(int qqnumber,int friendnumber,String groudname);
	public void changeStatus(int qqnumber,String state);
	
	@SuppressWarnings("rawtypes")
	public Vector<Vector> findFriend(final String sql);
	public ID getSelectedFriend(int myqqnumber);
	public boolean isFriend(int myqqnumber,int friendqqnumber);
	public void addFriend(int myqqnumber,int friendqqnumber);
	public ID updateID(ID id);
	public ID getDelFriend(int myqqnumber, int friendnumber);
}
