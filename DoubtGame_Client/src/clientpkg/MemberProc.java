package clientpkg;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dbpkg.MemberDAO;
import dbpkg.MemberDTO;

public class MemberProc extends JFrame implements ActionListener {

    JPanel pNorth, pCenter ;
    JLabel imageLabel ;
    JTextField tfId, tfImage;
    JPasswordField pfPw; //��й�ȣ    
    JButton btnInsert, btnCancel, btnUpdate,btnDelete; //����, ���, ���� , Ż�� ��ư
    JComboBox<String> cardcombo;
    ImageIcon imageI ;
    String Path = ""; // ���ڿ� ������ ���� �̹��� ��� 
    String imagePath = "Ohye.png"; // ����Ʈ �̹��� ���
    Member_Login Mlogin ;
    Member_List mList ;
    Communicate comm = new Communicate();
    
  //������ ���� �������̽� ���� ������. �� �޼ҵ带 �������Ʈ���� ȣ���ؼ� �׼��̺�Ʈ ó���ؼ� �ҷ����� ���̴�.
    public MemberProc(Member_List mList){ 

        createUI(); // UI�ۼ����ִ� �޼ҵ�
        
        btnUpdate.setEnabled(false);// ��Ȳ�� ���� �ʿ��� ��ư�� �����ְ� �ƴϸ� �����.
        btnUpdate.setVisible(false);
        
        btnDelete.setEnabled(false);
        btnDelete.setVisible(false);
        this.mList = mList;
    }   //������
    
    // ������ ���������� �� ������ ������ ���� �������̽� ���� ������. �� �޼ҵ带 �������Ʈ���� ȣ���ؼ� �׼��̺�Ʈ ó���ؼ� �ҷ����� ���̴�.
    public MemberProc(String id, Member_List mList){ 
    	
        createUI(); 
        btnInsert.setEnabled(false);// ��Ȳ�� ���� �ʿ��� ��ư�� �����ְ� �ƴϸ� �����.
        btnInsert.setVisible(false);
        this.mList = mList;
        
        System.out.println("id="+id);
        
//        MemberDAO dao = new MemberDAO();
//        MemberDTO vMem = dao.getMemberDTO(id);
        viewData2(id);
    }//id�� ������ ����
    
    // �α��� ȭ�鿡�� �� ������ ������ ���� �������̽� ���� ������. �� �޼ҵ带 �������Ʈ���� ȣ���ؼ� �׼��̺�Ʈ ó���ؼ� �ҷ����� ���̴�.
	public MemberProc(String id, LoginSwing loginSwing) {
        createUI(); 
        btnInsert.setEnabled(false);// ��Ȳ�� ���� �ʿ��� ��ư�� �����ְ� �ƴϸ� �����.
        btnInsert.setVisible(false);
        this.mList = mList;
        
        System.out.println("id="+id);
        comm.setSocket(loginSwing.socket);
        
//        MemberDAO dao = new MemberDAO();
//        MemberDTO vMem = dao.getMemberDTO(id);
        viewData2(id);
        comm.setSocket(loginSwing.socket);
	}

	// �α��� ȭ�鿡�� �� ������ ���� �������̽� ���� ������. �� �޼ҵ带 �������Ʈ���� ȣ���ؼ� �׼��̺�Ʈ ó���ؼ� �ҷ����� ���̴�.
    public MemberProc(LoginSwing LoginSwing) {
        createUI(); // UI�ۼ����ִ� �޼ҵ�
        
        btnUpdate.setEnabled(false);// ��Ȳ�� ���� �ʿ��� ��ư�� �����ְ� �ƴϸ� �����.
        btnUpdate.setVisible(false);
        
        btnDelete.setEnabled(false);
        btnDelete.setVisible(false);
        this.mList = mList;
        comm.setSocket(LoginSwing.socket);
	}



