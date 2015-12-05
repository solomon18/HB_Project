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
    JPasswordField pfPw; //비밀번호    
    JButton btnInsert, btnCancel, btnUpdate,btnDelete; //가입, 취소, 수정 , 탈퇴 버튼
    JComboBox<String> cardcombo;
    ImageIcon imageI ;
    String Path = ""; // 문자열 결합을 위한 이미지 경로 
    String imagePath = "Ohye.png"; // 디폴트 이미지 경로
    Member_Login Mlogin ;
    Member_List mList ;
    Communicate comm = new Communicate();
    
  //가입을 위한 인터페이스 제공 생성자. 이 메소드를 멤버리스트에서 호출해서 액션이벤트 처리해서 불러오는 것이다.
    public MemberProc(Member_List mList){ 

        createUI(); // UI작성해주는 메소드
        
        btnUpdate.setEnabled(false);// 상황에 따라 필요한 버튼은 보여주고 아니면 숨긴다.
        btnUpdate.setVisible(false);
        
        btnDelete.setEnabled(false);
        btnDelete.setVisible(false);
        this.mList = mList;
    }   //생성자
    
    // 관리자 페이지에서 쓸 수정과 삭제를 위한 인터페이스 제공 생성자. 이 메소드를 멤버리스트에서 호출해서 액션이벤트 처리해서 불러오는 것이다.
    public MemberProc(String id, Member_List mList){ 
    	
        createUI(); 
        btnInsert.setEnabled(false);// 상황에 따라 필요한 버튼은 보여주고 아니면 숨긴다.
        btnInsert.setVisible(false);
        this.mList = mList;
        
        System.out.println("id="+id);
        
//        MemberDAO dao = new MemberDAO();
//        MemberDTO vMem = dao.getMemberDTO(id);
        viewData2(id);
    }//id를 가지고 생성
    
    // 로그인 화면에서 쓸 수정과 삭제를 위한 인터페이스 제공 생성자. 이 메소드를 멤버리스트에서 호출해서 액션이벤트 처리해서 불러오는 것이다.
	public MemberProc(String id, LoginSwing loginSwing) {
        createUI(); 
        btnInsert.setEnabled(false);// 상황에 따라 필요한 버튼은 보여주고 아니면 숨긴다.
        btnInsert.setVisible(false);
        this.mList = mList;
        
        System.out.println("id="+id);
        comm.setSocket(loginSwing.socket);
        
//        MemberDAO dao = new MemberDAO();
//        MemberDTO vMem = dao.getMemberDTO(id);
        viewData2(id);
        comm.setSocket(loginSwing.socket);
	}

	// 로그인 화면에서 쓸 가입을 위한 인터페이스 제공 생성자. 이 메소드를 멤버리스트에서 호출해서 액션이벤트 처리해서 불러오는 것이다.
    public MemberProc(LoginSwing LoginSwing) {
        createUI(); // UI작성해주는 메소드
        
        btnUpdate.setEnabled(false);// 상황에 따라 필요한 버튼은 보여주고 아니면 숨긴다.
        btnUpdate.setVisible(false);
        
        btnDelete.setEnabled(false);
        btnDelete.setVisible(false);
        this.mList = mList;
        comm.setSocket(LoginSwing.socket);
	}



	//MemberDTO 의 회원 정보를 가지고 수정 인터페이스 화면에 셋팅해주는 메소드
    private void viewData2(String id){

//        String id = vMem.getId();
//        String pw = vMem.getPw();

        //화면에 세팅
        tfId.setText(id);
        tfId.setEditable(false); //편집 안되게
        pfPw.setText(""); //비밀번호는 안보여준다.
//	    if(id==null){
//	    	JOptionPane.showMessageDialog(this, "아이디를 다시 확인하세요");
//	    	this.setVisible(false);
//	    }
    }//viewData
    
//createUI Method
    private void createUI(){
    	
        this.setTitle("회원정보");
        super.setLayout(null);
        
        //아이디
        JLabel bId = new JLabel("아이디 : ");
        super.add(bId);
        bId.setBounds(10, 40, 100, 20);
        tfId = new JTextField();
        super.add(tfId);
        tfId.setBounds(70, 40, 100, 20);

        
        //비밀번호
        JLabel bPw = new JLabel("비밀번호 : ");
        super.add(bPw);
        bPw.setBounds(10, 70, 100, 20);
        pfPw = new JPasswordField();
        super.add(pfPw);
        pfPw.setBounds(80, 70, 100, 20);

        
        //카드백이미지 설정
        JLabel imageL = new JLabel("카드 프로필 : ");
        super.add(imageL);
        imageL.setBounds(10, 100, 100, 20);
        cardcombo = new JComboBox<String>();
        super.add(cardcombo);
        cardcombo.setBounds(100, 100, 200, 20);
        cardcombo.addItem( "imagePath1" );
        cardcombo.addItem( "imagePath2" );
        cardcombo.addItem( "imagePath3" );
        cardcombo.addItem( "imagePath4" );
        
        // 이미지 보여주는 판넬
        imageLabel = new JLabel();
        super.add(imageLabel);
        imageLabel.setBounds(45, 130, 400, 500);
        imageI = new ImageIcon(Path+imagePath);
//        imageLabel.setIcon(imageI);
        String imagefile = Path + "CardBack0.png";
		imageLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource(imagefile)));

        //버튼
        JPanel pButton = new JPanel();
        btnInsert = new JButton("가입");
        btnUpdate = new JButton("수정");  
        btnDelete = new JButton("탈퇴");
        btnCancel = new JButton("취소");      
        pButton.add(btnInsert);
        pButton.add(btnUpdate);
        pButton.add(btnDelete);
        pButton.add(btnCancel); 
        super.add(pButton);
        pButton.setBounds(65, 650, 200, 35);
        
        //선택된 카드이미지 경로 보여주는 텍스트 필드
