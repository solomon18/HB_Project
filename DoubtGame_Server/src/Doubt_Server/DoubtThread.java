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
	private int		readyCount = 0;
	String			userName = null;						// 이름
	Socket			socket;									// 서버소켓
	boolean			ready = false;							// 준비여부
	BufferedReader	reader;									// 받기
	PrintWriter		writer;									// 보내기
//	BroadcastManager broadcast = new BroadcastManager();	// 클라이언트에게 방송해주는 객체
//	Calculation		calculation = new Calculation();			//
	DoubtUser		player = new DoubtUser();		// 유저 테이블 객체 생성
//	DoubtDao 		dao	= new DoubtDao();

	
	public DoubtThread(Socket socket) {
		this.socket = socket;
		userCount = broadcast.size();
		
		System.out.println("소켓 생생됨  " + socket);

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
	public void run(){														// 실제 패킷을 받아서 내용을 확인하는 부분
		
		try{
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			String msg;
			while ((msg = reader.readLine()) != null){
				
				if (msg.startsWith(Connect)){
					this.Connect(msg, reader);
				}else if (msg.startsWith(Ready)){					// 클라이언트에서 레디한 경우
					this.Ready(msg);
				}else if (msg.startsWith(NoReady)){					// 클라이언트에서 레디를 다시 해제했을 경우
					this.NoReady(msg);
				}else if (msg.startsWith(MSG)){						// 클라이언트에서 메세지를 받았을 때
					broadcast.sendToAll(MSG + msg.substring(4));
				}else if (msg.startsWith(Start)){					// 첫번째 시작할 플레이어가 보냈을 
					calculation.Start(msg);
				}else if (msg.startsWith(Drop)){						// 플레이어가 카드를 낸 경우
					calculation.Drop(msg);
				}else if (msg.startsWith(Doubt)){					// 플레이어가 다우트를 선언한 경우
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
			System.out.println("에러났다!");
		}finally{
			this.disConnect(reader);
		}
	}

	public DoubtUser getUser(int i){
		return dao.getUser(player.getId());
	}
	
	private void Connect(String msg, BufferedReader reader){				// 접속한 메소드
			if (userCount >= 5){
				this.disConnect(reader);
			}else{	// 제대로 접속이 된경우
				
				userName = msg.substring(4);
//				player = this.Login(msg, userCount);
				player.setMyTurnNum(userCount);
				broadcast.sendToOthers(this, Enter + userName);
//				broadcast.sendToAll(packet.Connect + broadcast.getNames());
				broadcast.sendTo(userCount, Connect + player.getId());
//				System.out.println(userCount);
				calculation.DivideCard(userCount);		// test용
				
			}	
	}
	private void Ready(String msg){
		System.out.println("ready 받았다!");
		userName = msg.substring(4);
		System.out.println(userName);
		
		player.setReady(true);
		System.out.println(player.getId() + userName);
		System.out.println(player.getReady());
					readyCount++;
		
//			if(user[i].getReady()==true)
//				userCount++;
		
		
		System.out.println("현재 카운트 : " + readyCount);
		broadcast.sendToAll(MSG + userName + "님 준비완료! <Server>");
		if (readyCount == 4){
			broadcast.sendToAll("게임을 시작합니다.");
//			calculation.GameInit();											// 게임초기화
			
			NowPlayer = 0;													// 게임시작은 0번부터
//			calculation.DivideCard();
			broadcast.sendToAll(MSG + player.getId() + "님 차례입니다. <Server>");
			broadcast.sendTo(NowPlayer, Start);
		}
	}
	private void NoReady(String msg) {										// 레디를 해제한 경우
		userName = msg.substring(4);
		player.setReady(false);
				
		
		broadcast.sendToAll(MSG + userName + "님이 레디를 해제했습니다. <Server>");		
	}
	private DoubtUser Login(String msg, int i) {
		userName = msg.substring(4);
		String getDbUser = null;
		DoubtUser user = dao.getUser(userName);
		
		getDbUser = user.getId();
		if(getDbUser != null){
			return user;
		}else {
			broadcast.sendTo(i, Login + "ID가 없습니다.");
			return null;
		}
	}
	private void CreateUser(String msg){
		String[] createuser; 
		createuser = msg.split(":");
		int cnt = -1;
		cnt = dao.createUser(createuser[1], createuser[2], createuser[3]);
		if(cnt == -1)
			broadcast.sendTo(userCount, Create + "생성에 성공하셨습니다.");
		else
			broadcast.sendTo(userCount, Create + "생성에 실패하였습니다.");
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
			System.out.println("소켓이니? " + socket);
			broadcast.remove(this);
			
			System.out.println(userName + "님이 접속을 끊었습니다.");
			System.out.println("접속자 수: " + broadcast.size());
			broadcast.sendToAll(DisConnect + userName);
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	public void setCard(int[] card) {
		calculation.Card = card;
	}
	private void Login(String msg){				// 접속한 메소드
		if (userCount >= 5){
			broadcast.sendTo(userCount, Login + "이미 방이 모두 찼습니다.");
			this.disConnect(reader);
		}else{	// 제대로 접속이 된경우
			String idpass[]; 
			idpass = msg.split(":");
			userName = idpass[1];
			player = dao.getUser(userName);
			broadcast.sendTo(userCount, Connect + player.getId() + ":" + player.getPass());
		}	
}
}
