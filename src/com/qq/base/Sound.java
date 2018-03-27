package com.qq.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

public class Sound {   

	public Sound(int cmd) {
		try {
			String filename="sound/Audio.wav";
			switch(cmd){
			case Cmd.CMD_ONLINE:
				filename="sound/system.wav";
				break;
			case Cmd.CMD_SEND:
				filename="sound/msg.wav";
				break;
			}
			File file = new File(filename);
			InputStream is = new FileInputStream(file);
			int size = is.available();
			byte b[] = new byte[size];
			is.read(b, 0, size);
			 //* ׼�����ŵ���Դ
			AudioData ad = new AudioData(b);
			// * ׼�����ŵ��豸
			AudioDataStream ds = new AudioDataStream(ad);
			// * ���豸����׼���õ���Դ
			AudioPlayer.player.start(ds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Sound(Cmd.CMD_ARGEE);
	}
}
