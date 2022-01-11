### Performance tests module

This performance tests module have been writing in gatling

## How to execute
mvn gatling:test

## How to execute one test
mvn gatling:test "-Dgatling.simulationClass=simulations.GetExpenseSimulation"

## How to generate the jar file
cd "assets-manager\performance-test"
mvn clean package

## How to execute one test from the jar file
cd "assets-manager\performance-test\target"
java -jar performance-test-fat-tests.jar -s simulations.GetExpenseSimulation

## How to execute several test from the jar file
cd "assets-manager\performance-test\target"
java "-Dgatling.core.directory.simulations=simulations" -jar performance-test-fat-tests.jar



