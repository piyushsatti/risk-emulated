package main.java.utils.exceptions;

public class DuplicateCountryException extends Exception{
    public DuplicateCountryException(int id){
        super("Country " + id + " already exists");
    }
    public DuplicateCountryException(String name){
        super("Country " + name + " already exists");
    }
}
