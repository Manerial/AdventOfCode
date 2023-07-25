# AdventOfCode
All my answers for the exercises of the AdventOfCode event.

More informations here : https://adventofcode.com/

## Installation & Run
The project uses JAVA 17 and Maven to run.

To run the project, go to src/main/java/application and run the Application class.

WARNING : This class must be run with parameters, as follows :

-h | -d (DAY) -y (YEAR) [-p] [-e] [-u]

    -h is for help. This option overlap each other and list all the available arguments to launch the project.
    -d is for the day (mandatory). "-d 1" indicates the first day of the year.
    -y is for the year (mandatory). "-y 2015" indicates the year 2015.
    -p is for print (optional). If present, we want the result to be printed into the console.
    -e is for example (optional). If present, we want the exercise to run with the example input.
    -u is for URL (optional). If present, we want the application to fetch the input from the AdventOfCode website.

## Architecture
The project is split in two parts : the exercises and the tests.

### Main
The main/java folder contains 3 parts :

main/java/application :contains the main method to run the project.

main/java/aoc : contains the exercises sorted by year / day.

main/java/utilities : contains some useful classes to improve the use of the project.

### Test

Unlike a "normal" java project, the tests are not there to test the application,
just to ensure the results are what we expect.

That can be useful if an update is made somewhere on the application,
and we want to ensure we still have the expected results.