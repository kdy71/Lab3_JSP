package exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Created by Oleksandr Dudkin on 17.06.2016.
 *
 */
public class ApplicationException extends Exception {
    //todo проверить как работает класс

    private static final Logger LOG = LogManager.getLogger(ApplicationException.class);

    private String message;

    public ApplicationException(String message, Exception exception) {
        super(message, exception);
        this.message = message;
        LOG.error(Arrays.toString(exception.getStackTrace()).replaceAll(" ", "\t\n"));
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */
    @Override
    public String getMessage() {
        return super.getMessage() + "is: " + message;
    }
}
