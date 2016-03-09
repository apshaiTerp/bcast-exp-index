# bcast-exp-index
Java libraries to support the Wireless Data Indexing project for CS 5572.

This project will accept data, convert that data into a flat 'broadcast' designed to mimic a wireless data broadcast,
and construct an exponential index for said data blocks, with two key parameters allowing for customization:
Exponential Factor:  The exponential factor by which bucket ranges will increase.
Bucket Size:  The number of data blocks to be contained in one common block of data.

Each Bucket will have a global index block that defines how to skip across buckets to maximize doze time, and a local index block that gives the access time of data blocks contained later in that bucket.

This project is built in Java using Maven to build.

For an introduction to maven, you can read here:
https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html

Sample commands for this project:

To run only the tests:
mvn clean test

To bulid the executable file (which is not yet executable):
mvn clean package
OR
mvn clean install

This project follows mavenized folder conventions defined here:
https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html


