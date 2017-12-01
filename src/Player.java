import java.net.InetAddress;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Player {
	String name;
	int score;
	InetAddress ip;
	App app;
	GamePlay game;
	//adds
	public Player(){
		//initialize player's name and score
		this.score = 0;
	}
	
	public Player(String name){
		//initialize player's name and score
		this.name = name;
		this.score = 0;
	}
	
	// with ip field
	public Player (String name, InetAddress ip, App app) {
		this.name = name;
		this.score = 0;
		this.ip = ip;
		this.app = app;
	}

	public void updateScore(int score){
		//update score of player
		this.score = this.score + score; 
	}

	public void resetScore(){
		//reset score for new game
		this.score = 0;
	}
	
	public void add() {
		JButton p1 = new JButton(name);
		app.updatePlayersPanel(p1);
	}

	public String getName() {
		return this.name;
	}
}
