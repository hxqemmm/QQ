package com.qq.bean;

import java.util.Date;

public class News {			//��Ϣ��

	private int id;									//--���
	private String news;							//--��Ϣ����
	private int Mnumber;							//--ԭ�˺�
	private int Ynumber;							//--Ŀ���˺�
	private Date sends;							//--����ʱ��
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
