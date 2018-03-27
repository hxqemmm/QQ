
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class qqBq extends JFrame implements MouseListener{
	
	/**
	 * 表情数组
	 * */
	JLabel [] bqicon;
	/**
	 * 保存文件的路径
	 * */
	String iconlist[];
	qqChat chat;
	public qqBq(qqChat chat,int x,int y) {
		this.chat = chat;
		setUndecorated(true);
		setResizable(false);
		/**
		 * 设置窗口在最前面
		 */
		setAlwaysOnTop(true);
		/**
		 * 获取到当前的内容面板
		 * */
		Container con = getContentPane();
		/**
		 * 设置内容面板的布局管理器为流布局
		 * 设置内容面板的背景颜色为白色
		 * */
		con.setLayout(new FlowLayout(FlowLayout.LEFT));
		con.setBackground(Color.WHITE);
		
		File file = new File("bq");
		
		/**
		 * 获取bq文件夹的所有文件的名称
		*/
		iconlist = file.list();
		/**
		 * 创建一个JLabel的数组，数组的大小就是文件的数量
		 * */
		bqicon = new JLabel[iconlist.length];
		
		/**
		 * 遍历文件名的数组显示qq表情 
		 * */
		for(int i=0;i<iconlist.length;i++){
			/**
			 * 每个JLabel控件显示一个标签图标
			 * */
			bqicon[i]= new JLabel(new ImageIcon("bq/"+iconlist[i]));
			/**
			 * 设置白色的边框颜色，边框为2个像素的宽度
			 * */
			bqicon[i].setBorder(BorderFactory.createLineBorder(Color.WHITE,2));
			/**
			 * 为每一个JLable控件添加鼠标事件
			 * */
			bqicon[i].addMouseListener(this);
			add(bqicon[i]);
		}
		
		setSize(300, 320);
		setVisible(true);
		setLocation(x, y);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public void mouseClicked(MouseEvent e) {
		/**
		 * 如果鼠标双击
		 * */
		if(e.getClickCount()==2){
			/**
			 * 遍历整个数组,看是那一个表情被选中
			 * */
			for(int i=0;i<iconlist.length;i++){
				if(e.getSource()==bqicon[i]){
					/**
					 * 把选中的表情显示到聊天窗口的发送文本框中
					 * insertIcon函数的作用：把图标插入到文本框中
					 * **/
					chat.txtSend.insertIcon(bqicon[i].getIcon());
					/**
					 * 把当前表情窗口关闭
					 * */
					dispose();
					break;
				}
			}

		}
	
	}
	public void mouseEntered(MouseEvent e) {
		/**
		 * 鼠标移动到控件区域的上方时会触发该事件
		 * **/
		for(int i=0;i<iconlist.length;i++){
			if(e.getSource()==bqicon[i]){
				/**
				 * 如果鼠标在该JLabel的上面，就改变该JLabel的边框颜色为蓝色
				 * */
				bqicon[i].setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
				break;
			}
		}
	}
	public void mouseExited(MouseEvent e) {
		/**
		 * 鼠标离开该控件区域的上方时会触发该事件
		 * */
		for(int i=0;i<iconlist.length;i++){
			if(e.getSource()==bqicon[i]){
				/**
				 * 如果鼠标离开该JLabel时，就改变该JLabel的边框颜色为背景颜色(白色)
				 * */
				bqicon[i].setBorder(BorderFactory.createLineBorder(Color.WHITE,2));
				break;
			}
		}
	
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
