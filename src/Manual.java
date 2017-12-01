/*
 * Manual/Help page of the game
 * AUTHORS:
 * 	Gonzales, Ma. Pauline
 * 	Sanchez, Mark Stephen
 * 	Sta. Ana, Yves Robert
 * 	Vaethbrueckner, Jan-Josel
 * References: 
 * Description: Uses CardLayout manager
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Manual {
	
	JFrame frame;
	JButton next, prev;
	JPanel panel, prevNext, p1, p2, p3, p4;
	CardLayout help;
	
	ActionListener actionListener = new ActionListener() {
		 
		  public void actionPerformed(ActionEvent e) {
			  //proceed to next card or panel
			  if(e.getSource() == next) {
				  help.next(panel);
			  } 
			  //goes back to previous panel
			  else if(e.getSource() == prev) {
				  help.previous(panel);
			  }
		  }
	};
	
	public Manual() {
		super();
	}
	
	public void open(JFrame mainFrame) {
		
		frame = new JFrame("Help");
		
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		
		next = new JButton("Next");
		prev = new JButton("Prev");
		next.setBackground(Color.decode("#ffffff"));
		prev.setBackground(Color.decode("#ffffff"));
		
		next.addActionListener(actionListener);
		prev.addActionListener(actionListener);
		
		panel = new JPanel();
		help = new CardLayout();
		panel.setLayout(help);
		
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		
		//Main rules of the game
		String text ="<html><br><br><p>Welcome to Drawing Drawing!<br>"
				+ "The main goal of the game is to guess<br>"
				+ "the word being drawn on the drawing board.<br>"
				+ "The faster you guess the word, the higher the points you get.<br><br><br>"
				+ "A maximum of seven players are allowed to be in the game.<br>"
				+ "The game will proceed if seven players joined. <br><br><br>"
				+ "Each player will start with 0 points, and the the play order<br>"
				+ " will be randomly selected.<br>"
				+ "Each player has the chance to sketched. The word to be drawn is randomly<br>"
				+ "selected from a pool of words and is given a time limit of 40 seconds.<br>"
				+ "The other players must guess the word before the time ends.<br>"
				+ "Each correct guess will also reward points to the Skribbl-er. <br>"
				+ "The current round will end if all the players answered correctly or if the <br>"
				+ "time allotted lapsed.<br></html>";
	
		//Objectives and End Game
		String obj = "<html><br><br><h1>Objective</h1>"
				+ "		<p>The objective of the game is to earn the highest score among<br>" + 
				"		 the other players before the game ends.<br><br>"
				+ "The game will end after an inputted number of rounds by the host. <br>"
				+ "Each round will end after all the players got their turn to draw. <br>"
				+ "The player with the highest points after all of the rounds will win <br>"
				+ "the game" + 
				"</html>";
		
		//Types of players
		String player = "<html><h1>Players:</h1><br>"
				+ "PLAYER - the one who will guess the word being sketched.<br>"
				+ "<p>1. The players are capable of using the chat for guessing the word set <br>"
				+ "by the Skribbl-er, and also for chatting with other users. After <br>"
				+ "guessing the word correctly, the player can only chat with the Skribbl-er <br>"
				+ "and other players who have guessed the word being sketched until the turn ends.<br>"
				+ "2. The players are capable of viewing the clue bar which shows the blanks for each<br>"
				+ "letter of the word to be guessed and reveals random letters (up to two letters)<br>"
				+ "of the word before the end of the turn. <br>"
				+ "3. The players can view the current state of the sketch being done by the Skribbl-er<br>"
				+ "on the canvas.<br><br></p>"
				+ "SKRIBBL-ER  - one of the players; every player has the chance to be the Skribbl-er;<br>"
				+ "and, will provide the word to be guessed by the other players as well as the one <br>"
				+ "doing the sketch." + 
				"</html>";
		
		//Point system
		String pointSys = "<html><br><br><h1>Point System</html>";
		
		JLabel label1 = new JLabel(text);
		JLabel label2 = new JLabel(obj);
		JLabel label3 = new JLabel(player);
		JLabel label4 = new JLabel(pointSys);
		
		p1.add(label1);
		p2.add(label2);
		p3.add(label3);
		p4.add(label4);
		
		//add the panels to main panel with CardLayout layout manager
		panel.add("a", p1);
		panel.add("b", p2);
		panel.add("c", p3);
		panel.add("d", p4);
		
		p1.setBackground(Color.decode("#e0eaf9"));
		p2.setBackground(Color.decode("#e0eaf9"));
		p3.setBackground(Color.decode("#e0eaf9"));
		p4.setBackground(Color.decode("#e0eaf9"));
		
		prevNext = new JPanel();
		prevNext.setLayout(new BorderLayout());
		prevNext.add(next, BorderLayout.EAST);
		prevNext.add(prev, BorderLayout.WEST);
		prevNext.setBackground(Color.decode("#79a7ea"));
		
		content.add(prevNext, BorderLayout.NORTH);
		content.add(panel, BorderLayout.CENTER);
				
		frame.setSize(500,500);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
}
