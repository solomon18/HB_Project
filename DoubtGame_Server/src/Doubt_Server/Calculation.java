package Doubt_Server;

import java.util.Arrays;
import java.util.Random;

import Shared.DoubtUser;

public class Calculation extends DoubtMain implements PacketNumber {
	Random			rnd = new Random(); 
	private int		NowPlayer;								// ���� ���ʰ� �������� ����
	private int		LastCard;								// �������� �� ī��
	private int		CurrentCard;							// ���� ���ʿ� �ش��ϴ� ī�� 
<<<<<<< HEAD
	private int[]	DeckCard = new int[totalCard];	// �� ���� ���� ī�� ��
=======
	private int[]	DeckCard = new int[totalCard];			// �� ���� ���� ī�� ��
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
	private int		DeckCount = 0;							// �� ���� ���� ����
	
	int[] Card = new int[totalCard];
	int[][] PlayerCard = new int[4][14];
	
//	BroadcastManager broadcast = new BroadcastManager();					// Ŭ���̾�Ʈ���� ������ִ� ��ü
//	DoubtUser players[] = new DoubtUser[MAX_USER];
//	DoubtDao dao = new DoubtDao();
	
	public void GameInit(){
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
<<<<<<< HEAD
	public void DivideCard(){												// ī�带 Ŭ���̾�Ʈ�鿡�� ������
		for (int i = 0; i < MAX_USER; i++){
			if(super.players[i] != null){
				int[] temp = new int[14];
				
				for(int j = 0; j < 14; j++){
					temp[j] = Card[i*14+j*4];
					dao.UpdateDeck(super.players[i].getId(), temp[j]);
				}
				super.players[i].setCard(temp);	
			}
			broadcast.sendToObject(super.players[i], i);
		}
	}
=======
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
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
	public void DivideCard(int i){												// ī�带 Ŭ���̾�Ʈ�鿡�� ������
			int[] temp = new int[14];
			super.players[i] = broadcast.getDT(i).getUserName();
			for(int z = 0; z < 4; z++){
				
				if(super.players[z].getId() != null){
					for(int j = 0; j < 14; j++){
						temp[j] = Card[z*13+j];
					}
<<<<<<< HEAD
					
					super.players[z].setCard(temp);
					System.out.println("divid" + super.players[z]);	
=======
					super.players[z].setCard(temp);
					System.out.println("divid" + super.players[z]);	
//					dao.UpdateDeck(super.players[z].getId(), super.players[z].getCard());
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
				}
			}
//			System.out.println("divid" + user[i]);
			broadcast.sendToObject(super.players[i], i);
	}
	
	public void Start(String msg) {											// ù��° �÷��̾ ������ ī�带 ��
		LastCard = Integer.parseInt(msg.substring(7));
		CurrentCard = Integer.parseInt(msg.substring(7));
		for(int i = 0; i < MAX_USER; i++){
			if(super.players[i] != null){
<<<<<<< HEAD
				if(super.players[i].getId() == super.players[0].getId()){
	//				broadcast.sendTo(i, packet.Start + String.valueOf(CurrentCard));
=======
				if(super.players[i].getId().equals(super.players[0].getId())){
	//				broadcast.sendTo(i, packet.Start + String.valueOf(CurrentCard));
					CurrentCard = this.setUserCard(CurrentCard);			// Card Index�� 12���� �׸� �� �Ʒ��� �ٽ� �������ִ� �޼ҵ�
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
					broadcast.sendToAll(Start + String.valueOf(CurrentCard));
					DeckCard[DeckCount] = LastCard;
				}
			}
		}
	}
	public void Doubt(String msg){											// �ٿ�Ʈ ����� ȣ��Ǵ� �޼ҵ�
		String player = msg.substring(4);
		for(int i = 0; i < MAX_USER; i++){
			if(super.players[i] != null){
<<<<<<< HEAD
				if(super.players[i].getId() == player){
					if(CurrentCard != LastCard){								// ���� ī��Ʈ �� ī��� ���� �÷��̾ �� ī�尡 ���� ���� ���(�� �ٿ�Ʈ�� ���)
	//					broadcast.sendTo(i, packet.Doubt + player);
						NowPlayer--;
						if(NowPlayer ==0)
							NowPlayer = 4;
=======
				if(super.players[i].getId().equals(player)){
					LastCard = this.setUserCard(LastCard);
					CurrentCard = this.setUserCard(CurrentCard);
					if(CurrentCard != LastCard){								// ���� ī��Ʈ �� ī��� ���� �÷��̾ �� ī�尡 ���� ���� ���(�� �ٿ�Ʈ�� ���)
	//					broadcast.sendTo(i, packet.Doubt + player);
						NowPlayer--;
						if(NowPlayer == -1)
							NowPlayer = 3;
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
						this.UpdateCardDeck(i);
						broadcast.sendToOthers(broadcast.getDT(i), Doubt + player + "���� �ٿ�Ʈ�� �����ϼ̽��ϴ�.");
						broadcast.sendToObject(super.players[NowPlayer], i);
					}else{
	//					broadcast.sendTo(i, packet.FailDoubt + player);
						this.UpdateCardDeck(i);
						broadcast.sendToOthers(broadcast.getDT(i), FailDoubt + player + "���� �ٿ�Ʈ�� �����ϼ̽��ϴ�.");
						broadcast.sendToObject(super.players[NowPlayer], i);
					}
					super.players[i].setMyTurnNum(0);
<<<<<<< HEAD
					broadcast.sendToOthers(broadcast.getDT(NowPlayer), Reset + super.players[NowPlayer].getId() + "|" + String.valueOf(super.players[NowPlayer].getCardCount()));
=======
					broadcast.sendToOthers(broadcast.getDT(NowPlayer), Reset + super.players[NowPlayer].getId() + ":" + String.valueOf(super.players[NowPlayer].getCardCount()));
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
				}else {
					int temp = i - 1;
					if(temp == -1)
						temp = 3;
					super.players[i].setMyTurnNum(temp);
<<<<<<< HEAD
					broadcast.sendToOthers(broadcast.getDT(NowPlayer), Reset + super.players[NowPlayer].getId() + "|" + String.valueOf(super.players[NowPlayer].getCardCount()));
=======
					broadcast.sendToOthers(broadcast.getDT(NowPlayer), Reset + super.players[NowPlayer].getId() + ":" + String.valueOf(super.players[NowPlayer].getCardCount()));
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
				}
			}
		}
	}
<<<<<<< HEAD
	public void Drop(String msg){											// �÷��̾ ���� ������ �ִ� ī�带 ��������� ȣ��Ǵ� �޼ҵ�
		String player = msg.substring(6);
		int playerDropedCard = Integer.parseInt(msg.substring(4));			// 1003??(ī���ε��� ���ڸ�)*(�÷��̾��̸�)
		
		if(super.players[NowPlayer].getId() == player){
=======
	public void Drop(String msg){													// �÷��̾ ���� ������ �ִ� ī�带 ��������� ȣ��Ǵ� �޼ҵ�
		String dropplayer[] = msg.split(":");
		int playerDropedCard = Integer.parseInt(dropplayer[2]);						// 1003:(�÷��̾��̸�):(ī���ε��� ���ڸ�)
		
		if(super.players[NowPlayer].getId().equals(dropplayer[1])){
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
			int[] userCard = null;
			userCard = super.players[NowPlayer].getCard();
			
			for(int i = 0; i < userCard.length; i++){
<<<<<<< HEAD
				if(userCard[i] == playerDropedCard){						// �÷��̾ ������ �ִ� ī���������� ����� ī�尡 �ִ� ���
					int usercount = super.players[NowPlayer].getCardCount();
					dao.deleteDeckCard(player, playerDropedCard);			// db�� �ش� �÷��̾��� ī�带 ����
					userCard[i] = 0;										// ������ ����� �÷��̾��� ī�嵦���� ����� ī�带 0���� ����
					super.players[NowPlayer].setCard(userCard);
					super.players[NowPlayer].setCardCount(usercount-1);
					if(usercount == 0){										// �÷��̾� ���� ���ڰ� 0�� ���
=======
				if(userCard[i] == playerDropedCard){								// �÷��̾ ������ �ִ� ī���������� ����� ī�尡 �ִ� ���
					int usercount = super.players[NowPlayer].getCardCount();
					dao.deleteDeckCard(dropplayer[2], playerDropedCard);			// db�� �ش� �÷��̾��� ī�带 ����
					userCard[i] = -1;												// ������ ����� �÷��̾��� ī�嵦���� ����� ī�带 0���� ����
					super.players[NowPlayer].setCard(userCard);
					super.players[NowPlayer].setCardCount(usercount-1);
					if(usercount == 0){												// �÷��̾� ���� ���ڰ� 0�� ���
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
						super.players[NowPlayer].setWin(super.players[NowPlayer].getWin()+1);
						this.EndGame();
					}
				}
			}
<<<<<<< HEAD
			LastCard = playerDropedCard;									// ����� ī�带 ������ �� ī�� ������ ����
			CurrentCard++; 
			this.UpdateCardNum();
=======
			LastCard = playerDropedCard;											// ����� ī�带 ������ �� ī�� ������ ����
			CurrentCard++; 
//			this.UpdateCardNum();
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
			DeckCount++;
			DeckCard[DeckCount] = LastCard;
			
			NowPlayer++;
			if(NowPlayer == 4)
				NowPlayer = 0;
			
<<<<<<< HEAD
			broadcast.sendToOthers(broadcast.getDT(NowPlayer), Drop + super.players[NowPlayer].getId() + "|" + String.valueOf(super.players[NowPlayer].getCardCount()));		// ���� �÷��̾��� ī�� ī��Ʈ�� �ٸ� �÷��̾�鿡�� ����
=======
			broadcast.sendToOthers(broadcast.getDT(NowPlayer), Drop + ":" + super.players[NowPlayer].getId() + ":" + String.valueOf(super.players[NowPlayer].getCardCount()));		// ���� �÷��̾��� ī�� ī��Ʈ�� �ٸ� �÷��̾�鿡�� ����
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
			
		}else{
			for(int i = 0; i < MAX_USER; i++){
				if(super.players[i] != null){
<<<<<<< HEAD
					if(super.players[i].getId() == player)
=======
					if(super.players[i].getId().equals(dropplayer[1]))
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
						broadcast.sendTo(i, NotYourTurn + "���� ���� �ƴմϴ�.");
				}
			}
		}
	}
<<<<<<< HEAD
	public void UpdateCardNum(){											// Ŭ���̾�Ʈ�鿡�� ī�������� ������Ʈ���� �˸�.
		for (int i=0; i < MAX_USER; i++){
			if (super.players[i].getPlaying()){
				broadcast.sendToAll(CardNum + CurrentCard);
			} 
		}
	}
	public void UpdateCardDeck(int i){										// ������ ������ �ִ� �÷��̾� ������ db�� ����Ǿ��ִ� �÷��̾� ������ ���� ������Ʈ
		int[] userCard = null;
		for(int j = 0; j < DeckCard.length; j++){
			dao.UpdateDeck(super.players[NowPlayer].getId(), DeckCard[j]);
		}
		userCard = dao.getCarddeck();
		super.players[NowPlayer].setCard(userCard );
		broadcast.sendToObject(super.players[NowPlayer], i);
		super.players[NowPlayer].setMyTurnNum(0);
=======
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
				for(int k = 0; k < DeckCard.length; k++){
					tempCard[i+k] = DeckCard[k];
				}
				break;
			}
			
		}
		dao.UpdateDeck(super.players[NowPlayer].getId(), DeckCard);
		userCard = dao.getCarddeck();
		super.players[NowPlayer].setCard(userCard );
		broadcast.sendToObject(super.players[NowPlayer], i);
		super.players[NowPlayer].setMyTurnNum(0); 
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
		DeckCard = null;
		DeckCount = 0;
	}
	public void result(){
		
	}
	public void EndGame(){													// ������ ������ ��� ȣ��Ǵ� �޼ҵ�
		int TurnNum = 0;
		for(int i = 0; i < MAX_USER; i++){
			int UserWin = 0;
			int DbWin = 0;
			DoubtUser dbuser;
			dbuser = dao.getUser(super.players[i].getId());
			UserWin = super.players[i].getWin();										// ������ ������ �ִ� �¸� ��
			DbWin = dbuser.getWin();										// db���� ������ ���������� �¸� ��
			String Winner = "";												// �¸��� �÷��̾� �̸� ���� ����
			if(UserWin != DbWin){
				Winner = super.players[i].getId();
				super.players[i].setMyTurnNum(0);
				broadcast.sendTo(i ,EndGame + "����� �̰���ϴ�.");
			}else{
				super.players[i].setLose(super.players[i].getLose()+1);
				super.players[i].setMyTurnNum(TurnNum + 1);
				broadcast.sendToAll(EndGame + Winner + "���� �̰���ϴ�.");
			}
			dao.resetDeck(super.players[i].getId());									// db�� �ִ� ī�嵦 ���� �ʱ�ȭ
			DeckCard = null;
			DeckCount = 0;
			this.GameInit();												// ���� �ٽ� ����
			this.DivideCard(i);												// �ٽ� ī���и� Ŭ���̾�Ʈ���� ����
			broadcast.sendToAll(Winner + "�� �����Դϴ�.");
		}
	}
<<<<<<< HEAD
=======
	public int setUserCard(int card){
		if(card >= 13){
			card = card -12;
		}else if (card >= 26){
			card = card - 24;
		}else if (card >= 39){
			card = card - 36;
		}
		return card;
	}
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
}
