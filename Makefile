SYSJ_HOME=$(shell find ./ -name 'sysj' -type d)
SYSJC=$(SYSJ_HOME)/bin/sysjc
SYSJR=$(SYSJ_HOME)/bin/sysjr

TARGET_DIR=./tmpfile

SOURCE_FILE_PATH=./source_file
EXT_JAVA_FILE_PATH=src

ifeq ($(SYSJ),)
#SYSJ_FILE=$(shell find $(SOURCE_FILE_PATH) -name '*.sysj' -depth 1)
#XML_FILE=$(shell find $(SOURCE_FILE_PATH) -name '*.xml' -depth 1)
SYSJ_FILE=$(SOURCE_FILE_PATH)/*.sysj
XML_FILE=$(SOURCE_FILE_PATH)/*.xml
else
SYSJ_FILE=$(SOURCE_FILE_PATH)/$(SYSJ).sysj
XML_FILE=$(SOURCE_FILE_PATH)/$(SYSJ).xml
endif

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

EXT_JAVA_FILE=$(shell find $(SOURCE_FILE_PATH)/$(EXT_JAVA_FILE_PATH) -name '*.java')

ifeq ($(JAVA_FILE), )
JAVA_FILE_PREFIX=$(shell cat $(XML_FILE) | grep ClockDomain | grep Class | cut -d ' ' -f3 | grep -oE '[^\"]'+ | grep -v Class | grep -v '>')
JAVA_FILE=$(JAVA_FILE_PREFIX).java
endif

all:
	$(SYSJC) $(SILENCE) -d $(TARGET_DIR) --nojavac $(SYSJ_FILE)

run:
	javac -classpath $(SYSJ_HOME)/lib/*$(S)$(TARGET_DIR)/* -d $(TARGET_DIR) $(EXT_JAVA_FILE)
	javac -classpath $(SYSJ_HOME)/lib/*$(S)$(TARGET_DIR)/*$(S)$(TARGET_DIR)/ -d $(TARGET_DIR) ./tmpfile/$(JAVA_FILE)
	$(SYSJR) $(XML_FILE)

clean:
	$(shell find ./$(TARGET_DIR) -name '*.class' -exec rm {} \;)
	$(shell find ./$(TARGET_DIR) -name '*.java' -exec rm {} \;)

