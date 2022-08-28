package typingTutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class WordMover extends Thread {
	private FallingWord myWord;
	private FallingWord hungryWord;
	private AtomicBoolean done;
	private AtomicBoolean pause; 
	private Score score;
	CountDownLatch startLatch; //so all can start at once
	
	WordMover( FallingWord word) {
		myWord = word;
	}
	
	WordMover( FallingWord word,FallingWord hungryWord,WordDictionary dict, Score score,
			CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p) {
		this(word);
	    this.hungryWord = hungryWord;
		this.startLatch = startLatch;
		this.score=score;
		this.done=d;
		this.pause=p;
	}
	
	
	
	public void run() {

		try {
			System.out.println(myWord.getWord() + " waiting to start " );
			startLatch.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //wait for other threads to start
		System.out.println(myWord.getWord() + " started" );
		int first=0;
		int second=0;

		while (!done.get()) {

			//animate the word
			synchronized(this){
			while (!myWord.dropped() && !done.get()) {
				boolean guard = false;
                if (hungryWord.getX()>=myWord.getX()){
					first=hungryWord.getX();
					second=myWord.getX(); 
				 }
				 else{
				   	second = hungryWord.getX();
					first = myWord.getX();
				 }
				 	
					 if (myWord.getY()>=180 && myWord.getY()<=210 && ((1000-second)-(1000-first)<=26)){ 
						   
				           myWord.resetWord();
						   score.missedWord();
						   guard = true;
						}

				    myWord.drop(10);

					if (!guard  && myWord.getY()>=180 && myWord.getY()<=210 && ((1000-second)-(1000-first)<=26)){ 
					    
							myWord.resetWord();
							score.missedWord();
					}

					try {
						sleep(myWord.getSpeed());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};		
					while(pause.get() && !done.get()) {};
			}
			if (!done.get() && myWord.dropped()) {
				score.missedWord();
				myWord.resetWord();
			}
		}
			myWord.resetWord();
		}
	}
	
}
