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
	String ssex[]={"不选择","男","女"};
	String sstate[]={"不选择","在线","离线","忙碌","隐身"};
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
		setIconImage(new ImageIcon("images/tubiao.png").getImage());	//图标
		bgimg = new JLabel(new ImageIcon("images/bgmain3.jpg"));
		bgimg.setLayout(new BorderLayout());
		add(bgimg);
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bqqnumber = new JLabel("QQ号码");
		bnickname = new JLabel("昵称");
		bage = new JLabel("年龄");
		tqqnumber = new JTextField(8);
		tnickname = new JTextField(8);
		tage = new JTextField(5);
		csex = new JComboBox(ssex);
		cstate = new JComboBox(sstate);
		btfind = new JButton("查找");
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
		
		String[] cname={"头像", "QQ号码", "昵称", "生日","性别","民族","血型","爱好","状态","备注"};
		vhead=new Vector<String>();
		for(int i=0;i<cname.length;i++){
			vhead.addElement(cname[i]);
		}
		//向数据库查询数据
		String sql="select head,qqnumber,nickname,birthday,sex,nation,blood,hobby,state,signature from ID";
		//给表中的数据赋值
		vdata=iddao.findFriend(sql);
		table=new JTable(new MyTableModel(vhead,vdata));
		table.addMouseListener(this);
		
		table.setRowHeight(60);
		//把表格增加到滚动面板上
		add(new JScrollPane(table));
		JPanel p=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btadd=new JButton("添加好友");
		btclose=new JButton("关闭窗口");
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
		setResizable(false);//让窗口不可改变大小
		setTitle("QQ查找好友");
		//setLocationRelativeTo(null);//让窗口在中间出现,
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

		public int getColumnCount() {	//表格列长度
			return vhead.size();
		}

		public int getRowCount() {		//表格行长度
			return vdata.size();
		}
		
		 public String getColumnName(int col) {		//根据序号获取列名
	            return vhead.get(col);
	        }
		@SuppressWarnings("rawtypes")
		public Object getValueAt(int row, int col) {	//根据行列获取单元格信息
			Vector rowdata=(Vector) vdata.get(row);
			if(col==0){
				return new ImageIcon(rowdata.get(col).toString());
			}else{
				return rowdata.get(col);
			}
		}
		
		public Class<?> getColumnClass(int c) {		//返回指定列的Class对象
        	if(c==0){
				return ImageIcon.class;
			}else{
				return super.getColumnClass(c);
			}
        }
		
		public boolean isCellEditable(int row, int col) {	//设置表格能否修改
	            return false;
	        }
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void setValueAt(Object value, int row, int col) {	//根据行列获取单元格的值
        	Vector rowData = (Vector)vdata.get(row);
        	rowData.set(col, value);
        	//更新表格，调用的是父类的方法
            fireTableCellUpdated(row, col);  
        }
		
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==table){
			int index=table.getSelectedRow();
			if(e.getClickCount()==2){		//双击鼠标
				@SuppressWarnings("rawtypes")
				Vector row = (Vector)vdata.get(index);
				int qqnumber = Integer.parseInt(row.get(1).toString());
				if(qqnumber==myinfo.getQqnumber()){
					JOptionPane.showMessageDialog(this, "不能添加自己为好友！");
					return;
				}
				if(iddao.isFriend(myinfo.getQqnumber(), qqnumber)){
					JOptionPane.showMessageDialog(this, "你们已经是好友！");
					return;
				}
				ID friendInfo = iddao.getSelectedFriend(qqnumber);
				String str = "是否添加【"+friendInfo.getNickname()+"】为您的好友？";
				if(JOptionPane.showConfirmDialog(this, str,"添加好友",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
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
				if(!sex.equals("不选择")){
					sql+=" and sex='"+sex +"'";
				}
				if(!state.equals("不选择")){
					sql += " and state='"+state + "'";
				}
				sql +=" order by qqnumber";
				vdata=iddao.findFriend(sql);
				table.setModel(new MyTableModel(vhead, vdata));
			
		}else if(e.getSource()==btadd){
			//获取选中的JTble序号
			int index=table.getSelectedRow();
			if(index>=0){
				@SuppressWarnings("rawtypes")
				Vector row = (Vector)vdata.get(index);
				int qqnumber = Integer.parseInt(row.get(1).toString());
				if(qqnumber==myinfo.getQqnumber()){
					JOptionPane.showMessageDialog(this, "不能添加自己为好友！");
					return;
				}
				if(iddao.isFriend(myinfo.getQqnumber(), qqnumber)){
					JOptionPane.showMessageDialog(this, "你们已经是好友！");
					return;
				}
				ID friendInfo = iddao.getSelectedFriend(qqnumber);
				String str = "是否添加【"+friendInfo.getNickname()+"】为您的好友？";
				if(JOptionPane.showConfirmDialog(this, str,"添加好友",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
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

