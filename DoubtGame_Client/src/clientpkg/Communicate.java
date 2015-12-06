package clientpkg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import Shared.DoubtUser;

public class Communicate implements PacketNumber{
	Socket socket;
	BufferedReader reader; // 입력스트림 
	PrintWriter writer; // 출력스트림
	PrintWriter 	pw = null;
	InputStream iStream;
	ObjectInputStream oiStream ;
	
	public Communicate() {
	}
	public Communicate(Socket socket) {
		this.socket = socket;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendTo(String msg) {
		try {
			pw = new PrintWriter(this.socket.getOutputStream(), true);
			pw.println(msg);
			System.out.println(msg);
			System.out.println("Sent successful");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getMessage(String id, String pass){
		String msg = null;
		try{
			pw = new PrintWriter(socket.getOutputStream(), true);
			pw.println(Login + ":" + id + ":" + pass);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			if((msg = reader.readLine()) == null){
				return null;
			}
		}catch(IOException e){
			e.printStackTrace();
			try{
				if(pw != null) {pw.close();}
				if(reader != null){reader.close();}
			}catch(IOException e1){
				e1.printStackTrace();
			}
		}
		return msg;
		
	}
	public String CreateUser(String id, String pass, String Cardback){
		String msg = null;
		try{
			pw = new PrintWriter(socket.getOutputStream(), true);
			pw.println(Create + ":" + id + ":" +  pass +  ":" + Cardback);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			if((msg = reader.readLine()) != null){
				if(msg.equals("Fail"))
					return msg;
			}
		}catch(IOException e){
			e.printStackTrace();
			try{
				if(pw != null) {pw.close();}
				if(reader != null){reader.close();}
				}catch(IOException e1){
					e1.printStackTrace();
				}
		}
		return msg;
		
	}
	public String UpdateUser(String id, String pass, String Cardback){
		String msg = null;
		
		try{
			pw = new PrintWriter(socket.getOutputStream(), true);
			pw.println(Edit + ":" + id + ":" +  pass +  ":" + Cardback);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			if((msg = reader.readLine()) != null){
				if(msg.equals("Fail"))
					return msg;
			}
		}catch(IOException e){
			e.printStackTrace();
			try{
				if(pw != null) {pw.close();}
				if(reader != null){reader.close();}
				}catch(IOException e1){
					e1.printStackTrace();
				}
		}
		return msg;
	}
	 public DoubtUser setUser(){
		 DoubtUser playerInfo = new DoubtUser();
			try{
			iStream = this.socket.getInputStream();
			oiStream = new ObjectInputStream(socket.getInputStream());
		}catch (IOException ioExcept) {
			ioExcept.printStackTrace();
			System.out.println("User 정보 받기에 실패 하였습니다" + ioExcept);
		}
	    	try {
				playerInfo = (DoubtUser) oiStream.readObject();
				System.out.println("Received user : " + playerInfo);
			
			} catch (ClassNotFoundException classNotExcept){
				classNotExcept.printStackTrace();
				System.out.println("Class Not Found" + classNotExcept);
			} catch (IOException e){
				e.printStackTrace();
			}
	    	return playerInfo;
	    }
	public void setSocket(Socket socket){
		this.socket = socket;
// 		this.pw = pw;
	}

}
