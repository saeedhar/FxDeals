# Define variables
MAIN_CLASS := com.clustered.data.warehouse.FxDealsApplication
BUILD_DIR := target
JAR_NAME := clustered.data.warehouse-0.0.1-SNAPSHOT.jar

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


