import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePlay {
	ArrayList<Player> players = new ArrayList<>();
	
	
	public void addPlayer(Player player1) {
		// TODO Auto-generated method stub
		Player player = player1;
		players.add(player);
		
	}
	
	public void playerList(JPanel panel) {
		for(Player player : players) {
			JButton p1 = new JButton(player.getName());
			panel.add(p1);
		}
		
	}

}
