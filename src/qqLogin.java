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

//注册界面
@SuppressWarnings("serial")
public class qqLogin extends JFrame implements ActionListener,ItemListener{	
	private JLabel lbBG;
	private JLabel lbqqnumber,lbnickname,lbpassword,lbpass,lbhead,lbnation,lbsex,lbage,
				lbbirthday,lbstar,lbhobby,lbIP,lbport,lbblood,lbsignature;
	private JComboBox chead,cnation,cblood,cstar;
	private JTextField tqqnumber,tnicknime,tage,thobby,tIP,tport;
	private JRadioButton man,woman;
	private JPasswordField ppassword,ppass;			//加密文本框组件
	private JTextArea tasignature;
	private JButton btsave,btclose;
	private ButtonGroup bg;							//组合
	private JComboBox year,month,day;

	private String sNation[]={
			"汉族","苗族","壮族","高山族","回族","侗族",
			"傣族","藏族","朝鲜族","其他"
	};
	private String sStar[]={
			"双鱼座","金牛座","摩羯座","天蝎座","处女座","狮子座","白羊座",
			"水瓶座","射手座","天秤座","巨蟹座","双子座",
	};
	private String sBlood[]={"A","B","O","AB"};
	private String sHeadImg[] = {
			"head/tx1.png","head/1.png","head/2.png",
			"head/3.png","head/4.png","head/5.png",
			"head/6.png","head/7.png","head/8.png",
			"head/9.png","head/10.png"
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
			new ImageIcon(sHeadImg[10])
	};
	
