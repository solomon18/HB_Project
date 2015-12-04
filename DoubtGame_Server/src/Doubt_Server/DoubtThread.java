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
	int				NowPlayer;								// 현재 차례가 누구인지 저장
	private int		userCount = 0;
	String			userName = null;						// 이름
	Socket			socket;									// 서버소켓
	boolean			ready = false;							// 준비여부
	BufferedReader	reader;									// 받기
	PrintWriter		writer;									// 보내기
//	BroadcastManager broadcast = new BroadcastManager();	// 클라이언트에게 방송해주는 객체
	Calculation		calculation = new Calculation();			//
//	DoubtUser		user[] = new DoubtUser[MAX_USER];		// 유저 테이블 객체 생성
//	DoubtDao 		dao	= new DoubtDao();
	PacketNumber	packet;
	
	public DoubtThread(Socket socket) {
		this.socket = socket;
		System.out.println("소켓 생생됨  " + socket);
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
	public void run(){														// 실제 패킷을 받아서 내용을 확인하는 부분
		
		try{
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);

			String msg;
			while ((msg = reader.readLine()) != null){
				
				if (msg.startsWith(packet.Connect)){
					this.Connect(msg, reader);
				}else if (msg.startsWith(packet.Ready)){					// 클라이언트에서 레디한 경우
					this.Ready(msg);
				}else if (msg.startsWith(packet.NoReady)){					// 클라이언트에서 레디를 다시 해제했을 경우
					this.NoReady(msg);
				}else if (msg.startsWith(packet.MSG)){						// 클라이언트에서 메세지를 받았을 때
					broadcast.sendToAll(packet.MSG + msg.substring(4));
				}else if (msg.startsWith(packet.Start)){					// 첫번째 시작할 플레이어가 보냈을 
					calculation.Start(msg);
				}else if (msg.startsWith(packet.Drop)){						// 플레이어가 카드를 낸 경우
					calculation.Drop(msg);
				}else if (msg.startsWith(packet.Doubt)){					// 플레이어가 다우트를 선언한 경우
					calculation.Doubt(msg);
				}else if (msg.startsWith(packet.Create)){
					this.CreateUser(msg);
				}else if (msg.startsWith(packet.Edit)){
					this.EditUser(msg);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("에러났다!");
		}finally{
			this.disConnect(reader);
		}
	}

	public DoubtUser getUser(int i){
		return dao.getUser(user[i].getId());
	}
	
	private void Connect(String msg, BufferedReader reader){				// 접속한 메소드
			if (userCount >= 5){
				this.disConnect(reader);
			}else{	// 제대로 접속이 된경우
				
				userName = msg.substring(4);
				user[userCount] = this.Login(msg, userCount);
				user[userCount].setMyTurnNum(userCount);
				
				broadcast.sendToOthers(this, packet.Enter + userName);
//				broadcast.sendToAll(packet.Connect + broadcast.getNames());
				broadcast.sendTo(userCount, packet.Connect + user[userCount].getId());
				calculation.GameInit();					// test용
				calculation.DivideCard(userCount);		// test용
				
				
			}	
	}
	private void Ready(String msg){
		System.out.println("ready 받았다!");
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
		
		System.out.println("현재 카운트 : " + userCount);
		broadcast.sendToAll(packet.MSG + userName + "님 준비완료! <Server>");
		if (userCount == 4){
			broadcast.sendToAll("게임을 시작합니다.");
//			calculation.GameInit();											// 게임초기화
			
			NowPlayer = 0;													// 게임시작은 0번부터
//			calculation.DivideCard();
			broadcast.sendToAll(packet.MSG + user[NowPlayer].getId() + "님 차례입니다. <Server>");
			broadcast.sendTo(NowPlayer, packet.Start);
		}
	}
	private void NoReady(String msg) {										// 레디를 해제한 경우
		userName = msg.substring(4);
		for(int i =0; i < packet.MAX_USER; i++){
			if(user[i] != null){
				if(user[i].getId() == userName){
					user[i].setReady(false);
				}
			}
		}
		broadcast.sendToAll(packet.MSG + userName + "님이 레디를 해제했습니다. <Server>");		
	}
	private DoubtUser Login(String msg, int i) {
		userName = msg.substring(4);
		String getDbUser = null;
		DoubtUser user = dao.getUser(userName);
		
		getDbUser = user.getId();
		if(getDbUser != null){
			return user;
		}else {
			broadcast.sendTo(i, packet.Login + "ID가 없습니다.");
			return null;
		}
	}
	private void CreateUser(String msg){
		String[] createuser = msg.split("|");
		int cnt = -1;
		cnt = dao.createUser(createuser[1], createuser[2], createuser[3]);
		if(cnt != -1)
			broadcast.sendTo(userCount, packet.Create + "생성에 성공하셨습니다.");
		else
			broadcast.sendTo(userCount, packet.Create + "생성에 실패하였습니다.");
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
			System.out.println("소켓이니? " + socket);
			broadcast.remove(this);
			
			System.out.println(userName + "님이 접속을 끊었습니다.");
			System.out.println("접속자 수: " + broadcast.size());
			broadcast.sendToAll(packet.DisConnect + userName);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
