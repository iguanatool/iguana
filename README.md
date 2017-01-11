#IGUANA

## Requirements
IGUANA requires the Maven build automation tool, and JDK 7 (or more recent) installed.

## Installation
To install IGUANA you need to check out the repository, and run the following command to produce a JAR file (or run the equivalent command in your IDE):

`mvn package`

You'll then need to set the following environment variables. 

* The `IGUANA_HOME` environment variable needs to be set to the root directory of this repository, where it is checked out on your system.
* The `JAVA_HOME` environment variable needs to be set to the root directory of your JDK installation.
* You need to update your `CLASSPATH` environment variable to the JAR file (that includes dependent libraries) produced by Maven. Typically this lives in the `target` directory and is called `iguanatool-1.0-jar-with-dependencies.jar`. So in Bash, you will need to use the command:

`export CLASSPATH="$IGUANA_HOME/target/iguanatool-1.0-jar-with-dependencies.jar":$CLASSPATH`

## Compiling Case Studies
You will need to create a directory for your case studies (test objects/subjects). The easiest way to do this with the required structure is to clone the `casestudies` repository (https://github.com/iguanatool/casestudies).

To install a new case study you will need to perform the following steps:

1. Create a new directory for your case study in the `c` subdirectory of your casestudies directory, e.g. `calendar` and place the code (functions you want to test) in there in a file called `calendar.c`
2. Perform step one assimilation, by running the following command
`java org.iguanatool.Assimilate calendar`
(replace `calendar` with your own case study name)
3. Edit the input specification Java class code for each function to be tested that has been produced in the `casestudies/java/calendar/inputspecifications` directory
4. Each case study becomes a part of the IGUANA code, so you'll now need to compile IGUANA again, using `mvn package`
5. Complete the call code for each C file for each function to be tested in the `casestudies/c/calendar/call` directory
6. Perform step two assimilation, by again running `java org.iguanatool.Assimilate calendar`

Your case study should be ready to go.

## Running Test Data Generation
Running test data generation is then as simple as a command such as

`java org.iguanatool.Run calendar nhc`

where `calendar` is the case study and `nhc` is the search. (Search names are derived from the method names in the org.iguanatool.search.SearchFactory class.)

Further configuration options can be set through the `iguana.config` file in IGUANA's root directory. Each of these can be overridden at the command line by preceeding the option with a dash. For example, to set the seed from the command line, use

`java org.iguanatool.Run calendar nhc -seed=100`
