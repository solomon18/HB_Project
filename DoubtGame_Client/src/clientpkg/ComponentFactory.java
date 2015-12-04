package clientpkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import Shared.DoubtUser;

public class ComponentFactory {
	private ArrayList<JLabel> labels;
	private ArrayList<JLabel> decks;
	private ArrayList<JButton> ButtonRow1;
	private ArrayList<JButton> ButtonRow2;
	private ArrayList<JButton> ButtonRow3;
	private JButton currPlayerCard0, currPlayerCard1, currPlayerCard2, currPlayerCard3, 
					currPlayerCard4, currPlayerCard5, currPlayerCard6;
	private JButton currPlayerCard7, currPlayerCard8, currPlayerCard9, currPlayerCard10, 
					currPlayerCard11, currPlayerCard12, currPlayerCard13;
	private JLabel othPlayer1_0, othPlayer1_1, othPlayer1_2, othPlayer1_3, othPlayer1_4, othPlayer1_5;
	private JLabel othPlayer2_0, othPlayer2_1, othPlayer2_2, othPlayer2_3, othPlayer2_4, othPlayer2_5;
	private JLabel othPlayer3_0, othPlayer3_1, othPlayer3_2, othPlayer3_3, othPlayer3_4, othPlayer3_5;
	private JLabel deck;

	DoubtUser user;
	int[] card;
	ImageIcon cardImgArr[];
	String PATH = "c:\\images\\cards\\";
	String bckCard = "c:\\images\\cards\\";
	Communicate comm = new Communicate();
	PacketNumber packet;

