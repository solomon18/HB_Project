package Doubt_Server;

import java.util.Arrays;
import java.util.Random;

import Shared.DoubtUser;

public class Calculation implements PacketNumber {
	PacketNumber packet;
	Random			rnd = new Random(); 
	private int		NowPlayer;								// ���� ���ʰ� �������� ����
	private int		LastCard;								// �������� �� ī��
	private int		CurrentCard;							// ���� ���ʿ� �ش��ϴ� ī�� 
	private int[]	DeckCard = new int[packet.totalCard];	// �� ���� ���� ī�� ��
	private int		DeckCount = 0;							// �� ���� ���� ����
	
	int[] Card = new int[totalCard];
	int[][] PlayerCard = new int[4][14];
	
//	BroadcastManager broadcast = new BroadcastManager();					// Ŭ���̾�Ʈ���� ������ִ� ��ü
//	DoubtUser user[] = new DoubtUser[MAX_USER];
//	DoubtDao dao = new DoubtDao();
	
	public void GameInit(){
		for(int i = 0; i < Card.length; i++){								// ī�� ����
			Card[i] = i;
		}
		for(int i = 0; i < Card.length; i++){								// ī�� ����
//			Card[i] = rnd.nextInt(totalCard);
//			for(int j = 0; j < Card[i]; j++){								// ī�� �ߺ� �˻�
//				if(Card[j] == Card[i])
//					Card[i] = rnd.nextInt(totalCard);
//			}
			int n = (int)(Math.random()*Card.length);        // 0~9�� ���� ���� ��ȣ ���

		       int tmp = Card[0];
		       Card[0] = Card[n];
		       Card[n] = tmp;


		}
		
	}
	public void DivideCard(){												// ī�带 Ŭ���̾�Ʈ�鿡�� ������
		for (int i = 0; i < MAX_USER; i++){
			if(user[i] != null){
				int[] temp = new int[14];
				
				for(int j = 0; j < 14; j++){
					temp[j] = Card[i*14+j*4];
					dao.UpdateDeck(user[i].getId(), temp[j]);
				}
				user[i].setCard(temp);	
			}
			broadcast.sendToObject(user[i], i);
		}
	}
	public void DivideCard(int i){												// ī�带 Ŭ���̾�Ʈ�鿡�� ������
			int[] temp = new int[14];
			
			for(int z = 0; z < 4; z++){
				for(int j = 0; j < 14; j++){
					temp[j] = Card[j];
				}
//				user[z] = new DoubtUser();
				user[z].setCard(temp);
			}
			System.out.println(user[i]);
			broadcast.sendToObject(user[i], i);
	}
	
