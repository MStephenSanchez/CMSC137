import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Word{
	
	private String word;
	private ArrayList<String> words = new ArrayList<String>();
	
	public Word(){
		this.word = this.getWord();
	} 
	
	public String getWord(){
		//add some words, maybe better to read from a file?
		this.words.add("Apple");
		this.words.add("Banana");
		this.words.add("Fish");
		this.words.add("Car");
		this.words.add("Cellphone");
		this.words.add("Computer");
		this.words.add("Pencil");
		this.words.add("Horse");
		this.words.add("Dog");
		
		String randomWord = this.words.get(new Random().nextInt(this.words.size()));
		System.out.println("Word to be drawn: " + randomWord);
		return randomWord;
	}
	
	public String retWord() {
		return this.word;
	}
	
	
	public void revealLetter(int timeInMs) throws InterruptedException{
		int length = this.word.length();
		
		//initialize hidden word
		char[] charArray = new char[length];
		Arrays.fill(charArray, '*');
		String wordReveal = new String(charArray);
		System.out.println(wordReveal);
		
		ArrayList<Integer> availableIndex = new ArrayList<Integer>();
		
		for(int i=0; i<length; i++) {
			availableIndex.add(i);
			//System.out.println(availableIndex.get(i));
		}
		
		while(!availableIndex.isEmpty()) {
			//Pause for timeInMs/1000 seconds
	        Thread.sleep(timeInMs);
			
			int index = new Random().nextInt(availableIndex.size());
			//System.out.println("Index: " + availableIndex.get(index));
			
			//reveal letter at that index
			char[] wordRevealChars = wordReveal.toCharArray();
			wordRevealChars[availableIndex.get(index)] = word.charAt(availableIndex.get(index));
			wordReveal = String.valueOf(wordRevealChars);
				
			//remove index from array
			availableIndex.remove(index);
			//System.out.println("Size: " + availableIndex.size());
			
			System.out.println(wordReveal);
		}
	}
	
	//public static void main(String args[]) throws InterruptedException{
	//	Word w = new Word();
	//	w.revealLetter(10000);
	//}
	
}
