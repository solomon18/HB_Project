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
	String			userName = null;						// �̸�
	Socket			socket;									// ��������
	boolean			ready = false;							// �غ񿩺�
	BufferedReader	reader;									// �ޱ�
	PrintWriter		writer;									// ������
//	BroadcastManager broadcast = new BroadcastManager();	// Ŭ���̾�Ʈ���� ������ִ� ��ü
	Calculation		calculation = new Calculation();			//
//	DoubtUser		user[] = new DoubtUser[MAX_USER];		// ���� ���̺� ��ü ����
//	DoubtDao 		dao	= new DoubtDao();
	PacketNumber	packet;
	
	public DoubtThread(Socket socket) {
		this.socket = socket;
		System.out.println("���� ������  " + socket);
		user[0] = new DoubtUser();
		user[1] = new DoubtUser();
		user[2] = new DoubtUser();
		user[3] = new DoubtUser();
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	DoubtUser getUserName(int i){
		return user[i];
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
				
				if (msg.startsWith(packet.Connect)){
					this.Connect(msg, reader);
				}else if (msg.startsWith(packet.Ready)){					// Ŭ���̾�Ʈ���� ������ ���
					this.Ready(msg);
				}else if (msg.startsWith(packet.NoReady)){					// Ŭ���̾�Ʈ���� ���� �ٽ� �������� ���
					this.NoReady(msg);
				}else if (msg.startsWith(packet.MSG)){						// Ŭ���̾�Ʈ���� �޼����� �޾��� ��
					broadcast.sendToAll(packet.MSG + msg.substring(4));
				}else if (msg.startsWith(packet.Start)){					// ù��° ������ �÷��̾ ������ 
					calculation.Start(msg);
				}else if (msg.startsWith(packet.Drop)){						// �÷��̾ ī�带 �� ���
					calculation.Drop(msg);
				}else if (msg.startsWith(packet.Doubt)){					// �÷��̾ �ٿ�Ʈ�� ������ ���
					calculation.Doubt(msg);
				}else if (msg.startsWith(packet.Create)){
					this.CreateUser(msg);
				}else if (msg.startsWith(packet.Edit)){
					this.EditUser(msg);
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
		return dao.getUser(user[i].getId());
	}
	
	private void Connect(String msg, BufferedReader reader){				// ������ �޼ҵ�
			if (userCount >= 5){
				this.disConnect(reader);
			}else{	// ����� ������ �Ȱ��
				
				userName = msg.substring(4);
				user[userCount] = this.Login(msg, userCount);
				user[userCount].setMyTurnNum(userCount);
				
				broadcast.sendToOthers(this, packet.Enter + userName);
//				broadcast.sendToAll(packet.Connect + broadcast.getNames());
				broadcast.sendTo(userCount, packet.Connect + user[userCount].getId());
				calculation.GameInit();					// test��
				calculation.DivideCard(userCount);		// test��
				
				
			}	
	}
	private void Ready(String msg){
		System.out.println("ready �޾Ҵ�!");
		userName = msg.substring(4);
		System.out.println(userName);
		for(int i = 0; i < packet.MAX_USER; i++){
			if(true){
				System.out.println(user[i]);
				if(user[i].getId().equals(userName))
					user[i].setReady(true);
					System.out.println(user[i].getId() + userName);
					System.out.println(user[i].getReady());
					userCount++;
			}
//			if(user[i].getReady()==true)
//				userCount++;
		}
		
		System.out.println("���� ī��Ʈ : " + userCount);
		broadcast.sendToAll(packet.MSG + userName + "�� �غ�Ϸ�! <Server>");
		if (userCount == 4){
			broadcast.sendToAll("������ �����մϴ�.");
//			calculation.GameInit();											// �����ʱ�ȭ
			
			NowPlayer = 0;													// ���ӽ����� 0������
//			calculation.DivideCard();
			broadcast.sendToAll(packet.MSG + user[NowPlayer].getId() + "�� �����Դϴ�. <Server>");
			broadcast.sendTo(NowPlayer, packet.Start);
		}
	}
	private void NoReady(String msg) {										// ���� ������ ���
		userName = msg.substring(4);
		for(int i =0; i < packet.MAX_USER; i++){
			if(user[i] != null){
				if(user[i].getId() == userName){
					user[i].setReady(false);
				}
			}
		}
		broadcast.sendToAll(packet.MSG + userName + "���� ���� �����߽��ϴ�. <Server>");		
	}
	private DoubtUser Login(String msg, int i) {
		userName = msg.substring(4);
		String getDbUser = null;
		DoubtUser user = dao.getUser(userName);
		
		getDbUser = user.getId();
		if(getDbUser != null){
			return user;
		}else {
			broadcast.sendTo(i, packet.Login + "ID�� �����ϴ�.");
			return null;
		}
	}
	private void CreateUser(String msg){
		String[] createuser = msg.split("|");
		int cnt = -1;
		cnt = dao.createUser(createuser[1], createuser[2], createuser[3]);
		if(cnt != -1)
			broadcast.sendTo(userCount, packet.Create + "������ �����ϼ̽��ϴ�.");
		else
			broadcast.sendTo(userCount, packet.Create + "������ �����Ͽ����ϴ�.");
	}
	private void EditUser(String msg){
		String[] createuser = msg.split("|");
		DoubtUser user = new DoubtUser();
		user = dao.getUser(createuser[0]);
		user.setCardback(createuser[2]);
		if(user.getId() != null){
			dao.UpdateUser(user);
		}else{
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
			broadcast.sendToAll(packet.DisConnect + userName);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
