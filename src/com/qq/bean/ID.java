package com.qq.bean;

import java.io.Serializable;

public class ID implements Serializable{		//用户账号表
	private static final long serialVersionUID = 1L;
	private String nickname;				//--昵称
	private int qqnumber;					//--qq号码
	private String password;				//--qq密码
	private String head;					//--头像
	private String nation;					//--民族
	private String sex;						//--性别
	//private int age;						//--年龄
	private String birthday;				//--生日
	private String groupname;				//朋友分组
	
	
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	private String year;
	private String month;
	private String day;
	//private String star;					//--星座
	private String hobby;					//爱好
	private String state;					//--状态
	private String IP;						//ip地址
	private int port;						//端口号
	private String blood;					//--血型
	private float active;					//--活跃天数
	private String signature;				//--个性签名
	
	
	public int getQqnumber() {
		return qqnumber;
	}
	public void setQqnumber(int qqnumber) {
		this.qqnumber = qqnumber;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}

	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getBlood() {
		return blood;
	}
	public void setBlood(String blood) {
		this.blood = blood;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getNumber() {
		return qqnumber;
	}
	public void setNumber(int number) {
		this.qqnumber = number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public float getActive() {
		return active;
	}
	public void setActive(float active) {
		this.active = active;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
	
}
