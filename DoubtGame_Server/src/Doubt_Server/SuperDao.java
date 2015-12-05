package Doubt_Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SuperDao {
	static final String driver = "oracle.jdbc.driver.OracleDriver";
	static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	static final String id = "userdb";
	static final String password = "oracle";
	
	Connection conn = null;
	//접속 객체(접속 허가권 취득 객체), 수퍼 클래스의 생성자에서 구현.
	
	public SuperDao(){
		// 단계 1 : 드라이브 로딩(수퍼 클래스의 생성자)
		try {
			Class.forName(driver);// 문자열인 driver를 이용해서 객체 생성
			
			// 단계 2 : 접속 객체 구하기
			this.conn = DriverManager.getConnection(url, id, password);
			
			if(this.conn != null){
				System.out.println("접속 성공.");
			} else {
				System.out.println("접속 실패.");
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 경로 다시 확인하세요.");
			e.printStackTrace();
			
		} catch (SQLException e) {
			System.out.println("접속 객체 취득 실패");
			e.printStackTrace();
		} 	
	}

	public void closeConnection() {
		// TODO Auto-generated method stub
		try{
			if(this.conn != null){this.conn.close();}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
