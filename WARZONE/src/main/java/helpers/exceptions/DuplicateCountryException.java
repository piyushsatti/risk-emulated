package helpers.exceptions;

/**
 * The DuplicateCountryException class represents an exception that is thrown
 * when attempting to add a duplicate country.
 */
public class DuplicateCountryException extends Exception{
    /**
     * Constructs a DuplicateCountryException with a detail message indicating
     * that a country with the specified ID already exists.
     *
     * @param id The ID of the country that already exists.
     */
    public DuplicateCountryException(int id){
        super("Country " + id + " already exists");
    }
    /**
     * Constructs a DuplicateCountryException with a detail message indicating
     * that a country with the specified name already exists.
     *
     * @param name The name of the country that already exists.
     */
    public DuplicateCountryException(String name){
        super("Country " + name + " already exists");
    }
}
