package clientpkg;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Shared.DoubtUser;

public class LabelFactory {
	private ArrayList<JLabel> labels;
	private ArrayList<JLabel> labelRow1;
	private ArrayList<JLabel> labelRow2;
	private ArrayList<JLabel> labelRow3;
	private	JLabel 		currPlayerCard0, currPlayerCard1, currPlayerCard2, currPlayerCard3, currPlayerCard4, currPlayerCard5, currPlayerCard6;
	private	JLabel		currPlayerCard7, currPlayerCard8, currPlayerCard9, currPlayerCard10, currPlayerCard11, currPlayerCard12, currPlayerCard13; 
	private	JLabel 		othPlayer1_0, othPlayer1_1, othPlayer1_2, othPlayer1_3, othPlayer1_4, othPlayer1_5;
	private	JLabel 		othPlayer2_0, othPlayer2_1, othPlayer2_2, othPlayer2_3, othPlayer2_4, othPlayer2_5;
	private	JLabel 		othPlayer3_0, othPlayer3_1, othPlayer3_2, othPlayer3_3, othPlayer3_4, othPlayer3_5;
	
	DoubtUser	user;
	int[]		card;
	ImageIcon	cardImgArr[];
	String		PATH = "images\\cards\\";
	final	String		bckCard = "images\\cards\\";
	
	LabelFactory(DoubtUser user, String labelT) {
		labels = new ArrayList<JLabel>();
		labelRow1 = new ArrayList<JLabel>();
		labelRow2 = new ArrayList<JLabel>();
		labelRow3 = new ArrayList<JLabel>();
	    this.card = user.getCard();
	    initLabel(labelT);
	}
	
