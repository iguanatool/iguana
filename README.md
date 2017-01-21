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
You will need to create a directory for your case studies (i.e., your test objects or experimental subjects). The easiest way to do this with the required structure is to clone the `casestudies` repository (https://github.com/iguanatool/casestudies).

To install a new case study you will need to perform the following steps:

1. Create a new directory for your case study in the `c` subdirectory of your casestudies directory, e.g. `calendar` and place the code (functions you want to test) in there in a file called `calendar.c`
2. Perform step one assimilation, by running the following command
`java org.iguanatool.Assimilate calendar`
(replace `calendar` with your own case study name)
3. Edit the input specification Java code for the `.java` class generated for each C function to be tested. The source code for each class resides in the `casestudies/java/calendar/inputspecifications` directory (see the next section if you need help with this).
4. Each case study becomes a part of the IGUANA code, so you'll now need to compile IGUANA again, using `mvn package`
5. Complete the call code for each C file for each function to be tested in the `casestudies/c/calendar/call` directory (see the next section if you need help with this).
6. Perform step two assimilation, by again running `java org.iguanatool.Assimilate calendar`

Your case study should be ready to go.

## Setting up the input specification Java code and the C code in the test object's `call` directory
At its core, IGUANA optimizes a fixed vector of double variables. Code must be written to specify the vector of double variables to be optimized for each test object (each function under test) concerned. This is the Java code that lives in the `inputspecifications` package for your case study. Each test object will have its own Java class generated as part of step one assimilation detailed above. You need to complete the code in the `defaultSetup` method of this class to set the bounds of each variable to be optimized by IGUANA. For example, if the C function under test involves three integer inputs, in the range 0-255, the code to specify this would be `addInt(3, 0, 255);`. For more information on the method calls that can be used to specify the input to the C function, see the code for the `org.iguanatool.testobject.InputSpecification` class. (See the `calendar` case study example already provided in the `casestudies` repository.)

After the input specification is complete, you then need to set up the link so that the C function under test can take a vector of double variables from IGUANA and translate it into input arguments. To do this you need to complete the stub automatically generated for each test object in the `call` directory for that lives in the case study subdirectory under `casestudies\c` directory. First you need to set the `NUM_ARGS` constant to the correct number of arguments that the function will accept. This number should match the number of arguments you specified in the Java code for the test object's input specification. Then, you need to cast each element of the array of doubles passed into the `perform_call` function to the correct type for the function under test, and then pass those variables to the function under test so that it can be executed with the data. (See the `calendar` case study example already provided in the `casestudies` repository.)

## Running Test Data Generation
Running test data generation is then as simple as a command such as

`java org.iguanatool.Run calendar nhc`

where `calendar` is the case study and `nhc` is the search. (Search names are derived from the method names in the org.iguanatool.search.SearchFactory class.)

Further configuration options can be set through the `iguana.config` file in IGUANA's root directory. Each of these can be overridden at the command line by preceeding the option with a dash. For example, to set the seed from the command line, use

`java org.iguanatool.Run calendar nhc -seed=100`
