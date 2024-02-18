package utils.exceptions;

/**
 * The DuplicateBorderException class represents an exception that is thrown
 * when attempting to add a duplicate border between two countries.
 */
public class DuplicateBorderException extends Exception{
    /**
     * Constructs a DuplicateBorderException with a detail message indicating
     * that a border between the specified source and target countries already exists.
     *
     * @param source The ID of the source country.
     * @param target The ID of the target country.
     */
    public DuplicateBorderException(int source, int target){
        super("Border between " + source + "and " + target + " already exists");
    }

}
