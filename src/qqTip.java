import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.qq.base.Cmd;
import com.qq.bean.ID;


@SuppressWarnings("serial")
public class qqTip extends JFrame{
	JLabel myLabel;

	public qqTip(ID myInfo) {
		setUndecorated(true);
		getContentPane().setBackground(Color.YELLOW);
		String str  = myInfo.getNickname()+"("+myInfo.getQqnumber()+")";
		if(myInfo.getState().equals(Cmd.STATUS[0])){
			str +="上线了";
		}else if(myInfo.getState().equals(Cmd.STATUS[1])){
			str +="下线了";
		}else if(myInfo.getState().equals(Cmd.STATUS[3])){
			str +="下线了";
		}
		String headimg = changeState(myInfo);
		myLabel = new JLabel(str,new ImageIcon(headimg),JLabel.RIGHT);
		
		add(myLabel);
		setAlwaysOnTop(true);
		setSize(200, 100);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int width = toolkit.getScreenSize().width-200;
		int height = toolkit.getScreenSize().height;
		setVisible(true);
		for(int i=0;i<100;i++){
			setLocation(width,height-i);
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		for(int i=100;i>0;i--){
			try{
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		dispose();
	}

	private String changeState(ID myInfo) {
		String status = myInfo.getState();
		String filename=myInfo.getHead();
		String headImg= myInfo.getHead();
		if(status.equals(Cmd.STATUS[0])){
			filename = headImg;
		}else if(status.equals(Cmd.STATUS[1])){
			int pos = headImg.indexOf('.');
			String pre = headImg.substring(0,pos);
			String fix = headImg.substring(pos,headImg.length());
			filename = pre + "_h"+fix;
		}
		return filename;
	}

}
