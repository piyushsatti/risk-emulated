package main.java.utils.exceptions;

public class ContinentAlreadyExistsException extends Exception {
    public ContinentAlreadyExistsException(String name){
        super("Continent " +name+ " already exists, cannot add it again ");
    }
}
