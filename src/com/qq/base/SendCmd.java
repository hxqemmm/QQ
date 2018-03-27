package com.qq.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Vector;

import com.qq.bean.ID;

public class SendCmd {
	public static void send(SendMsg msg){
		try {
			/**定义Socket**/
			DatagramSocket socket = new DatagramSocket();
			
			/**创建一个字节数组输出流**/
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			/**把对象输出到字节数组中**/
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			/**向字节数组里面写入数据*/
			oos.writeObject(msg);
			oos.flush();
			
			/**把要发送的数据转换为字节数组**/
			byte b[]=bos.toByteArray();
			/**获取好友的IP地址**/
			InetAddress addr = InetAddress.getByName(msg.friendInfo.getIP());
			/**获取好友的接收端口**/
			int port = msg.friendInfo.getPort();
			/**生成发送报**/
			DatagramPacket pack = new DatagramPacket(b,0,b.length,addr,port);
			/**发送*/
			socket.send(pack);
			
			socket.close();
			oos.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public  static void sendAll(Vector<ID> allInfo,ID myInfo,int cmd){
		/**
		 * 给所有QQ好友群发消息
		 * */
		for (ID acc : allInfo) {
			/**
			 * 好友不是离线状态
			 * 才通知
			 * */
			if(!acc.getState().equals(Cmd.STATUS[1]) && acc.getQqnumber()!=myInfo.getQqnumber()){
				SendMsg msg = new SendMsg();
				msg.cmd = cmd;
				msg.myInfo = myInfo;
				msg.friendInfo = acc;  
				send(msg);
			}
		}
		
	}
}
