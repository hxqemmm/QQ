import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.event.MouseInputListener;
import javax.swing.text.BadLocationException;

import com.qq.base.Cmd;
import com.qq.base.SendCmd;
import com.qq.base.SendMsg;
import com.qq.base.Sound;
import com.qq.bean.ID;
import com.qq.dao.IDDao;
import com.qq.daoimp.IDdaoImp;


public class qqMain extends JFrame implements ActionListener,MouseInputListener,WindowListener,
	Runnable,ItemListener{
	private static final long serialVersionUID = 1L;
	
	ID myinfo,friendsinfo;							//全局化构造参数
	IDDao iddao=new IDdaoImp();		//数据库持久层的操作对象

	Vector<ID> vinfo,vfriend,vfamliy,vmate,vblack;		//创建五个向量
	JMenuItem miChat,miFamily,miFriend,miMate,miHmd,miLookInfo,miDel;

	JTabbedPane jtpanel;
	JLabel bgimg,lbmyinfo,lbmin,lbclose;
	JList jlfriend,jlmate,jlfamliy,jlblack;
	JButton bfind,bchangebg;
	JComboBox cbstate;
	JPopupMenu popmenu;	
	
	TrayIcon trayIcon;		//托盘
	PopupMenu popTray;		//菜单
	MenuItem miOpen,miExit,miOnline,miLevel,miBuys;		//菜单项
	
	Hashtable<Integer, qqChat> chatWin = new Hashtable<Integer, qqChat>();

	static Point origin= new Point();
	public qqMain(ID a) {
		this.myinfo=a;
		setResizable(false);	//设置窗口没有边框，且不能改变大小
		setIconImage(new ImageIcon("images/tubiao.png").getImage());	//图标
		bgimg = new JLabel(new ImageIcon("images/bgmain.jpg"));
		bgimg.setLayout(new BorderLayout());
		add(bgimg);
		
		lbmin = new JLabel("-");
		lbmin.setForeground(Color.WHITE);
		lbmin.setFont(new Font("黑体",Font.BOLD,20));
		lbclose = new JLabel("x");
		lbclose.setForeground(Color.WHITE);
		lbclose.setFont(new Font("黑体",Font.BOLD,18));
		
		jlfriend =new JList();
		jlmate=new JList();
		jlfamliy=new JList();
		jlblack=new JList();
		
		//把列表设置为透明的
		jlfriend.setOpaque(false);
		jlmate.setOpaque(false);
		jlfamliy.setOpaque(false);
		jlblack.setOpaque(false);
		
		UIManager.put("TabbedPane.contentOpaque", false);	//设置选项卡面板的透明效果
		jtpanel=new JTabbedPane();	//选项卡面板
		jtpanel.setOpaque(false);	//把选项卡面板设为透明的	
		
		jtpanel.addTab("好友", jlfriend);
		jtpanel.addTab("同学", jlmate);
		jtpanel.addTab("家人群", jlfamliy);
		jtpanel.addTab("黑名单", jlblack);
		
		vinfo = new Vector<ID>();
		vfriend = new Vector<ID>();
		vfamliy = new Vector<ID>();
		vmate = new Vector<ID>();
		vblack = new Vector<ID>();
		
		refresh();
		
		bgimg.add(jtpanel,BorderLayout.CENTER);
		ImageIcon icon = new ImageIcon(myinfo.getHead());
		
		String nickname = myinfo.getNickname()+"("+myinfo.getQqnumber()+")【"+myinfo.getSignature()+"】";
		lbmin.setBounds(250, 12, 20, 20);
		lbclose.setBounds(280, 10, 20, 20);
		lbmyinfo = new JLabel(nickname,icon,JLabel.LEFT);
		lbmyinfo.setOpaque(false);
		lbmyinfo.add(lbmin);
		lbmyinfo.add(lbclose);
		bgimg.add(lbmyinfo,BorderLayout.NORTH);	
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.setOpaque(false);
		
		bfind = new JButton("查找");
		bchangebg = new JButton("换肤");

		//下拉框调用构造方法的参数是一个字符串数据，这个字符串数据定义在Cmd这个java类里面的
		cbstate=new JComboBox(Cmd.STATUS);
		cbstate.removeItemAt(1);	//默认状态

		bottomPanel.add(bfind);
		bottomPanel.add(bchangebg);
		bottomPanel.add(cbstate);
		bgimg.add(bottomPanel,BorderLayout.SOUTH);
		
		createMenu();		
		createTrayMenu();	//创建作用在托盘上的图标
		setTrayIcon();		//设置托盘图标
		
		//启动消息接收线程
		Thread t = new Thread(this);
		t.start();
	
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
		
		//给所有QQ好友群发消息
		SendCmd.sendAll(vinfo, myinfo, Cmd.CMD_ONLINE);

		
		setUndecorated(true);
		setSize(300, 700);   
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setLocation(1000, 10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		bfind.addActionListener(this);
		bchangebg.addActionListener(this);
		miChat.addActionListener(this);
		lbmin.addMouseListener(this);
		lbclose.addMouseListener(this);
		popmenu.addMouseListener(this);
		jlfriend.addMouseListener(this);
		jlfamliy.addMouseListener(this);
		jlmate.addMouseListener(this);
		jlfamliy.addMouseListener(this);
		jlblack.addMouseListener(this);
		cbstate.addItemListener(this);
		lbmyinfo.addMouseListener(this);
		lbmyinfo.addMouseMotionListener(this);
	}
	
	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==bfind){
			new qqFind(myinfo);
		}else if(e.getSource()==bchangebg){
			JFileChooser dlg=new JFileChooser();	//创建一个文件选择框
			dlg.setCurrentDirectory(new File("E:\\课堂文档\\JAVA\\项目\\QQ\\bgmain"));
			dlg.setDialogType(JFileChooser.OPEN_DIALOG);	//设置选择框的风格
			dlg.setFileSelectionMode(JFileChooser.FILES_ONLY);	//设置选择框的模式
			dlg.setDialogTitle("更换皮肤");  //设置选择框的窗口名
            if (dlg.showOpenDialog(this) == dlg.APPROVE_OPTION) {
	            File file = dlg.getSelectedFile();
	            String path =file.getPath();	//获取文件路径
	        	bgimg.setIcon(new ImageIcon(path));
            }
		}else if(e.getSource()==miChat){
			openChat();
		}else if(e.getSource()==miDel){
			IDDao id=new IDdaoImp();
			id.getDelFriend(myinfo.getQqnumber(),friendsinfo.getQqnumber());
			 SendCmd.sendAll(vinfo, myinfo, Cmd.CMD_DELFRIEND);
			refresh();
		}else if(e.getSource()==miFriend){
			//移动好友
			iddao.moveGroup(myinfo.getQqnumber(), friendsinfo.getQqnumber(), Cmd.GROUPNAME[0]);
			refresh();
		}else if(e.getSource()==miMate){
			iddao.moveGroup(myinfo.getQqnumber(), friendsinfo.getQqnumber(), Cmd.GROUPNAME[1]);
			refresh();
		}else if(e.getSource()==miFamily){
			iddao.moveGroup(myinfo.getQqnumber(), friendsinfo.getQqnumber(), Cmd.GROUPNAME[2]);
			refresh();
		}else if(e.getSource()==miHmd){
			iddao.moveGroup(myinfo.getQqnumber(), friendsinfo.getQqnumber(), Cmd.GROUPNAME[3]);
			refresh();
		}else if(e.getSource()==miOpen){
			//获取系统托盘
			SystemTray tray = SystemTray.getSystemTray();
			//删除当前托盘
			tray.remove(trayIcon);
			//显示主面板
			qqMain.this.setVisible(true);
			qqMain.this.setState(JFrame.NORMAL);
		}else if(e.getSource()==miBuys){
				//把QQ状态改为忙碌
			 String status = Cmd.STATUS[2];  
			 changeState(status);
			 iddao.changeStatus(myinfo.getQqnumber(), status);
			 SendCmd.sendAll(vinfo, myinfo, Cmd.CMD_CHANGESTATE);
		}else if(e.getSource()==miLevel){
			 String status = Cmd.STATUS[1];
			 changeState(status);
			 iddao.changeStatus(myinfo.getQqnumber(), status);
			 SendCmd.sendAll(vinfo, myinfo, Cmd.CMD_CHANGESTATE);
		}else if(e.getSource()==miOnline){
			 String status = Cmd.STATUS[0];
			 changeState(status);
			 iddao.changeStatus(myinfo.getQqnumber(), status);
			 SendCmd.sendAll(vinfo, myinfo, Cmd.CMD_CHANGESTATE);
		}else if(e.getSource() == miExit){
			iddao.changeStatus(myinfo.getQqnumber(), Cmd.STATUS[1]);
			//群发离线
			SendCmd.sendAll(vinfo, myinfo, Cmd.CMD_OFFLINE);
			System.exit(0);
		}else if(e.getSource() == miLookInfo){		//查看好友信息
			new qqLookinfo(friendsinfo);
		}
	}
	
	public void changeState(String status){
		//获取当前头像路径
		String filename=myinfo.getHead();
		String headImg= myinfo.getHead();
		
		if(status.equals(Cmd.STATUS[0])){
			filename = headImg;
		
		}else if(status.equals(Cmd.STATUS[2])){
			int pos = headImg.indexOf('.');
			String pre = headImg.substring(0,pos);
			String fix = headImg.substring(pos,headImg.length());
			filename = pre + "_h"+fix;
		}else if(status.equals(Cmd.STATUS[2])){
			int pos = headImg.indexOf('.');
			String pre = headImg.substring(0,pos);
			String fix = headImg.substring(pos,headImg.length());
			filename = pre + "_l"+fix;
		}else if(status.equals(Cmd.STATUS[3])){
			int pos = headImg.indexOf('.');
			String pre = headImg.substring(0,pos);
			String fix = headImg.substring(pos,headImg.length());
			filename = pre + "_w"+fix;
		}
		lbmyinfo.setIcon(new ImageIcon(filename));
	}
	
	public void refresh() {
		vinfo=iddao.getMyFriend(myinfo.getQqnumber());
		vmate.clear();
		vfriend.clear();   
		vfamliy.clear();
		vblack.clear();
		for(ID id:vinfo){
			String groupname=id.getGroupname();
			if(groupname.equals(Cmd.GROUPNAME[0])){
				vfriend.add(id);
			}else if(groupname.equals(Cmd.GROUPNAME[1])){
				vmate.add(id);
			}else if(groupname.equals(Cmd.GROUPNAME[2])){
				vfamliy.add(id);
			}else if(groupname.equals(Cmd.GROUPNAME[3])){
				vblack.add(id);
			}
		}
		//进行数据绑定
		jlfriend.setModel(new DataModel(vfriend));
		jlfamliy.setModel(new DataModel(vfamliy));
		jlmate.setModel(new DataModel(vmate));
		jlblack.setModel(new DataModel(vblack));
		
		//设置渲染器
		jlfriend.setCellRenderer(new Myheadimg(vfriend));
		jlmate.setCellRenderer(new Myheadimg(vmate));
		jlfamliy.setCellRenderer(new Myheadimg(vfamliy));
		jlblack.setCellRenderer(new Myheadimg(vblack)); 	
	}
	
	public void createMenu(){
		
		miChat = new JMenuItem("聊天");
		miFamily = new JMenuItem("移到家人组");
		miFriend = new JMenuItem("移到好友组");
		miMate = new JMenuItem("移到同学组");
		miHmd = new JMenuItem("移到黑名单");
		miLookInfo = new JMenuItem("查看好友信息");
		miDel = new JMenuItem("删除好友资料");
		
		miChat.addActionListener(this);
		miFamily.addActionListener(this);
		miFriend.addActionListener(this);
		miMate.addActionListener(this);
		miHmd.addActionListener(this);
		miLookInfo.addActionListener(this);
		miDel.addActionListener(this);
		
		popmenu = new JPopupMenu();
		popmenu.add(miChat);
		popmenu.add(miFamily);
		popmenu.add(miFriend);   
		popmenu.add(miMate);
		popmenu.add(miHmd);
		popmenu.add(miLookInfo);
		popmenu.add(miDel);
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==lbmin){
			SystemTray tray = SystemTray.getSystemTray();
			if(trayIcon!=null){
				tray.remove(trayIcon);
			}
			try{
				 //把自己创建的托盘加到系统托盘里面
				tray.add(trayIcon);
				// 把当前主面板隐藏起来
				qqMain.this.setVisible(false);
			}catch (AWTException e1) {
				e1.printStackTrace();
			}
			this.setState(JFrame.HIDE_ON_CLOSE);	//让窗口隐藏，最小化
		}else if(e.getSource()==lbclose){
			dispose();
			iddao.changeStatus(myinfo.getQqnumber(), Cmd.STATUS[1]);
		}else if(e.getSource()==jlfriend){
			if(jlfriend.getSelectedIndex()>=0){
				friendsinfo = vfriend.get(jlfriend.getSelectedIndex());
			}
			if(e.getButton()==3 ){		//getButton==3,鼠标右键事件
				if(jlfriend.getSelectedIndex()>=0){
					popmenu.show(jlfriend, e.getX(), e.getY());
				}
			}
		}else if(e.getSource()==jlmate){
			if(jlmate.getSelectedIndex()>=0){
				friendsinfo = vmate.get(jlmate.getSelectedIndex());
			}
			if(e.getButton()==3 ){
				if(jlmate.getSelectedIndex()>=0){
					popmenu.show(jlmate, e.getX(), e.getY());
				}
			}
		}else if(e.getSource()==jlfamliy){
			if(jlfamliy.getSelectedIndex()>=0){
				friendsinfo = vfamliy.get(jlfamliy.getSelectedIndex());
			}
			if(e.getButton()==3 ){
				if(jlfamliy.getSelectedIndex()>=0){
					popmenu.show(jlfamliy, e.getX(), e.getY());
				}
			}
		}else if(e.getSource()==jlblack){
			if(jlblack.getSelectedIndex()>=0){
				friendsinfo = vblack.get(jlblack.getSelectedIndex());
			}
			if(e.getButton()==3 ){
				if(jlblack.getSelectedIndex()>=0){
					popmenu.show(jlblack, e.getX(), e.getY());
				}
			}
		}else if(e.getSource()==lbmyinfo){
			if(e.getClickCount()==2){
				new  qqUpdate(myinfo,this);
				SendCmd.sendAll(vinfo, myinfo, Cmd.CMD_CHANGESTATE);
				refresh();
			}
		}
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	public void mouseDragged(MouseEvent arg0) {}
	public void mouseMoved(MouseEvent arg0) {}
	
	class DataModel extends AbstractListModel{
		private static final long serialVersionUID = 6176054163998588472L;
		Vector<ID> data;
		//用构造方法来接受好友信息
		public DataModel(){}
		public DataModel(Vector<ID> data){
			this.data=data;
		}
		public Object getElementAt(int index) {
			ID info=data.get(index);
			return info.getNickname()+"("+info.getQqnumber()+")"+info.getSignature()+" ";
		}
		public int getSize() {
			return data.size();
		}
	}
	//继承一个渲染器
	class Myheadimg extends DefaultListCellRenderer{
		private static final long serialVersionUID = -1236038736169907926L;
		Vector<ID> datas;
		public Myheadimg(Vector<ID> datas){
			this.datas = datas;
		}
		@SuppressWarnings("unused")
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			//获取list里面的某一行
			Component c = super.getListCellRendererComponent(list, value,
					index, isSelected, cellHasFocus);
			if(index>=0 && index<datas.size()){
				ID user=datas.get(index);
				String state=user.getState();
				String head=user.getHead();
				String filename=" ";
				if(state.equals(Cmd.STATUS[0])){
					filename=head;
				}else if(state.equals(Cmd.STATUS[1])){
					int pos=head.indexOf('.');
					String pre=head.substring(0,pos);
					String fix=head.substring(pos,head.length());
					filename=pre+"_h"+fix;
				}else if(state.equals(Cmd.STATUS[2])){
					int pos=head.indexOf('.');
					String pre=head.substring(0,pos);
					String fix=head.substring(pos,head.length());
					filename=pre+"_l"+fix;
				}else if(state.equals(Cmd.STATUS[3])){
					int pos=head.indexOf('.');
					String pre=head.substring(0,pos);
					String fix=head.substring(pos,head.length());
					filename=pre+"_x"+fix;	
				}
				setIcon(new ImageIcon(filename));	//设置当前逐渐的图标为一张图片
			}
			setOpaque(false);
			return this;
		}
	}

	public void createTrayMenu() {
		popTray = new PopupMenu();
		miOpen = new MenuItem("打开");
		miExit = new MenuItem("退出");
		miOnline = new MenuItem("在线");
		miLevel = new MenuItem("隐身");
		miBuys = new MenuItem("忙碌");
		
		miOpen.addActionListener(this);
		miExit.addActionListener(this);
		miOnline.addActionListener(this);
		miLevel.addActionListener(this);
		miBuys.addActionListener(this);
		
		popTray.add(miOpen);
		popTray.add(miExit);
		popTray.add(miOnline);
		popTray.add(miLevel);
		popTray.add(miBuys);
	}
	
	public void setTrayIcon() {
		Image image=new ImageIcon("images/tb1.png").getImage();
		trayIcon=new TrayIcon(image,"QQ2016",popTray);
		trayIcon.setImageAutoSize(true);
	}

	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {
		iddao.changeStatus(myinfo.getQqnumber(), Cmd.STATUS[1]);
	}
	public void windowIconified(WindowEvent e) {
		SystemTray tray = SystemTray.getSystemTray();
		if(trayIcon!=null){
			tray.remove(trayIcon);
		}
		try {
			 //把自己创建的托盘加到系统托盘里面
			tray.add(trayIcon);
			// 把当前主面板隐藏起来
			qqMain.this.setVisible(false);
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	public void run() {
		
		try{
			DatagramSocket soket=new DatagramSocket(myinfo.getPort());
			while(true){
			
				byte b[]=new byte[1024*512];
				//数据包
				DatagramPacket packet=new DatagramPacket(b,0,b.length);
				soket.receive(packet);		//接收数据包
				//字节输入流
				ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
				//对象输入流
				ObjectInputStream ois = new ObjectInputStream(bais);
				SendMsg msg = (SendMsg)ois.readObject();
				myinfo = msg.friendInfo;
				friendsinfo = msg.myInfo;
				switch(msg.cmd){
					case Cmd.CMD_ADDFRI: 	//接受请求
						String str = "【"+friendsinfo.getNickname()+"】请求添加你为好友，是否同意？";
						SendMsg msg2 = new SendMsg();
						if(JOptionPane.showConfirmDialog(null, str,"添加好友",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
							msg2.cmd = Cmd.CMD_ARGEE;
							iddao.addFriend(myinfo.getQqnumber(), friendsinfo.getQqnumber());
							refresh();
						}else{
							msg2.cmd = Cmd.CMD_REFUSE;
						}
						msg2.myInfo=myinfo;
						msg2.friendInfo=friendsinfo;
						SendCmd.send(msg2);
						break;
					case Cmd.CMD_ARGEE:		//同意请求
						refresh();
						break; 
					case Cmd.CMD_REFUSE:	//拒绝请求
						str = "【"+friendsinfo.getNickname()+"】拒绝了你的好友请求。";
						JOptionPane.showMessageDialog(null, str);
						break;
					case Cmd.CMD_DELFRIEND:		//删除好友
						refresh();
						break;
					case Cmd.CMD_SEND: 	//接受消息
						new Sound(msg.cmd);
						refresh();
						qqChat chat = openChat();
						try {
						//向接收框里面添加记录
							chat.appendView(msg.myInfo.getNickname(), msg.doc);
						} catch (BadLocationException e) {
							e.printStackTrace();
						}
						break;
					case Cmd.CMD_SHAKE:		//接受抖动
						chat = openChat();
						chat.shake();
						break;
					case Cmd.CMD_FILE:	
						str = friendsinfo.getNickname()+"发送了一个【"+msg.fileName+"文件】，是否接收？";
						if(JOptionPane.showConfirmDialog(null, "是否接收文件","接收文件",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
							JFileChooser chooser = new JFileChooser(" ");
				            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
				            chooser.setDialogTitle("保存文件");
				            if (chooser.showOpenDialog(null) == chooser.APPROVE_OPTION) {
				            	String ext = msg.fileName.substring(msg.fileName.indexOf('.'),msg.fileName.length());
				            	String filename = chooser.getSelectedFile().getAbsolutePath()+ext;
				            	/**
				            	 * 使用文件输出流把接收到的字节数组输出的对应的文件中
				            	 * */
				            	FileOutputStream fos = new FileOutputStream(filename);
				            	fos.write(msg.b);
				            	fos.flush();
				            	fos.close();
				            }
						}
						break;
					case Cmd.CMD_ONLINE:		//上线通知
						new Sound(msg.cmd);
						refresh();
						new qqTip(friendsinfo);
						break;
					case Cmd.CMD_OFFLINE:		//离线通知
						refresh();
						friendsinfo.setState("离线");
						new qqTip(friendsinfo);
						break;
					case Cmd.CMD_CHANGESTATE:	//更改状态
						refresh();
						break;
				}
			}
		}
		catch (Exception e) {
			
		}
	}
	
	@SuppressWarnings("deprecation")
	public qqChat openChat(){
		/**
		 * 点击了聊天菜单,不是去new一个ChatUI类出来
		 * 而是去一个集合里面得到一个聊天窗口对象
		 */
		qqChat chat = chatWin.get(friendsinfo.getQqnumber());
		/**
		 * 如果是第一次找人聊天,在集合里面得到的聊天对象是null
		 * 去new一个聊天窗口对象 new ChatUI()
		 * 把这个对象放到集合里面去
		 * */
		if(chat==null){
			chat = new qqChat(myinfo,friendsinfo,vfamliy);
			chatWin.put(friendsinfo.getQqnumber(),chat);
		}
		chat.show();
		return chat;
	}

	public void itemStateChanged(ItemEvent e) {
		if(e.getSource()==cbstate){
			String str=cbstate.getSelectedItem().toString().trim();
			if(str.equals("在线")){
				String status = Cmd.STATUS[0];
				changeState(status);
				iddao.changeStatus(myinfo.getQqnumber(), status);
				SendCmd.sendAll(vinfo, myinfo, Cmd.CMD_CHANGESTATE);
			}else if(str.equals("忙碌")){
				 String status = Cmd.STATUS[2];  
				 changeState(status);
				 iddao.changeStatus(myinfo.getQqnumber(), status);
				 SendCmd.sendAll(vinfo, myinfo, Cmd.CMD_CHANGESTATE);
			}else if(str.equals("隐身")){
				String status = Cmd.STATUS[1];
				changeState(status);
				iddao.changeStatus(myinfo.getQqnumber(), status);
				SendCmd.sendAll(vinfo, myinfo, Cmd.CMD_CHANGESTATE);
			}
		}
	}
}
