package typingTutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class HungryWordMover extends Thread {

	private FallingWord myWord;
	private FallingWord hungryWord;
	private AtomicBoolean done;
	private AtomicBoolean pause;
	private Score score;
	CountDownLatch startLatch; // so all can start at once

	HungryWordMover(FallingWord word) {
		myWord = word;
	}

	HungryWordMover(FallingWord word, FallingWord word1, WordDictionary dict, Score score,
			CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p) {
		this(word);
		this.hungryWord = word1;
		this.startLatch = startLatch;
		this.score = score;
		this.done = d;
		this.pause = p;
	}

	@Override
	public void run() {

		try {
			System.out.println(myWord.getWord() + " waiting to start ");
			startLatch.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // wait for other threads to start
		System.out.println(myWord.getWord() + " started");
		int first = 0, second = 0;
		while (!done.get()) {
			// animate the word

			while (!myWord.dropped() && !done.get()) {

				synchronized (this) {

					if (hungryWord.getX() >= myWord.getX()) {
						first = hungryWord.getX();
						second = myWord.getX();

					} else {
						second = hungryWord.getX();
						first = myWord.getX();
					}
					boolean gard = false;
					if (hungryWord.getY() >= 180 && hungryWord.getY() <= 210
							&& ((1000 - second) - (1000 - first) <= 30)) {

						hungryWord.resetWord();
						score.missedWord();
						gard = true;
					}
					myWord.leftDrop(10);
					;
					if (!gard && hungryWord.getY() >= 180 && hungryWord.getY() <= 210
							&& ((1000 - second) - (1000 - first) <= 30)) {

						score.missedWord();
						hungryWord.resetWord();

					}

					try {
						sleep(myWord.getSpeed());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					while (pause.get() && !done.get()) {
					}
					;
				}
				if (!done.get() && myWord.dropped()) {
					score.missedWord();
					myWord.resetWord();
				}

				myWord.resetWord();
			}
		}

	}
}