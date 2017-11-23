import java.net.InetAddress;

public class Player {
	String name;
	int score;
	InetAddress ip;

	public Player(String name){
		//initialize player's name and score
		this.name = name;
		this.score = 0;
	}
	
	/* with ip field
	public Player (String name, InetAddress ip) {
		this.name = name;
		this.score = 0;
		this.ip = ip;
	}
	*/
	public void updateScore(int score){
		//update score of player
		this.score = this.score + score; 
	}

	public void resetScore(){
		//reset score for new game
		this.score = 0;
	}
}
