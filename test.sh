rm ./bin/*.class ./bin/selfish/*.class ./bin/selfish/deck/*.class ./bin/test/*/*.class

javac src/main/*.java src/main/selfish/*.java src/main/selfish/deck/*.java -d ./bin/

javac -cp .;junit-platform-console-standalone.jar --source-path ./src/main/ ./src/test/test/structural/*.java -d ./bin/
javac -cp .;junit-platform-console-standalone.jar --source-path ./src/main/ ./src/test/test/functional/*.java -d ./bin/
javac -cp .;junit-platform-console-standalone.jar --source-path ./src/main/ ./src/test/test/javadoc/*.java -d ./bin/

java -jar junit-platform-console-standalone.jar --class-path ./bin/ --scan-class-path --fail-if-no-tests
