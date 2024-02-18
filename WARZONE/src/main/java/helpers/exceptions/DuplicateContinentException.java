package helpers.exceptions;

/**
 * The DuplicateContinentException class represents an exception that is thrown
 * when attempting to add a duplicate continent.
 */
public class DuplicateContinentException extends Exception{
    /**
     * Constructs a DuplicateContinentException with a detail message indicating
     * that a continent with the specified ID already exists.
     *
     * @param id The ID of the continent that already exists.
     */
    public DuplicateContinentException(int id){
        super("Continent " + id + " already exists");
    }
    /**
     * Constructs a DuplicateContinentException with a detail message indicating
     * that a continent with the specified name already exists.
     *
     * @param name The name of the continent that already exists.
     */
    public DuplicateContinentException(String name){
        super("Continent " + name + " already exists");
    }
}
