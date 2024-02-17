package main.java.utils.exceptions;

public class DuplicateBorderException extends Exception{

    public DuplicateBorderException(int source, int target){
        super("Border between " + source + "and " + target + " already exists");
    }

}