	//MemberDTO �� ȸ�� ������ ������ ���� �������̽� ȭ�鿡 �������ִ� �޼ҵ�
    private void viewData2(String id){

//        String id = vMem.getId();
//        String pw = vMem.getPw();

        //ȭ�鿡 ����
        tfId.setText(id);
        tfId.setEditable(false); //���� �ȵǰ�
        pfPw.setText(""); //��й�ȣ�� �Ⱥ����ش�.
//	    if(id==null){
//	    	JOptionPane.showMessageDialog(this, "���̵� �ٽ� Ȯ���ϼ���");
//	    	this.setVisible(false);
//	    }
    }//viewData
    
//createUI Method
    private void createUI(){
    	
        this.setTitle("ȸ������");
        super.setLayout(null);
        
        //���̵�
        JLabel bId = new JLabel("���̵� : ");
        super.add(bId);
        bId.setBounds(10, 40, 100, 20);
        tfId = new JTextField();
        super.add(tfId);
        tfId.setBounds(70, 40, 100, 20);

        
        //��й�ȣ
        JLabel bPw = new JLabel("��й�ȣ : ");
        super.add(bPw);
        bPw.setBounds(10, 70, 100, 20);
        pfPw = new JPasswordField();
        super.add(pfPw);
        pfPw.setBounds(80, 70, 100, 20);

        
        //ī����̹��� ����
        JLabel imageL = new JLabel("ī�� ������ : ");
        super.add(imageL);
        imageL.setBounds(10, 100, 100, 20);
        cardcombo = new JComboBox<String>();
        super.add(cardcombo);
        cardcombo.setBounds(100, 100, 200, 20);
        cardcombo.addItem( "imagePath1" );
        cardcombo.addItem( "imagePath2" );
        cardcombo.addItem( "imagePath3" );
        cardcombo.addItem( "imagePath4" );
        
        // �̹��� �����ִ� �ǳ�
        imageLabel = new JLabel();
        super.add(imageLabel);
        imageLabel.setBounds(45, 130, 400, 500);
        imageI = new ImageIcon(Path+imagePath);
//        imageLabel.setIcon(imageI);
        String imagefile = Path + "CardBack0.png";
		imageLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource(imagefile)));

        //��ư
        JPanel pButton = new JPanel();
        btnInsert = new JButton("����");
        btnUpdate = new JButton("����");  
        btnDelete = new JButton("Ż��");
        btnCancel = new JButton("���");      
        pButton.add(btnInsert);
        pButton.add(btnUpdate);
        pButton.add(btnDelete);
        pButton.add(btnCancel); 
        super.add(pButton);
        pButton.setBounds(65, 650, 200, 35);
        
        //���õ� ī���̹��� ��� �����ִ� �ؽ�Ʈ �ʵ�
//        JLabel viewLabel = new JLabel("��� : ");
//        super.add(viewLabel);
//        viewLabel.setBounds(10, 610, 200, 20);
//        tfImage = new JTextField();
//        super.add(tfImage);
//        tfImage.setBounds(60, 610, 200, 20);
//        tfImage.setText(Path+imagePath);
        
        //�׼Ǹ�����, �̺�Ʈ ������ ������
        btnInsert.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnCancel.addActionListener(this);
        btnDelete.addActionListener(this);
        cardcombo.addItemListener(new MyItemListener());

        setSize(400,750);
        setVisible(true);

        //setDefaultCloseOperation(EXIT_ON_CLOSE); //System.exit(0) //���α׷�����
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //dispose(); //����â�� �ݴ´�.
       

    }
    
    //�̹��� �޺��ڽ� �̺�Ʈ�� ó���� ������ �̳�Ŭ���� 
    class MyItemListener implements ItemListener{
    	@Override
    	public void itemStateChanged(ItemEvent event) {
    		Object who = event.getSource();
    		if(who==cardcombo){
    			int index = cardcombo.getSelectedIndex();
    			String imagefile = Path + "CardBack"+(index)+".png";
    			imageLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource(imagefile)));
//    			tfImage.setText(Path+imagefile);
    		}
    	}
    }

//actionPerformed  
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == btnInsert){
            insertMember(); // ���� ó�� �޼ҵ�
            System.out.println("insertMember() ȣ�� ����");
        }else if(ae.getSource() == btnCancel){
            this.dispose(); //â�ݱ� (����â�� ����)
            //system.exit(0)=> ���� ��� ��� â�� �� ����           
        }else if(ae.getSource() == btnUpdate){
            UpdateMember(); // ���� ó�� �޼ҵ�         
        }else if(ae.getSource() == btnDelete){
            int x = JOptionPane.showConfirmDialog(this,"���� �����Ͻðڽ��ϱ�?","����",JOptionPane.YES_NO_OPTION);
            if (x == JOptionPane.OK_OPTION){
                deleteMember(); // ���� ó�� �޼ҵ�
            }else{
                JOptionPane.showMessageDialog(this, "������ ����Ͽ����ϴ�.");
            }         
        }
        //jTable���� ���� �޼ҵ� ȣ��.
