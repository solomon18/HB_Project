package Doubt_Server;

import java.net.*;
import java.io.*;
import java.util.*;

import Shared.DoubtUser;


public class DoubtMain implements PacketNumber{
//	BroadcastManager		broadcast = new BroadcastManager();					// Ŭ���̾�Ʈ���� ������ִ� ��ü (Vector ���)
//	Calculation				calculation = new Calculation();
	DoubtUser		players[]	= new DoubtUser[MAX_USER];
	ServerSocket	server;
	
	private InetSocketAddress inboundAddr;
	
	public DoubtMain() {
		for(int i = 0; i < players.length; i++){
			players[i] = new DoubtUser();
		}
	}
	
	public void StartServer(){
		try {
			this.inboundAddr = new InetSocketAddress(6000);
			server = new ServerSocket(6000, 4);
			server.isBound();
			System.out.println("���� ������ �����Ǿ����ϴ�.");
			calculation.GameInit();
			while(true){
				// Ŭ���̾�Ʈ�� ����� �����带 ��´�.
				Socket socket = server.accept();
				
				// �����带 ����� �����Ų��.
				DoubtThread dt = new DoubtThread(socket);
				dt.start();
				dt.setCard(calculation.Card);

				
				broadcast.add(dt);
				System.out.println("���� ������ �� :" + broadcast.size());
//				if(server.isClosed()){
//					System.out.println("������ �����?");
//					broadcast.remove(dt);
//				}
//				dt.run();
				
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