	ComponentFactory(DoubtUser user, String userT) {
		labels = new ArrayList<JLabel>();
		ButtonRow1 = new ArrayList<JButton>();
		ButtonRow2 = new ArrayList<JButton>();
		ButtonRow3 = new ArrayList<JButton>();
		this.card = user.getCard();
		initComponents(userT);
		currPlayerCard0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("´­·È´Ù!");
				comm.sendTo(packet.Drop + user.getId());
			}
		});
		currPlayerCard1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("´­·È´Ù!");
				comm.sendTo(packet.Drop + user.getId());
			}
		});
		currPlayerCard2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("´­·È´Ù!");
				comm.sendTo(packet.Drop + user.getId());
			}
		});
		currPlayerCard3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("´­·È´Ù!");
				comm.sendTo(packet.Drop + user.getId());
			}
		});
		currPlayerCard4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("´­·È´Ù!");
				comm.sendTo(packet.Drop + user.getId());
			}
		});
		currPlayerCard5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("´­·È´Ù!");
				comm.sendTo(packet.Drop + user.getId());
			}
		});
		currPlayerCard6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("´­·È´Ù!");
				comm.sendTo(packet.Drop + user.getId());
			}
		});
		currPlayerCard7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("´­·È´Ù!");
				comm.sendTo(packet.Drop + user.getId());
			}
		});
		currPlayerCard8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("´­·È´Ù!");
				comm.sendTo(packet.Drop + user.getId());
			}
		});
		currPlayerCard9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("´­·È´Ù!");
				comm.sendTo(packet.Drop + user.getId());
			}
		});
		currPlayerCard10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("´­·È´Ù!");
				comm.sendTo(packet.Drop + user.getId());
			}
		});
		currPlayerCard11.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("´­·È´Ù!");
				comm.sendTo(packet.Drop + user.getId());
			}
		});
		currPlayerCard12.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("´­·È´Ù!");
				comm.sendTo(packet.Drop + user.getId());
			}
		});
		currPlayerCard13.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("´­·È´Ù!");
				comm.sendTo(packet.Drop + user.getId());
			}
		});
	}

	public void initComponents(String userType) {
		if (userType == "currentPlayer") {
			currPlayerCard0 = createButton(175, card[0]);
			ButtonRow1.add(currPlayerCard0);

			currPlayerCard1 = createButton(255, card[1]);
			ButtonRow1.add(currPlayerCard1);

			currPlayerCard2 = createButton(335, card[2]);
			ButtonRow1.add(currPlayerCard2);

			currPlayerCard3 = createButton(415, card[3]);
			ButtonRow1.add(currPlayerCard3);

			currPlayerCard4 = createButton(495, card[4]);
			ButtonRow1.add(currPlayerCard4);

			currPlayerCard5 = createButton(575, card[5]);
			ButtonRow1.add(currPlayerCard5);

			// //////////////////////////////////////////
			currPlayerCard6 = createButton(175, card[6]);
			ButtonRow2.add(currPlayerCard6);

			currPlayerCard7 = createButton(255, card[7]);
			ButtonRow2.add(currPlayerCard7);

			currPlayerCard8 = createButton(335, card[8]);
			ButtonRow2.add(currPlayerCard8);

			currPlayerCard9 = createButton(415, card[9]);
			ButtonRow2.add(currPlayerCard9);

			currPlayerCard10 = createButton(495, card[10]);
			ButtonRow2.add(currPlayerCard10);

			currPlayerCard11 = createButton(575, card[11]);
			ButtonRow2.add(currPlayerCard11);

			// //////////////////////////////////////////
			currPlayerCard12 = createButton(175, card[12]);
			ButtonRow3.add(currPlayerCard12);

			currPlayerCard13 = createButton(255, card[13]);
			ButtonRow3.add(currPlayerCard13);

		} else if (userType == "NULL") {
			currPlayerCard0 = createButton(175, bckCard + "Bck3.png");
			ButtonRow1.add(currPlayerCard0);

			currPlayerCard1 = createButton(255, bckCard + "Bck3.png");
			ButtonRow1.add(currPlayerCard1);

			currPlayerCard2 = createButton(335, bckCard + "Bck3.png");
			ButtonRow1.add(currPlayerCard2);

			currPlayerCard3 = createButton(415, bckCard + "Bck3.png");
			ButtonRow1.add(currPlayerCard3);

			currPlayerCard4 = createButton(495, bckCard + "Bck3.png");
			ButtonRow1.add(currPlayerCard4);

			currPlayerCard5 = createButton(575, bckCard + "Bck3.png");
			ButtonRow1.add(currPlayerCard5);

			// //////////////////////////////////////////
			currPlayerCard6 = createButton(175, bckCard + "Bck3.png");
			ButtonRow2.add(currPlayerCard6);

			currPlayerCard7 = createButton(255, bckCard + "Bck3.png");
			ButtonRow2.add(currPlayerCard7);

			currPlayerCard8 = createButton(335, bckCard + "Bck3.png");
			ButtonRow2.add(currPlayerCard8);

			currPlayerCard9 = createButton(415, bckCard + "Bck3.png");
			ButtonRow2.add(currPlayerCard9);

			currPlayerCard10 = createButton(495, bckCard + "Bck3.png");
			ButtonRow2.add(currPlayerCard10);

			currPlayerCard11 = createButton(575, bckCard + "Bck3.png");
			ButtonRow2.add(currPlayerCard11);

			// //////////////////////////////////////////
			currPlayerCard12 = createButton(175, bckCard + "Bck3.png");
			ButtonRow3.add(currPlayerCard12);

			currPlayerCard13 = createButton(255, bckCard + "Bck3.png");
			ButtonRow3.add(currPlayerCard13);

		} else if (userType == "othPlayer1") {
			othPlayer1_0 = createLabel(36, 144, bckCard + "Bck1.png");
			labels.add(othPlayer1_0);

			othPlayer1_1 = createLabel(36, 304, bckCard + "Bck1.png");
			labels.add(othPlayer1_1);

			othPlayer1_2 = createLabel(36, 224, bckCard + "Bck1.png");
			labels.add(othPlayer1_2);

			othPlayer1_3 = createLabel(140, 144, bckCard + "Bck1.png");
			labels.add(othPlayer1_3);

			othPlayer1_4 = createLabel(140, 224, bckCard + "Bck1.png");
			labels.add(othPlayer1_4);

			othPlayer1_5 = createLabel(140, 304, bckCard + "Bck1.png");
			labels.add(othPlayer1_5);

		} else if (userType == "othPlayer2") {
			othPlayer2_0 = createLabel(175, 20, bckCard + "Bck5.png");
			labels.add(othPlayer2_0);

			othPlayer2_1 = createLabel(255, 20, bckCard + "Bck5.png");
			labels.add(othPlayer2_1);

			othPlayer2_2 = createLabel(335, 20, bckCard + "Bck5.png");
			labels.add(othPlayer2_2);

			othPlayer2_3 = createLabel(415, 20, bckCard + "Bck5.png");
			labels.add(othPlayer2_3);

			othPlayer2_4 = createLabel(495, 20, bckCard + "Bck5.png");
			labels.add(othPlayer2_4);

			othPlayer2_5 = createLabel(575, 20, bckCard + "Bck5.png");
			labels.add(othPlayer2_5);

		} else if (userType == "othPlayer3") {
			othPlayer3_0 = createLabel(559, 144, bckCard + "Bck0.png");
			labels.add(othPlayer3_0);

			othPlayer3_1 = createLabel(559, 224, bckCard + "Bck0.png");
			labels.add(othPlayer3_1);

			othPlayer3_2 = createLabel(559, 304, bckCard + "Bck0.png");
			labels.add(othPlayer3_2);

			othPlayer3_3 = createLabel(667, 144, bckCard + "Bck0.png");
			labels.add(othPlayer3_3);

			othPlayer3_4 = createLabel(667, 224, bckCard + "Bck0.png");
			labels.add(othPlayer3_4);

			othPlayer3_5 = createLabel(667, 304, bckCard + "Bck0.png");
			labels.add(othPlayer3_5);
		} else if (userType == "deck") {
			deck = createLabel(400, 300, bckCard + ".png");
			decks.add(deck);
		}
	}

	public void userInfoButton() {

	}

	public ArrayList<JLabel> getLables() {
		return this.labels;
	}

	public ArrayList<JButton> getButtons(int count) {
		if (count == 0)
			return this.ButtonRow1;
		else if (count == 1)
			return this.ButtonRow2;
		else
			return this.ButtonRow3;
	}

	public JLabel createLabel(int x, int y, String backCard) {
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
				backCard)));
		if (y == 20)
			label.setBounds(x, y, 67, 99);
		else
			label.setBounds(x, y, 99, 67);

		return label;
	}

	public JButton createButton(int x, String backCard) {
		JButton Button = new JButton("");
		Button.setIcon(new ImageIcon(backCard));
		Button.setBounds(x, 440, 67, 99);
		return Button;
	}

	public JButton createButton(int x, int cardNum) {
		String temp = "";
		JButton Button = new JButton("");
		if (cardNum < 10) {
			temp = (PATH + "0" + cardNum + ".png");
			Button.setIcon(new ImageIcon(getClass().getClassLoader()
					.getResource(temp)));
		} else {
			temp = (PATH + cardNum + ".png");
			Button.setIcon(new ImageIcon(getClass().getClassLoader()
					.getResource(temp)));
		}
		System.out.println(Button.getIcon());
		Button.setBounds(x, 440, 67, 99);
		return Button;
	}
}
