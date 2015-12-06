package Doubt_Server;

import java.util.Arrays;
import java.util.Random;

import Shared.DoubtUser;

public class Calculation extends DoubtMain implements PacketNumber {
	Random			rnd = new Random(); 
	private int		NowPlayer;								// 현재 차례가 누구인지 저장
	private int		LastCard;								// 마지막에 낸 카드
	private int		CurrentCard;							// 현재 차례에 해당하는 카드 
<<<<<<< HEAD
	private int[]	DeckCard = new int[totalCard];	// 총 덱에 쌓인 카드 배
=======
	private int[]	DeckCard = new int[totalCard];			// 총 덱에 쌓인 카드 배
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
	private int		DeckCount = 0;							// 총 덱에 쌓인 개수
	
	int[] Card = new int[totalCard];
	int[][] PlayerCard = new int[4][14];
	
//	BroadcastManager broadcast = new BroadcastManager();					// 클라이언트에게 방송해주는 객체
//	DoubtUser players[] = new DoubtUser[MAX_USER];
//	DoubtDao dao = new DoubtDao();
	
	public void GameInit(){
		for(int i = 0; i < Card.length; i++){								// 카드 생성
			Card[i] = i;
		}
		for(int i = 0; i < Card.length; i++){								// 카드 섞기
			Card[i] = rnd.nextInt(totalCard);
			for(int j= 0; j < i; j++){								// 카드 중복 검사
				if(Card[i] == Card[j]){
					i--;
					break;
				}
			}			
		}
	}
<<<<<<< HEAD
	public void DivideCard(){												// 카드를 클라이언트들에게 나눠줌
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
//	public void DivideCard(){												// 카드를 클라이언트들에게 나눠줌
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
	public void DivideCard(int i){												// 카드를 클라이언트들에게 나눠줌
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
	
	public void Start(String msg) {											// 첫번째 플레이어가 시작할 카드를 냄
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
					CurrentCard = this.setUserCard(CurrentCard);			// Card Index가 12보다 그면 그 아래로 다시 세팅해주는 메소드
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
					broadcast.sendToAll(Start + String.valueOf(CurrentCard));
					DeckCard[DeckCount] = LastCard;
				}
			}
		}
	}
	public void Doubt(String msg){											// 다우트 선언시 호출되는 메소드
		String player = msg.substring(4);
		for(int i = 0; i < MAX_USER; i++){
			if(super.players[i] != null){
<<<<<<< HEAD
				if(super.players[i].getId() == player){
					if(CurrentCard != LastCard){								// 현재 카운트 된 카드와 이전 플레이어가 낸 카드가 같지 않을 경우(즉 다우트인 경우)
	//					broadcast.sendTo(i, packet.Doubt + player);
						NowPlayer--;
						if(NowPlayer ==0)
							NowPlayer = 4;
=======
				if(super.players[i].getId().equals(player)){
					LastCard = this.setUserCard(LastCard);
					CurrentCard = this.setUserCard(CurrentCard);
					if(CurrentCard != LastCard){								// 현재 카운트 된 카드와 이전 플레이어가 낸 카드가 같지 않을 경우(즉 다우트인 경우)
	//					broadcast.sendTo(i, packet.Doubt + player);
						NowPlayer--;
						if(NowPlayer == -1)
							NowPlayer = 3;
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
						this.UpdateCardDeck(i);
						broadcast.sendToOthers(broadcast.getDT(i), Doubt + player + "님이 다우트에 성공하셨습니다.");
						broadcast.sendToObject(super.players[NowPlayer], i);
					}else{
	//					broadcast.sendTo(i, packet.FailDoubt + player);
						this.UpdateCardDeck(i);
						broadcast.sendToOthers(broadcast.getDT(i), FailDoubt + player + "님이 다우트에 실패하셨습니다.");
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
	public void Drop(String msg){											// 플레이어가 덱에 가지고 있던 카드를 드랍했을때 호출되는 메소드
		String player = msg.substring(6);
		int playerDropedCard = Integer.parseInt(msg.substring(4));			// 1003??(카드인덱스 두자리)*(플레이어이름)
		
		if(super.players[NowPlayer].getId() == player){
=======
	public void Drop(String msg){													// 플레이어가 덱에 가지고 있던 카드를 드랍했을때 호출되는 메소드
		String dropplayer[] = msg.split(":");
		int playerDropedCard = Integer.parseInt(dropplayer[2]);						// 1003:(플레이어이름):(카드인덱스 두자리)
		
		if(super.players[NowPlayer].getId().equals(dropplayer[1])){
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
			int[] userCard = null;
			userCard = super.players[NowPlayer].getCard();
			
			for(int i = 0; i < userCard.length; i++){
<<<<<<< HEAD
				if(userCard[i] == playerDropedCard){						// 플레이어가 가지고 있는 카드정보에서 드랍된 카드가 있는 경우
					int usercount = super.players[NowPlayer].getCardCount();
					dao.deleteDeckCard(player, playerDropedCard);			// db에 해당 플레이어의 카드를 삭제
					userCard[i] = 0;										// 서버에 저장된 플레이어의 카드덱에서 드랍한 카드를 0으로 변경
					super.players[NowPlayer].setCard(userCard);
					super.players[NowPlayer].setCardCount(usercount-1);
					if(usercount == 0){										// 플레이어 덱에 숫자가 0인 경우
=======
				if(userCard[i] == playerDropedCard){								// 플레이어가 가지고 있는 카드정보에서 드랍된 카드가 있는 경우
					int usercount = super.players[NowPlayer].getCardCount();
					dao.deleteDeckCard(dropplayer[2], playerDropedCard);			// db에 해당 플레이어의 카드를 삭제
					userCard[i] = -1;												// 서버에 저장된 플레이어의 카드덱에서 드랍한 카드를 0으로 변경
					super.players[NowPlayer].setCard(userCard);
					super.players[NowPlayer].setCardCount(usercount-1);
					if(usercount == 0){												// 플레이어 덱에 숫자가 0인 경우
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
						super.players[NowPlayer].setWin(super.players[NowPlayer].getWin()+1);
						this.EndGame();
					}
				}
			}
<<<<<<< HEAD
			LastCard = playerDropedCard;									// 드랍한 카드를 마지막 낸 카드 변수에 대입
			CurrentCard++; 
			this.UpdateCardNum();
=======
			LastCard = playerDropedCard;											// 드랍한 카드를 마지막 낸 카드 변수에 대입
			CurrentCard++; 
//			this.UpdateCardNum();
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
			DeckCount++;
			DeckCard[DeckCount] = LastCard;
			
			NowPlayer++;
			if(NowPlayer == 4)
				NowPlayer = 0;
			
<<<<<<< HEAD
			broadcast.sendToOthers(broadcast.getDT(NowPlayer), Drop + super.players[NowPlayer].getId() + "|" + String.valueOf(super.players[NowPlayer].getCardCount()));		// 현재 플레이어의 카드 카운트를 다른 플레이어들에게 전달
=======
			broadcast.sendToOthers(broadcast.getDT(NowPlayer), Drop + ":" + super.players[NowPlayer].getId() + ":" + String.valueOf(super.players[NowPlayer].getCardCount()));		// 현재 플레이어의 카드 카운트를 다른 플레이어들에게 전달
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
			
		}else{
			for(int i = 0; i < MAX_USER; i++){
				if(super.players[i] != null){
<<<<<<< HEAD
					if(super.players[i].getId() == player)
=======
					if(super.players[i].getId().equals(dropplayer[1]))
>>>>>>> b82b8f65473a04cecae9d7ca6dfeb8d5aaec52aa
						broadcast.sendTo(i, NotYourTurn + "아직 턴이 아닙니다.");
				}
			}
		}
	}
<<<<<<< HEAD
	public void UpdateCardNum(){											// 클라이언트들에게 카드정보가 업데이트됨을 알림.
		for (int i=0; i < MAX_USER; i++){
			if (super.players[i].getPlaying()){
				broadcast.sendToAll(CardNum + CurrentCard);
			} 
		}
	}
	public void UpdateCardDeck(int i){										// 서버가 가지고 있는 플레이어 정보와 db에 저장되어있는 플레이어 정보의 덱을 업데이트
		int[] userCard = null;
		for(int j = 0; j < DeckCard.length; j++){
			dao.UpdateDeck(super.players[NowPlayer].getId(), DeckCard[j]);
		}
		userCard = dao.getCarddeck();
		super.players[NowPlayer].setCard(userCard );
		broadcast.sendToObject(super.players[NowPlayer], i);
		super.players[NowPlayer].setMyTurnNum(0);
=======
//	public void UpdateCardNum(){											// 클라이언트들에게 카드정보가 업데이트됨을 알림.
//		for (int i=0; i < MAX_USER; i++){
//			if (super.players[i].getPlaying()){
//				broadcast.sendToAll(CardNum + CurrentCard);
//			} 
//		}
//	}
	public void UpdateCardDeck(int i){										// 서버가 가지고 있는 플레이어 정보와 db에 저장되어있는 플레이어 정보의 덱을 업데이트
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
	public void EndGame(){													// 게임이 끝났을 경우 호출되는 메소드
		int TurnNum = 0;
		for(int i = 0; i < MAX_USER; i++){
			int UserWin = 0;
			int DbWin = 0;
			DoubtUser dbuser;
			dbuser = dao.getUser(super.players[i].getId());
			UserWin = super.players[i].getWin();										// 서버가 가지고 있는 승리 수
			DbWin = dbuser.getWin();										// db에서 가져온 유저정보의 승리 수
			String Winner = "";												// 승리한 플레이어 이름 저장 변수
			if(UserWin != DbWin){
				Winner = super.players[i].getId();
				super.players[i].setMyTurnNum(0);
				broadcast.sendTo(i ,EndGame + "당신이 이겼습니다.");
			}else{
				super.players[i].setLose(super.players[i].getLose()+1);
				super.players[i].setMyTurnNum(TurnNum + 1);
				broadcast.sendToAll(EndGame + Winner + "님이 이겼습니다.");
			}
			dao.resetDeck(super.players[i].getId());									// db에 있는 카드덱 정보 초기화
			DeckCard = null;
			DeckCount = 0;
			this.GameInit();												// 게임 다시 세팅
			this.DivideCard(i);												// 다시 카드패를 클라이언트에게 보냄
			broadcast.sendToAll(Winner + "님 차례입니다.");
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
