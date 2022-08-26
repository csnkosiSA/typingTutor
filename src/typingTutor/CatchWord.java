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
	private static FallingWord[] ree ;
	
	CatchWord(String typedWord) {
		target=typedWord;
	}
	
	public static void setWords(FallingWord[] wordList, FallingWord[] re) {
		words=wordList;	
		ree  = re;
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
			if (words[i].matchWord(target) || (ree[i].matchWord(target))) {
				if (words[i].matchWord(target)){

					if(words[i].getY() > minXX  ){
						pos = i;
						test = true;
						test1=false;

						minXX=words[i].getY();

					}
				}
				if (ree[i].matchWord(target)){
					System.out.println(" reee"+ ree[0].getWord());
					System.out.println(target);
					if (ree[i].getY() > minXX  ){
					
						pos = i;
						minXX=ree[i].getY();
						test1=true;
						test=false;
					
					
				}
					
				// else if (ree[0].matchWord(target) && ree[0].getY() > minXX){
				// 	minXX=ree[0].getY();
				// 	pos=0;
				// 	test1=true;
				// 	test=false;
				// }
			}
				//FallingWord.increaseSpeed();
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
			score.caughtWord(ree[0].getWord().length());
			ree[0].resetWord();	
		}
	}	
}
