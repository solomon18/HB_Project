package Doubt_Server;

import java.util.Arrays;
import java.util.Random;

import Shared.DoubtUser;

public class Calculation implements PacketNumber {
	PacketNumber packet;
	Random			rnd = new Random(); 
	private int		NowPlayer;								// 현재 차례가 누구인지 저장
	private int		LastCard;								// 마지막에 낸 카드
	private int		CurrentCard;							// 현재 차례에 해당하는 카드 
	private int[]	DeckCard = new int[packet.totalCard];	// 총 덱에 쌓인 카드 배
	private int		DeckCount = 0;							// 총 덱에 쌓인 개수
	
	int[] Card = new int[totalCard];
	int[][] PlayerCard = new int[4][14];
	
//	BroadcastManager broadcast = new BroadcastManager();					// 클라이언트에게 방송해주는 객체
//	DoubtUser user[] = new DoubtUser[MAX_USER];
//	DoubtDao dao = new DoubtDao();
	
	public void GameInit(){
		for(int i = 0; i < Card.length; i++){								// 카드 생성
			Card[i] = i;
		}
		for(int i = 0; i < Card.length; i++){								// 카드 섞기
//			Card[i] = rnd.nextInt(totalCard);
//			for(int j = 0; j < Card[i]; j++){								// 카드 중복 검사
//				if(Card[j] == Card[i])
//					Card[i] = rnd.nextInt(totalCard);
//			}
			int n = (int)(Math.random()*Card.length);        // 0~9번 까지 랜덤 번호 얻기

		       int tmp = Card[0];
		       Card[0] = Card[n];
		       Card[n] = tmp;


		}
		
	}
	public void DivideCard(){												// 카드를 클라이언트들에게 나눠줌
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
	public void DivideCard(int i){												// 카드를 클라이언트들에게 나눠줌
			int[] temp = new int[14];
			
			for(int z = 0; z < 4; z++){
				for(int j = 0; j < 14; j++){
					temp[j] = Card[j];
				}
				user[z] = new DoubtUser();
				user[z].setCard(Card);
			}
//				for(int j = 0; j < 14; j++){
//					temp[j] = Card[j];
//				}
//				user[1] = new DoubtUser();
//				user[1].setCard(Card);
//				for(int j = 0; j < 14; j++){
//					temp[j] = Card[j];
//				}
//				user[2] = new DoubtUser();
//				user[2].setCard(Card);
//				for(int j = 0; j < 14; j++){
//					temp[j] = Card[j];
//				}
//				user[3] = new DoubtUser();
//				user[3].setCard(Card);
//			
//			user[i].setCard(temp);
			broadcast.sendToObject(user[i], i);
	}
	
	public void Start(String msg) {											// 첫번째 플레이어가 시작할 카드를 냄
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
	public void Doubt(String msg){											// 다우트 선언시 호출되는 메소드
		String player = msg.substring(4);
		for(int i = 0; i < packet.MAX_USER; i++){
			if(user[i] != null){
				if(user[i].getId() == player){
					if(CurrentCard != LastCard){								// 현재 카운트 된 카드와 이전 플레이어가 낸 카드가 같지 않을 경우(즉 다우트인 경우)
	//					broadcast.sendTo(i, packet.Doubt + player);
						NowPlayer--;
						if(NowPlayer ==0)
							NowPlayer = 4;
						this.UpdateCardDeck(i);
						broadcast.sendToOthers(broadcast.getDT(i), packet.Doubt + player + "님이 다우트에 성공하셨습니다.");
						broadcast.sendToObject(user[NowPlayer], i);
					}else{
	//					broadcast.sendTo(i, packet.FailDoubt + player);
						this.UpdateCardDeck(i);
						broadcast.sendToOthers(broadcast.getDT(i), packet.FailDoubt + player + "님이 다우트에 실패하셨습니다.");
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
	public void Drop(String msg){											// 플레이어가 덱에 가지고 있던 카드를 드랍했을때 호출되는 메소드
		String player = msg.substring(6);
		int playerDropedCard = Integer.parseInt(msg.substring(4));			// 1003??(카드인덱스 두자리)*(플레이어이름)
		
		if(user[NowPlayer].getId() == player){
			int[] userCard = null;
			userCard = user[NowPlayer].getCard();
			
			for(int i = 0; i < userCard.length; i++){
				if(userCard[i] == playerDropedCard){						// 플레이어가 가지고 있는 카드정보에서 드랍된 카드가 있는 경우
					int usercount = user[NowPlayer].getCardCount();
					dao.deleteDeckCard(player, playerDropedCard);			// db에 해당 플레이어의 카드를 삭제
					userCard[i] = 0;										// 서버에 저장된 플레이어의 카드덱에서 드랍한 카드를 0으로 변경
					user[NowPlayer].setCard(userCard);
					user[NowPlayer].setCardCount(usercount-1);
					if(usercount == 0){										// 플레이어 덱에 숫자가 0인 경우
						user[NowPlayer].setWin(user[NowPlayer].getWin()+1);
						this.EndGame();
					}
				}
			}
			LastCard = playerDropedCard;									// 드랍한 카드를 마지막 낸 카드 변수에 대입
			CurrentCard++; 
			this.UpdateCardNum();
			DeckCount++;
			DeckCard[DeckCount] = LastCard;
			
			NowPlayer++;
			if(NowPlayer == 4)
				NowPlayer = 0;
			
			broadcast.sendToOthers(broadcast.getDT(NowPlayer), packet.Drop + user[NowPlayer].getId() + "|" + String.valueOf(user[NowPlayer].getCardCount()));		// 현재 플레이어의 카드 카운트를 다른 플레이어들에게 전달
			
		}else{
			for(int i = 0; i < packet.MAX_USER; i++){
				if(user[i] != null){
					if(user[i].getId() == player)
						broadcast.sendTo(i, packet.NotYourTurn + "아직 턴이 아닙니다.");
				}
			}
		}
	}
	public void UpdateCardNum(){											// 클라이언트들에게 카드정보가 업데이트됨을 알림.
		for (int i=0; i < MAX_USER; i++){
			if (user[i].getPlaying()){
				broadcast.sendToAll(packet.CardNum + CurrentCard);
			} 
		}
	}
	public void UpdateCardDeck(int i){										// 서버가 가지고 있는 플레이어 정보와 db에 저장되어있는 플레이어 정보의 덱을 업데이트
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
	public void EndGame(){													// 게임이 끝났을 경우 호출되는 메소드
		int TurnNum = 0;
		for(int i = 0; i < packet.MAX_USER; i++){
			int UserWin = 0;
			int DbWin = 0;
			DoubtUser dbuser;
			dbuser = dao.getUser(user[i].getId());
			UserWin = user[i].getWin();										// 서버가 가지고 있는 승리 수
			DbWin = dbuser.getWin();										// db에서 가져온 유저정보의 승리 수
			String Winner = "";												// 승리한 플레이어 이름 저장 변수
			if(UserWin != DbWin){
				Winner = user[i].getId();
				user[i].setMyTurnNum(0);
				broadcast.sendTo(i ,packet.EndGame + "당신이 이겼습니다.");
			}else{
				user[i].setLose(user[i].getLose()+1);
				user[i].setMyTurnNum(TurnNum + 1);
				broadcast.sendToAll(packet.EndGame + Winner + "님이 이겼습니다.");
			}
			dao.resetDeck(user[i].getId());									// db에 있는 카드덱 정보 초기화
			DeckCard = null;
			DeckCount = 0;
			this.GameInit();												// 게임 다시 세팅
			this.DivideCard(i);												// 다시 카드패를 클라이언트에게 보냄
			broadcast.sendToAll(Winner + "님 차례입니다.");
		}
	}
}
