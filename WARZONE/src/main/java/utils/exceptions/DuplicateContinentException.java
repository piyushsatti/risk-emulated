package main.java.utils.exceptions;

public class DuplicateContinentException extends Exception{
    public DuplicateContinentException(int id){
        super("Continent " + id + " already exists");
    }
    public DuplicateContinentException(String name){
        super("Continent " + name + " already exists");
    }
}
