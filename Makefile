

SRC_DIR = src
BIN_DIR = bin
JFLAGS = -g
JUNIT = ./junit-platform-console-standalone-1.8.2.jar
JC = javac
JAVA = java
CP = $(BIN_DIR):$(SRC_DIR):$(JUNIT)
.SUFFIXES: .java .class

$(BIN_DIR)/%.class: $(SRC_DIR)/%.java
	$(JC) $(JFLAGS) -d $(BIN_DIR) -cp $(CP) $(SRC_DIR)/$*.java

SRC = \
	$(SRC_DIR)/scheduler/JobStatus.java \
	$(SRC_DIR)/scheduler/JobId.java \
	$(SRC_DIR)/scheduler/Scheduler.java \
	$(SRC_DIR)/scheduler/Job.java \
	$(SRC_DIR)/scheduler/Score.java \
	$(SRC_DIR)/scheduler/User.java

# SRC_TESTS = $(SRC_DIR)/tests/SimpleTest.java
SRC_TESTS = \
	$(SRC_DIR)/tests/SimpleTest.java \
	$(SRC_DIR)/tests/SchedulerTest.java \
	$(SRC_DIR)/tests/StressTest.java

CLASSES = $(patsubst $(SRC_DIR)/%.java, $(BIN_DIR)/%.class, $(SRC))

CLASSES_TESTS = $(patsubst $(SRC_DIR)/%.java, $(BIN_DIR)/%.class, $(SRC_TESTS))

default: classes

classes: $(CLASSES)

tests: $(CLASSES_TESTS)
	$(JAVA) -jar $(JUNIT) -cp $(CP) $(foreach test, $^, --select-class $(subst .class, , $(subst /,., $(subst $(BIN_DIR)/, , $(test)))))

clean:
	$(RM) -R $(BIN_DIR)



