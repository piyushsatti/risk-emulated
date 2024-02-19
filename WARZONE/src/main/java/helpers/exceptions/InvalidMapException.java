package helpers.exceptions;

/**
 * The InvalidMapException is thrown when a map is determined to be invalid.
 * This exception typically occurs during map loading or validation processes.
 */
public class InvalidMapException extends Throwable {

    /**
     * Constructs an InvalidMapException with a message indicating the name of the
     * invalid map.
     *
     * @param p_mapName The name of the invalid map.
     */
    public InvalidMapException(String p_mapName) {

        super("Invalid Map: " + p_mapName);

    }
}
