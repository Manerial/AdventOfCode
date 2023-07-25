package utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static utilities.ResourceIO.DELIMITER;

@Getter
@AllArgsConstructor
public class AOCArguments {
    private int year;
    private int day;
    private boolean print = false;
    private boolean isExample = false;
    private boolean isFromURL = false;
    private String message;

    public AOCArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-y", "-year" -> year = Integer.parseInt(args[i + 1]);
                case "-d", "-day" -> day = Integer.parseInt(args[i + 1]);
                case "-p", "-print" -> print = true;
                case "-e", "-isExample" -> isExample = true;
                case "-u", "-url" -> isFromURL = true;
                case "-h", "-help" -> message = String.join(DELIMITER,
                        "",
                        "Available arguments: ",
                        "",
                        "<TYPE, DEFAULT>",
                        "-y, -year <int, 2015>\t: Year of the exercise",
                        "-d, -day <int, 1>\t: Day of the exercise",
                        "-p, -print\t: Print the answer",
                        "-e, -isExample\t: Use the example input",
                        "-u, -url\t: Get the exercise input from the website",
                        "-t, -testAll\t: Use the example input. Override upper options",
                        "-h\t: Help. Override upper options");
                default -> {
                    // Do nothing
                }
            }
        }
    }
}
