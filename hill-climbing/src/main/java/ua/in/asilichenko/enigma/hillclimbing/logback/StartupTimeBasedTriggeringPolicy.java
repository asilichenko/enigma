package ua.in.asilichenko.enigma.hillclimbing.logback;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import ch.qos.logback.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy;
import ch.qos.logback.core.rolling.RolloverFailure;

/**
 * Rolling log file at startup.
 * <p>
 * https://itecnote.com/tecnote/java-how-to-roll-the-log-file-on-startup-in-logback/
 * <p>
 * Creation date: 04.11.2022
 */
@NoAutoStart
public class StartupTimeBasedTriggeringPolicy<E>
        extends DefaultTimeBasedFileNamingAndTriggeringPolicy<E> {

    @Override
    public void start() {
        super.start();
        nextCheck = 0L;
        isTriggeringEvent(null, null);
        try {
            tbrp.rollover();
        } catch (RolloverFailure ignored) {
        }
    }
}
