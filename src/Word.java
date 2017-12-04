import java.io.Serializable;

public class Word implements Serializable {
	private final String wordObj;
	private final int score;
	public Word(String word, int score){
		this.wordObj = word;
		this.score = score;
	}
	
	public String getWord(){
		return this.wordObj;
	}
	public int getScore(){
		return this.score;
	}
}
