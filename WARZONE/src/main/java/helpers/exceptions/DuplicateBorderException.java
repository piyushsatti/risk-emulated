package helpers.exceptions;

/**
 * The DuplicateBorderException class represents an exception that is thrown
 * when attempting to add a duplicate border between two countries.
 */
public class DuplicateBorderException extends Exception {
    /**
     * Constructs a DuplicateBorderException with a detail message indicating
     * that a border between the specified source and target countries already exists.
     *
     * @param p_source The ID of the source country.
     * @param p_target The ID of the target country.
     */
    public DuplicateBorderException(int p_source, int p_target) {
        super("Border between " + p_source + "and " + p_target + " already exists");
    }

}
