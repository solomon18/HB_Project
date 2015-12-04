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
	String tempid = "";
	GameManager() {}
	public GameManager(String temp) {
		new Thread(this).start();
		this.tempid = temp;
	}
	
    public void run() {
    	connect();
    	comm.sendTo(packet.Connect + tempid);
    	String msg;
    	try
		{
    		while(true){
    			if ((msg = reader.readLine())!= null){
    				System.out.println("yes!!");
    				if (msg.startsWith(packet.Connect)) {
    					setUser();
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
    					setUser();
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
    
    public void sendTo(String msg) {
		try{
			pw = new PrintWriter(this.socket.getOutputStream(), true);
			pw.println(msg);
			System.out.println(msg);
			System.out.println("Sent successful");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
    
    public void setUser(){
    	try {
			iStream = this.socket.getInputStream();
			oiStream = new ObjectInputStream(socket.getInputStream());
			this.playerInfo = (DoubtUser) oiStream.readObject();
			System.out.println("Received user : " + playerInfo);
		} catch (IOException ioExcept) {
			ioExcept.printStackTrace();
			System.out.println("User 정보 받기에 실패 하였습니다" + ioExcept);
		} catch (ClassNotFoundException classNotExcept){
			classNotExcept.printStackTrace();
			System.out.println("Class Not Found" + classNotExcept);
		}
    }
    
    public boolean connect() {
    	try
        {
    		System.out.println("Connecting..");
    		this.socket = new Socket(hostName, portNumber);
	 		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	 		writer = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (UnknownHostException unknownHostExcept)
        {
            System.out.println("호스트정보를 받아올 수 없습니다. " + hostName);
            System.out.println(unknownHostExcept);
            System.exit(1);
            return false;
        }
    	catch (StreamCorruptedException streamExcept)
    	{
    		System.out.println("Stream Fail ");
            System.out.println(streamExcept);
            System.exit(1);
            return false;
    	}
        catch (IOException ioExcept)
        {
            System.out.println("Connection I/O Error from: " +
            		hostName + ":" + portNumber);
            System.out.println(ioExcept);
            ioExcept.printStackTrace();
            System.exit(1);
            return false;
        }finally{
        	try{
        		if(oiStream != null){oiStream.close();}
        		if(iStream != null){iStream.close();}
        	} catch(IOException e) {
        		e.printStackTrace();
        	}
        }
    	comm.setSocket(socket);
        return true;
    }

}

