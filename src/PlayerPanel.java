
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class PlayerPanel {
	
	public static void main(String args[]) {
		JFrame mainFrame = new JFrame("Game UI");
	    mainFrame.setSize(400,400);
	    mainFrame.setLayout(new GridLayout(2, 3));
	      
	    mainFrame.addWindowListener(new WindowAdapter() {
	       	public void windowClosing(WindowEvent windowEvent){
	       	System.exit(0);
	        }        
	    });   
	    
	    //test data
	    Player p1 = new Player("Jan");
	    Player p2 = new Player("Stephen");
	    Player p3 = new Player("Paulene");
	    Player p4 = new Player("Yves");
	    p2.updateScore(3434);
	    p4.updateScore(1922);
	    p1.updateScore(9999);
	    p1.resetScore();

	    //create list of players on server
	    ArrayList<Player> players = new ArrayList<Player>();
	    //add each player to the list
	    players.add(p1);
	    players.add(p2);
	    players.add(p3);
	    players.add(p4);

	    //create list model
	    DefaultListModel<String> listModel = new DefaultListModel<String>();
		JList<String> myJList = new JList<String>();

		//add each player's info to the model
		for (Player p : players) {
			System.out.println(p.name + " - " + p.score);
	  		listModel.addElement(p.name + " - " + p.score);
		}
		
		myJList.setModel(listModel);
		JScrollPane listScrollPane = new JScrollPane(myJList);

	    mainFrame.add(listScrollPane);
	    mainFrame.setVisible(true); 
    } 
	
}
