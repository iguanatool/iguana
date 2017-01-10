#IGUANA

## Requirements
IGUANA requires the Maven build automation tool, and JDK 7 (or more recent) installed.

## Installation
To install IGUANA you need to check out the repository, and run the following command to produce a JAR file (or run the equivalent command in your IDE):

`mvn package`.

You'll then need to set the following environment variables. 

* The `IGUANA_HOME` environment variable needs to be set to the root directory of this repository, where it is checked out on your system.
* You need to update your `CLASSPATH` environment variable to the JAR file (that includes dependent libraries) produced by Maven. Typically this lives in the `target` directory and is called `iguanatool-1.0-jar-with-dependencies.jar`. So in Bash, you will need to use the command:

`export CLASSPATH="$IGUANA_HOME/target/iguanatool-1.0-jar-with-dependencies.jar":$CLASSPATH`

## Compiling Case Studies
