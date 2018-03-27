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
			/**����Socket**/
			DatagramSocket socket = new DatagramSocket();
			
			/**����һ���ֽ����������**/
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			/**�Ѷ���������ֽ�������**/
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			/**���ֽ���������д������*/
			oos.writeObject(msg);
			oos.flush();
			
			/**��Ҫ���͵�����ת��Ϊ�ֽ�����**/
			byte b[]=bos.toByteArray();
			/**��ȡ���ѵ�IP��ַ**/
			InetAddress addr = InetAddress.getByName(msg.friendInfo.getIP());
			/**��ȡ���ѵĽ��ն˿�**/
			int port = msg.friendInfo.getPort();
			/**���ɷ��ͱ�**/
			DatagramPacket pack = new DatagramPacket(b,0,b.length,addr,port);
			/**����*/
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
		 * ������QQ����Ⱥ����Ϣ
		 * */
		for (ID acc : allInfo) {
			/**
			 * ���Ѳ�������״̬
			 * ��֪ͨ
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
