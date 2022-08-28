package typingTutor;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.text.html.StyleSheet;

//Thread to monitor the word that has been typed.
public class CatchWord extends Thread {
	String target;
	static AtomicBoolean done ; //REMOVE
	static AtomicBoolean pause; //REMOVE
	
	private static  FallingWord[] words; //list of words
	private static int noWords; //how many
	private static Score score; //user score
	private static FallingWord[] hungryWord ;
	
	CatchWord(String typedWord) {
		target=typedWord;
	}
	
	public static void setWords(FallingWord[] wordList, FallingWord[] re) {
		words=wordList;	
		hungryWord  = re;
		noWords = words.length;
	}
	
	public static void setScore(Score sharedScore) {
		score=sharedScore;
	}
				
	public static void setFlags(AtomicBoolean d, AtomicBoolean p) {
		done=d;
		pause=p;
	}
	synchronized public void run() {
		
		int i=0;
		int pos =0;
		int minXX = 0;
		boolean test = false;
		boolean test1 = false;
		
		while (i<noWords) {		
			
			while(pause.get()) {};
			
			if (words[i].matchWord(target) || (hungryWord[i].matchWord(target))) {
				if (words[i].matchWord(target)){

					if(words[i].getY() > minXX  ){
						pos = i;
						test = true;
						test1=false;						
						minXX=words[i].getY();
					}
				}
				if (hungryWord[i].matchWord(target)){

					if (hungryWord[i].getY() > minXX  ){
						pos = i;
						minXX=hungryWord[i].getY();
						test1=true;
						test=false;					
					}	
				}

			}
			
		   	i++;
		}
		if (test){

			
			System.out.println( " score! '" + target); //for checking
			score.caughtWord(words[pos].getWord().length());
			words[pos].resetWord();	
		}
		else  if(test1){
			System.out.println( " score! '" + target); //for checking
			score.caughtWord(hungryWord[0].getWord().length());
			hungryWord[0].resetWord();	
		}
	}	
}
