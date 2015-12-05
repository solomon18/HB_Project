package dbpkg;
public class MemberDTO {

    private String id;
    private String pw;
    private String cardback;
    private int winnum;
    private int losenum;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getCardback() {
		return cardback;
	}
	public void setCardback(String cardback) {
		this.cardback = cardback;
	}
	public int getWinnum() {
		return winnum;
	}
	public void setWinnum(int winnum) {
		this.winnum = winnum;
	}
	public int getLosenum() {
		return losenum;
	}
	public void setLosenum(int losenum) {
		this.losenum = losenum;
	}
	
	@Override
	public String toString() {
		return "MemberDTO [id=" + id + ", pw=" + pw + ", cardback=" + cardback
				+ ", winnum=" + winnum + ", losenum=" + losenum + "]";
	}

}