	static Point origin= new Point();
	public qqLogin() {
		setIconImage(new ImageIcon("images/tubiao.png").getImage());	//图标
		lbBG = new JLabel(new ImageIcon("images/huayuan.jpg"));	//把所有组件放到一个标签上，而不是面板
		add(lbBG);
		lbBG.setLayout(null);
		JLabel title = new JLabel("用户注册",JLabel.CENTER);
		title.setFont(new Font("楷体",Font.BOLD,36));
		title.setForeground(Color.BLACK);
		title.setBounds(0,30,160,40);
		lbBG.add(title);
		
		lbqqnumber = new JLabel("QQ号码:",JLabel.RIGHT);
		lbnickname = new JLabel("昵称:",JLabel.RIGHT);
		lbhead = new JLabel("头像:",JLabel.RIGHT);
		lbpassword = new JLabel("登录密码:",JLabel.RIGHT);
		lbpass = new JLabel("确认密码:",JLabel.RIGHT);
		lbage = new JLabel("年龄:",JLabel.RIGHT);
		lbsex = new JLabel("性别:",JLabel.RIGHT);
		lbnation = new JLabel("民族:",JLabel.RIGHT);
		lbstar = new JLabel("星座:",JLabel.RIGHT);
		lbblood = new JLabel("血型:",JLabel.RIGHT);
		lbhobby = new JLabel("爱好:",JLabel.RIGHT);
		lbIP = new JLabel("IP地址:",JLabel.RIGHT);
		lbport = new JLabel("端口:",JLabel.RIGHT);
		lbsignature = new JLabel("个人说明:",JLabel.RIGHT);
		
		tqqnumber = new JTextField(10);
		tqqnumber.setText("系统自动生成");
		tqqnumber.setEditable(false);
		tnicknime = new JTextField(10);
		chead = new JComboBox(headIcon);
		ppassword = new JPasswordField(10);
		ppassword.setEchoChar('*');
		ppass = new JPasswordField(10);
		ppass.setEchoChar('*');
		tage = new JTextField(5);
		man = new JRadioButton("男",true);
		woman = new JRadioButton("女");
		bg = new ButtonGroup();
		bg.add(man);
		bg.add(woman);
		cnation = new JComboBox(sNation);
		cstar = new JComboBox(sStar);
		cblood = new JComboBox(sBlood);
		thobby = new JTextField(20);
		InetAddress addr=null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		tIP = new JTextField(addr.getHostAddress());
		tport = new JTextField(5);
		tport.setEditable(false);
		tport.setText("系统自动生成");
		tasignature  = new JTextArea(3,80);

		year =new JComboBox();
		month =new JComboBox();
		day =new JComboBox();
		lbbirthday=new JLabel("生日:",JLabel.RIGHT);
		
		lbnickname.setBounds(0, 100, 100, 20);
		tnicknime.setBounds(100, 100, 200, 20);
		lbpassword.setBounds(0, 140, 100, 20);
		ppassword.setBounds(100, 140, 200, 20);
		
		//lbhead.setBounds(280, 100, 80, 60);
		//chead.setBounds(360, 100, 80, 60);
		
		lbpass.setBounds(0, 180, 100, 20);
		ppass.setBounds(100, 180, 200, 20);
		//lbpass.setBounds(280, 180, 80, 20);
		//ppass.setBounds(360, 180, 150, 20);
		lbbirthday.setBounds(0, 220, 100, 20);
		year.setBounds(100, 220, 70, 20);
		month.setBounds(180, 220, 50, 20);
		day.setBounds(240, 220, 50, 20);

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
		
		for(int i=1900;i<2050;i++){
			year.addItem(i);
		}
		for(int i=1;i<=12;i++){
			month.addItem(i);
		}
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
		
		btsave = new JButton("保存(S)");
		btsave.setMnemonic('S');
		btclose = new JButton("关闭(X)");
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
		
		//super("QQ注册");
		setUndecorated(true);		
		setVisible(true);
		setTitle("QQ注册");
		setSize(480,560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		btclose.addActionListener(this);
		btsave.addActionListener(this);
		year.addItemListener(this);
		month.addItemListener(this);
		}
	public static void main(String[] args) {
		new qqLogin();
	}
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btclose){
			dispose();
		}else if(e.getSource()==btsave){
			ID a=new ID();
			String username = tnicknime.getText().trim();
			String password = ppassword.getText().trim();
			String password2 = ppass.getText().trim();	
			if(username.length()<=0){
				JOptionPane.showMessageDialog(null, "用户名不能为空");
				return;
			}else{
				try{
					String.valueOf(username);
					String.valueOf(password);
					String.valueOf(password2);
					
					if(password.equals(password2)&&username.length()>0&&password.length()>0){
						a.setNickname(tnicknime.getText());
					    a.setPassword(ppassword.getText());
					    a.setHobby(thobby.getText());
					    a.setSignature(tasignature.getText());
					    a.setIP(tIP.getText());
					    
					    //getSelectedIndex()返回的是选择项的序列号
					    a.setNation(sNation[cnation.getSelectedIndex()]);		
					    a.setBlood(sBlood[cblood.getSelectedIndex()]);
					    a.setHead(sHeadImg[chead.getSelectedIndex()]);
					    String str1=year.getSelectedItem().toString().trim();
					    String str2=month.getSelectedItem().toString().trim();
					    String str3=day.getSelectedItem().toString().trim();
					    a.setYear(str1);
					    a.setMonth(str2);
					    a.setDay(str3);
					  
					    if(man.isSelected()){	 // isSelected()判断单选框是否被选中
					    	a.setSex("男");
					    }else{
					    	a.setSex("女");
					    }
					    IDDao idDao = new IDdaoImp();
					    String qqnumber = idDao.save(a);
					    int  i = JOptionPane.showConfirmDialog(this, "注册成功,您注册的qq号码是"+qqnumber+",\n"+" 要跳转登录界面吗？","确认",JOptionPane.NO_OPTION);
						if(i==0){		//是
							dispose();
							new qqRegister();
						}else if(i==-1){	//否
							dispose();
						}else{		//取消
							
						}
					}else{
						if(password.length()>0&&username.length()>0){
						JOptionPane.showMessageDialog(null, "前后两次密码不一");	
						}else if(password.length()<=0||password2.length()<=0){
							JOptionPane.showMessageDialog(null, "请输入密码");
						}
					}	
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, "用户名不能输入字符或为空");	
					ex.printStackTrace();
				}			
			}
		}
	}
	@Override
	public void itemStateChanged(ItemEvent t) {
		String m="";
		String y="";	
		if(t.getSource()==month){	
			// SelecectedItem()  返回当前选中项的值
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
