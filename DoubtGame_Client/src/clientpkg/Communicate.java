package clientpkg;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Communicate implements PacketNumber{
	Socket socket;
	BufferedReader reader; // 입력스트림 
	PrintWriter writer; // 출력스트림
	PrintWriter 	pw = null;
	
	public Communicate() {
	}

	public void sendTo(String msg) {
		try {
			pw = new PrintWriter(socket.getOutputStream(), true);
			pw.println(msg);
			System.out.println(msg);
			System.out.println("Sent successful");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setSocket(Socket socket){
		this.socket = socket;
// 		this.pw = pw;
	}

}
