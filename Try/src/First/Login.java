package First;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;

class User {
	private String username;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	private String passwd;
	public String toString() {
		return username+":"+passwd;
	}

}
public class Login extends JFrame implements ActionListener{

	JButton login=new JButton("��¼");
	JButton cancel=new JButton("ȡ��");
	JLabel name=new JLabel("�û�����");
	JLabel passwd=new JLabel("���룺");
	JTextField jname=new JTextField(10);
	JTextField jpasswd=new JTextField(10);
	Map<String, String> users = new HashMap<String, String>();
	
	public Login() {
		JPanel jp=new JPanel();
		jp.setLayout(new GridLayout(3,2));
		name.setHorizontalAlignment(SwingConstants.RIGHT);
		passwd.setHorizontalAlignment(SwingConstants.RIGHT);
		jp.add(name);
		jp.add(jname);
		jp.add(passwd);
		jp.add(jpasswd);
		jp.add(login);
		jp.add(cancel);
		
		login.addActionListener(this);
		cancel.addActionListener(this);
		
		this.add(jp,BorderLayout.CENTER);
		this.setTitle("�û���¼");
		this.setLocation(500,300);
		this.setSize(400,300);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==login) {
			String tname=jname.getText();
			String tpass=jpasswd.getText();
			//if(tname.equalsIgnoreCase("user")&& tpass.equalsIgnoreCase("123456"))
				//System.out.println("��ӭ��¼");
			if(validate(tname,tpass)) {
				System.out.println("��ӭ��¼");
				//dispose();//����������,�ͷ��ڴ���Դ
				//new JTableTest();
				JOptionPane.showMessageDialog(null, "��¼�ɹ�����ӭ������");
				//��ʾ��Ϣ��ʾ��
				System.exit(0);
			}
			else {
				JOptionPane.showMessageDialog(null, "�û��������������µ�¼��");
				//��ʾ��Ϣ��ʾ��
				jname.setText("");
				jpasswd.setText("");
			}
		}
		else if (e.getSource()==cancel) {
			System.exit(0);
		}
	}
	
	//�������ݿ�
	public static Connection getConnection() {
		Connection con = null;

		String url = "jdbc:MySQL://127.0.0.1:3306/test";
		String user = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	public static void close(ResultSet rs, PreparedStatement ps, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	// �����ݿ��л�ȡ�û���Ϣ�б�
	public static List getUserInfo() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "select * from userinfo";
		try {
			//����getConnection()���������Connection���� con
			con = getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			List list = new ArrayList();

			while (rs.next()) {
				User user = new User();
				user.setUsername(rs.getString("username"));
				user.setPasswd(rs.getString("upasswd"));
				list.add(user);
				System.out.println(user);
			}
			//System.out.println("list======" + list);
			return list;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		} finally {
			close(rs, ps, con);
		}
	}

	private boolean validate(String userinput, String userpasswd) {
		// TODO Auto-generated method stub
	
		List al=this.getUserInfo();
		boolean flag=false;
		Iterator it=al.iterator();
		while(it.hasNext())
		{
			User u=(User)it.next();
			String key =u.getUsername();
			//���˼�����ͨ��map���ϵ�get������ȡ���Ӧ��ֵ��
			String value  = u.getPasswd();
			//System.out.println("key:"+key+",value:"+value);
			if(key.equals(userinput) && value.equals(userpasswd))
			{
				flag=true;
				break;
			}
		}
		return flag;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame.setDefaultLookAndFeelDecorated(true);
		new Login();
	}

}

