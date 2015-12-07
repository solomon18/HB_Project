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
		} finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){rs.close();}
//				if(super.conn != null){super.closeConnection();}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return user;
	}
	
	// ī�� ���. ���� ������Ʈ�ؼ� db�� ����
	public int UpdateDeck(String id, int[] i) {
		String sql = " update cardinfo set cardindex = ? ";
		sql+=" where id = ? and deckindex = ?";
		
		int cnt = -1;
		
		PreparedStatement pstmt = null;
		this.resetDeck(id);
		try {
			super.conn.setAutoCommit(false);
			for(int j = 1; j < i.length; j++){
				pstmt = super.conn.prepareStatement(sql);
				
				pstmt.setInt(1, i[j]);
				pstmt.setString(2, this.getUser(id).getId());
				pstmt.setInt(3, j);
				
				cnt = pstmt.executeUpdate();
			}
			super.conn.commit();
			
		} catch (SQLException e) {
			try {
				super.conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				return -1;
			}
			e.printStackTrace();
		} finally{
			try{
				if(pstmt != null){pstmt.close();}
//				if(super.conn != null){super.closeConnection();}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}
	
	// ������ 52�� ���� �����κ��� ������ db�� ����
	public int[] getCarddeck(DoubtUser player) {
		String sql = " select * from cardinfo where id = ? and deckindex = ? ";
		int cnt[] = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = super.conn.prepareStatement(sql);
			super.conn.setAutoCommit(false);
			for(int i = 1; i < player.getCard().length; i++){
				
				pstmt.setString(1, id);
				pstmt.setInt(2, i);
				
				rs  = pstmt.executeQuery();
				while(rs.next()){
					cnt[i] = rs.getInt("cardindex");
				}
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
		} finally{
			try{
				if(pstmt != null){pstmt.close();}
//				if(super.conn != null){super.closeConnection();}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}
	
	// �÷��� cardinfo db�� ����� ������ ����.
	public int deleteDeckCard(String id, int playerDropedCard) {
		String sql = " delete from cardinfo where id= ? ";
		int cnt =  -1;
		PreparedStatement pstmt = null;
		try {
			pstmt = super.conn.prepareStatement(sql);
			pstmt.setString(1, id);
			cnt = pstmt.executeUpdate();
			
			super.conn.setAutoCommit(false);
			super.conn.commit();
			
		} catch (SQLException e) {
			try{
				super.conn.rollback();
			} catch(SQLException e1){
				e1.printStackTrace();
				return -1;
			}
			e.printStackTrace();
		} finally{
			try{
				if(pstmt != null){pstmt.close();}
//				if(super.conn != null){super.closeConnection();}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}
	
	// ���� ����. �� �ʱ�ȭ. 
	public int resetDeck(String id) {
		String sql = " delete from cardinfo where id = ? ";
		int cnt = -1;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = super.conn.prepareStatement(sql);
			
			super.conn.setAutoCommit(false);
			pstmt.setString(1, id);
			
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
		} finally {
			try{
				if(pstmt != null){pstmt.close();}
//				if(super.conn != null){super.closeConnection();}
			}catch(SQLException e){
				e.printStackTrace();
			}
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
		} finally {
			try{
				if(pstmt != null){pstmt.close();}
//				if(super.conn != null){super.closeConnection();}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return cnt;
		
	}

	public int createUserDeck(DoubtUser player) {
		String sql = "for i in 1..52 loop ";
		sql+= " insert into cardinfo(id, deckindex, cardindex) values( ?, doubtseq.nextval, default) ";
		sql+= "end loop ";
	        PreparedStatement pstmt = null;
	        int cnt = -1;
	        try {
	        	super.conn.setAutoCommit(false);
//	        	for(int i = 1; i < player.getCard().length; i ++){
					pstmt = super.conn.prepareStatement(sql);
					pstmt.setString(1, player.getId());
					
					cnt = pstmt.executeUpdate();
//	        	}
				super.conn.commit();
				
			} catch (SQLException e) {
				try {
					super.conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} finally {
				try{
					if(pstmt != null){pstmt.close();}
//					if(super.conn != null){super.closeConnection();}
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
	        return cnt;
	}

	

}
