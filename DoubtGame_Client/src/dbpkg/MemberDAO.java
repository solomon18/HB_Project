
package dbpkg;


import java.sql.*;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

//DB ó��
public class MemberDAO {

    private static final String DRIVER
        = "oracle.jdbc.driver.OracleDriver";
    private static final String URL
        = "jdbc:oracle:thin:@192.168.10.84:1521:xe";

    private static final String USER = "userdb"; //DB ID
    private static final String PASS = "oracle"; //DB �н�����
    Member_List mList;

    public MemberDAO() {}

    public MemberDAO(Member_List mList){
        this.mList = mList;
        System.out.println("DAO=>"+mList);
    }
    
    /**DB���� �޼ҵ�*/
    public Connection getConn(){
        Connection con = null;

        try {
            Class.forName(DRIVER); //1. ����̹� �ε�
            con = DriverManager.getConnection(URL,USER,PASS); //2. ����̹� ����
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    /**�ѻ���� ȸ�� ������ ��� �޼ҵ�*/
    public MemberDTO getMemberDTO(String id){

        MemberDTO dto = new MemberDTO();
        Connection con = null;       //����
        PreparedStatement ps = null; //���
        ResultSet rs = null;         //���

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
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return dto;
    }

    /**�������Ʈ ���*/


    public Vector getMemberList(){

        Vector data = new Vector();  //Jtable�� ���� ���� �ִ� ��� 1. 2�����迭   2. Vector �� vector�߰�

        Connection con = null;       //����
        PreparedStatement ps = null; //���
        ResultSet rs = null;         //���

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

    /**ȸ�� ���*/
    public boolean insertMember(MemberDTO dto){

        boolean ok = false;

        Connection con = null;       //����
        PreparedStatement ps = null; //���

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
            int r = ps.executeUpdate(); //���� -> ����

            if(r>0){
                System.out.println("���� ����");    
                ok=true;

            }else{
                System.out.println("���� ����");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return ok;

    }//insertMmeber


    /**ȸ������ ����*/
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

            int r = ps.executeUpdate(); //���� -> ����
            // 1~n: ���� , 0 : ����

            if(r>0) ok = true; //������ �����Ǹ� ok���� true�� ����

        }catch(Exception e){
            e.printStackTrace();
        }

        return ok;
    }


    


    /**ȸ������ ���� :


     *tip: �ǹ������� ȸ�������� Delete ���� �ʰ� Ż�𿩺θ� üũ�Ѵ�.*/
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

            int r = ps.executeUpdate(); // ���� -> ����
            if (r>0) ok=true; //������;

        } catch (Exception e) {
            System.out.println(e + "-> �����߻�");
        }       
        return ok;
    }

    /**DB������ �ٽ� �ҷ�����*/    
    public void userSelectAll(DefaultTableModel model) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = getConn();
            String sql = " select * from userinfo order by id";
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            // DefaultTableModel�� �ִ� ������ �����

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

