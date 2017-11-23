public interface OnSocketListener
{
	void onConnected(Channel channel);

	void onDisconnected(Channel channel);
	
	void onReceived(Channel channel, String msg);
	
	void onWord(Channel channel, String word);
}
