package Shared;

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("serial")
public class DoubtUser implements Serializable, Cloneable {
	private String id;
	private String pass;
	private int win;
	private int lose;
	private String Cardback;
	private int Card[] ;
	private int CardCount = 14;
	private Boolean Playing = true;
	private Boolean Ready = false;
	private int MyTurnNum = 0;
	public DoubtUser() {
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getWin() {
		return win;
	}
	public void setWin(int win) {
		this.win = win;
	}
	public int getLose() {
		return lose;
	}
	public void setLose(int lose) {
		this.lose = lose;
	}
	public String getCardback() {
		return Cardback;
	}
	public void setCardback(String cardback) {
		Cardback = cardback;
	}
	public int[] getCard() {
		return Card;
	}
	public void setCard(int[] card) {
		Card = card;
	}
	public Boolean getPlaying() {
		return Playing;
	}
	public void setPlaying(Boolean Playing) {
		this.Playing = Playing;
	}
	public int getCardCount() {
		return CardCount;
	}
	public void setCardCount(int cardCount) {
		CardCount = cardCount;
	}
	public Boolean getReady() {
		return Ready;
	}
	public void setReady(Boolean ready) {
		Ready = ready;
	}
	public int getMyTurnNum() {
		return MyTurnNum;
	}
	public void setMyTurnNum(int myTurnNum) {
		MyTurnNum = myTurnNum;
	}
	@Override
	public String toString() {
		return "DoubtUser [id=" + id + ", pass=" + pass + ", win=" + win + ", lose=" + lose + ", Cardback=" + Cardback
				+ ", Card=" + Arrays.toString(Card) + ", CardCount=" + CardCount + ", Playing=" + Playing + ", Ready="
				+ Ready + ", MyTurnNum=" + MyTurnNum + "]";
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		DoubtUser imsi = (DoubtUser)super.clone();
		imsi.id = this.id;
		imsi.pass = this.pass;
		imsi.win = this.win;
		imsi.lose = this.lose;
		imsi.Cardback = this.Cardback;
		imsi.Card = this.Card;
		imsi.CardCount = this.CardCount;
		imsi.Ready = this.Ready;
		imsi.MyTurnNum = this.MyTurnNum;
		imsi.Playing = this.Playing;
		return imsi;
	}
}
