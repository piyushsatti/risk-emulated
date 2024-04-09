package helpers.exceptions;


/**
 * The InvalidCommandException class represents an exception that is thrown
 * when an invalid command is encountered.
 */
public class InvalidCommandException extends Exception {
    /**
     * Constructs an InvalidCommandException with the specified detail message.
     *
     * @param p_message The detail message explaining the reason for the exception.
     */
    public InvalidCommandException(String p_message) {
        super(p_message);
    }
}