	public void initLabel(String labelType) {
		if(labelType == "currentPlayer") {
			currPlayerCard0 = createJLabel(175, card[0]);
			labelRow1.add(currPlayerCard0);
			
			currPlayerCard1 = createJLabel(255, card[1]);
			labelRow1.add(currPlayerCard1);
			
			currPlayerCard2 = createJLabel(335, card[2]);
			labelRow1.add(currPlayerCard2);
			
			currPlayerCard3 = createJLabel(415, card[3]);
			labelRow1.add(currPlayerCard3);
			
			currPlayerCard4 = createJLabel(495, card[4]);
			labelRow1.add(currPlayerCard4);
			
			currPlayerCard5 = createJLabel(575, card[5]);
			labelRow1.add(currPlayerCard5);
			
			////////////////////////////////////////////
			currPlayerCard6 = createJLabel(175, card[6]);
			labelRow2.add(currPlayerCard6);
			
			currPlayerCard7 = createJLabel(255, card[7]);
			labelRow2.add(currPlayerCard7);
			
			currPlayerCard8 = createJLabel(335, card[8]);
			labelRow2.add(currPlayerCard8);
			
			currPlayerCard9 = createJLabel(415, card[9]);
			labelRow2.add(currPlayerCard9);
			
			currPlayerCard10 = createJLabel(495, card[10]);
			labelRow2.add(currPlayerCard10);
			
			currPlayerCard11 = createJLabel(575, card[11]);
			labelRow2.add(currPlayerCard11);
			
			////////////////////////////////////////////
			currPlayerCard12 = createJLabel(175, card[12]);
			labelRow3.add(currPlayerCard12);
			
			currPlayerCard13 = createJLabel(255, card[13]);
			labelRow3.add(currPlayerCard13);
			
		} else if (labelType == "NULL") {
			currPlayerCard0 = createJLabel(175, bckCard + "Bck3.png");
			labels.add(currPlayerCard0);
			
			currPlayerCard1 = createJLabel(255, bckCard + "Bck3.png");
			labels.add(currPlayerCard1);
			
			currPlayerCard2 = createJLabel(335, bckCard + "Bck3.png");
			labels.add(currPlayerCard2);
			
			currPlayerCard3 = createJLabel(415, bckCard + "Bck3.png");
			labels.add(currPlayerCard3);
			
			currPlayerCard4 = createJLabel(495, bckCard + "Bck3.png");
			labels.add(currPlayerCard4);
			
			currPlayerCard5 = createJLabel(575, bckCard + "Bck3.png");
			labels.add(currPlayerCard5);
			
			////////////////////////////////////////////
			currPlayerCard6 = createJLabel(175, bckCard + "Bck3.png");
			labels.add(currPlayerCard6);
			
			currPlayerCard7 = createJLabel(255, bckCard + "Bck3.png");
			labels.add(currPlayerCard7);
			
			currPlayerCard8 = createJLabel(335, bckCard + "Bck3.png");
			labels.add(currPlayerCard8);
			
			currPlayerCard9 = createJLabel(415, bckCard + "Bck3.png");
			labels.add(currPlayerCard9);
			
			currPlayerCard10 = createJLabel(495, bckCard + "Bck3.png");
			labels.add(currPlayerCard10);
			
			currPlayerCard11 = createJLabel(575, bckCard + "Bck3.png");
			labels.add(currPlayerCard11);
			
			////////////////////////////////////////////
			currPlayerCard12 = createJLabel(175, bckCard + "Bck3.png");
			labels.add(currPlayerCard12);
			
			currPlayerCard13 = createJLabel(255, bckCard + "Bck3.png");
			labels.add(currPlayerCard13);
			
		} else if (labelType == "othPlayer1") {
			othPlayer1_0 = createJLabel(36, 144, bckCard + "Bck1.png"); 
			labels.add(othPlayer1_0);
			
			othPlayer1_1 = createJLabel(36, 304, bckCard + "Bck1.png");
			labels.add(othPlayer1_1);
			
			othPlayer1_2 = createJLabel(36, 224, bckCard + "Bck1.png");
			labels.add(othPlayer1_2);
			
			othPlayer1_3 = createJLabel(140, 144, bckCard + "Bck1.png");
			labels.add(othPlayer1_3);
			
			othPlayer1_4 = createJLabel(140, 224, bckCard + "Bck1.png");
			labels.add(othPlayer1_4);
			
			othPlayer1_5 = createJLabel(140, 304, bckCard + "Bck1.png");
			labels.add(othPlayer1_5);
			
		} else if (labelType == "othPlayer2") {
			othPlayer2_0 = createJLabel(175, 20, bckCard + "Bck5.png"); 
			labels.add(othPlayer2_0);
			
			othPlayer2_1 = createJLabel(255, 20, bckCard + "Bck5.png");
			labels.add(othPlayer2_1);
			
			othPlayer2_2 = createJLabel(335, 20, bckCard + "Bck5.png");
			labels.add(othPlayer2_2);
			
			othPlayer2_3 = createJLabel(415, 20, bckCard + "Bck5.png");
			labels.add(othPlayer2_3);
			
			othPlayer2_4 = createJLabel(495, 20, bckCard + "Bck5.png");
			labels.add(othPlayer2_4);
			
			othPlayer2_5 = createJLabel(575, 20, bckCard + "Bck5.png");
			labels.add(othPlayer2_5);
			
		} else if (labelType == "othPlayer3") {
			othPlayer3_0 = createJLabel(559, 144, bckCard + "Bck0.png"); 
			labels.add(othPlayer3_0);
			
			othPlayer3_1 = createJLabel(559, 224, bckCard + "Bck0.png");
			labels.add(othPlayer3_1);
			
			othPlayer3_2 = createJLabel(559, 304, bckCard + "Bck0.png");
			labels.add(othPlayer3_2);
			
			othPlayer3_3 = createJLabel(667, 144, bckCard + "Bck0.png");
			labels.add(othPlayer3_3);
			
			othPlayer3_4 = createJLabel(667, 224, bckCard + "Bck0.png");
			labels.add(othPlayer3_4);
			
			othPlayer3_5 = createJLabel(667, 304, bckCard + "Bck0.png");
			labels.add(othPlayer3_5);
		}
	}

	public void userInfoLabel() {
		
	}
	
	public ArrayList<JLabel> getLabels() {
		return this.labels;
	}
	
	public ArrayList<JLabel> getLabels(int count) {
		if(count == 0)  
			return this.labelRow1;
		else if(count == 1)
			return this.labelRow2;
		else if(count == 2)
			return this.labelRow3;
		return this.labels;
	}
	
	public JLabel createJLabel(int x, String backCard) {
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(backCard));
		label.setBounds(x, 440, 67, 99);
		return label;
	}
	
	public JLabel createJLabel(int x, int y, String backCard) {
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(getClass().getClassLoader().getResource(backCard)));
		if (y == 20) 
			label.setBounds(x, y, 67, 99);
		else 
			label.setBounds(x, y, 99, 67);
		
		return label;
	}
	
	public JLabel createJLabel(int x, int cardNum) {
		String temp = "";
		JLabel label = new JLabel("");
		if(cardNum < 10) {
			temp = (PATH + "0" + cardNum + ".png");
			label.setIcon(new ImageIcon(getClass().getClassLoader().getResource(temp)));
		} else {
			temp = (PATH + cardNum + ".png");
			label.setIcon(new ImageIcon(getClass().getClassLoader().getResource(temp)));
		}
		System.out.println(label.getIcon());
		label.setBounds(x, 440, 67, 99);
		return label;
	}
	
}
	
