
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
	 * ��������
	 * */
	JLabel [] bqicon;
	/**
	 * �����ļ���·��
	 * */
	String iconlist[];
	qqChat chat;
	public qqBq(qqChat chat,int x,int y) {
		this.chat = chat;
		setUndecorated(true);
		setResizable(false);
		/**
		 * ���ô�������ǰ��
		 */
		setAlwaysOnTop(true);
		/**
		 * ��ȡ����ǰ���������
		 * */
		Container con = getContentPane();
		/**
		 * �����������Ĳ��ֹ�����Ϊ������
		 * �����������ı�����ɫΪ��ɫ
		 * */
		con.setLayout(new FlowLayout(FlowLayout.LEFT));
		con.setBackground(Color.WHITE);
		
		File file = new File("bq");
		
		/**
		 * ��ȡbq�ļ��е������ļ�������
		*/
		iconlist = file.list();
		/**
		 * ����һ��JLabel�����飬����Ĵ�С�����ļ�������
		 * */
		bqicon = new JLabel[iconlist.length];
		
		/**
		 * �����ļ�����������ʾqq���� 
		 * */
		for(int i=0;i<iconlist.length;i++){
			/**
			 * ÿ��JLabel�ؼ���ʾһ����ǩͼ��
			 * */
			bqicon[i]= new JLabel(new ImageIcon("bq/"+iconlist[i]));
			/**
			 * ���ð�ɫ�ı߿���ɫ���߿�Ϊ2�����صĿ��
			 * */
			bqicon[i].setBorder(BorderFactory.createLineBorder(Color.WHITE,2));
			/**
			 * Ϊÿһ��JLable�ؼ��������¼�
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
		 * ������˫��
		 * */
		if(e.getClickCount()==2){
			/**
			 * ������������,������һ�����鱻ѡ��
			 * */
			for(int i=0;i<iconlist.length;i++){
				if(e.getSource()==bqicon[i]){
					/**
					 * ��ѡ�еı�����ʾ�����촰�ڵķ����ı�����
					 * insertIcon���������ã���ͼ����뵽�ı�����
					 * **/
					chat.txtSend.insertIcon(bqicon[i].getIcon());
					/**
					 * �ѵ�ǰ���鴰�ڹر�
					 * */
					dispose();
					break;
				}
			}

		}
	
	}
	public void mouseEntered(MouseEvent e) {
		/**
		 * ����ƶ����ؼ�������Ϸ�ʱ�ᴥ�����¼�
		 * **/
		for(int i=0;i<iconlist.length;i++){
			if(e.getSource()==bqicon[i]){
				/**
				 * �������ڸ�JLabel�����棬�͸ı��JLabel�ı߿���ɫΪ��ɫ
				 * */
				bqicon[i].setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
				break;
			}
		}
	}
	public void mouseExited(MouseEvent e) {
		/**
		 * ����뿪�ÿؼ�������Ϸ�ʱ�ᴥ�����¼�
		 * */
		for(int i=0;i<iconlist.length;i++){
			if(e.getSource()==bqicon[i]){
				/**
				 * �������뿪��JLabelʱ���͸ı��JLabel�ı߿���ɫΪ������ɫ(��ɫ)
				 * */
				bqicon[i].setBorder(BorderFactory.createLineBorder(Color.WHITE,2));
				break;
			}
		}
	
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
