package dbpkg;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.*;

import clientpkg.GameManager;

public class Member_Login {
	public static void main(String[] args) {
		new LoginSwing("Member_Login");
	}
}

class LoginSwing extends JFrame implements ActionListener{

	JButton btnlogin, btnjoin, btnedit, btnadmin;
	JPanel btpanel, mainpanel;
	JLabel idlb, pwlb;
	JTextField idjtf, pwjtf;
	BorderLayout bl;
	FlowLayout fl;
	GridLayout gl;
	Member_List mList ;
	
	public LoginSwing(String title) {
		super(title);
		this.craeteUI();
		this.setevent();
		this.setVisible(true);
		this.setSize(400,300);
		
	}
	private void craeteUI() {
		btnlogin = new JButton("접속");
		btnjoin = new JButton("가입");
		btnedit = new JButton("수정");
		btnadmin = new JButton("관리자");
		idlb = new JLabel("id : ");
		pwlb = new JLabel("pw : ");
		idjtf = new JTextField();
		pwjtf = new JPasswordField();
		
		btpanel = new JPanel();
		
		bl = new BorderLayout();
		fl = new FlowLayout();
		
		btpanel.setLayout(fl);
		super.setLayout(null);
		
		btpanel.add(btnlogin);
		btpanel.add(btnjoin);
		btpanel.add(btnedit);
		btpanel.add(btnadmin);
		
		super.add(btpanel);
		btpanel.setBounds(40, 20, 300, 50);
		
		super.add(idlb);
		idlb.setBounds(110, 70, 50, 30);
		super.add(pwlb);
		pwlb.setBounds(110, 100, 50, 30);
		
		super.add(idjtf);
		idjtf.setBounds(140, 70, 120, 30);
		super.add(pwjtf);
		pwjtf.setBounds(140, 100, 120, 30);

	}
	
	private void setevent(){
		btnlogin.addActionListener(this);
		btnjoin.addActionListener(this);
		btnedit.addActionListener(this);
		btnadmin.addActionListener(this);
	}
	

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnlogin ) { 
			MemberDAO dao = new MemberDAO();
		    String id = idjtf.getText();
		    String pw = pwjtf.getText();
		    
		    MemberDTO dto = dao.getMemberDTO(id);
		    String id2 = dto.getId();
		    String pw2 = dto.getPw();
		    System.out.println(dto);
		    System.out.println(id + id2);
		    
		    if(id.equals(dto.getId()) && pw.equals(dto.getPw())){
		    	JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다.");
		    	new GameManager(dto.getId()); 
		    	this.setVisible(false);
		    }else
		    	JOptionPane.showMessageDialog(null, "로그인에 실패하였습니다.");
		    
		}
		if(e.getSource() == btnjoin ) { new MemberProc(this);}
		
		if(e.getSource() == btnedit ) {
		    MemberDAO dao = new MemberDAO();
		    String id = "";
		    MemberDTO dto = dao.getMemberDTO(id);
		    try{
		    	while(id.equals("")){
		    		id = JOptionPane.showInputDialog(this, "변경할 아이디를 적어주세요");
				    MemberProc mem = new MemberProc(id, this);
				    
		    	}
		    	

		    	
		    } catch(NullPointerException e1){
		    	System.exit(0);
		    }
		}
		
		if(e.getSource() == btnadmin ) {
			String id = "";
			try{
				while(id.equals("")){
					id = JOptionPane.showInputDialog(this, "관리자 암호를 적어주세요");
					if(id.equals("admin")){
						Member_List mem = new Member_List(id, this);
					}
					else{
						JOptionPane.showMessageDialog(this, "암호를 다시 확인하세요");
					}
				}
			}catch(NullPointerException e2){
				System.exit(0);
			}
		}
	}
}
	


