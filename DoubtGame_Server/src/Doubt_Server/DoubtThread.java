package Doubt_Server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.Socket;

import Shared.DoubtUser;

public class DoubtThread extends Thread implements PacketNumber{
	int				NowPlayer;								// ���� ���ʰ� �������� ����
	private int		userCount = 0;
	private int		readyCount = 0;
	String			userName = null;						// �̸�
	Socket			socket;									// ��������
	boolean			ready = false;							// �غ񿩺�
	BufferedReader	reader;									// �ޱ�
	PrintWriter		writer;									// ������
//	BroadcastManager broadcast = new BroadcastManager();	// Ŭ���̾�Ʈ���� ������ִ� ��ü
//	Calculation		calculation = new Calculation();			//
	DoubtUser		player = new DoubtUser();		// ���� ���̺� ��ü ����
//	DoubtDao 		dao	= new DoubtDao();

	
	public DoubtThread(Socket socket) {
		this.socket = socket;
		userCount = broadcast.size();
		
		System.out.println("���� ������  " + socket);

	}
	
	public Socket getSocket() {
		return socket;
	}
	
	DoubtUser getUserName(){
		return player;
	}

	boolean isReady(){
		return ready;
	}
	public void run(){														// ���� ��Ŷ�� �޾Ƽ� ������ Ȯ���ϴ� �κ�
		
		try{
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			String msg;
			while ((msg = reader.readLine()) != null){
				
				if (msg.startsWith(Connect)){
					this.Connect(msg, reader);
				}else if (msg.startsWith(Ready)){					// Ŭ���̾�Ʈ���� ������ ���
					this.Ready(msg);
				}else if (msg.startsWith(NoReady)){					// Ŭ���̾�Ʈ���� ���� �ٽ� �������� ���
					this.NoReady(msg);
				}else if (msg.startsWith(MSG)){						// Ŭ���̾�Ʈ���� �޼����� �޾��� ��
					broadcast.sendToAll(MSG + msg.substring(4));
				}else if (msg.startsWith(Start)){					// ù��° ������ �÷��̾ ������ 
					calculation.Start(msg);
				}else if (msg.startsWith(Drop)){						// �÷��̾ ī�带 �� ���
					calculation.Drop(msg);
				}else if (msg.startsWith(Doubt)){					// �÷��̾ �ٿ�Ʈ�� ������ ���
					calculation.Doubt(msg);
				}else if (msg.startsWith(Create)){
					this.CreateUser(msg);
				}else if (msg.startsWith(Edit)){
					this.EditUser(msg);
				}else if (msg.startsWith(Login)){
					this.Login(msg);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("��������!");
		}finally{
			this.disConnect(reader);
		}
	}

	public DoubtUser getUser(int i){
		return dao.getUser(player.getId());
	}
	
	private void Connect(String msg, BufferedReader reader){				// ������ �޼ҵ�
			if (userCount >= 5){
				this.disConnect(reader);
			}else{	// ����� ������ �Ȱ��
				
				userName = msg.substring(4);
//				player = this.Login(msg, userCount);
				player.setMyTurnNum(userCount);
				broadcast.sendToOthers(this, Enter + userName);
//				broadcast.sendToAll(packet.Connect + broadcast.getNames());
				broadcast.sendTo(userCount, Connect + player.getId());
//				System.out.println(userCount);
				calculation.DivideCard(userCount);		// test��
				
			}	
	}
	private void Ready(String msg){
		System.out.println("ready �޾Ҵ�!");
		userName = msg.substring(4);
		System.out.println(userName);
		
		player.setReady(true);
		System.out.println(player.getId() + userName);
		System.out.println(player.getReady());
					readyCount++;
		
//			if(user[i].getReady()==true)
//				userCount++;
		
		
		System.out.println("���� ī��Ʈ : " + readyCount);
		broadcast.sendToAll(MSG + userName + "�� �غ�Ϸ�! <Server>");
		if (readyCount == 4){
			broadcast.sendToAll("������ �����մϴ�.");
//			calculation.GameInit();											// �����ʱ�ȭ
			
			NowPlayer = 0;													// ���ӽ����� 0������
//			calculation.DivideCard();
			broadcast.sendToAll(MSG + player.getId() + "�� �����Դϴ�. <Server>");
			broadcast.sendTo(NowPlayer, Start);
		}
	}
	private void NoReady(String msg) {										// ���� ������ ���
		userName = msg.substring(4);
		player.setReady(false);
				
		
		broadcast.sendToAll(MSG + userName + "���� ���� �����߽��ϴ�. <Server>");		
	}
	private DoubtUser Login(String msg, int i) {
		userName = msg.substring(4);
		String getDbUser = null;
		DoubtUser user = dao.getUser(userName);
		
		getDbUser = user.getId();
		if(getDbUser != null){
			return user;
		}else {
			broadcast.sendTo(i, Login + "ID�� �����ϴ�.");
			return null;
		}
	}
	private void CreateUser(String msg){
		String[] createuser; 
		createuser = msg.split(":");
		int cnt = -1;
		cnt = dao.createUser(createuser[1], createuser[2], createuser[3]);
		if(cnt == -1)
			broadcast.sendTo(userCount, Create + "������ �����ϼ̽��ϴ�.");
		else
			broadcast.sendTo(userCount, Create + "������ �����Ͽ����ϴ�.");
	}
	private void EditUser(String msg){
		String[] edituser; 
		edituser = msg.split(":");
		String result = null;
		DoubtUser user = new DoubtUser();
		user = dao.getUser(edituser[1]);
		if(edituser[1].equals(user.getId()) && edituser[2].equals(user.getPass())){
			user.setCardback(edituser[3]);
			result = "Sucess";
			broadcast.sendToUser(this, result);
		}else{
			result = "Fail";
			broadcast.sendToUser(this, result);
		}
	}
	private void disConnect(BufferedReader reader){
		try{
			reader.close();
			writer.close();
			socket.close();
			reader = null;
			writer = null;
			socket = null;
			System.out.println("�����̴�? " + socket);
			broadcast.remove(this);
			
			System.out.println(userName + "���� ������ �������ϴ�.");
			System.out.println("������ ��: " + broadcast.size());
			broadcast.sendToAll(DisConnect + userName);
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	public void setCard(int[] card) {
		calculation.Card = card;
	}
	private void Login(String msg){				// ������ �޼ҵ�
		if (userCount >= 5){
			broadcast.sendTo(userCount, Login + "�̹� ���� ��� á���ϴ�.");
			this.disConnect(reader);
		}else{	// ����� ������ �Ȱ��
			String idpass[]; 
			idpass = msg.split(":");
			userName = idpass[1];
			player = dao.getUser(userName);
			broadcast.sendTo(userCount, Connect + player.getId() + ":" + player.getPass());
		}	
}
}
