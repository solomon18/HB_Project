package Doubt_Server;

import java.net.*;
import java.io.*;
import java.util.*;


public class DoubtMain implements PacketNumber{
//	BroadcastManager		broadcast = new BroadcastManager();					// Ŭ���̾�Ʈ���� ������ִ� ��ü (Vector ���)
	Calculation				calculation = new Calculation();
	ServerSocket	server;
	private InetSocketAddress inboundAddr;
	
	public DoubtMain() {}
	
	public void StartServer(){
		try {
			this.inboundAddr = new InetSocketAddress(6000);
			server = new ServerSocket(6000, 4);
			server.isBound();
			System.out.println("���� ������ �����Ǿ����ϴ�.");
			
			while(true){
				// Ŭ���̾�Ʈ�� ����� �����带 ��´�.
				Socket socket = server.accept();
				
				// �����带 ����� �����Ų��.
				DoubtThread dt = new DoubtThread(socket);
				dt.start();

				
				broadcast.add(dt);
				System.out.println("���� ������ �� :" + broadcast.size());
//				if(server.isClosed()){
//					System.out.println("������ �����?");
//					broadcast.remove(dt);
//				}
				System.out.println("�ɳ�?");
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
