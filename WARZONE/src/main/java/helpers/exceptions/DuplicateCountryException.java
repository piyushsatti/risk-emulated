package helpers.exceptions;

/**
 * The DuplicateCountryException class represents an exception that is thrown
 * when attempting to add a duplicate country.
 */
public class DuplicateCountryException extends Exception {
    /**
     * Constructs a DuplicateCountryException with a detail message indicating
     * that a country with the specified ID already exists.
     *
     * @param p_id The ID of the country that already exists.
     */
    public DuplicateCountryException(int p_id) {
        super("Country " + p_id + " already exists");
    }

    /**
     * Constructs a DuplicateCountryException with a detail message indicating
     * that a country with the specified name already exists.
     *
     * @param p_name The name of the country that already exists.
     */
    public DuplicateCountryException(String p_name) {
        super("Country " + p_name + " already exists");
    }
}
