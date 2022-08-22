package typingTutor;

import java.util.concurrent.atomic.AtomicBoolean;

//Thread to monitor the word that has been typed.
public class CatchWord extends Thread {
	String target;
	static AtomicBoolean done ; //REMOVE
	static AtomicBoolean pause; //REMOVE
	
	private static  FallingWord[] words; //list of words
	private static int noWords; //how many
	private static Score score; //user score
	
	CatchWord(String typedWord) {
		target=typedWord;
	}
	
	public static void setWords(FallingWord[] wordList) {
		words=wordList;	
		noWords = words.length;
	}
	
	public static void setScore(Score sharedScore) {
		score=sharedScore;
	}
	
	public static void setFlags(AtomicBoolean d, AtomicBoolean p) {
		done=d;
		pause=p;
	}
	
	public void run() {
		int i=0;
		int pos =0;
		int minXX = 0;
		boolean test = false;
		while (i<noWords) {		
			while(pause.get()) {};
			if (words[i].matchWord(target)) {
				if(words[i].getY() > minXX){
					pos = i;
					test = true;
				}

				//FallingWord.increaseSpeed();
				break;
			}
		   i++;
		}
		if (test){

			
			System.out.println( " score! '" + target); //for checking
			score.caughtWord(words[pos].getWord().length());	
		}
	}	
}
