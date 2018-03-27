package com.qq.bean;

import java.util.Date;

public class News {			//消息表

	private int id;									//--编号
	private String news;							//--消息类型
	private int Mnumber;							//--原账号
	private int Ynumber;							//--目标账号
	private Date sends;							//--发送时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNews() {
		return news;
	}
	public void setNews(String news) {
		this.news = news;
	}
	public int getMnumber() {
		return Mnumber;
	}
	public void setMnumber(int mnumber) {
		Mnumber = mnumber;
	}
	public int getYnumber() {
		return Ynumber;
	}
	public void setYnumber(int ynumber) {
		Ynumber = ynumber;
	}
	public Date getSends() {
		return sends;
	}
	public void setSends(Date sends) {
		this.sends = sends;
	}
	
}
