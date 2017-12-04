import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
public class App {
	
	private JFrame frame;
	private JButton newGame;
	private JButton join;
	private JPanel menu;
	private Boolean isServer;
	Server server=null;
	Channel channel=null;
	GamePlay gameplay = null;
	String wordString;
	
	JFrame mainFrame;
	JButton clearBtn, blackBtn, blueBtn, greenBtn, redBtn,eraserBtn, start;
	JSlider strokeSize;
	JLabel enterWord, countdown;	
	JTextField word, enterChat;
	TextArea chatArea;
	DrawArea drawArea;
	static String playerName;
	static String portNum;
	static String ipAddr;
	JPanel canvas, controls, wordPanel, chat, cdownPanel, playersPanel;
	
	Player player;
	
	 //set values for slider
    final int SLIDE_MIN = 5;
    final int SLIDE_MAX = 50;
    final int SLIDE_INIT = 5;
    
	ChangeListener changeListener = new ChangeListener(){
		  public void stateChanged(ChangeEvent e){
			  if (e.getSource() == strokeSize){
		    	  drawArea.setStroke(strokeSize.getValue());
		      }
		  }
	  };
	  
	ActionListener actionListener = new ActionListener() {
	 
	  public void actionPerformed(ActionEvent e) {
	      if (e.getSource() == clearBtn) {
	        drawArea.clear();
	      } else if (e.getSource() == blackBtn) {
	        drawArea.black();
	      } else if (e.getSource() == blueBtn) {
	        drawArea.blue();
	      } else if (e.getSource() == greenBtn) {
	        drawArea.green();
	      } else if (e.getSource() == redBtn) {
	        drawArea.red();
	      } else if(e.getSource() == eraserBtn) {
	    	drawArea.eraser();  
	      } else if(e.getSource() == enterChat) {
//	    	  chatArea.append(playerName + ": " + enterChat.getText() + "\n");
	    	  if(server!=null){
					server.broadcast(playerName +": " + enterChat.getText());
					addChat(playerName +": " + enterChat.getText());
				}else{
					System.out.println(channel);
					channel.send(playerName + ": " + enterChat.getText());
				}
				enterChat.setText("");
//	    	  enterChat.setText("Type your guess here...");
	    	  enterChat.selectAll();
	      } else if(e.getSource() == start) {
	    	  Manual manual = new Manual();
	    	  manual.open(mainFrame);
	    	  
	      }
	    }
	  };
	  
	public App(String name, String port, String ip, Boolean isServer) throws IOException{
		String option;
		this.isServer = isServer;
		playerName = name;
		portNum = port;
		ipAddr = ip;
	}