	public void Start(String msg) {											// ù��° �÷��̾ ������ ī�带 ��
		LastCard = Integer.parseInt(msg.substring(7));
		CurrentCard = Integer.parseInt(msg.substring(7));
		for(int i = 0; i < MAX_USER; i++){
			if(user[i] != null){
				if(user[i].getId() == user[0].getId()){
	//				broadcast.sendTo(i, packet.Start + String.valueOf(CurrentCard));
					broadcast.sendToAll(packet.Start + String.valueOf(CurrentCard));
					DeckCard[DeckCount] = LastCard;
				}
			}
		}
	}
	public void Doubt(String msg){											// �ٿ�Ʈ ����� ȣ��Ǵ� �޼ҵ�
		String player = msg.substring(4);
		for(int i = 0; i < packet.MAX_USER; i++){
			if(user[i] != null){
				if(user[i].getId() == player){
					if(CurrentCard != LastCard){								// ���� ī��Ʈ �� ī��� ���� �÷��̾ �� ī�尡 ���� ���� ���(�� �ٿ�Ʈ�� ���)
	//					broadcast.sendTo(i, packet.Doubt + player);
						NowPlayer--;
						if(NowPlayer ==0)
							NowPlayer = 4;
						this.UpdateCardDeck(i);
						broadcast.sendToOthers(broadcast.getDT(i), packet.Doubt + player + "���� �ٿ�Ʈ�� �����ϼ̽��ϴ�.");
						broadcast.sendToObject(user[NowPlayer], i);
					}else{
	//					broadcast.sendTo(i, packet.FailDoubt + player);
						this.UpdateCardDeck(i);
						broadcast.sendToOthers(broadcast.getDT(i), packet.FailDoubt + player + "���� �ٿ�Ʈ�� �����ϼ̽��ϴ�.");
						broadcast.sendToObject(user[NowPlayer], i);
					}
					user[i].setMyTurnNum(0);
					broadcast.sendToOthers(broadcast.getDT(NowPlayer), packet.Reset + user[NowPlayer].getId() + "|" + String.valueOf(user[NowPlayer].getCardCount()));
				}else {
					int temp = i - 1;
					if(temp == -1)
						temp = 3;
					user[i].setMyTurnNum(temp);
					broadcast.sendToOthers(broadcast.getDT(NowPlayer), packet.Reset + user[NowPlayer].getId() + "|" + String.valueOf(user[NowPlayer].getCardCount()));
				}
			}
		}
	}
	public void Drop(String msg){											// �÷��̾ ���� ������ �ִ� ī�带 ��������� ȣ��Ǵ� �޼ҵ�
		String player = msg.substring(6);
		int playerDropedCard = Integer.parseInt(msg.substring(4));			// 1003??(ī���ε��� ���ڸ�)*(�÷��̾��̸�)
		
		if(user[NowPlayer].getId() == player){
			int[] userCard = null;
			userCard = user[NowPlayer].getCard();
			
			for(int i = 0; i < userCard.length; i++){
				if(userCard[i] == playerDropedCard){						// �÷��̾ ������ �ִ� ī���������� ����� ī�尡 �ִ� ���
					int usercount = user[NowPlayer].getCardCount();
					dao.deleteDeckCard(player, playerDropedCard);			// db�� �ش� �÷��̾��� ī�带 ����
					userCard[i] = 0;										// ������ ����� �÷��̾��� ī�嵦���� ����� ī�带 0���� ����
					user[NowPlayer].setCard(userCard);
					user[NowPlayer].setCardCount(usercount-1);
					if(usercount == 0){										// �÷��̾� ���� ���ڰ� 0�� ���
						user[NowPlayer].setWin(user[NowPlayer].getWin()+1);
						this.EndGame();
					}
				}
			}
			LastCard = playerDropedCard;									// ����� ī�带 ������ �� ī�� ������ ����
			CurrentCard++; 
			this.UpdateCardNum();
			DeckCount++;
			DeckCard[DeckCount] = LastCard;
			
			NowPlayer++;
			if(NowPlayer == 4)
				NowPlayer = 0;
			
			broadcast.sendToOthers(broadcast.getDT(NowPlayer), packet.Drop + user[NowPlayer].getId() + "|" + String.valueOf(user[NowPlayer].getCardCount()));		// ���� �÷��̾��� ī�� ī��Ʈ�� �ٸ� �÷��̾�鿡�� ����
			
		}else{
			for(int i = 0; i < packet.MAX_USER; i++){
				if(user[i] != null){
					if(user[i].getId() == player)
						broadcast.sendTo(i, packet.NotYourTurn + "���� ���� �ƴմϴ�.");
				}
			}
		}
	}
	public void UpdateCardNum(){											// Ŭ���̾�Ʈ�鿡�� ī�������� ������Ʈ���� �˸�.
		for (int i=0; i < MAX_USER; i++){
			if (user[i].getPlaying()){
				broadcast.sendToAll(packet.CardNum + CurrentCard);
			} 
		}
	}
	public void UpdateCardDeck(int i){										// ������ ������ �ִ� �÷��̾� ������ db�� ����Ǿ��ִ� �÷��̾� ������ ���� ������Ʈ
		int[] userCard = null;
		for(int j = 0; j < DeckCard.length; j++){
			dao.UpdateDeck(user[NowPlayer].getId(), DeckCard[j]);
		}
		userCard = dao.getCarddeck();
		user[NowPlayer].setCard(userCard );
		broadcast.sendToObject(user[NowPlayer], i);
		user[NowPlayer].setMyTurnNum(0);
		DeckCard = null;
		DeckCount = 0;
	}
	public void result(){
		
	}
	public void EndGame(){													// ������ ������ ��� ȣ��Ǵ� �޼ҵ�
		int TurnNum = 0;
		for(int i = 0; i < packet.MAX_USER; i++){
			int UserWin = 0;
			int DbWin = 0;
			DoubtUser dbuser;
			dbuser = dao.getUser(user[i].getId());
			UserWin = user[i].getWin();										// ������ ������ �ִ� �¸� ��
			DbWin = dbuser.getWin();										// db���� ������ ���������� �¸� ��
			String Winner = "";												// �¸��� �÷��̾� �̸� ���� ����
			if(UserWin != DbWin){
				Winner = user[i].getId();
				user[i].setMyTurnNum(0);
				broadcast.sendTo(i ,packet.EndGame + "����� �̰���ϴ�.");
			}else{
				user[i].setLose(user[i].getLose()+1);
				user[i].setMyTurnNum(TurnNum + 1);
				broadcast.sendToAll(packet.EndGame + Winner + "���� �̰���ϴ�.");
			}
			dao.resetDeck(user[i].getId());									// db�� �ִ� ī�嵦 ���� �ʱ�ȭ
			DeckCard = null;
			DeckCount = 0;
			this.GameInit();												// ���� �ٽ� ����
			this.DivideCard(i);												// �ٽ� ī���и� Ŭ���̾�Ʈ���� ����
			broadcast.sendToAll(Winner + "�� �����Դϴ�.");
		}
	}
}
