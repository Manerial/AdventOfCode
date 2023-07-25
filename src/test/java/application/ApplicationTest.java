package application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

class ApplicationTest {

    @Test
    void run_main() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // ARRANGE
        String[] args = {"-d", "1", "-y", "2015"};

        // ACT
        Application.main(args);

        // ASSERT
    }

    @ParameterizedTest
    @ValueSource(strings = {"-e", "-u", "-p"})
    void run_main_with_example(String arg) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // ARRANGE
        String[] args = {"-d", "1", "-y", "2015", arg};

        // ACT
        Application.main(args);

        // ASSERT
    }

    @Test
    void run_main_with_help() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // ARRANGE
        String[] args = {"-h"};

        // ACT
        Application.main(args);

        // ASSERT
    }
}