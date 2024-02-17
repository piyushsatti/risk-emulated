package main.java.utils.exceptions;

import java.util.concurrent.ExecutionException;

public class ContinentDoesNotExistException extends Exception {
    public ContinentDoesNotExistException(int id){
        super("Continent does not exist, id: " + id);
    }
    public ContinentDoesNotExistException(String name){
        super("Continent does not exist, name: " + name);
    }
}
