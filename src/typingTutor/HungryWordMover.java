package typingTutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
public class HungryWordMover extends Thread{
    private FallingWord myWord;
    private FallingWord word1;
	private AtomicBoolean done;
	private AtomicBoolean pause; 
	private Score score;
	CountDownLatch startLatch; //so all can start at once
	
	HungryWordMover( FallingWord word) {
		myWord = word;
	}
	
	HungryWordMover( FallingWord word,FallingWord word1,WordDictionary dict, Score score,
			CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p) {
		this(word);
		this.startLatch = startLatch;
		this.score=score;
		this.done=d;
		this.pause=p;
      	this.word1=word1;
	}
	
	

	@Override
	public void run() {


		//System.out.println(myWord.getWord() + " falling speed = " + myWord.getSpeed());
		try {
			System.out.println(myWord.getWord() + " waiting to start " );
			startLatch.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //wait for other threads to start
		System.out.println(myWord.getWord() + " started" );
      int one=0,two=0;

	  	while (!done.get()) {


			//animate the word
			while (!myWord.dropped() && !done.get()) {
				if (word1.getX()>myWord.getX()){
                   one=word1.getX();
                   two=myWord.getX();
                }
                else{
                  two=word1.getX();
				  one=myWord.getX();
                }

				if (word1.getY()>180 && word1.getY()<210 && ((1000-two)-(1000-one)<=26)){

					score.missedWord();
					word1.resetWord();
				}
				myWord.leftDrop(5);
				if (word1.getY()>180 && word1.getY()<210 && ((1000-two)-(1000-one)<=26)){
					score.missedWord();
					word1.resetWord();}

				try {
					sleep(myWord.getSpeed());
				} catch (InterruptedException e) {

					e.printStackTrace();
				};
				while(pause.get()&&!done.get()) {};
			}
			if (!done.get() && myWord.dropped()) {
				score.missedWord();
				myWord.resetWord();
			}
			myWord.resetWord();
		}
    }
}