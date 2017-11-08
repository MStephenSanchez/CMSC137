import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Chat {
	private Server server=null;
	private Channel channel=null;
	private JFrame chat_frame;
	private JPanel messagePanel;
	private JPanel inputPanel;
	private String name;
	
	private JTextArea messagesBox;
	private JTextArea chatBox;
	private JButton sendButton;
	

	public Chat(Server server, String name){
		this.name = name;
		this.server = server;
		createChat();
	}
	public Chat(Channel channel, String name){
		this.name = name;
		this.channel = channel;
		createChat();
	}
	
	private void createChat(){
		chat_frame = new JFrame("Drawing drawing");
		messagePanel = new JPanel();
		inputPanel = new JPanel();
		
		messagesBox = new JTextArea();
		chatBox = new JTextArea();
		sendButton = new JButton("Send");
		
		messagesBox.setPreferredSize(new Dimension(330,500));
		chatBox.setPreferredSize(new Dimension(270,50));
		sendButton.setPreferredSize(new Dimension(50,50));
		
		sendButton.addActionListener(send());
		
		messagesBox.setEditable(false);
		
		messagePanel.add(messagesBox);
		inputPanel.add(chatBox,BorderLayout.EAST);
		inputPanel.add(sendButton,BorderLayout.WEST);

		chat_frame.setPreferredSize(new Dimension(350,600));
		chat_frame.setResizable(false);

		chat_frame.add(messagePanel, BorderLayout.NORTH);
		chat_frame.add(inputPanel, BorderLayout.SOUTH);
		
		chat_frame.pack();
		chat_frame.setVisible(true);
	}
	
	public void show(String msg){
		messagesBox.append(msg+"\n");
	}
	
	public boolean isRunning(){
		return chat_frame.isVisible();
	}
	
	private ActionListener send(){
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(server!=null){
					server.broadcast(name +">> " + chatBox.getText());
					show(name +">> " + chatBox.getText());
				}else{
					channel.send(name + " >> " + chatBox.getText());
				}
				chatBox.setText("");
			}
        };
        return action;
	}
}
