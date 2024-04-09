package helpers.exceptions;

/**
 * The ContinentDoesNotExistException class represents an exception that is thrown
 * when attempting to access a continent that does not exist in the game.
 */
public class ContinentDoesNotExistException extends Exception {

    /**
     * Constructs a ContinentDoesNotExistException with a detail message indicating
     * that the continent with the specified ID does not exist.
     *
     * @param p_id The ID of the continent that does not exist.
     */
    public ContinentDoesNotExistException(int p_id) {
        super("Continent does not exist, id: " + p_id);
    }

    /**
     * Constructs a ContinentDoesNotExistException with a detail message indicating
     * that the continent with the specified name does not exist.
     *
     * @param p_name The name of the continent that does not exist.
     */
    public ContinentDoesNotExistException(String p_name) {
        super("Continent does not exist, name: " + p_name);
    }
}
