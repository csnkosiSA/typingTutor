package typingTutor;

public class FallingWord {
	private String word; // the word
	private int x; //position - width
	private int y; // postion - height
	private int maxY; //maximum height
	private boolean dropped; //flag for if user does not manage to catch word in time
	
	private int fallingSpeed; //how fast this word is
	private static int maxWait=1000;
	private static int minWait=100;

	public static WordDictionary dict;
	
	FallingWord() { //constructor with defaults
		word="computer"; // a default - not used
		x=0;
		y=0;	
		maxY=300;
		dropped=false;
		
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
	}
	
	FallingWord(String text) { 
		this();
		this.word=text;
	}
	
	FallingWord(String text,int x, int maxY) { //most commonly used constructor - sets it all.
		this(text);
		this.x=x; //only need to set x, word is at top of screen at start
		this.maxY=maxY;
		
	}
	
	public static void increaseSpeed( ) {
		minWait+=50;
		maxWait+=50;
	}
	
	public static void resetSpeed( ) {
		maxWait=1000;
		minWait=100;
	}
	

// all getters and setters must be synchronized
	public synchronized  void setY(int y) {
		if (y>maxY) {
			y=maxY;
			dropped=true; //user did not manage to catch this word
		}
		this.y=y;
	}
	
	public synchronized  void setX(int x) {
		int maxX = 800;
		if (this.getX()> maxX && y==200) {
			x= maxX;
			dropped=true; //user did not manage to catch this word
		}
		this.x=x;
	}
	
	public synchronized  void setWord(String text) {
		this.word=text;
	}

	public synchronized  String getWord() {
		return word;
	}
	
	public synchronized  int getX() {
		return x;
	}	
	
	public synchronized  int getY() {
		return y;
	}
	
	public synchronized  int getSpeed() {
		return fallingSpeed;
	}

	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}
	public synchronized void resetPos() {
		setY(0);
		setX(0);
	}

	public synchronized void resetWord() {
		resetPos();
		word=dict.getNewWord();
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait);
	}
	
	public synchronized boolean matchWord(String typedText) {
		//System.out.println("Matching against: "+text);
		return typedText.equals(this.word);
	}

	public synchronized  void drop(int inc) {
		setY(y+inc);
	}

	public synchronized void leftDrop(int inc){
		sleep(4000);
		setX(x+inc);
		setY(200);
	}
	
	public synchronized  boolean dropped() {
		return dropped;
	}

}
