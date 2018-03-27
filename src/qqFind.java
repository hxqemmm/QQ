import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;
import javax.swing.table.AbstractTableModel;

import com.qq.base.Cmd;
import com.qq.base.SendCmd;
import com.qq.base.SendMsg;
import com.qq.bean.ID;
import com.qq.dao.IDDao;
import com.qq.daoimp.IDdaoImp;


@SuppressWarnings("serial")
public class qqFind extends JFrame implements MouseInputListener,ActionListener{
	JLabel bgimg;
	JTable table;
	JLabel bqqnumber,bnickname,bage;
	JTextField tqqnumber,tnickname,tage;
	JComboBox csex,cstate;
	JButton btfind,btadd,btclose;
	ID myinfo;
	Vector<String> vhead;
	@SuppressWarnings("rawtypes")
	Vector vdata;
	String ssex[]={"��ѡ��","��","Ů"};
	String sstate[]={"��ѡ��","����","����","æµ","����"};
	IDDao iddao=new IDdaoImp();
	
	static Point origin= new Point();

	public qqFind() {
		init();
	}
	public qqFind(ID id) {
		this.myinfo = id;
		init();
	}
	
	public void init(){
		setIconImage(new ImageIcon("images/tubiao.png").getImage());	//ͼ��
		bgimg = new JLabel(new ImageIcon("images/bgmain3.jpg"));
		bgimg.setLayout(new BorderLayout());
		add(bgimg);
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bqqnumber = new JLabel("QQ����");
		bnickname = new JLabel("�ǳ�");
		bage = new JLabel("����");
		tqqnumber = new JTextField(8);
		tnickname = new JTextField(8);
		tage = new JTextField(5);
		csex = new JComboBox(ssex);
		cstate = new JComboBox(sstate);
		btfind = new JButton("����");
		topPanel.add(bqqnumber);
		topPanel.add(tqqnumber);
		topPanel.add(bnickname);
		topPanel.add(tnickname);
		//topPanel.add(bage);
		//topPanel.add(tage);
		topPanel.add(csex);
		topPanel.add(cstate);
		topPanel.add(btfind);
		add(topPanel,BorderLayout.NORTH);
		
		String[] cname={"ͷ��", "QQ����", "�ǳ�", "����","�Ա�","����","Ѫ��","����","״̬","��ע"};
		vhead=new Vector<String>();
		for(int i=0;i<cname.length;i++){
			vhead.addElement(cname[i]);
		}
		//�����ݿ��ѯ����
		String sql="select head,qqnumber,nickname,birthday,sex,nation,blood,hobby,state,signature from ID";
		//�����е����ݸ�ֵ
		vdata=iddao.findFriend(sql);
		table=new JTable(new MyTableModel(vhead,vdata));
		table.addMouseListener(this);
		
		table.setRowHeight(60);
		//�ѱ�����ӵ����������
		add(new JScrollPane(table));
		JPanel p=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btadd=new JButton("��Ӻ���");
		btclose=new JButton("�رմ���");
		p.add(btadd);
		p.add(btclose);
		add(p,BorderLayout.SOUTH);
		
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
		btfind.addActionListener(this);
		btadd.addActionListener(this);
		btclose.addActionListener(this);
		
		setUndecorated(true);
		setVisible(true);
		setSize(800,500);
		setResizable(false);//�ô��ڲ��ɸı��С
		setTitle("QQ���Һ���");
		//setLocationRelativeTo(null);//�ô������м����,
		setLocation(200, 100);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public static void main(String[] args) {
		new qqFind();
	}
	
	class MyTableModel extends AbstractTableModel{
		Vector<String> vhead;
		@SuppressWarnings("rawtypes")
		Vector vdata;
		
		@SuppressWarnings("rawtypes")
		public MyTableModel(Vector<String> vHead,Vector vdata) {
			this.vhead=vHead;
			this.vdata=vdata;
		}

		public int getColumnCount() {	//����г���
			return vhead.size();
		}

		public int getRowCount() {		//����г���
			return vdata.size();
		}
		
		 public String getColumnName(int col) {		//������Ż�ȡ����
	            return vhead.get(col);
	        }
		@SuppressWarnings("rawtypes")
		public Object getValueAt(int row, int col) {	//�������л�ȡ��Ԫ����Ϣ
			Vector rowdata=(Vector) vdata.get(row);
			if(col==0){
				return new ImageIcon(rowdata.get(col).toString());
			}else{
				return rowdata.get(col);
			}
		}
		
		public Class<?> getColumnClass(int c) {		//����ָ���е�Class����
        	if(c==0){
				return ImageIcon.class;
			}else{
				return super.getColumnClass(c);
			}
        }
		
		public boolean isCellEditable(int row, int col) {	//���ñ���ܷ��޸�
	            return false;
	        }
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void setValueAt(Object value, int row, int col) {	//�������л�ȡ��Ԫ���ֵ
        	Vector rowData = (Vector)vdata.get(row);
        	rowData.set(col, value);
        	//���±�񣬵��õ��Ǹ���ķ���
            fireTableCellUpdated(row, col);  
        }
		
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==table){
			int index=table.getSelectedRow();
			if(e.getClickCount()==2){		//˫�����
				@SuppressWarnings("rawtypes")
				Vector row = (Vector)vdata.get(index);
				int qqnumber = Integer.parseInt(row.get(1).toString());
				if(qqnumber==myinfo.getQqnumber()){
					JOptionPane.showMessageDialog(this, "��������Լ�Ϊ���ѣ�");
					return;
				}
				if(iddao.isFriend(myinfo.getQqnumber(), qqnumber)){
					JOptionPane.showMessageDialog(this, "�����Ѿ��Ǻ��ѣ�");
					return;
				}
				ID friendInfo = iddao.getSelectedFriend(qqnumber);
				String str = "�Ƿ���ӡ�"+friendInfo.getNickname()+"��Ϊ���ĺ��ѣ�";
				if(JOptionPane.showConfirmDialog(this, str,"��Ӻ���",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
					SendMsg msg = new SendMsg();
					msg.cmd = Cmd.CMD_ADDFRI;
					msg.myInfo = myinfo;
					msg.friendInfo = friendInfo;
					SendCmd.send(msg);
				}
			}
		}
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	public void mouseDragged(MouseEvent arg0) {}
	public void mouseMoved(MouseEvent arg0) {}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btfind){
				String sql = "select head,qqnumber,nickname,birthday,sex,nation,blood,hobby,state,signature from ID";
				sql+=" where 1=1";
				String qqnumber=tqqnumber.getText().trim();
				String nickname=tnickname.getText().trim();
			//	String age=tage.getText().trim();
				if(!qqnumber.equals("")){
					sql+=" and qqnumber like '%"+qqnumber+"%'";
				}
				if(!nickname.equals("")){
					sql+=" and nickname like '"+nickname + "%'";
				}
				/*if(!age.equals("")){
					sql+=" and age ="+age;
				}*/
				String sex=ssex[csex.getSelectedIndex()];
				String state=sstate[cstate.getSelectedIndex()];
				if(!sex.equals("��ѡ��")){
					sql+=" and sex='"+sex +"'";
				}
				if(!state.equals("��ѡ��")){
					sql += " and state='"+state + "'";
				}
				sql +=" order by qqnumber";
				vdata=iddao.findFriend(sql);
				table.setModel(new MyTableModel(vhead, vdata));
			
		}else if(e.getSource()==btadd){
			//��ȡѡ�е�JTble���
			int index=table.getSelectedRow();
			if(index>=0){
				@SuppressWarnings("rawtypes")
				Vector row = (Vector)vdata.get(index);
				int qqnumber = Integer.parseInt(row.get(1).toString());
				if(qqnumber==myinfo.getQqnumber()){
					JOptionPane.showMessageDialog(this, "��������Լ�Ϊ���ѣ�");
					return;
				}
				if(iddao.isFriend(myinfo.getQqnumber(), qqnumber)){
					JOptionPane.showMessageDialog(this, "�����Ѿ��Ǻ��ѣ�");
					return;
				}
				ID friendInfo = iddao.getSelectedFriend(qqnumber);
				String str = "�Ƿ���ӡ�"+friendInfo.getNickname()+"��Ϊ���ĺ��ѣ�";
				if(JOptionPane.showConfirmDialog(this, str,"��Ӻ���",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
					SendMsg msg = new SendMsg();
					msg.cmd = Cmd.CMD_ADDFRI;
					msg.myInfo = myinfo;
					msg.friendInfo = friendInfo;
					SendCmd.send(msg);
				}
			}
		}else if(e.getSource()==btclose){
			dispose();
		}
	}
}