//        JLabel viewLabel = new JLabel("경로 : ");
//        super.add(viewLabel);
//        viewLabel.setBounds(10, 610, 200, 20);
//        tfImage = new JTextField();
//        super.add(tfImage);
//        tfImage.setBounds(60, 610, 200, 20);
//        tfImage.setText(Path+imagePath);
        
        //액션리스너, 이벤트 리스너 감지기
        btnInsert.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnCancel.addActionListener(this);
        btnDelete.addActionListener(this);
        cardcombo.addItemListener(new MyItemListener());

        setSize(400,750);
        setVisible(true);

        //setDefaultCloseOperation(EXIT_ON_CLOSE); //System.exit(0) //프로그램종료
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //dispose(); //현재창만 닫는다.
       

    }
    
    //이미지 콤보박스 이벤트를 처리할 리스너 이너클래스 
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
            insertMember(); // 가입 처리 메소드
            System.out.println("insertMember() 호출 종료");
        }else if(ae.getSource() == btnCancel){
            this.dispose(); //창닫기 (현재창만 닫힘)
            //system.exit(0)=> 내가 띄운 모든 창이 다 닫힘           
        }else if(ae.getSource() == btnUpdate){
            UpdateMember(); // 수정 처리 메소드         
        }else if(ae.getSource() == btnDelete){
            int x = JOptionPane.showConfirmDialog(this,"정말 삭제하시겠습니까?","삭제",JOptionPane.YES_NO_OPTION);
            if (x == JOptionPane.OK_OPTION){
                deleteMember(); // 삭제 처리 메소드
            }else{
                JOptionPane.showMessageDialog(this, "삭제를 취소하였습니다.");
            }         
        }
        //jTable내용 갱신 메소드 호출.
//        mList.jTableRefresh(); 
    }
//deleteMember버튼의 동작 메소드
    private void deleteMember() {
        String id = tfId.getText();
        String pw = pfPw.getText();
        
        if(pw.length()==0){ //길이가 0이면
            JOptionPane.showMessageDialog(this, "비밀번호를 꼭 입력하세요!");
            return; //메소드 끝
        }
        
        System.out.println(mList);
        MemberDAO dao = new MemberDAO();
        boolean ok = dao.deleteMember(id, pw);
        
        if(ok){
            JOptionPane.showMessageDialog(this, "삭제완료");
            dispose(); 
        }else{
            JOptionPane.showMessageDialog(this, "삭제실패");
        }
        
    }
    
//UpdateMember버튼의 동작 메소드
    private void UpdateMember() {
    	String msg = null;
    	msg = comm.UpdateUser(tfId.getText(), pfPw.getText(), "ImagePath" + cardcombo.getSelectedIndex());
    	if(msg.equals("Sucess"))
    		JOptionPane.showMessageDialog(null, "정보 업데이트에 성공 하였습니다.","성공 ㅇwㅇ",JOptionPane.INFORMATION_MESSAGE);
    	else
    		JOptionPane.showMessageDialog(null, "정보 업데이트에 실패 하였습니다.","실패 orz",JOptionPane.WARNING_MESSAGE);
//        //1. 화면의 정보를 얻는다.
//        MemberDTO dto = getViewData();      
//        //2. 그정보로 DB를 수정
//        MemberDAO dao = new MemberDAO();
//        boolean ok = dao.updateMember(dto);
//        
//        if(ok){
//            JOptionPane.showMessageDialog(this, "수정되었습니다.");
//            this.dispose();
//        }else{
//            JOptionPane.showMessageDialog(this, "수정실패: 값을 확인하세요");    
//        }
        
    }

    //insertMember버튼의 동작 메소드
    private void insertMember(){
    	String msg = null; 
    	msg = comm.CreateUser(tfId.getText(), pfPw.getText(), "ImagePath" + cardcombo.getSelectedIndex());
    	if(msg.equals("Sucess"))
    		JOptionPane.showMessageDialog(null, "계정을 생성 하였습니다.","성공 ㅇㅈㅇ/",JOptionPane.INFORMATION_MESSAGE);
    	else
    		JOptionPane.showMessageDialog(null, "계정생성에 실패 하였습니다.","실패 orz",JOptionPane.WARNING_MESSAGE);
    	
        //화면에서 사용자가 입력한 내용을 얻는다.
//        MemberDTO dto = getViewData();
//        MemberDAO dao = new MemberDAO();        
//        boolean ok = dao.insertMember(dto);
//
//        if(ok){
//            JOptionPane.showMessageDialog(this, "가입이 완료되었습니다.");
//            dispose();
//        }else{
//            JOptionPane.showMessageDialog(this, "가입이 정상적으로 처리되지 않았습니다.");
//        }
    }
    
//회원 리스트 테이블 화면 또는 수정버튼으로 넘어왔을때 테이블에서 담고 있는 내용을 UI에 넣는다.
    public MemberDTO getViewData(){
        //화면에서 사용자가 입력한 내용을 얻는다.
        MemberDTO dto = new MemberDTO();
        String id = tfId.getText();
        String pw = pfPw.getText();
        String cardback = String.valueOf(cardcombo.getSelectedItem());
        int winnum = 0;
        int losenum = 0;

        //dto에 담는다.
        dto.setId(id);
        dto.setPw(pw);
        dto.setCardback(cardback);
        dto.setWinnum(winnum);
        dto.setLosenum(losenum);
        return dto;
    }

}//end


