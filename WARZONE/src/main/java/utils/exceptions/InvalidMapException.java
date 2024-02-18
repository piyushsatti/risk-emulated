package utils.exceptions;

public class InvalidMapException extends Throwable {

    public InvalidMapException(String p_mapName) {

        super("Invalid Map: " + p_mapName);

    }
}
