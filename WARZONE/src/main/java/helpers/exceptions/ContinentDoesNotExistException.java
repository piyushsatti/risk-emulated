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
     * @param id The ID of the continent that does not exist.
     */
    public ContinentDoesNotExistException(int id){
        super("Continent does not exist, id: " + id);
    }
    /**
     * Constructs a ContinentDoesNotExistException with a detail message indicating
     * that the continent with the specified name does not exist.
     *
     * @param name The name of the continent that does not exist.
     */
    public ContinentDoesNotExistException(String name){
        super("Continent does not exist, name: " + name);
    }
}
