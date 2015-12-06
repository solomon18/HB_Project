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
	BufferedReader reader;								// �Է½�Ʈ��
	PrintWriter writer;									// ��½�Ʈ��
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
    					// �ٸ� ���������� �޾Ƽ� ���̺� �Ѹ���
    					
    				}else if(msg.startsWith(packet.Start)){
    					// �ٸ� �÷��̾ ������ �ϸ� �޾Ƽ� ���̺� �Ѹ���
    					JOptionPane.showMessageDialog(null,msg.substring(4));
    				}else if(msg.startsWith(packet.Doubt)){
    					// �� ��Ŷ ������ �޽��� �ڽ��� ���� string �Ѹ���
    					JOptionPane.showMessageDialog(null,msg.substring(4));
    				}else if(msg.startsWith(packet.FailDoubt)){
    					// �� ��Ŷ�� ������ �� ���� �ִ� ī�� �缳��
    					comm.setUser();
    				}else if(msg.startsWith(packet.Drop)){
    					// �ش� �÷��̾��� ī�� ī��Ʈ �缳��
    					
    				}else if(msg.startsWith(packet.NotYourTurn)){
    					// �޽��� �ڽ��� String �Ѹ���
    					JOptionPane.showMessageDialog(null,msg.substring(4));
    				}else if(msg.startsWith(packet.CardNum)){
    					// ���� ������ ī������ ������Ʈ
    					
    				}else if(msg.startsWith(packet.EndGame)){
    					// �޽��� �ڽ� �Ѹ���
    					JOptionPane.showMessageDialog(null,msg.substring(4));
    				}else if(msg.startsWith(packet.Reset)){
    					String temp[] = msg.split("|");
    					temp[0] = temp[0].substring(4);
    					// ���̵�� temp[0] ī��Ʈ�� temp[1] ���̺� �Ѹ���
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

