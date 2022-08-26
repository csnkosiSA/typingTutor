package typingTutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class WordMover extends Thread {
	private FallingWord myWord;
   private FallingWord word1;
	private AtomicBoolean done;
	private AtomicBoolean pause; 
	private Score score;
	CountDownLatch startLatch; //so all can start at once
	
	WordMover( FallingWord word) {
		myWord = word;
	}
	
	WordMover( FallingWord word,FallingWord word1,WordDictionary dict, Score score,
			CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p) {
		this(word);
		this.startLatch = startLatch;
		this.score=score;
		this.done=d;
		this.pause=p;
      this.word1=word1;
	}
	
	
	
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
                  System.out.println("word " +myWord.getWord()+" green "+(1000-two)+" nom: "+(1000-one));
				    if (myWord.getY()>180 && myWord.getY()<210 && ((1000-two)-(1000-one)<=26)){ 
               
                
//                   System.out.println("word " +myWord.getWord()+" green "+word1.getY()+" nom: "+myWord.getY());

						score.missedWord();
				      myWord.resetWord();}
                // else if (word1.getX()>myWord.getX()){
// 				   if (myWord.getY()>180 && myWord.getY()<210 && word1.getX()-80>=myWord.getX()){ 
//                 int p=Integer.valueOf(word1.getX());
//                  System.out.println("word " +myWord.getWord()+" green "+p+" nom: "+(myWord.getX()-70));
//                   System.out.println("word " +myWord.getWord()+" green "+word1.getY()+" nom: "+myWord.getY());
// 
//                                 //   score.missedWord();
// 				      myWord.resetWord();}}
// 
                myWord.drop(5);
                       
				    if (myWord.getY()>180 && myWord.getY()<210 && ((1000-two)-(1000-one)<=26)){ 
               
                
//                   System.out.println("word " +myWord.getWord()+" green "+word1.getY()+" nom: "+myWord.getY());

                                 score.missedWord();
				      myWord.resetWord();}

               // //  if (myWord.getY()>180 && myWord.getY()<210 &&  word1.getX()>=myWord.getX()-75 
//                  
//               ){
//         // score.missedWord();
//         System.out.println("green "+word1.getX()+" nom: "+myWord.getX());
// 				      myWord.resetWord();}

					try {
						sleep(myWord.getSpeed());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
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
