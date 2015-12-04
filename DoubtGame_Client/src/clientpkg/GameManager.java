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
    					// �ٸ� ���������� �޾Ƽ� ���̺� �Ѹ���
    					
    				}else if(msg.startsWith(packet.Start)){
    					// �ٸ� �÷��̾ ������ �ϸ� �޾Ƽ� ���̺� �Ѹ���
    					JOptionPane.showMessageDialog(null,msg.substring(4));
    				}else if(msg.startsWith(packet.Doubt)){
    					// �� ��Ŷ ������ �޽��� �ڽ��� ���� string �Ѹ���
    					JOptionPane.showMessageDialog(null,msg.substring(4));
    				}else if(msg.startsWith(packet.FailDoubt)){
    					// �� ��Ŷ�� ������ �� ���� �ִ� ī�� �缳��
    					setUser();
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
			System.out.println("User ���� �ޱ⿡ ���� �Ͽ����ϴ�" + ioExcept);
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
            System.out.println("ȣ��Ʈ������ �޾ƿ� �� �����ϴ�. " + hostName);
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

