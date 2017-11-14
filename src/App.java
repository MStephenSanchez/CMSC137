import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class App {
	
	private JFrame frame;
	private JButton newGame;
	private JButton join;
	private JPanel menu;
	

	public App() throws IOException{
		String option;
		frame = new JFrame("Drawing drawing");
		menu = new JPanel();
		newGame = new JButton("New Server");
		join = new JButton("Join Server");
		
		newGame.addActionListener(startServer());
		join.addActionListener(joinServer());
		
		menu.add(newGame);
		menu.add(join);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setPreferredSize(new Dimension(150,100));
		frame.setResizable(false);
		
		frame.getContentPane().add(menu);
		
		frame.pack();
		frame.setVisible(true);
		
	}
	
	private ActionListener startServer(){
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ServerProgram program = new ServerProgram();
				frame.setVisible(false);
				frame.dispose();
				try {
					program.start();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	// Start server
			}
        };
        return action;
	}
	
	private ActionListener joinServer(){
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientProgram program = new ClientProgram();
				frame.setVisible(false);
				frame.dispose();
				try {
					program.start();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	// Start Client
			}
        };
        return action;
	}

}
