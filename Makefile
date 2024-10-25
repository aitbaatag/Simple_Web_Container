# Variables
JAVAC = javac                     # Java compiler command
SRC_DIR = src/main/java            # Directory where your .java files are
BIN_DIR = bin                      # Directory where compiled .class files go
MAIN_CLASS = org.web.container.Main  # Fully qualified class with your main() function

# Source files
SRC_WEB = $(wildcard $(SRC_DIR)/org/web/container/*.java)
SRC_SER = $(wildcard $(SRC_DIR)/servlet/*.java)
SOURCES = $(SRC_WEB) $(SRC_SER)

# Corresponding .class files in bin/ directory
CLASSES	= $(patsubst $(SRC_DIR)/%.java, $(BIN_DIR)/%.class, $(SOURCES))

# Compile everything
all: $(CLASSES)

# Rule to compile .java files into .class files
$(BIN_DIR)/%.class: $(SRC_DIR)/%.java
	@mkdir -p $(dir $@)         # Create bin/ directory structure
	$(JAVAC) -d $(BIN_DIR) $<   # Compile .java files to .class files in bin/

# Run the program
run: all
	@java -cp $(BIN_DIR) $(MAIN_CLASS)

# Clean up the compiled .class files
clean:
	@rm -rf $(BIN_DIR)
