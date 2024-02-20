package helpers.exceptions;

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

    /**
     * Constructs a ContinentAlreadyExistsException with a message indicating that
     * the continent with the given ID already exists in the map.
     *
     * @param p_id The ID of the continent that already exists.
     */
    public ContinentAlreadyExistsException(int p_id){
        super("Continent " +p_id+ " already exists, cannot add it again ");
    }
}
