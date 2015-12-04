package Doubt_Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.Vector;

import Shared.DoubtUser;

public class BroadcastManager  extends Vector{
	OutputStream oStream ;
	ObjectOutputStream ooStream;
	public BroadcastManager() {}

	public void sendToAll(String msg) {
		for (int i=0; i<size(); i++){
			sendTo(i, msg);
		}

	}
	void sendTo(int i, String msg) {
		try{
			PrintWriter pw = new PrintWriter(getSocket(i).getOutputStream(), true);
			pw.println(msg);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public Socket getSocket(int i) {
		return getDT(i).getSocket();
	}

	public DoubtThread getDT(int i) {							// i의 스레드를 반환.
		return (DoubtThread)elementAt(i);
	}

	synchronized boolean isFull(){								// 서버가 다 찼는지 확인
		if (size() >= 5){
			return true;
		}
		return false;
	}
	
	synchronized boolean isReady(){								// 전부준비여부확인
		int count = 0;
		for (int i=0; i<size(); i++){
			if (getDT(i).isReady()){
				count++;
			}
		}
		if (count == 4){
			return true;
		}
		return false;
	}
	
	void sendToOthers(DoubtThread dt, String msg){				// 자기를 제외한 유저에게 보내는 메시지
		for (int i=0; i<size(); i++){
			if (getDT(i) != dt){
				sendTo(i, msg);
			}
		}
	}
	void sendToOthers(DoubtThread dt, DoubtUser doubtuser){				// 자기를 제외한 유저에게 보내는 객체
		for (int i=0; i<size(); i++){
			if (getDT(i) != dt){
				sendToObject(doubtuser, i);
			}
		}
	}
	String getNames(){											// 현재 접속된 스레드의 이름을 가져옴.
		StringBuffer sb = new StringBuffer("[PLAYERS]");
		for (int i=0; i<size(); i++){
			sb.append(getDT(i).getUserName(i) + "\t");
		}
		return sb.toString();
	}
	void sendToObject(DoubtUser doubtuser, DoubtThread doubtThread){				// 플레이어 객체를 클라이언트에게 전송
		try {
//			oStream = doubtThread.getSocket().getOutputStream();
//			ObjectOutputStream oos = new ObjectOutputStream(oStream);
			ooStream = new ObjectOutputStream(doubtThread.getSocket().getOutputStream());
			ooStream.writeObject(doubtuser);
//			ooStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				ooStream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
//		} finally{
//			try{
//				if(ooStream != null){ooStream.close();}
//				if(oStream != null){oStream.close();}
//			}catch(IOException e){
//				e.printStackTrace();
//			}
		}
		
	}
	void sendToObject(DoubtUser doubtuser, int i){
		OutputStream oStream = null;
		ObjectOutputStream oos = null;
		try {
			oStream = getSocket(i).getOutputStream();
			oos = new ObjectOutputStream(oStream);
			oos.writeObject(doubtuser);
			System.out.println(doubtuser);
		} catch (IOException e) {
			e.printStackTrace();
			try {
				oos.close();
				oStream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	public void sendToObjectAll(DoubtUser doubtuser) {
		for (int i=0; i<size(); i++){
			sendToObject(doubtuser, i);
		}
	}
}
