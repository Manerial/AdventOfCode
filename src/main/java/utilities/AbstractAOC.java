package utilities;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static utilities.ResourceIO.DELIMITER;

/**
 * AdventOfCode generic interface that is returned by the AOCFactory
 * Must be used to run a program
 */
public abstract class AbstractAOC {
    /**
     * Contains the input for the exercise
     */
    protected List<String> inputList;

    @Getter
    protected Object solution1;
    @Getter
    protected Object solution2;

    @Getter
    @Setter
    private int day;
    @Getter
    @Setter
    private int year;
    @Setter
    protected boolean isExample;
    @Setter
    protected boolean isFromURL;

    /**
     * Using an {@link AOCArguments}, return AOC implementation to use.
     *
     * @param aocArguments : the arguments with which the program was called
     *
     * @return the AOC implementation to use
     *
     * @throws ClassNotFoundException if year or day doesn't match an exercise already done
     * @throws NoSuchMethodException should not be thrown
     * @throws InvocationTargetException should not be thrown
     * @throws InstantiationException should not be thrown
     * @throws IllegalAccessException should not be thrown
     */
    public static AbstractAOC build(AOCArguments aocArguments) throws ClassNotFoundException, NoSuchMethodException, IOException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String dayStr = String.format("%02d", aocArguments.getDay());
        String classPath = "aoc.exercises.year" + aocArguments.getYear() + ".day" + dayStr + ".AOCRunner";

        // Get the AOC class to run
        Class<?> classRef = Class.forName(classPath);
        AbstractAOC aoc = (AbstractAOC) classRef.getDeclaredConstructor().newInstance();

        // Set the parameters
        aoc.setDay(aocArguments.getDay());
        aoc.setYear(aocArguments.getYear());
        aoc.setExample(aocArguments.isExample());
        aoc.setFromURL(aocArguments.isFromURL());

        // Must go last
        aoc.setInput();

        return aoc;
    }

    /**
     * Run a program using a file as data input
     * The legacy variable inputList contains the data inputs
     */
    public abstract void run();

    /**
     * Set the input of the exercise.
     * If isExample, get the input from resources/example_input.
     * If isFromUrl, get the input from the website AdventOfCode.com
     * Else, get the input from resources/puzzle_input.
     * @throws IOException if the input file does not exist
     */
    public void setInput() throws IOException {
        // If needed, get the input from the URL, else from our local files
        if(!isExample && isFromURL) {
            try {
                setInputFromURL(getPuzzleInputURL());
            } catch (Exception ignored) {
                // Couldn't get from URL, so do nothing
            }
        }

        if(isExample) {
            setInputFromFile(getExampleInputPath());
        } else {
            setInputFromFile(getPuzzleInputPath());
        }
    }

    /**
     * Set the basic input for the instance using the AOC URL and save it locally in puzzle_input.
     *
     * @param url : The input file path (located in resources) to run
     * @throws IOException if the input file does not exist
     */
    private void setInputFromURL(String url) throws IOException {
        ResourceIO.saveLocalSession();
        inputList = ResourceIO.readListFromURL(url);
        String inputStr = String.join(DELIMITER, inputList);
        ResourceIO.writeInFile(getPuzzleInputPath(), inputStr);
    }

    /**
     * Set the basic input for the instance
     *
     * @param file : The input file path (located in resources) to run
     * @throws IOException if the input file does not exist
     */
    private void setInputFromFile(String file) throws IOException {
        inputList = ResourceIO.readListFromResourceFile(file);
    }

    private String getPuzzleInputURL() {
        return "https://adventofcode.com/" + year + "/day/" + day + "/input";
    }

    private String getExampleInputPath() {
        return "example_input/" + year + "/aoc" + day + ".txt";
    }

    private String getPuzzleInputPath() {
        return "puzzle_input/" + year + "/aoc" + day + ".txt";
    }
}
