package dbpkg;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Member_List extends JFrame implements MouseListener,ActionListener{

    Vector v;   
    Vector cols;
    DefaultTableModel model;
    JTable jTable;
    JScrollPane pane;
    JPanel pbtn;
    JButton btnInsert;  

    public Member_List(){
    	
        super("회원관리 프로그램");
        MemberDAO dao = new MemberDAO();
        v = dao.getMemberList();
        System.out.println("v="+v+"\n");
        cols = getColumn();
        model = new DefaultTableModel(v, cols);
        jTable = new JTable(model);
        pane = new JScrollPane(jTable){
        	   public boolean isCellEditable(int rowIndex, int mColIndex) {
        		   return false;
        		   
        	   } 
        };
        add(pane);
        pbtn = new JPanel();
        btnInsert = new JButton("회원가입");
        pbtn.add(btnInsert);
        add(pbtn,BorderLayout.NORTH);


        jTable.addMouseListener(this); //리스너 등록
        btnInsert.addActionListener(this); //회원가입버튼 리스너 등록
        setSize(600,600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }//end 생성자

    public Member_List(String id, LoginSwing loginSwing) {
    	
        super("회원관리 프로그램");
        MemberDAO dao = new MemberDAO();
        v = dao.getMemberList();
        System.out.println("v="+v+"\n");
        cols = getColumn();
        model = new DefaultTableModel(v, cols);
        jTable = new JTable(model);
        pane = new JScrollPane(jTable){
        	   public boolean isCellEditable(int rowIndex, int mColIndex) {
        		   return false;
        		   
        	   } 
        };
        add(pane);
        pbtn = new JPanel();
        btnInsert = new JButton("회원가입");
        pbtn.add(btnInsert);
        add(pbtn,BorderLayout.NORTH);


        jTable.addMouseListener(this); //리스너 등록
        btnInsert.addActionListener(this); //회원가입버튼 리스너 등록
        setSize(600,600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	//JTable의 컬럼
    public Vector getColumn(){
    	
        Vector col = new Vector();
        col.add("아이디");
        col.add("비밀번호");
        col.add("카드그림주소");
        col.add("통산 승리");
        col.add("통산 패배");

        return col;
    }//getColumn

    //Jtable 내용 갱신 메서드 

    public void jTableRefresh(){

        MemberDAO dao = new MemberDAO();
        DefaultTableModel model= new DefaultTableModel(dao.getMemberList(), getColumn());
        jTable.setModel(model);

    }

    public static void main(String[] args) {


        new Member_List();


    }//main

    @Override
    public void mouseClicked(MouseEvent e) {
        // mouseClicked 만 사용
        int r = jTable.getSelectedRow();
        String id = (String) jTable.getValueAt(r, 0);
        MemberProc mem = new MemberProc(id,this); //아이디를 인자로 수정창 생성
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //버튼을 클릭하면
        if(e.getSource() == btnInsert ) { new MemberProc(this); }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}

}
