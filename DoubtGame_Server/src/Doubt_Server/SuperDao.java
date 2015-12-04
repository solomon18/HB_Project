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
	//���� ��ü(���� �㰡�� ��� ��ü), ���� Ŭ������ �����ڿ��� ����.
	
	public SuperDao(){
		// �ܰ� 1 : ����̺� �ε�(���� Ŭ������ ������)
		try {
			Class.forName(driver);// ���ڿ��� driver�� �̿��ؼ� ��ü ����
			
			// �ܰ� 2 : ���� ��ü ���ϱ�
			this.conn = DriverManager.getConnection(url, id, password);
			
			if(this.conn != null){
				System.out.println("���� ����.");
			} else {
				System.out.println("���� ����.");
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("����̹� ��� �ٽ� Ȯ���ϼ���.");
			e.printStackTrace();
			
		} catch (SQLException e) {
			System.out.println("���� ��ü ��� ����");
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
