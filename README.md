#IGUANA

IGUANA is a tool for generating branch coverage test data for C functions.

## Requirements
IGUANA requires the [Maven build automation tool](https://maven.apache.org/), and [JDK 7 (or more recent)](http://www.oracle.com/technetwork/java/javase/downloads/) installed.

## Installation
To install IGUANA you need to check out the repository, and run the following command to produce a JAR file (or run the equivalent command in your IDE):

`mvn package`

You'll then need to set the following environment variables. 

* The `IGUANA_HOME` environment variable needs to be set to the root directory of this repository, where it is checked out on your system.
* The `JAVA_HOME` environment variable needs to be set to the root directory of your JDK installation.
* You need to update your `CLASSPATH` environment variable to the JAR file (that includes dependent libraries) produced by Maven. Typically this lives in the `target` directory and is called `iguanatool-1.0-jar-with-dependencies.jar`. So in Bash, you will need to use the command:

`export CLASSPATH="$IGUANA_HOME/target/iguanatool-1.0-jar-with-dependencies.jar":$CLASSPATH`

## Compiling Case Studies
To use IGUANA to generate test data for a C function, you need to include it as part of a "case study". A "case study" comprises a set of C functions you want to test, also referred to as "test objects".

You will need to create a directory for your case studies. The easiest way to do this with the required structure is to clone the `casestudies` repository, available at https://github.com/iguanatool/casestudies.

To install a new case study you will need to perform the following steps:

1. Create a new directory for your case study in the `c` subdirectory of your casestudies directory, e.g. `my_example` and place the C function you want to test in a file called `my_example.c` where `my_example` is the name of your case study.
2. Perform step one assimilation, by running the following command
`java org.iguanatool.Assimilate my_example`
(replace `my_example` with your own case study name)
3. Edit the input specification Java code for the `.java` class generated for each test object (C function) to be tested. The source code for each class resides in the `casestudies/java/my_example/inputspecifications` directory (see the next section if you need further instructions or help with this).
4. Each case study becomes a part of the IGUANA code, so you'll now need to compile IGUANA again, using `mvn package`
5. Complete the call code for each C file for each test object (C function to be tested) in the `casestudies/c/my_example/call` directory (see the next section if you need help with this).
6. Perform step two assimilation, by again running `java org.iguanatool.Assimilate my_example`

Your case study should be ready to go.

## Setting up the input specification Java code and the C code in the test object's `call` directory
At its core, IGUANA optimizes a fixed vector of double variables. Code must be written to specify the vector of double variables to be optimized for each test object (each function under test) concerned. This is the Java code that lives in the `inputspecifications` package for your case study. Each test object will have its own Java class generated as part of step one assimilation detailed above. You need to complete the code in the `defaultSetup` method of this class to set the bounds of each variable to be optimized by IGUANA. For example, if the C function under test involves three integer inputs, in the range 0-255, the code to specify this would be `addInt(3, 0, 255);`. For more information on the method calls that can be used to specify the input to the C function, see the code for the `org.iguanatool.testobject.InputSpecification` class. (See the `calendar` case study example already provided in the `casestudies` repository.)

After the input specification is complete, you then need to set up the link so that the C function under test can take a vector of double variables from IGUANA and translate it into input arguments. To do this you need to complete the stub automatically generated for each test object in the `call` directory for that lives in the case study subdirectory under `casestudies\c` directory. First you need to set the `NUM_ARGS` constant to the correct number of arguments that the function will accept. This number should match the number of arguments you specified in the Java code for the test object's input specification. Then, you need to cast each element of the array of doubles passed into the `perform_call` function to the correct type for the function under test, and then pass those variables to the function under test so that it can be executed with the data. (See the `calendar` case study example already provided in the `casestudies` repository.)

## Running Test Data Generation
Running test data generation is then as simple as a command such as

`java org.iguanatool.Run my_example nhc`

where `my_example` is the case study and `nhc` is the search. (Search names are derived from the method names in the org.iguanatool.search.SearchFactory class.) 

IGUANA will now attempt to generate test inputs for each test object (C function) that is a part of your case study. The list of C functions for which data are generated can be changed by editing the `testobjects` file placed in the case study directory in which your C code lives. This is initially populated with all the functions IGUANA found when parsing your case study C file (i.e., `my_example.c` in the above).

Further configuration options can be set through the `iguana.config` file in IGUANA's root directory. Each of these can be overridden at the command line by preceeding the option with a dash. For example, to set the seed from the command line, use

`java org.iguanatool.Run my_example nhc -seed=100`
