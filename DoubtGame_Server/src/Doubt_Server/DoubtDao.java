package Doubt_Server;

import java.sql.*;
import java.util.*;

import Shared.DoubtUser;


public class DoubtDao extends SuperDao{
	
//DoubtUser user;

	//���� 1�� ������ ������
	public DoubtUser getUser(String id) {
		String sql = " select * from userinfo where id = ? " ;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DoubtUser user = null;
		
		try {
			pstmt = super.conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				user = new DoubtUser();
				user.setId(rs.getString("id"));
				user.setPass(rs.getString("pass"));
				user.setWin(rs.getInt("win"));
				user.setLose(rs.getInt("lose"));
				user.setCardback(rs.getString("cardback"));
//				System.out.println(user);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
//		} finally{
//			try{
//				if(rs!=null){rs.close();}
//				if(pstmt!=null){rs.close();}
//				if(super.conn != null){super.closeConnection();}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			
		}
		return user;
	}
	
	// ī�� ���. ���� ������Ʈ�ؼ� db�� ����
<<<<<<< HEAD
	public int UpdateDeck(String id, int i) {
=======
	public int UpdateDeck(String id, int[] i) {
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
		String sql = " update cardinfo set cardindex = ? ";
		sql+=" where id = ?";
		
		int cnt = -1;
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = super.conn.prepareStatement(sql);
			
			pstmt.setInt(1, this.getUser(id).getCard().length);
			pstmt.setString(2, this.getUser(id).getId());
			
			super.conn.setAutoCommit(false);
<<<<<<< HEAD
			cnt = pstmt.executeUpdate();
=======
			for(int j = 0; j < i.length; j++){
				cnt = pstmt.executeUpdate();
			}
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
			super.conn.commit();
			
		} catch (SQLException e) {
			try {
				super.conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				return -1;
			}
			e.printStackTrace();
//		} finally{
//			try{
//				if(pstmt != null){pstmt.close();}
//				if(super.conn != null){super.closeConnection();}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
		return cnt;
	}
	
	// ������ 52�� ���� �����κ��� ������ db�� ����
	public int[] getCarddeck() {
		String sql = " insert into cardinfo(player, cardindex)";
		sql += " values(?, default)";
		int cnt[] = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = super.conn.prepareStatement(sql);
			pstmt.setString(1, this.getUser(id).getId());
			pstmt.setInt(2, this.getUser(id).getCard().length);
			
			super.conn.setAutoCommit(false);
			for(int i=0; i<this.getUser(id).getCard().length; i++){
				cnt[i]  = pstmt.executeUpdate();
			}
			
			super.conn.commit();
			
		} catch (SQLException e) {
			try{
				super.conn.rollback();
			} catch(SQLException e1){
				e1.printStackTrace();
				return null;
			}
			e.printStackTrace();
//		} finally{
//			try{
//				if(pstmt != null){pstmt.close();}
//				if(super.conn != null){super.closeConnection();}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
		return cnt;
	}
	
	// �÷��� cardinfo db�� ����� ������ ����.
	public int deleteDeckCard(String id, int playerDropedCard) {
		String sql = " delete from cardifo where id=? ";
		int cnt =  -1;
		PreparedStatement pstmt = null;
		try {
			pstmt = super.conn.prepareStatement(sql);
			pstmt.setString(1, id);
			cnt = pstmt.executeUpdate();
			
			super.conn.commit();
			
		} catch (SQLException e) {
			try{
				super.conn.rollback();
			} catch(SQLException e1){
				e1.printStackTrace();
				return -1;
			}
			e.printStackTrace();
//		} finally{
//			try{
//				if(pstmt != null){pstmt.close();}
//				if(super.conn != null){super.closeConnection();}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
		return cnt;
	}
	
	// ���� ����. �� �ʱ�ȭ. 
	public int resetDeck(String id) {
		String sql = " delete from cardifo where id = ?";
		int cnt = -1;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = super.conn.prepareStatement(sql);
			for(int i = 1; i<5; i++){
				pstmt.setString(i, id);
			}
			cnt = pstmt.executeUpdate();
			
			super.conn.commit();
			
		} catch (SQLException e) {
			try{
				super.conn.rollback();
			}catch(SQLException e1){
				e1.printStackTrace();
				return -1;
			}
			e.printStackTrace();
//		} finally {
//			try{
//				if(pstmt != null){pstmt.close();}
//				if(super.conn != null){super.closeConnection();}
//			}catch(SQLException e){
//				e.printStackTrace();
//			}
		}
		return cnt;
	}
	
	// ���� 1�� ������ �����ϱ�
	public int createUser(String userId, String userPw, String userCardImage) {
		String sql = "  insert into userinfo( id, pass, cardback )";
        sql += " values( ?, ?, ? )";
        
        int cnt = -1;
        
        PreparedStatement pstmt = null;
        
        try {
			pstmt = super.conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			pstmt.setString(3, userCardImage);
			
			super.conn.setAutoCommit(false);
			cnt = pstmt.executeUpdate();
			super.conn.commit();
			
		} catch (SQLException e) {
			try {
				super.conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				return -1;
			}
			e.printStackTrace();
//		} finally {
//			try{
//				if(pstmt != null){pstmt.close();}
//				if(super.conn != null){super.closeConnection();}
//			}catch(SQLException e){
//				e.printStackTrace();
//			}
		}
		return cnt;
		
	}

	public void UpdateUser(DoubtUser user2) {
		
	}	

}
