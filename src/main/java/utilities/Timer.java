package utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Timer {
    private static final Logger logger = LoggerFactory.getLogger(Timer.class);
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
        String timeInfo = String.format("%sm %ss %sms", min, sec, millis);
        logger.info(timeInfo);
        previousDate = currentDate;
    }
}
