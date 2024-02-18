package utils.exceptions;

/**
 * The ContinentAlreadyExistsException class represents an exception that is thrown
 * when attempting to add a continent that already exists in the game.
 */
public class ContinentAlreadyExistsException extends Exception {

    /**
     * Constructs a ContinentAlreadyExistsException with a detail message indicating
     * that the continent with the specified name already exists.
     *
     * @param name The name of the continent that already exists.
     */
    public ContinentAlreadyExistsException(String name){
        super("Continent " +name+ " already exists, cannot add it again ");
    }
}
