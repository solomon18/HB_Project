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

	public DoubtThread getDT(int i) {							// i�� �����带 ��ȯ.
		return (DoubtThread)elementAt(i);
	}

	synchronized boolean isFull(){								// ������ �� á���� Ȯ��
		if (size() >= 5){
			return true;
		}
		return false;
	}
	
	synchronized boolean isReady(){								// �����غ񿩺�Ȯ��
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
	
	void sendToOthers(DoubtThread dt, String msg){				// �ڱ⸦ ������ �������� ������ �޽���
		for (int i=0; i<size(); i++){
			if (getDT(i) != dt){
				sendTo(i, msg);
			}
		}
	}
	void sendToOthers(DoubtThread dt, DoubtUser doubtuser){				// �ڱ⸦ ������ �������� ������ ��ü
		for (int i=0; i<size(); i++){
			if (getDT(i) != dt){
				sendToObject(doubtuser, i);
			}
		}
	}
	String getNames(){											// ���� ���ӵ� �������� �̸��� ������.
		StringBuffer sb = new StringBuffer("[PLAYERS]");
		for (int i=0; i<size(); i++){
			sb.append(getDT(i).getUserName(i) + "\t");
		}
		return sb.toString();
	}
	void sendToObject(DoubtUser doubtuser, DoubtThread doubtThread){				// �÷��̾� ��ü�� Ŭ���̾�Ʈ���� ����
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
