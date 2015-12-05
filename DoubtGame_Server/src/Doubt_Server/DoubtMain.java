package Doubt_Server;

import java.net.*;
import java.io.*;
import java.util.*;


public class DoubtMain implements PacketNumber{
//	BroadcastManager		broadcast = new BroadcastManager();					// 클라이언트에게 방송해주는 객체 (Vector 상속)
	Calculation				calculation = new Calculation();
	ServerSocket	server;
	private InetSocketAddress inboundAddr;
	
	public DoubtMain() {}
	
	public void StartServer(){
		try {
			this.inboundAddr = new InetSocketAddress(6000);
			server = new ServerSocket(6000, 4);
			server.isBound();
			System.out.println("서버 소켓이 생성되었습니다.");
			
			while(true){
				// 클라이언트와 연결된 스레드를 얻는다.
				Socket socket = server.accept();
				
				// 스레드를 만들고 실행시킨다.
				DoubtThread dt = new DoubtThread(socket);
				dt.start();

				
				broadcast.add(dt);
				System.out.println("현재 접속자 수 :" + broadcast.size());
//				if(server.isClosed()){
//					System.out.println("접속이 끊겼넹?");
//					broadcast.remove(dt);
//				}
				System.out.println("냥냥?");
//				dt.run();
				calculation.GameInit();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	public static void main(String[] args) {
		DoubtMain dmain = new DoubtMain();
		dmain.StartServer();
	}
}