	public void show() throws UnknownHostException{
		
		//main frame
		mainFrame = new JFrame("Drawing Drawing!");
		Container content = mainFrame.getContentPane();
		
		content.setLayout(new BorderLayout());
		drawArea = new DrawArea(InetAddress.getByName(ipAddr), !isServer);
		
		//panel for the canvas and colors
		canvas = new JPanel();
		canvas.setBackground(Color.decode("#bfd7fc"));
		canvas.setLayout(new BorderLayout());
		
		canvas.add(drawArea, BorderLayout.CENTER);
		 
	    // create controls to apply colors and call clear feature
	    controls = new JPanel();
	  
	    //Insert Commands
	    strokeSize = new JSlider(JSlider.HORIZONTAL, SLIDE_MIN, SLIDE_MAX, SLIDE_INIT);
	    strokeSize.setPreferredSize(new Dimension(100, 100));
	    strokeSize.addChangeListener(changeListener);
	    clearBtn = new JButton("Clear");
	    clearBtn.addActionListener(actionListener);
	    blackBtn = new JButton("Black");
	    blackBtn.setText("");
	    blackBtn.setBackground(Color.black);
	    blackBtn.addActionListener(actionListener);
	    blueBtn = new JButton("Blue");
	    blueBtn.setText("");
	    blueBtn.setBackground(Color.blue);
	    blueBtn.addActionListener(actionListener);
	    greenBtn = new JButton("Green");
	    greenBtn.setText("");
	    greenBtn.setBackground(Color.green);
	    greenBtn.addActionListener(actionListener);
	    redBtn = new JButton("Red");
	    redBtn.setText("");
	    redBtn.setBackground(Color.red);
	    redBtn.addActionListener(actionListener);
	    eraserBtn = new JButton("Eraser");
	    eraserBtn.addActionListener(actionListener);
	 
	    // add to controls panel
	    controls.add(clearBtn);
	    controls.add(redBtn);
	    controls.add(greenBtn); 
	    controls.add(blueBtn);
	    controls.add(blackBtn);
	    controls.add(strokeSize);
	    controls.add(eraserBtn);
	    controls.setBackground(Color.decode("#bfd7fc"));
	    
	    // add to canvas panel
	    canvas.add(controls, BorderLayout.SOUTH);
	    
	    
	    //Enter word to guess panel
	    wordPanel = new JPanel();
	    wordPanel.setBackground(Color.decode("#a9c9f9"));
	    enterWord = new JLabel("Word to be drawn:", JLabel.CENTER);
	    word = new JTextField(20);
	    word.setEditable(false);
	    
	    wordPanel.add(enterWord);
	    wordPanel.add(word);
	    
	    //Chat panel
	    chat = new JPanel();
	    enterChat = new JTextField("");
	    enterChat.addActionListener(actionListener);
	    
	    chatArea = new TextArea(29,25);
	    chatArea.setEditable(false);
	    chat.setLayout(new BoxLayout(chat, BoxLayout.PAGE_AXIS));
	    
	    chat.add(chatArea);
	    chat.add(enterChat);
	    
	    //players panel
	    playersPanel = new JPanel();
	    player = new Player(playerName, InetAddress.getByName(ipAddr), this) ;
	    playersPanel.setBackground(Color.decode("#bfd7fc"));
	    //player.add(playersPanel);
	    
	    cdownPanel = new JPanel();
	    cdownPanel.setBackground(Color.decode("#a9c9f9"));
	    cdownPanel.setLayout(new BorderLayout());
	    start = new JButton("Help");
	    start.addActionListener(actionListener);
	    countdown = new JLabel("0", JLabel.CENTER);
	    countdown.setFont (countdown.getFont ().deriveFont (28.0f));
	    cdownPanel.add(countdown, BorderLayout.CENTER);
	    cdownPanel.add(start, BorderLayout.EAST);
	    
	    //add other panels to content pane
	    content.add(canvas, BorderLayout.CENTER);
	    content.add(wordPanel, BorderLayout.SOUTH);
	    content.add(chat, BorderLayout.EAST);
	    content.add(cdownPanel, BorderLayout.NORTH);
	    content.add(playersPanel, BorderLayout.WEST);
	    
	    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		mainFrame.setSize(900,600);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(true);
		mainFrame.setVisible(true);
		
		if(isServer){
			ServerProgram serverProgram = new ServerProgram(playerName, this, player);
			try {
				serverProgram.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			ClientProgram clientProgram = new ClientProgram(playerName, ipAddr, Integer.parseInt(portNum), this, player);
			try {
				
				clientProgram.start();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	// Start Client
		}
		
	}
	
	public void addChat(String msg){
		chatArea.append(msg+"\n");
	}
	
	public void addServer(Server server){
		this.server = server;
	}
	
	public void addChannel(Channel client){
		this.channel = client;
	
	}
	
	public Channel getChannel(){
		return this.channel;
	}
	
	public void addGamePlay(GamePlay gameplay) {
		this.gameplay = gameplay;
	}
	
	public GamePlay getGamePlay() {
		return this.gameplay;
	}
	public void updatePlayersPanel(JButton b) {
		this.playersPanel.add(b);
	}
	public JPanel getPlayersPanel() {
		return this.playersPanel;
	}
}
