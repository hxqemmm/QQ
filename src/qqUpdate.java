import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.qq.bean.ID;
import com.qq.dao.IDDao;
import com.qq.daoimp.IDdaoImp;

@SuppressWarnings("serial")
public class qqUpdate extends JFrame implements ActionListener,ItemListener{
	private JLabel lbBG;
	private JLabel lbqqnumber,lbnickname,lbpassword,lbpass,lbhead,lbnation,lbsex,lbage,
				lbbirthday,lbstar,lbhobby,lbIP,lbport,lbblood,lbsignature;
	private JComboBox chead,cnation,cblood,cstar;
	private JTextField tqqnumber,tnicknime,tage,thobby,tIP,tport;
	private JRadioButton man,woman;
	private JPasswordField ppassword,ppass;			//�����ı������
	private JTextArea tasignature;
	private JButton btsave,btclose;
	private ButtonGroup bg;							//���
	private JComboBox year,month,day;
	private ID myInfo;
	private qqMain mainUI;
	private String sNation[]={
			"����","����","׳��","��ɽ��","����","����",
			"����","����","������","����"
	};
	private String sStar[]={
			"˫����","��ţ��","Ħ����","��Ы��","��Ů��","ʨ����","������",
			"ˮƿ��","������","�����","��з��","˫����",
	};
	private String sBlood[]={"A","B","O","AB"};
	private String sHeadImg[] = {
			"head/0.png","head/1.png","head/2.png",
			"head/3.png","head/4.png","head/5.png",
			"head/6.png","head/7.png","head/8.png",
			"head/9.png","head/10.png","head/11.png"
	};
	private ImageIcon[] headIcon = {
			new ImageIcon(sHeadImg[0]),
			new ImageIcon(sHeadImg[1]),
			new ImageIcon(sHeadImg[2]),
			new ImageIcon(sHeadImg[3]),
			new ImageIcon(sHeadImg[4]),
			new ImageIcon(sHeadImg[5]),
			new ImageIcon(sHeadImg[6]),
			new ImageIcon(sHeadImg[7]),
			new ImageIcon(sHeadImg[8]),
			new ImageIcon(sHeadImg[9]),
			new ImageIcon(sHeadImg[10]),
			new ImageIcon(sHeadImg[11])
	};
	
	static Point origin= new Point();
	
