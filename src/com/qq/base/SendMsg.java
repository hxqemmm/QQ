package com.qq.base;

import java.io.Serializable;

import javax.swing.text.StyledDocument;

import com.qq.bean.ID;

/**
 * 发送数据
 * */
@SuppressWarnings("serial")
public class SendMsg implements Serializable{

	/**发送消息的类型*/
	public int cmd;
	/**发送人信息*/
	public ID myInfo;
	/**接收人信息*/
	public ID friendInfo;
	/**发送的内容*/
	public StyledDocument doc;
	/**发送文件内容在64K以下*/
	public byte b[];
	
	public String fileName;
}
