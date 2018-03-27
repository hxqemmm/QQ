
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.qq.base.Cmd;
import com.qq.base.SendCmd;
import com.qq.base.SendMsg;
import com.qq.bean.ID;

@SuppressWarnings("serial")
public class qqChat extends JFrame implements ActionListener,ItemListener{
	private JLabel title;
	
	JTextPane txtReceive,txtSend;			 		// �������ͷ�����Ϣ���
	JButton btnSend,btnClose;						//���ͺ͹رհ�ť
	JButton btnShake,btnFile,btnColor,btnFace;		//�������ļ�����ɫ������ 
	JComboBox cbFont,cbSize;						//����ʹ�С
	ID myInfo,friendInfo;							//�����ߺͽ�����
	Vector<ID> familyGroup;
	
	JLabel lbmyinfo,lbmin,lbclose,lbcenter,lbsouth;
	
	String sFont[] = {"����","����","����","����"};
	String sSize[]={"8","10","12","14","16","18","24","28","32","36","72"};
	Font font;
	
	public qqChat(ID myInfo,ID friendInfo,Vector<ID> familyGroup) {
		//setResizable(false);
		setIconImage(new ImageIcon("images/tubiao.png").getImage());	//ͼ��
		String str =myInfo.getNickname()+"("+myInfo.getQqnumber() +")��";
		str += friendInfo.getNickname()+"("+friendInfo.getQqnumber()+")��������...";
		setTitle(str);
		
		this.myInfo = myInfo;
		this.friendInfo = friendInfo;
		this.familyGroup = familyGroup;
		
		setIconImage(new ImageIcon(friendInfo.getHead()).getImage());
		
		title = new JLabel(str,new ImageIcon(friendInfo.getHead()),JLabel.LEFT);
		title.setForeground(Color.WHITE);
		title.setOpaque(false);
		
		JLabel titlebg = new JLabel(new ImageIcon("images/2.jpg"));
		titlebg.setLayout(new FlowLayout(FlowLayout.LEFT));
		titlebg.add(title);
		// * ��title���뵽JFrame����,���Ҳ��õ��Ǳ߿򲼾�,���ڱ���
		add(titlebg,BorderLayout.NORTH);
		
		/**
		 * ����������Ϲ������
		 * */
		txtReceive = new JTextPane();
		
		JLabel btnPanel = new JLabel(new ImageIcon("images/11.jpg"));
		btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		cbFont = new JComboBox(sFont);
		cbSize = new JComboBox(sSize);
		cbFont.addItemListener(this);
		cbSize.addItemListener(this);
		
		btnShake = new JButton(new ImageIcon("images/zd.png"));
		
		btnShake.setMargin(new Insets(0,0,0,0));
		btnFile = new JButton("�ļ�");
		btnColor = new JButton("��ɫ");
		btnFace = new JButton(new ImageIcon("images/sendFace.png"));
		btnFace.setMargin(new Insets(0,0,0,0));
		
		btnShake.addActionListener(this);
		btnFile.addActionListener(this);
		btnColor.addActionListener(this);
		btnFace.addActionListener(this);
		
		btnPanel.add(cbFont);
		btnPanel.add(cbSize);
		btnPanel.add(btnColor);
		btnPanel.add(btnShake);
		btnPanel.add(btnFace);
		btnPanel.add(btnFile);
		
		txtSend = new JTextPane();
		
		btnSend = new JButton("����(S)");
		btnSend.setMnemonic('S');
		btnClose = new JButton("�ر�(X)");
		btnClose.setMnemonic('X');
		btnSend.addActionListener(this);
		btnClose.addActionListener(this);
		
		JLabel bottombg = new JLabel(new ImageIcon("images/11.jpg"));
		bottombg.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bottombg.add(btnSend);
		bottombg.add(btnClose);
		
		JPanel sendPanel = new JPanel(new BorderLayout());
		sendPanel.add(btnPanel,BorderLayout.NORTH);
		sendPanel.add(txtSend,BorderLayout.CENTER);
		sendPanel.add(bottombg,BorderLayout.SOUTH);
		
		//�м䲿�ֵ���壬�������񲼾�
		JPanel centerPanel = new JPanel(new GridLayout(2,1,0,0));
		centerPanel.add(new JScrollPane(txtReceive));
		centerPanel.add(new JScrollPane(sendPanel));
		add(centerPanel);
		
		/*setResizable(false);
		title = new JLabel(new ImageIcon("images/chat.png"));
		title.setLayout(new BorderLayout());
		add(title);
		*/
		
		setVisible(true);
		setTitle("������");
		setSize(600,540);
		setResizable(false);//�ô��ڲ��ɸı��С
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource()==cbFont){
			/**
			 * �������������Ĵ�С
			 * */
			setFont();
		}else if(e.getSource()==cbSize){
			setFont();
		}
	}
	
	public void setFont() {
		String sf=sFont[cbFont.getSelectedIndex()];
		String size = sSize[cbSize.getSelectedIndex()];
		/**
		 * ����һ��Font����
		 * ���췽��:����,����Ĵ�С
		 * */
		font = new Font(sf,Font.PLAIN,Integer.parseInt(size));
		/**
		 * ���÷����ı��������
		 **/
		txtSend.setFont(font);		
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public void actionPerformed(ActionEvent e) {
	
		if(e.getSource()==btnShake){		//���ڶ���
			SendMsg msg = new SendMsg();
			msg.cmd = Cmd.CMD_SHAKE;
			msg.myInfo = myInfo;
			msg.friendInfo = friendInfo;
			SendCmd.send(msg);
			shake();
		}else if(e.getSource()==btnColor){
			//����һ����ɫѡ���
			JColorChooser colordlg = new JColorChooser();
			//��ѡ��򣬻�ȡѡ�е���ɫ
			Color color=colordlg.showDialog(this, "��ѡ��������ɫ", Color.BLACK);
			txtSend.setForeground(color);
		}else if(e.getSource()==btnFace){
			int x,y;
			x=this.getLocation().x+220;
			y=this.getLocation().y-28;
			//��QQ���鴰��
			new qqBq(this,x,y);
		}else if(e.getSource()==btnFile){
	
			FileDialog dlg = new FileDialog(this,"��ѡ��Ҫ���͵��ļ�(64K����)",FileDialog.LOAD);
			dlg.show();
			String filename = dlg.getDirectory() + dlg.getFile();
			try {  
				if(filename!=null&&!filename.equals("")&&!filename.equals("nullnull")){
					// * ͨ���ļ����������ļ�������д���ֽ���������
					FileInputStream fis = new FileInputStream(filename);
					int size = fis.available();
					byte b[] = new byte[size];
					fis.read(b);
					
					SendMsg msg = new SendMsg();
					msg.cmd = Cmd.CMD_FILE;
					msg.myInfo=myInfo;
					msg.friendInfo = friendInfo;
					msg.b = b;
					msg.fileName = dlg.getFile();
					SendCmd.send(msg);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}else if(e.getSource()==btnSend){
			if(txtSend.getText().equals("")){
				JOptionPane.showMessageDialog(this, "��������Ҫ���͵����ݡ�");
				return;
			}
			//  �ѷ��Ϳ�������ύ�����տ�
			try {
				/**
				 * �ѷ��Ϳ�������ύ�����տ�
				 * */
				appendView(myInfo.getNickname(), txtSend.getStyledDocument());
				
				
				if(friendInfo.getGroupname()!=null && friendInfo.getGroupname().equals(Cmd.GROUPNAME[2])){
					//SendCmd.sendAll(familyGroup, myInfo, Cmd.CMD_SEND,txtSend.getStyledDocument());
				}else{
					SendMsg msg= new SendMsg();
					msg.cmd = Cmd.CMD_SEND;
					msg.myInfo = myInfo;
					msg.friendInfo = friendInfo;
					msg.doc = txtSend.getStyledDocument();
					SendCmd.send(msg);
				}
				txtSend.setText("");
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}else if(e.getSource()==btnClose){
			dispose();
		}
	}

	//�ļ����տ�
	public void appendView(String name, StyledDocument xx) throws BadLocationException {
		StyledDocument vdoc = txtReceive.getStyledDocument();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		String time = sdf.format(date);
		
		SimpleAttributeSet as = new SimpleAttributeSet();
	
		String s =name + "    " + time + "\n";
		vdoc.insertString(vdoc.getLength(), s, as);
		
		int end = 0;
		while (end < xx.getLength()) {
			
			Element e0 = xx.getCharacterElement(end);
			
			SimpleAttributeSet as1 = new SimpleAttributeSet();
			StyleConstants.setForeground(as1, StyleConstants.getForeground(e0.getAttributes()));
			StyleConstants.setFontSize(as1, StyleConstants.getFontSize(e0.getAttributes()));
			StyleConstants.setFontFamily(as1, StyleConstants.getFontFamily(e0.getAttributes()));

			s = e0.getDocument().getText(end, e0.getEndOffset() - end);
			
			if("icon".equals(e0.getName())){
				vdoc.insertString(vdoc.getLength(), s, e0.getAttributes());
			}
			else{
				vdoc.insertString(vdoc.getLength(), s, as1);
			}
			/**
			 * getEndOffset�����������ǻ�ȡ��ǰԪ�صĳ���
			 * */
			end = e0.getEndOffset(); 
		}
		/**
		 * ����һ������
		 * */
		vdoc.insertString(vdoc.getLength(), "\n", as);
		/**
		 * ������ʾ��ͼ���ַ���λ�����ĵ���β���Ա���ͼ����
		 * */
		txtReceive.setCaretPosition(vdoc.getLength());
	}
	//���ڶ���
	
	//���ڶ���
	public void shake() {
		int x = this.getLocation().x;
		int y = this.getLocation().y;
		/**
		 * ÿ��50����ı��´��ڵ�����  
		 * */
		for(int i=0;i<20;i++){
			if(i%2==0){
				this.setLocation(x+2, y+2);
			}else{
				this.setLocation(x-2, y-2);
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}