	public qqUpdate(){}
	public qqUpdate(ID myInfo,qqMain mainUI){
		super("�޸ĸ����û�����");
		this.myInfo = myInfo;
		this.mainUI = mainUI;
		setIconImage(new ImageIcon("images/tubiao.png").getImage());	//ͼ��
		lbBG = new JLabel(new ImageIcon("images/bgreg4.jpg"));	//����������ŵ�һ����ǩ�ϣ����������
		add(lbBG);
		lbBG.setLayout(null);
		JLabel title = new JLabel("�޸�����",JLabel.CENTER);
		title.setFont(new Font("����",Font.BOLD,36));
		title.setForeground(Color.YELLOW);
		title.setBounds(0,20,200,30);
		lbBG.add(title);
		
		lbqqnumber = new JLabel("QQ����:",JLabel.RIGHT);
		lbnickname = new JLabel("�ǳ�:",JLabel.RIGHT);
		lbhead = new JLabel("ͷ��:",JLabel.RIGHT);
		lbpassword = new JLabel("��¼����:",JLabel.RIGHT);
		lbpass = new JLabel("ȷ������:",JLabel.RIGHT);
		lbage = new JLabel("����:",JLabel.RIGHT);
		lbsex = new JLabel("�Ա�:",JLabel.RIGHT);
		lbnation = new JLabel("����:",JLabel.RIGHT);
		lbstar = new JLabel("����:",JLabel.RIGHT);
		lbblood = new JLabel("Ѫ��:",JLabel.RIGHT);
		lbhobby = new JLabel("����:",JLabel.RIGHT);
		lbIP = new JLabel("IP��ַ:",JLabel.RIGHT);
		lbport = new JLabel("�˿�:",JLabel.RIGHT);
		lbsignature = new JLabel("����˵��:",JLabel.RIGHT);
		
		tqqnumber = new JTextField(10);
		tqqnumber.setText(myInfo.getQqnumber()+"");
		tqqnumber.setEditable(false);
		tnicknime = new JTextField(10);
		tnicknime.setText(myInfo.getNickname()+"");
		chead = new JComboBox(headIcon);
		for(int i=0;i<sHeadImg.length;i++){
			if(sHeadImg[i].equals(myInfo.getHead())){
				chead.setSelectedIndex(i);
				break;
			}
		}
		ppassword = new JPasswordField(10);
		ppassword.setEchoChar('*');
		ppassword.setText(myInfo.getPassword()+"");
		ppass = new JPasswordField(10);
		ppass.setEchoChar('*');
		ppass.setText(myInfo.getPassword()+"");
		tage = new JTextField(5);
		man = new JRadioButton("��",true);
		woman = new JRadioButton("Ů");
		bg = new ButtonGroup();
		bg.add(man);
		bg.add(woman);
		cnation = new JComboBox(sNation);
		for(int i=0;i<sNation.length;i++){
			if(sNation[i].equals(myInfo.getNation())){
				cnation.setSelectedIndex(i);
				break;
			}
		}
		cstar = new JComboBox(sStar);
		cblood = new JComboBox(sBlood);
		for(int i=0;i<sBlood.length;i++){
			if(sBlood[i].equals(myInfo.getBlood())){
				cblood.setSelectedIndex(i);
				break;
			}
		}
		thobby = new JTextField(20);
		thobby.setText(myInfo.getHobby()+"");
		InetAddress addr=null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		tIP = new JTextField(addr.getHostAddress());
		tport = new JTextField(5);
		tport.setEditable(false);
		tport.setText("ϵͳ�Զ�����");
		tasignature  = new JTextArea(3,80);
		tasignature.setText(myInfo.getSignature()+"");

		year =new JComboBox();
		month =new JComboBox();
		day =new JComboBox();

		for(int i=1900;i<2050;i++){
			year.addItem(i);
		}
		for(int i=1;i<=12;i++){
			month.addItem(i);
		}
		
		//System.out.println(year.getItemAt(1)+"  ");
		//getItemCount()	������ĳ���
		String syear=myInfo.getBirthday().substring(0,4);
		for(int i=0;i<year.getItemCount();i++){
		//	byte[]  b[]=year.getItemAt(i);
			if(year.equals(syear)){
				year.setSelectedIndex(i);
				break;
			}
		}
		
		String smonth=myInfo.getBirthday().substring(5,6);
		for(int i=0;i<month.getItemCount();i++){
			if(month.equals(smonth)){
				month.setSelectedIndex(i);
				break;
			}
		}
		
		String sday=myInfo.getBirthday().substring(7);
		for(int i=0;i<day.getItemCount();i++){
			if(day.equals(sday)){
				day.setSelectedIndex(i);
				break;
			}
		}
		
		lbbirthday=new JLabel("����:",JLabel.RIGHT);
		
		lbqqnumber.setBounds(0, 100, 100, 20);
		tqqnumber.setBounds(100, 100, 200, 20);
		lbnickname.setBounds(0, 140, 100, 20);
		tnicknime.setBounds(100, 140, 200, 20);
		lbpassword.setBounds(0, 180, 100, 20);
		ppassword.setBounds(100, 180, 200, 20);
		
		lbhead.setBounds(280, 100, 80, 60);
		chead.setBounds(360, 100, 80, 60);
		
		lbpass.setBounds(0, 220, 100, 20);
		ppass.setBounds(100, 220, 200, 20);
		//lbpass.setBounds(280, 180, 80, 20);
		//ppass.setBounds(360, 180, 150, 20);
		//lbbirthday.setBounds(0, 220, 100, 20);
		//year.setBounds(100, 220, 70, 20);
		//month.setBounds(180, 220, 50, 20);
		//day.setBounds(240, 220, 50, 20);

		//lbage.setBounds(0, 220, 100, 20);
		//tage.setBounds(100, 220, 150, 20);
		lbsex.setBounds(20, 260, 80, 20);
		man.setBounds(100, 260, 40, 20);
		man.setOpaque(false);
		woman.setBounds(150, 260, 40, 20);
		woman.setOpaque(false);
		
		lbnation.setBounds(0, 300, 100, 20);
		cnation.setBounds(100, 300, 200, 20);
		//lbstar.setBounds(280, 260, 80, 20);
		//cstar.setBounds(360, 260, 150, 20);
		
		lbblood.setBounds(0, 340, 100, 20);
		cblood.setBounds(100, 340, 200, 20);
		lbhobby.setBounds(0, 380, 100, 20);
		thobby.setBounds(100, 380, 200, 20);

		//lbIP.setBounds(0, 380, 100, 20);
		//tIP.setBounds(100, 380, 150, 20);
		//lbport.setBounds(280, 380, 80, 20);
		//tport.setBounds(360, 380, 150, 20);
		
		lbsignature.setBounds(0, 420, 100, 20);
		tasignature.setBounds(100, 420, 200, 80);
		
	
		setDay(year.getSelectedItem().toString(),month.getSelectedItem().toString());

		lbBG.add(year);
		lbBG.add(month);
		lbBG.add(day);
		lbBG.add(lbbirthday);
		lbBG.add(lbqqnumber);
		lbBG.add(tqqnumber);
		lbBG.add(lbnickname);
		lbBG.add(tnicknime);
		lbBG.add(lbhead);
		lbBG.add(chead);
		lbBG.add(lbpassword);
		lbBG.add(ppassword);
		lbBG.add(lbpass);
		lbBG.add(ppass);
		lbBG.add(lbage);
		lbBG.add(tage);
		lbBG.add(lbsex);
		lbBG.add(man);
		lbBG.add(woman);
		lbBG.add(lbnation);
		lbBG.add(cnation);
		lbBG.add(lbstar);
		lbBG.add(cstar);
		lbBG.add(lbblood);
		lbBG.add(cblood);
		lbBG.add(lbhobby);
		lbBG.add(thobby);
		lbBG.add(lbIP);
		lbBG.add(tIP);
		lbBG.add(lbport);
		lbBG.add(tport);
		lbBG.add(lbsignature);
		lbBG.add(tasignature);
		
		btsave = new JButton("����(S)");
		btsave.setMnemonic('S');
		btclose = new JButton("�ر�(X)");
		btclose.setMnemonic('X');
		
		btsave.setBounds(100, 520, 80, 30);
		btclose.setBounds(230, 520, 80, 30);
		lbBG.add(btsave);
		lbBG.add(btclose);
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				origin.x = e.getX();
				origin.y = e.getY();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - origin.x, p.y + e.getY()- origin.y);
			}
		});
		
		//super("QQע��");
		setUndecorated(true);		
		setVisible(true);
		setTitle("QQע��");
		setSize(480,560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		btclose.addActionListener(this);
		btsave.addActionListener(this);
		year.addItemListener(this);
		month.addItemListener(this);
		//���ر�����
	}

	public static void main(String[] args) {
		new qqUpdate();
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btclose){
			dispose();
		}else if(e.getSource()==btsave){
			String nickname = tnicknime.getText().trim();
			String password = ppassword.getText().trim();
			String password2 = ppass.getText().trim();	
			if(nickname.equals("")){
				JOptionPane.showMessageDialog(this, "�������ǳ�");
				return;
			}else{
				if(password.equals(password2)&&nickname.length()>0&&password.length()>0){
					myInfo.setNickname(nickname);
					myInfo.setPassword(ppassword.getText());
					myInfo.setHead(sHeadImg[chead.getSelectedIndex()]);
					//myInfo.setAge(age);
					if(man.isSelected()){
						myInfo.setSex("��");
					}else{
						myInfo.setSex("Ů");
					}
					myInfo.setNation(sNation[cnation.getSelectedIndex()]);
					 	String str1=year.getSelectedItem().toString().trim();
					    String str2=month.getSelectedItem().toString().trim();
					    String str3=day.getSelectedItem().toString().trim();
					    myInfo.setYear(str1);
					    myInfo.setMonth(str2);
					    myInfo.setDay(str3);
					myInfo.setBlood(sBlood[cblood.getSelectedIndex()]);
					myInfo.setHobby(thobby.getText().trim());
					myInfo.setSignature(tasignature.getText().trim());
					IDDao accoutDao = new IDdaoImp();
					/**  
					 * �����޸ĺ����Ϣ�����ݿ��е�ID��
					 * */
					myInfo=accoutDao.updateID(myInfo);
					JOptionPane.showMessageDialog(this, "��ϲ���޸ĳɹ�");
					ImageIcon icon = new ImageIcon(myInfo.getHead());
					String str = myInfo.getNickname()+"("+myInfo.getQqnumber()+")��"+myInfo.getSignature()+"��";
					/**
					 * ˢ�������ڵ���Ϣ
					 * */
					mainUI.lbmyinfo.setIcon(icon);
					mainUI.lbmyinfo.setText(str);
					/**
					 * �رյ�ǰ����
					 * */
					dispose();
				}else{
					if(password.length()>0&&nickname.length()>0){
						JOptionPane.showMessageDialog(null, "ǰ���������벻һ");	
						}else if(password.length()<=0||password2.length()<=0){
							JOptionPane.showMessageDialog(null, "����������");
						}
				}
			}
		}else if(e.getSource()==btclose){
			dispose();
		}
	}
	@Override
	public void itemStateChanged(ItemEvent t) {
		String m="";
		String y="";	
		if(t.getSource()==month){	
			// SelecectedItem()  ���ص�ǰѡ�����ֵ
			y=year.getSelectedItem().toString();	
			m=month.getSelectedItem().toString();								
		}else if(t.getSource()==year){
			y=year.getSelectedItem().toString();	
			m=month.getSelectedItem().toString();	
		}
		setDay(y,m);
	}
	
	private void setDay(String y,String m){
		if(m.equals("1")||m.equals("3")||m.equals("5")||m.equals("7")||
				m.equals("8")||m.equals("10")||m.equals("12")){
			for(int i=1;i<=31;i++){
				day.addItem(i);
			}		
		}else if(m.equals("4")||m.equals("6")||m.equals("9")||m.equals("11")){
			for(int i=1;i<=30;i++){
				day.addItem(i);
			}
		}else{
			if(setYear(y)){
				for(int i=1;i<=29;i++){
					day.addItem(i);
				}
			}else{
				for(int i=1;i<=28;i++){
					day.addItem(i);
				}
			}
		}
	} 
	private Boolean setYear(String y){
		Integer Y = Integer.parseInt(y);
		if(Y%4==0 && Y%100!=0||Y%400==0){
			return true;		
		}else{
			return false;	
		}	
	}
	
}
