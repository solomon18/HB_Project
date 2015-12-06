package clientpkg;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import Shared.DoubtUser;

public class UIMgr extends JFrame implements PacketNumber{
	private ArrayList<JButton> currPlayersDeck = new ArrayList<JButton> ();
	private ArrayList<JLabel> player1 = new ArrayList<JLabel> ();
	private ArrayList<JLabel> player2 = new ArrayList<JLabel> ();
	private ArrayList<JLabel> player3 = new ArrayList<JLabel> ();
	private ArrayList<JButton> buttons = new ArrayList<JButton> ();
			JButton		rdyBtn, nextBtn, prevBtn, doubtBtn;
			static int buttonCnt = 0;
			ComponentFactory	makeComponents;
			LabelFactory	makeLabels;
			DoubtUser		playerInfo;
			int[]			card;
			PacketNumber	packet;
			String 			name;
	
	public UIMgr(DoubtUser user) {
		System.out.println("I'm in UIMgr");
		getContentPane().setBackground(new Color(0, 51, 0));
		setBackground(new Color(0, 51, 0));
		getContentPane().setLayout(null);
		
				
		try {
			this.playerInfo = (DoubtUser)user.clone();
			card = playerInfo.getCard();
			for(int i = 0; i < card.length; i++)
				System.out.println(card[i]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
			
		if (card == null) {
			currPlayersDeck = paintDeck(currPlayersDeck, "NULL");	
		} else if (card != null){	
			currPlayersDeck = paintDeck(currPlayersDeck, "currentPlayer", buttonCnt);		
		}
		player1 = paintothDeck(player1, "othPlayer1");
		player2 = paintothDeck(player2, "othPlayer2");
		player3 = paintothDeck(player3, "othPlayer3");
		
		initButton();
		buttons = paintBtn(buttons);
		
		rdyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				comm.sendTo(packet.Ready + playerInfo.getId());
                System.out.println("You clicked the button");
            }
        });  
		
		doubtBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				comm.sendTo(packet.Doubt + playerInfo.getId());
                System.out.println("You clicked the button");
            }
        });     
		
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				buttonCnt++;	
				currPlayersDeck = repaintDeck(currPlayersDeck, "currentPlayer", buttonCnt);
				
				System.out.println(buttonCnt);
                if(buttonCnt >= 0 && buttonCnt < 2){
                	nextBtn.setEnabled(true);
                	prevBtn.setEnabled(true);
                }else
                	nextBtn.setEnabled(false);
            }		
        });  
		
		prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				buttonCnt--;
				if(buttonCnt > 0)
                	nextBtn.setEnabled(true);
				else if(buttonCnt <= 0)
                	prevBtn.setEnabled(false);
				currPlayersDeck = paintDeck(currPlayersDeck, "currentPlayer", buttonCnt);
				System.out.println(buttonCnt);

            }
        });     
		
		
		System.out.println("I'm in UIMgr");
		draw();
	}
	
	public void draw() {
		super.setBackground(Color.green);
		
		super.setTitle("Doubt");
		super.setVisible(true);
		super.setSize(800, 600);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public ArrayList<JButton> paintDeck(ArrayList<JButton> thisLabels, String labelType) {
		this.makeComponents = new ComponentFactory(this.playerInfo, labelType);
		thisLabels = makeComponents.getButtons(Integer.parseInt(labelType));
		for(JButton eachLabel : thisLabels) {
			getContentPane().add(eachLabel);
		}
		return thisLabels;
	}
	
	public ArrayList<JButton> paintDeck(ArrayList<JButton> thisLabels, String labelType, int eventCount) {
		this.makeComponents = new ComponentFactory(this.playerInfo, labelType);
		thisLabels = makeComponents.getButtons(eventCount);
		Container ct = this.getContentPane();
		ct.removeAll();
		for(JButton eachLabel : thisLabels) {
			player1 = paintothDeck(player1, "othPlayer1");
			player2 = paintothDeck(player2, "othPlayer2");
			player3 = paintothDeck(player3, "othPlayer3");
			buttons = paintBtn(buttons);
			
			ct.repaint();
			getContentPane().add(eachLabel);
		}
		return thisLabels;
	}
	public ArrayList<JButton> repaintDeck(ArrayList<JButton> thisLabels, String labelType, int eventCount) {
		this.makeComponents = new ComponentFactory(this.playerInfo, labelType);
		thisLabels = makeComponents.getButtons(eventCount);
		Container ct = this.getContentPane();
		ct.removeAll();
		for(JButton eachLabel : thisLabels) {
			player1 = paintothDeck(player1, "othPlayer1");
			player2 = paintothDeck(player2, "othPlayer2");
			player3 = paintothDeck(player3, "othPlayer3");
			buttons = paintBtn(buttons);
			
			ct.repaint();
			getContentPane().add(eachLabel);
		}
		return thisLabels;
	}
	public ArrayList<JLabel> paintothDeck(ArrayList<JLabel> thisLabels, String labelType) {
		this.makeLabels = new LabelFactory(this.playerInfo, labelType);
		thisLabels = makeLabels.getLabels();
		for(JLabel eachLabel : thisLabels) {
			getContentPane().add(eachLabel);
		}
		return thisLabels;
	}
	
	public ArrayList<JLabel> paintothDeck(ArrayList<JLabel> thisLabels, String labelType, int eventCount) {
		this.makeLabels = new LabelFactory(this.playerInfo, labelType);
		thisLabels = makeLabels.getLabels(eventCount);
		Container ct = this.getContentPane();
		ct.removeAll();
		for(JLabel eachLabel : thisLabels) {
			player1 = paintothDeck(player1, "othPlayer1");
			player2 = paintothDeck(player2, "othPlayer2");
			player3 = paintothDeck(player3, "othPlayer3");
			buttons = paintBtn(buttons);
			
			ct.repaint();
			getContentPane().add(eachLabel);
		}
		return thisLabels;
	}
	public ArrayList<JLabel> repaintothDeck(ArrayList<JLabel> thisLabels, String labelType, int eventCount) {
		this.makeLabels = new LabelFactory(this.playerInfo, labelType);
		thisLabels = makeLabels.getLabels(eventCount);
		Container ct = this.getContentPane();
		ct.removeAll();
		for(JLabel eachLabel : thisLabels) {
			player1 = paintothDeck(player1, "othPlayer1");
			player2 = paintothDeck(player2, "othPlayer2");
			player3 = paintothDeck(player3, "othPlayer3");
			buttons = paintBtn(buttons);
			
			ct.repaint();
			getContentPane().add(eachLabel);
		}
		return thisLabels;
	}
	public ArrayList<JButton> paintBtn(ArrayList<JButton> thisButtons) {
		thisButtons = getButtons();
		for(JButton eachBtn : thisButtons) {
			getContentPane().add(eachBtn);
		}
		return thisButtons;
	}
	
	private void initButton() {
		rdyBtn = createJButton(445, "Ready");
		buttons.add(rdyBtn);
		doubtBtn = createJButton(412, "Doubt");
		buttons.add(doubtBtn);
		prevBtn = createJButton(511, "Prev");
		buttons.add(prevBtn);
		nextBtn = createJButton(478, "Next");
		buttons.add(nextBtn);
	}
	
	public ArrayList<JButton> getButtons() {
		return this.buttons;
	}
	
	public JButton createJButton(int yPos, String btnName) {
		JButton btn = new JButton(btnName);
		btn.setActionCommand(btnName);
		btn.setBounds(680, yPos, 97, 23);
		return btn;
	}
	
	public void refresh() {
		
	}
	
}
