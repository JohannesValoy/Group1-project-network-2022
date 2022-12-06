# Group 1 Network project

This is the main repository for Group 1 network project that is a part of the evaluation of IDATA2304: Computer Networks

## Summary

The project task is to design a system that is meant to take in data trough a sensor and let the users access it trough a server.

We chose to work with a temperature sensor using a arduino, building our own TCP server and creating a client with the use of Java.

## Documentation

The documentation of the application can be found through the [wiki](https://github.com/JohannesValoy/Group1-project-network-2022/wiki). It will consist of

- The task received from the teachers
- The documentation for each package

## Report

The report can be found [here](./report/REPORT.md)

## Demonstration

To run a demonstration of the application you need to compile the project using the "mvn compile" command and run it with the following steps. We highly recommend running the server on a different device or a VM with the port 6008 open. We did **not** get this to work with both the server and client on the same machine.

### Server

Since the application is reliant on the server side, you can start the server with the test script that is found [here](./src/test/java/no/ntnu/idata2304/group1/server/startTestServer.sh). 

There is 2 ways of injecting the test data to the server [database](target\classes\no\ntnu\idata2304\group1\server\database\data.db) (NB: the database is created when none is found when the server boots up, so you need to start server before):

1. Copying the [data.db](src\test\resources\no\ntnu\idata2304\group1\server\database\data.db) before the server starts or replacing the target database with the the test database.

2. Running the sql file found [here](src\test\resources\no\ntnu\idata2304\group1\server\database\fillDummData.sql) into the the data.db file.


### Application

The application by running the main function found in the [Main](src\main\java\no\ntnu\idata2304\group1\clientapp\app\Main.java).

### Sensor

You can run one sensor manually trough the [Main](src\main\java\no\ntnu\idata2304\group1\sensor\Main.java) by passing it arguments or using the [TestSensors](src\test\java\no\ntnu\idata2304\group1\sensors\TestSensors.java) function to use the test arguments.
