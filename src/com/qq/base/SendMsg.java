package com.qq.base;

import java.io.Serializable;

import javax.swing.text.StyledDocument;

import com.qq.bean.ID;

/**
 * ��������
 * */
@SuppressWarnings("serial")
public class SendMsg implements Serializable{

	/**������Ϣ������*/
	public int cmd;
	/**��������Ϣ*/
	public ID myInfo;
	/**��������Ϣ*/
	public ID friendInfo;
	/**���͵�����*/
	public StyledDocument doc;
	/**�����ļ�������64K����*/
	public byte b[];
	
	public String fileName;
}
