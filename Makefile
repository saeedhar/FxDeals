# Define variables
MAIN_CLASS := com.clustered.data.warehouse.ClusteredDataApplication
BUILD_DIR := target
JAR_NAME := clustered-data.jar

# Define targets
.PHONY: all clean build test run

all: clean build test run

clean:
	rm -rf $(BUILD_DIR)

build:
	mkdir -p $(BUILD_DIR)
	./mvnw package -DskipTests=true

test: build
	./mvnw test

run: build
	java -jar $(BUILD_DIR)/$(JAR_NAME)