//        mList.jTableRefresh(); 
    }
//deleteMember��ư�� ���� �޼ҵ�
    private void deleteMember() {
        String id = tfId.getText();
        String pw = pfPw.getText();
        
        if(pw.length()==0){ //���̰� 0�̸�
            JOptionPane.showMessageDialog(this, "��й�ȣ�� �� �Է��ϼ���!");
            return; //�޼ҵ� ��
        }
        
        System.out.println(mList);
        MemberDAO dao = new MemberDAO();
        boolean ok = dao.deleteMember(id, pw);
        
        if(ok){
            JOptionPane.showMessageDialog(this, "�����Ϸ�");
            dispose(); 
        }else{
            JOptionPane.showMessageDialog(this, "��������");
        }
        
    }
    
//UpdateMember��ư�� ���� �޼ҵ�
    private void UpdateMember() {
    	String msg = null;
    	msg = comm.UpdateUser(tfId.getText(), pfPw.getText(), "ImagePath" + cardcombo.getSelectedIndex());
    	if(msg.equals("Sucess"))
    		JOptionPane.showMessageDialog(null, "���� ������Ʈ�� ���� �Ͽ����ϴ�.","���� ��w��",JOptionPane.INFORMATION_MESSAGE);
    	else
    		JOptionPane.showMessageDialog(null, "���� ������Ʈ�� ���� �Ͽ����ϴ�.","���� orz",JOptionPane.WARNING_MESSAGE);
//        //1. ȭ���� ������ ��´�.
//        MemberDTO dto = getViewData();      
//        //2. �������� DB�� ����
//        MemberDAO dao = new MemberDAO();
//        boolean ok = dao.updateMember(dto);
//        
//        if(ok){
//            JOptionPane.showMessageDialog(this, "�����Ǿ����ϴ�.");
//            this.dispose();
//        }else{
//            JOptionPane.showMessageDialog(this, "��������: ���� Ȯ���ϼ���");    
//        }
        
    }

    //insertMember��ư�� ���� �޼ҵ�
    private void insertMember(){
    	String msg = null; 
    	msg = comm.CreateUser(tfId.getText(), pfPw.getText(), "ImagePath" + cardcombo.getSelectedIndex());
    	if(msg.equals("Sucess"))
    		JOptionPane.showMessageDialog(null, "������ ���� �Ͽ����ϴ�.","���� ������/",JOptionPane.INFORMATION_MESSAGE);
    	else
    		JOptionPane.showMessageDialog(null, "���������� ���� �Ͽ����ϴ�.","���� orz",JOptionPane.WARNING_MESSAGE);
    	
        //ȭ�鿡�� ����ڰ� �Է��� ������ ��´�.
//        MemberDTO dto = getViewData();
//        MemberDAO dao = new MemberDAO();        
//        boolean ok = dao.insertMember(dto);
//
//        if(ok){
//            JOptionPane.showMessageDialog(this, "������ �Ϸ�Ǿ����ϴ�.");
//            dispose();
//        }else{
//            JOptionPane.showMessageDialog(this, "������ ���������� ó������ �ʾҽ��ϴ�.");
//        }
    }
    
//ȸ�� ����Ʈ ���̺� ȭ�� �Ǵ� ������ư���� �Ѿ������ ���̺��� ��� �ִ� ������ UI�� �ִ´�.
    public MemberDTO getViewData(){
        //ȭ�鿡�� ����ڰ� �Է��� ������ ��´�.
        MemberDTO dto = new MemberDTO();
        String id = tfId.getText();
        String pw = pfPw.getText();
        String cardback = String.valueOf(cardcombo.getSelectedItem());
        int winnum = 0;
        int losenum = 0;

        //dto�� ��´�.
        dto.setId(id);
        dto.setPw(pw);
        dto.setCardback(cardback);
        dto.setWinnum(winnum);
        dto.setLosenum(losenum);
        return dto;
    }

}//end


