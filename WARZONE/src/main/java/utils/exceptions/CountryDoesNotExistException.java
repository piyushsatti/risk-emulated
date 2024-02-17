package main.java.utils.exceptions;

public class CountryDoesNotExistException extends Exception {
    public CountryDoesNotExistException(int id){
        super("Country does not exist, id: " + id);
    }
    public CountryDoesNotExistException(String name){
        super("Country does not exist, name: " + name);
    }
}
