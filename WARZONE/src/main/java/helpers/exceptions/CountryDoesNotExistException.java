package helpers.exceptions;

/**
 * The CountryDoesNotExistException class represents an exception that is thrown
 * when attempting to access a country that does not exist in the game.
 */
public class CountryDoesNotExistException extends Exception {
    /**
     * Constructs a CountryDoesNotExistException with a detail message indicating
     * that the country with the specified ID does not exist.
     *
     * @param p_id The ID of the country that does not exist.
     */
    public CountryDoesNotExistException(int p_id) {
        super("Country does not exist, id: " + p_id);
    }

    /**
     * Constructs a CountryDoesNotExistException with a detail message indicating
     * that the country with the specified name does not exist.
     *
     * @param p_name The name of the country that does not exist.
     */
    public CountryDoesNotExistException(String p_name) {
        super("Country does not exist, name: " + p_name);
    }
}
