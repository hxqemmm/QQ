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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.event.MouseInputListener;

import com.qq.bean.ID;
import com.qq.dao.IDDao;
import com.qq.daoimp.IDdaoImp;

//��¼����
@SuppressWarnings("serial")
public class qqRegister extends JFrame implements MouseInputListener,ActionListener,ItemListener{
	JLabel bg;
	JLabel lbmin,lbclose,lbhead,lblogin,lbpassword;	//�رպ���С��,ͷ��ע�ᣬ��������
	JButton btReg;				//��¼��ť
	JPasswordField tpassword;
	JComboBox cqqnumber;
	JCheckBox cbpassword,cbReg;		//��ס���룬�Զ���¼
	
	// ȫ�ֵ�λ�ñ��������ڱ�ʾ����ڴ����ϵ�λ��
	static Point origin= new Point();
	
	HashMap<Integer,ID> user;
	public qqRegister() {

		setIconImage(new ImageIcon("images/tubiao.png").getImage());	//ͼ��
		bg = new JLabel(new ImageIcon("images/login.jpg"));
		bg.setLayout(null);
		add(bg); 
		
		lbmin = new JLabel("-");
		lbmin.setForeground(Color.WHITE);
		lbmin.setFont(new Font("����",Font.BOLD,20));
		lbclose = new JLabel("x");
		lbclose.setForeground(Color.WHITE);
		lbclose.setFont(new Font("����",Font.BOLD,18));
		

		lbhead = new JLabel(new ImageIcon("head/tx1.png"));
		lblogin= new JLabel("        ");
		lbpassword = new JLabel("      ");
		
		cqqnumber = new JComboBox();
		cqqnumber.setEditable(true);
		cqqnumber.setToolTipText("�����˺�");		//��ʾ��Ϣ
		
		tpassword = new JPasswordField();
		tpassword.setToolTipText("��������");
		
		cbpassword = new JCheckBox("��ס����");
		cbpassword.setOpaque(false);
		cbReg = new JCheckBox("�Զ���¼");
		cbReg.setOpaque(false);				//�ñ�ǩ����ť͸��
		
		btReg = new JButton("��¼");
		btReg.setBackground(Color.LIGHT_GRAY);
		btReg.setOpaque(false);
		
		cqqnumber.setBounds(130, 185, 194, 30);
		lbhead.setBounds(70, 185, 60, 60);
		tpassword.setBounds(130, 215, 195, 30);
		btReg.setBounds(130, 278, 195, 30);
		cbpassword.setBounds(127, 243, 80, 30);
		cbReg.setBounds(254, 243, 80, 30);
		lblogin.setBounds(335, 185, 80, 30);
		lbmin.setBounds(380, 0, 20, 20);
		lbclose.setBounds(400, 0, 20, 20);
		lbpassword.setBounds(335, 215, 80, 30);
			
		bg.add(cqqnumber);
		bg.add(tpassword);
		bg.add(lblogin);
		bg.add(lbpassword);
		bg.add(lbhead);
		bg.add(btReg);
		bg.add(cbpassword);
		bg.add(cbReg);
		bg.add(lbmin);
		bg.add(lbclose);
		
		setUndecorated(true);		
		setVisible(true);
		setSize(427,321);
		setResizable(false);//�ô��ڲ��ɸı��С
		setTitle("QQ��¼");
		setLocationRelativeTo(null);//�ô������м����,
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		lbmin.addMouseListener(this);
		lbclose.addMouseListener(this);
		lblogin.addMouseListener(this);
		lbpassword.addMouseListener(this);
		btReg.addActionListener(this);
		cbpassword.addItemListener(this);
		readFile();
		
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
		
	}
	public static void main(String[] args) {
		new qqRegister();
		
	}
	
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==lbmin){
			this.setState(JFrame.HIDE_ON_CLOSE);	//�ô������أ���С��
		}else if(e.getSource()==lbclose){
			System.exit(0);
		}else if(e.getSource()==lblogin){
			dispose();
			new qqLogin();
		}else if(e.getSource()==lbpassword){
			JOptionPane.showMessageDialog(this,"�һ�����");		//�һ�����ҳ��
		}
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent arg0) {}
	public void mouseDragged(MouseEvent e) {	}
	public void mouseMoved(MouseEvent arg0) {}
	
	
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btReg){
			if(cqqnumber.getSelectedItem()!=null&&!cqqnumber.getSelectedItem().equals("")){
				if(tpassword.getText().equals("")){
					JOptionPane.showMessageDialog(this,"����������");
					return;
				}
				IDDao idDao = new IDdaoImp();
				ID a=new ID();
				try{
					a.setQqnumber(Integer.parseInt(cqqnumber.getSelectedItem().toString()));
				}catch(NumberFormatException en){
					JOptionPane.showMessageDialog(this,"qq��������");
					return;
				}
				a.setPassword(tpassword.getText().toString());
				a = idDao.longin(a);  
				if(a!=null){		
					dispose();
					save(a);
					new qqMain(a);
				}else{
					JOptionPane.showMessageDialog(this,"�û��������������");
				}
			}else{
				JOptionPane.showMessageDialog(this,"������qq����");
			} 
		}
	}
	@SuppressWarnings("unchecked")
	public void save(ID a){
		HashMap<Integer, ID> user=null;
		File file=new File("a.dat");
		try{
			if(!file.exists()){
				file.createNewFile();
				user = new HashMap<Integer, ID>();
			}else{
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				user = (HashMap<Integer, ID>)ois.readObject();	//��id�ŵ�HashMap����
				fis.close();
				ois.close();
			}
			user.put(a.getQqnumber(),a);
			//����һ������������
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("a.dat"));
			oos.writeObject(user);	//ͨ��������Ѷ�������������Ӳ�̵��ļ�
			oos.flush();
			oos.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public void readFile(){
		try{
			File file = new File("a.dat");
			if(!file.exists()){
				return;
			}
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			user = ((HashMap<Integer, ID>)ois.readObject());
			Set<Integer> set = user.keySet();
			Iterator<Integer> it = set.iterator();
			while(it.hasNext()){
				cqqnumber.addItem(it.next());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource()==cbpassword){
			if(!cqqnumber.getSelectedItem().toString().equals("") && user!=null){
				int qqcode = Integer.parseInt(cqqnumber.getSelectedItem().toString());
				ID a = user.get(qqcode);
				if(a!=null){
					tpassword.setText(a.getPassword());
				}
			}
		}
	}
}


