import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.qq.bean.ID;


@SuppressWarnings("serial")
public class qqLookinfo extends JFrame implements MouseListener{
	private JLabel lbBG;
	private JLabel lbqqnumber,lbnickname,lbhead,lbnation,lbsex,lbbirthday,lbhobby,lbblood,lbsignature;
	
	public qqLookinfo() {}
	public qqLookinfo(ID myInfo) {
		super("�鿴�����û�����");
		setIconImage(new ImageIcon("images/tubiao.jpg").getImage());
		lbBG = new JLabel(new ImageIcon("images/bgreg5.jpg"));
		add(lbBG);
		lbBG.setLayout(null);
		JLabel title = new JLabel("��������",JLabel.CENTER);
		title.setFont(new Font("����",Font.BOLD,36));
		title.setForeground(Color.RED);
		title.setBounds(0,30,260,40);
		lbBG.add(title);
		
		lbqqnumber=new JLabel("QQ���룺"+myInfo.getQqnumber(),JLabel.LEFT);
		lbnickname=new JLabel("�ǳƣ�"+myInfo.getNickname(),JLabel.LEFT);
		lbhead=new JLabel(new ImageIcon(myInfo.getHead()));
		lbbirthday=new JLabel("���գ�"+myInfo.getBirthday(),JLabel.LEFT);
		lbsex = new JLabel("�Ա�:"+myInfo.getSex(),JLabel.LEFT);
		lbnation = new JLabel("����:"+myInfo.getNation(),JLabel.LEFT);
		lbblood = new JLabel("Ѫ��:"+myInfo.getBlood()+"��",JLabel.LEFT);
		lbhobby = new JLabel("����:"+myInfo.getHobby(),JLabel.LEFT);
		lbsignature = new JLabel("��ע:"+myInfo.getSignature(),JLabel.LEFT);
		
		lbqqnumber.setBounds(100, 75, 100, 20);
		lbnickname.setBounds(100, 100, 100, 20);
		lbhead.setBounds(220, 120, 80, 60);
		lbsex.setBounds(100, 140, 80, 20);
		lbnation.setBounds(100, 180, 100, 20);
		lbbirthday.setBounds(100, 220, 100, 20);
		lbblood.setBounds(100, 260, 100, 20);
		lbhobby.setBounds(100, 300,200, 20);
		lbsignature.setBounds(100, 340, 300, 20);
		
		lbBG.add(lbqqnumber);
		lbBG.add(lbnickname);
		lbBG.add(lbhead);
		lbBG.add(lbsex);
		lbBG.add(lbnation);
		lbBG.add(lbbirthday);
		lbBG.add(lbblood);
		lbBG.add(lbhobby);
		lbBG.add(lbsignature);
		
		addMouseListener(this);
		setUndecorated(true);
		setResizable(false);
		setSize(420, 380);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public static void main(String[] args) {
		new qqLookinfo();
	}
	
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {
		dispose();
	}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}
