This directory contains example Quagent files and the Quagent runtime
library 'quagent.jar'.

To compile a Quagent Java file:

javac -classpath .;quagent.jar <name of quagent java file>

To run a Quagent program:

java -classpath .;quagent.jar <name of main class>

Example: in order to compile and run RandomWalker.java
javac -classpath .;quagent.jar Foo.java
java -classpath .;quagent.jar Foo 
