SRC_DIR = bn 
OUT_DIR = bin

JC = javac
JCR = java
JCFLAGS = -d $(OUT_DIR)

CLASSES = \
		MenuUI.java \
		Game.java \
		GameUI.java \
		Game.java \
		MainClass.java

default: build
		
build: 
	$(JC) $(JCFLAGS) $(CLASSES)

run:
	$(JCR) -cp ./bin MainClass


clean:
	$(RM) -r ./bin