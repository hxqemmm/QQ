package com.qq.bean;

import java.io.Serializable;

public class ID implements Serializable{		//�û��˺ű�
	private static final long serialVersionUID = 1L;
	private String nickname;				//--�ǳ�
	private int qqnumber;					//--qq����
	private String password;				//--qq����
	private String head;					//--ͷ��
	private String nation;					//--����
	private String sex;						//--�Ա�
	//private int age;						//--����
	private String birthday;				//--����
	private String groupname;				//���ѷ���
	
	
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
	//private String star;					//--����
	private String hobby;					//����
	private String state;					//--״̬
	private String IP;						//ip��ַ
	private int port;						//�˿ں�
	private String blood;					//--Ѫ��
	private float active;					//--��Ծ����
	private String signature;				//--����ǩ��
	
	
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
