# bcast-exp-index
Java libraries to support the Wireless Data Indexing project for CS 5572.

So, the broadcast generation code I think is at a pretty good point now.  Here's what it can do:

There are three index generation options, each of which drives off two parameters:
* Exponential Factor (The exponent value used to generate bucket ranges)
* Bucket Size (The amount of data blocks contained in a single bucket)

Each bucket begins with a GlobalIndex block, and a LocalIndex block.  The GlobalIndex block handles the skipping to later buckets in the broadcast, and the LocalIndex block maps all data blocks within this index.

From this, I have created code that will accept blocks of data, then generate the appropriate indexes and assemble a complete broadcast using the provided parameters.

There are really three types of indexes we can generate, and which we should demonstrate for our project:
* Flat Index
All data points are treated the same.  The data blocks are all sorted by a key, and the global index file cycles all the way through a broadcast.
* Clustered Index
Data blocks are defined as belonging to a collection of related data items (i.e. Stocks, Weather Data, etc).  In this variant, the GlobalIndex block has two sections:  A Cluster Index, and the Exponential Index.  The Cluster Index relates to the next occurrence of a given cluster in the broadcast.  The Exponential Index still follows the same rules as the Flat broadcast, but has fewer rows as it only needs to index to the end of the cluster.
* Skewed Clustered Index
This is in most ways similar to the Clustered Index, except that cluster blocks might appear mutliple times during a single broadcast.  This is done to improve access time for more important items, but either expands the broadcast cycle or forces other data out.  The indexing is identical to the clustered index, save that the cluster block points to the next occurrence of a cluster, since there may be multiple.

This project is meant to simulate a wireless data broadcast.  We are using simple blocks to represent time.  There are two key elements measured to reflect time spent in a query:  Access Time and Tuning Time.
* Access Time
Access Time is the time spent in total from the beginning of the request until the requested data is received.
* Tuning Time is the amount of time spent actively listening to the broadcast for data.

Our goal is to focus on minimize tuning time.

#Project Build
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
