SYSJ_HOME=$(shell find ./ -name 'sysj' -type d)
SYSJC=$(SYSJ_HOME)/bin/sysjc
SYSJR=$(SYSJ_HOME)/bin/sysjr

TARGET_DIR=./tmpfile
GENERATE_JAVA_FILE=$(TARGET_DIR)/source/
COMPILE_CLASS_FILE=$(TARGET_DIR)/class/

SYSJ_FILE=$(shell find ./ -name '*.sysj' -depth 1)
GEN_JAVA_FILE=$(shell find ./ -name '*.java' -depth 1)
XML_FILE=$(shell find ./ -name '*.xml' -depth 1)

ifeq ($(WINDIR),)
	S=:
else
	S=\;
endif

ifeq ($(SILENCE),false)
	override SILENCE=
else
	override SILENCE=--silence
endif

all:
	$(SYSJC) $(SILENCE) --nojavac $(SYSJ_FILE) 

run: all
	javac -classpath $(SYSJ_HOME)/lib/\*$(S). $(GEN_JAVA_FILE)
	$(SYSJR) $(XML_FILE)

clean:
	rm -f *.class *.java
	$(shell find ./org -name '*.class' -exec rm {} \;)

