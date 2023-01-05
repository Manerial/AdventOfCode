package utilities;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Timer {
    private Date previousDate;

    public Timer() {
        previousDate = new Date();
    }

    public void time() {
        Date currentDate = new Date();
        long diff = currentDate.getTime() - previousDate.getTime();
        long millis = TimeUnit.MILLISECONDS.toMillis(diff) % 1000;
        long sec = TimeUnit.MILLISECONDS.toSeconds(diff) % 60;
        long min = TimeUnit.MILLISECONDS.toMinutes(diff);
        Printer.println(min + "m " + sec + "s " + millis + "ms");
        previousDate = currentDate;
    }
}
