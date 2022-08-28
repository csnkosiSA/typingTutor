JAVAC=/usr/bin/javac
.SUFFEXES: /java .class
SRCDIR=src/typingTutor
BINDIR=bin

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES=WordDictionary.class Score.class ScoreUpdater.class\
		FallingWord.class CatchWord.class WordMover.class\
		HungryWordMover.class GamePanel.class\
		TypingTutorApp.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)
clean:
	rm $(BINDIR)/typingTutor/*.class
run:	$(CLASS_FILES)
	java -cp bin typingTutor.TypingTutorApp


