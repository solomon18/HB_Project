
package dbpkg;


import java.sql.*;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import clientpkg.Communicate;
import clientpkg.Member_List;
import clientpkg.PacketNumber;

//DB 처리
public class MemberDAO {

    private static final String DRIVER
        = "oracle.jdbc.driver.OracleDriver";
    private static final String URL
        = "jdbc:oracle:thin:@192.168.10.84:1521:xe";

    private static final String USER = "userdb"; //DB ID
    private static final String PASS = "oracle"; //DB 패스워드
    Member_List mList;

    public MemberDAO() {}

    public MemberDAO(Member_List mList){
        this.mList = mList;
        System.out.println("DAO=>"+mList);
    }
    
    /**DB연결 메소드*/
    public Connection getConn(){
        Connection con = null;

        try {
            Class.forName(DRIVER); //1. 드라이버 로딩
            con = DriverManager.getConnection(URL,USER,PASS); //2. 드라이버 연결
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
    
	Communicate comm = new Communicate();
	PacketNumber packet;
	
    /**한사람의 회원 정보를 얻는 메소드*/
    public MemberDTO getMemberDTO(String id){

        MemberDTO dto = new MemberDTO();
        Connection con = null;       //연결
        PreparedStatement ps = null; //명령
        ResultSet rs = null;         //결과

        try {
            con = getConn();
            String sql = "select * from userinfo where id=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, id);

            rs = ps.executeQuery();

            if(rs.next()){
                dto.setId(rs.getString("id"));
                dto.setPw(rs.getString("pass"));
                dto.setCardback(rs.getString("cardback"));
                dto.setWinnum(rs.getInt("win"));
                dto.setLosenum(rs.getInt("lose"));
            }
//            comm.sendTo(packet.GetUser + user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return dto;
    }
	
    /**멤버리스트 출력*/


    public Vector getMemberList(){

        Vector data = new Vector();  //Jtable에 값을 쉽게 넣는 방법 1. 2차원배열   2. Vector 에 vector추가

        Connection con = null;       //연결
        PreparedStatement ps = null; //명령
        ResultSet rs = null;         //결과

        try{

            con = getConn();
            String sql = " select * from userinfo order by id ";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){
                String id = rs.getString("id");
                String pw = rs.getString("pass");
                String cardback = rs.getString("cardback");
                int winnum = rs.getInt("win");
                int losenum = rs.getInt("lose");

                Vector row = new Vector();
                row.add(id);
                row.add(pw);
                row.add(cardback);
                row.add(winnum);
                row.add(losenum);
      
                data.add(row);
            }//while

        }catch(Exception e){

            e.printStackTrace();

        }
        return data;
    }//getMemberList()

    /**회원 등록*/
    public boolean insertMember(MemberDTO dto){

        boolean ok = false;

        Connection con = null;       //연결
        PreparedStatement ps = null; //명령

        try{

            con = getConn();

            String sql = " insert into userinfo(id, pass, cardback, win, lose)";

                   sql += " values(?, ?, ?, ?, ? )";

            ps = con.prepareStatement(sql);
            ps.setString(1, dto.getId());
            ps.setString(2, dto.getPw());
            ps.setString(3, dto.getCardback());
            ps.setInt(4, dto.getWinnum());
            ps.setInt(5, dto.getLosenum());
            System.out.println(sql);
            int r = ps.executeUpdate(); //실행 -> 저장

            if(r>0){
                System.out.println("가입 성공");    
                ok=true;

            }else{
                System.out.println("가입 실패");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
//        comm.sendTo(packet.Create + user.getId());
        return ok;

    }//insertMmeber


    /**회원정보 수정*/
    public boolean updateMember(MemberDTO vMem){
    	
        System.out.println("dto="+vMem.toString());
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;

        try{
            con = getConn();
            String sql = " update userinfo set cardback=?, win=?, lose=? where id=? and pass=?";

            ps = con.prepareStatement(sql);

            ps.setString(1, vMem.getCardback());
            ps.setInt(2, vMem.getWinnum());
            ps.setInt(3, vMem.getLosenum());
            ps.setString(4, vMem.getId());
            ps.setString(5, vMem.getPw());

            int r = ps.executeUpdate(); //실행 -> 수정
            // 1~n: 성공 , 0 : 실패

            if(r>0) ok = true; //수정이 성공되면 ok값을 true로 변경
//            comm.sendTo(packet.Edit + user.getId());

        }catch(Exception e){
            e.printStackTrace();
        }

        return ok;
    }


    


    /**회원정보 삭제 :


     *tip: 실무에서는 회원정보를 Delete 하지 않고 탈퇴여부만 체크한다.*/
    public boolean deleteMember(String id, String pw){

        boolean ok =false ;
        Connection con =null;
        PreparedStatement ps =null;

        try {
            con = getConn();
            String sql = " delete from userinfo where id=? and pass=?";

            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, pw);

            int r = ps.executeUpdate(); // 실행 -> 삭제
            if (r>0) ok=true; //삭제됨;

        } catch (Exception e) {
            System.out.println(e + "-> 오류발생");
        }       
        return ok;
//        comm.sendTo(packet.DeleteUser + user.getId());

    }

    /**DB데이터 다시 불러오기*/    
    public void userSelectAll(DefaultTableModel model) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = getConn();
            String sql = " select * from userinfo order by id";
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            // DefaultTableModel에 있는 데이터 지우기

            for (int i = 0; i < model.getRowCount();) {

                model.removeRow(0);

            }

            while (rs.next()) {

                Object data[] = { rs.getString(1), rs.getString(2), rs.getString(3), 
                		rs.getInt(4), rs.getInt(5)};

                model.addRow(data); 
            }

        } catch (SQLException e) {

            System.out.println(e + "=> userSelectAll fail");

        } finally{

            if(rs!=null)

                try {

                    rs.close();

                } catch (SQLException e2) {

                    // TODO Auto-generated catch block

                    e2.printStackTrace();

                }

            if(ps!=null)

                try {

                    ps.close();

                } catch (SQLException e1) {

                    // TODO Auto-generated catch block

                    e1.printStackTrace();

                }

            if(con!=null)

                try {

                    con.close();

                } catch (SQLException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                }
        }
    }
}

