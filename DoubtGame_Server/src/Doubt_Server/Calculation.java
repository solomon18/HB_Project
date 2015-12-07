package Doubt_Server;

import java.util.Arrays;
import java.util.Random;

import Shared.DoubtUser;

public class Calculation extends DoubtMain implements PacketNumber {
	Random			rnd = new Random(); 
	private int		NowPlayer;								// ���� ���ʰ� �������� ����
	private int		LastCard;								// �������� �� ī��
	private int		CurrentCard;							// ���� ���ʿ� �ش��ϴ� ī�� 
	private int[]	DeckCard = new int[totalCard];			// �� ���� ���� ī�� ��
	private int		DeckCount = 0;							// �� ���� ���� ����
	private boolean StartGame = false;
	
	int[] Card = new int[totalCard];
	int[][] PlayerCard = new int[4][14];
	
//	BroadcastManager broadcast = new BroadcastManager();					// Ŭ���̾�Ʈ���� ������ִ� ��ü
//	DoubtUser players[] = new DoubtUser[MAX_USER];
//	DoubtDao dao = new DoubtDao();
	
	public void GameInit(){
		NowPlayer = 0;
		for(int i = 0; i < Card.length; i++){								// ī�� ����
			Card[i] = i;
		}
		for(int i = 0; i < Card.length; i++){								// ī�� ����
			Card[i] = rnd.nextInt(totalCard);
			for(int j= 0; j < i; j++){								// ī�� �ߺ� �˻�
				if(Card[i] == Card[j]){
					i--;
					break;
				}
			}			
		}
	}
//	public void DivideCard(){												// ī�带 Ŭ���̾�Ʈ�鿡�� ������
//		for (int i = 0; i < MAX_USER; i++){
//			if(super.players[i] != null){
//				int[] temp = new int[14];
//				
//				for(int j = 0; j < 14; j++){
//					temp[j] = Card[i*14+j*4];
//					dao.UpdateDeck(super.players[i].getId(), temp[j]);
//				}
//				super.players[i].setCard(temp);	
//			}
//			broadcast.sendToObject(super.players[i], i);
//		}
//	}
	public void DivideCard(int i){												// ī�带 Ŭ���̾�Ʈ�鿡�� ������
			int[] temp = new int[14];
			super.players[i] = broadcast.getDT(i).getUserName();
			for(int z = 0; z < 4; z++){
				
				if(super.players[z].getId() != null){
					for(int j = 0; j < 13; j++){
						temp[j] = Card[z*13+j];
					}
					super.players[z].setCard(temp);
					System.out.println("divid" + super.players[z]);
//					dao.createUserDeck(super.players[z]);
//					dao.UpdateDeck(super.players[z].getId(), super.players[z].getCard());
				}
			}
//			System.out.println("divid" + user[i]);
			broadcast.sendToObject(super.players[i], i);
	}
	
