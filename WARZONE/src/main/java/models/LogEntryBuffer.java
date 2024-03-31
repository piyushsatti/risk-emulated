package models;

import view.Logger;

/**
 * The LogEntryBuffer class represents a buffer for logging messages.
 */
public class LogEntryBuffer extends Subject{
    /**
     * Represents the log content.
     */
    private String log;

    /**
     * Represents the folder path where logs are stored.
     */
    private String LOG_FOLDER = "WARZONE/src/main/resources/logs/";

    /**
     * Gets the log message.
     *
     * @return The log message
     */
    public String getLog()
    {
        return this.log;
    }

    /**
     * Gets the log folder path.
     *
     * @return The log folder path
     */
    public String getLogFolder()
    {
        return this.LOG_FOLDER;
    }

    /**
     * Sets the log message and notifies all registered views.
     *
     * @param logMessage The log message to set
     */
    public void setString(String logMessage)
    {
        this.log = logMessage;
        notifyAllViews(this);
    }

    /**
     * Main method for testing the LogEntryBuffer class.
     *
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        LogEntryBuffer lg = new LogEntryBuffer();
        Logger lw = new Logger(lg);
        lg.setString(" devdutt was here!");
    }

}