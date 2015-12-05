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

public class GameManager implements PacketNumber, Runnable {
	private DoubtUser 		playerInfo;
	private UIMgr 			uiMgr;
			PrintWriter 	pw;
	private BufferedReader	br;
	private String 			msg;
			Socket			socket;
	BufferedReader reader;								// 입력스트림
	PrintWriter writer;									// 출력스트림
	PacketNumber packet;
	InputStream iStream;
	ObjectInputStream oiStream ;
	Communicate comm;
	String tempid = "";
	//GameManager() {}
	public GameManager(String temp, Socket socket) {
//		this.socket = socket;
		comm = new Communicate(socket);
		
		this.tempid = temp;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(this).start();
		
	}
	
    public void run() {
//    	connect();
    	comm.sendTo(Connect + tempid);
    	String msg;
    	try
		{
    		while(true){
    			if ((msg = reader.readLine())!= null){
    				System.out.println("yes!!");
    				if (msg.startsWith(packet.Connect)) {
    					playerInfo = comm.setUser();
    					this.uiMgr = new UIMgr(this.playerInfo);
    				}else if(msg.startsWith(packet.MSG)){
    					JOptionPane.showMessageDialog(null,msg.substring(4));
    				}else if(msg.startsWith(packet.Enter)){
    					// 다른 유저네임을 받아서 레이블에 뿌리기
    					
    				}else if(msg.startsWith(packet.Start)){
    					// 다른 플레이어가 시작을 하면 받아서 레이블에 뿌리기
    					JOptionPane.showMessageDialog(null,msg.substring(4));
    				}else if(msg.startsWith(packet.Doubt)){
    					// 이 패킷 받으면 메시지 박스로 받은 string 뿌리기
    					JOptionPane.showMessageDialog(null,msg.substring(4));
    				}else if(msg.startsWith(packet.FailDoubt)){
    					// 이 패킷을 받으면 내 덱에 있는 카드 재설정
    					comm.setUser();
    				}else if(msg.startsWith(packet.Drop)){
    					// 해당 플레이어의 카드 카운트 재설정
    					
    				}else if(msg.startsWith(packet.NotYourTurn)){
    					// 메시지 박스로 String 뿌리기
    					JOptionPane.showMessageDialog(null,msg.substring(4));
    				}else if(msg.startsWith(packet.CardNum)){
    					// 현재 차례의 카드정보 업데이트
    					
    				}else if(msg.startsWith(packet.EndGame)){
    					// 메시지 박스 뿌리기
    					JOptionPane.showMessageDialog(null,msg.substring(4));
    				}else if(msg.startsWith(packet.Reset)){
    					String temp[] = msg.split("|");
    					temp[0] = temp[0].substring(4);
    					// 아이디는 temp[0] 카운트는 temp[1] 레이블에 뿌리기
    				}
    			}else{
//    				System.out.println("NO!!!");
    			}
    		}
		}catch(Exception e){
			e.printStackTrace();
		}
    	
    }
    
  
    
   
}