	public void Start(String cardindex) {											// ù��° �÷��̾ ������ ī�带 ��
		LastCard = Integer.parseInt(cardindex);
		CurrentCard = Integer.parseInt(cardindex);
		for(int i = 0; i < MAX_USER; i++){
			if(super.players[i] != null){
				if(super.players[i].getId().equals(super.players[0].getId())){
	//				broadcast.sendTo(i, packet.Start + String.valueOf(CurrentCard));
					CurrentCard = this.setUserCard(CurrentCard);			// Card Index�� 12���� �׸� �� �Ʒ��� �ٽ� �������ִ� �޼ҵ�
					broadcast.sendToAll(Start + "1 ������ �����Դϴ�.");
					System.out.println(cardindex + CurrentCard);
					DeckCard[DeckCount] = LastCard;
				}
			}
		}
		StartGame = true;
		NowPlayer++;
		System.out.println("���� ����!");
	}
	public void Doubt(String msg){											// �ٿ�Ʈ ����� ȣ��Ǵ� �޼ҵ�
		String player = msg.substring(4);
		for(int i = 0; i < MAX_USER; i++){
			if(super.players[i] != null){
				if(super.players[i].getId().equals(player)){
					LastCard = this.setUserCard(LastCard);
					CurrentCard = this.setUserCard(CurrentCard);
					if(CurrentCard != LastCard){								// ���� ī��Ʈ �� ī��� ���� �÷��̾ �� ī�尡 ���� ���� ���
	//					broadcast.sendTo(i, packet.Doubt + player);
						NowPlayer--;
						if(NowPlayer == -1)
							NowPlayer = 3;
						this.UpdateCardDeck(i);
						broadcast.sendToOthers(broadcast.getDT(i), Doubt + player + "���� �ٿ�Ʈ�� �����ϼ̽��ϴ�.");
						broadcast.sendToObject(super.players[NowPlayer], i);
					}else{														// ���� ī��Ʈ �� ī��� ���� �÷��̾ �� ī�尡 ���� ���
	//					broadcast.sendTo(i, packet.FailDoubt + player);
						this.UpdateCardDeck(i);
						broadcast.sendToOthers(broadcast.getDT(i), FailDoubt + player + "���� �ٿ�Ʈ�� �����ϼ̽��ϴ�.");
						broadcast.sendToObject(super.players[NowPlayer], i);
					}
					super.players[i].setMyTurnNum(0);
					broadcast.sendToOthers(broadcast.getDT(NowPlayer), Reset + super.players[NowPlayer].getId() + ":" + String.valueOf(super.players[NowPlayer].getCardCount()));
				}else {
					int temp = i - 1;
					if(temp == -1)
						temp = 3;
					super.players[i].setMyTurnNum(temp);
					broadcast.sendToOthers(broadcast.getDT(NowPlayer), Reset + super.players[NowPlayer].getId() + ":" + String.valueOf(super.players[NowPlayer].getCardCount()));
				}
			}
		}
		LastCard = 0;
		CurrentCard = 0;
	}
	public void Drop(String msg){													// �÷��̾ ���� ������ �ִ� ī�带 ��������� ȣ��Ǵ� �޼ҵ�
		String dropplayer[] = msg.split(":");
		
		int playerDropedCard = Integer.parseInt(dropplayer[2]);						// 1003:(�÷��̾��̸�):(ī���ε��� ���ڸ�)
		System.out.println(dropplayer[0]+dropplayer[1]+dropplayer[2]+super.players[NowPlayer]);
		if(super.players[NowPlayer].getId().equals(dropplayer[1])){					// �� �������� üũ
			int[] userCard = null;
			if(StartGame != false){
				userCard = super.players[NowPlayer].getCard();
				
				for(int i = 0; i < userCard.length; i++){
					if(userCard[i] == playerDropedCard){								// �÷��̾ ������ �ִ� ī���������� ����� ī�尡 �ִ� ���
						int usercount = super.players[NowPlayer].getCardCount();
						dao.deleteDeckCard(dropplayer[2], playerDropedCard);			// db�� �ش� �÷��̾��� ī�带 ����
						userCard[i] = -1;												// ������ ����� �÷��̾��� ī�嵦���� ����� ī�带 -1���� ����
						super.players[NowPlayer].setCard(userCard);
						super.players[NowPlayer].setCardCount(usercount-1);
						if(usercount == 0){												// �÷��̾� ���� ���ڰ� 0�� ���
							super.players[NowPlayer].setWin(super.players[NowPlayer].getWin()+1);
							this.EndGame();
						}
					}
				}
				LastCard = playerDropedCard;											// ����� ī�带 ������ �� ī�� ������ ����
				CurrentCard++; 
	//			this.UpdateCardNum();
				DeckCount++;
				DeckCard[DeckCount] = LastCard;
				System.out.println("���� �� ī���" + DeckCard[DeckCount]);
				NowPlayer++;
				
				if(NowPlayer == 4)
					NowPlayer = 0;
				System.out.println("���� �÷��̾��?" + NowPlayer);
				broadcast.sendToAll(MSG + "�̹� ���ʴ� " + super.players[NowPlayer].getId() + " �� �Դϴ�.");
				broadcast.sendToOthers(broadcast.getDT(NowPlayer), Drop + ":" + super.players[NowPlayer].getId() + ":" + String.valueOf(super.players[NowPlayer].getCardCount()));		// ���� �÷��̾��� ī�� ī��Ʈ�� �ٸ� �÷��̾�鿡�� ����
				
			}else if(broadcast.size() == 4){
				this.Start(dropplayer[2]);
			}
		}else{
			for(int i = 0; i < MAX_USER; i++){
				if(super.players[i] != null){
					if(super.players[i].getId().equals(dropplayer[1]))
						broadcast.sendTo(i, NotYourTurn + "���� ���� �ƴմϴ�.");
				}
			}
		}
	}
//	public void UpdateCardNum(){											// Ŭ���̾�Ʈ�鿡�� ī�������� ������Ʈ���� �˸�.
//		for (int i=0; i < MAX_USER; i++){
//			if (super.players[i].getPlaying()){
//				broadcast.sendToAll(CardNum + CurrentCard);
//			} 
//		}
//	}
	public void UpdateCardDeck(int i){										// ������ ������ �ִ� �÷��̾� ������ db�� ����Ǿ��ִ� �÷��̾� ������ ���� ������Ʈ
		int[] userCard = null;
		int[] tempCard = new int[52];
		int tempCounter = 0;
		userCard = super.players[NowPlayer].getCard();
		for(int j = 0; j < userCard.length; j++){
			tempCard[i] = userCard[i];
			if(tempCard[i] == 0){
				System.out.println(DeckCard[0]);
				for(int k = 0; k < DeckCard.length; k++){
					tempCard[i+k] = DeckCard[k];
				}
				break;
			}
		}
		
//		dao.UpdateDeck(super.players[NowPlayer].getId(), tempCard);
//		userCard = dao.getCarddeck(super.players[NowPlayer]);
//		super.players[NowPlayer].setCard(userCard );
		super.players[NowPlayer].setCard(tempCard );
		broadcast.sendToObject(super.players[NowPlayer], i);
		super.players[NowPlayer].setMyTurnNum(0); 
		DeckCard = null;
		DeckCount = 0;
		System.out.println("updatecarddeck �Ϸ�");
	}
	public void EndGame(){													// ������ ������ ��� ȣ��Ǵ� �޼ҵ�
		int TurnNum = 0;
		String Winner = "";												// �¸��� �÷��̾� �̸� ���� ����
		for(int i = 0; i < MAX_USER; i++){
			int UserWin = 0;
			int DbWin = 0;
			DoubtUser dbuser;
			dbuser = dao.getUser(super.players[i].getId());
			UserWin = super.players[i].getWin();										// ������ ������ �ִ� �¸� ��
			DbWin = dbuser.getWin();										// db���� ������ ���������� �¸� ��
			if(UserWin != DbWin){
				Winner = super.players[i].getId();
				super.players[i].setMyTurnNum(0);
				broadcast.sendTo(i ,EndGame + "����� �̰���ϴ�.");
			}else{
				super.players[i].setLose(super.players[i].getLose()+1);
				super.players[i].setMyTurnNum(TurnNum + 1);
			}
			dao.resetDeck(super.players[i].getId());									// db�� �ִ� ī�嵦 ���� �ʱ�ȭ
		}
		broadcast.sendToAll(EndGame + Winner + "���� �̰���ϴ�.");
		DeckCard = null;
		DeckCount = 0;
		broadcast.sendToAll(Winner + "�� �����Դϴ�.");
		this.GameInit();												// ���� �ٽ� ����
		for(int i = 0; i < MAX_USER; i++){
//			dao.createUserDeck(super.players[i]);
			this.DivideCard(i);												// �ٽ� ī���и� Ŭ���̾�Ʈ���� ����	
		}
	}
	public int setUserCard(int card){
		if(card >= 39){
			card = card -38;
		}else if (card >= 26){
			card = card - 25;
		}else if (card >= 13){
			card = card - 12;
		}
		return card;
	}
	public String send0player(){
		return super.players[0].getId();
	}
}
