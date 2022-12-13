package template;

/**
 * AdventOfCode generic interface that is returned by the AOCFactory
 * Must be used to run a program
 */
public interface AOC {

    /**
     * Run a program using a file as data input
     *
     * @param file : The input file path (located in resources) to run
     */
    void run(String file);
}
