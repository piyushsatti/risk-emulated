package mvc.models;

import mvc.view.Logger;

/**
 * The LogEntryBuffer class represents a buffer for logging messages.
 */
public class LogEntryBuffer extends Subject {
    /**
     * Represents the log content.
     */
    private String d_log;

    /**
     * Represents the folder path where logs are stored.
     */
    private String LOG_FOLDER = "risk-emulated/WARZONE/src/main/resources/logs/";

    /**
     * Gets the log message.
     *
     * @return The log message
     */
    public String getD_log() {
        return this.d_log;
    }

    /**
     * Gets the log folder path.
     *
     * @return The log folder path
     */
    public String getLogFolder() {
        return this.LOG_FOLDER;
    }

    /**
     * Sets the log message and notifies all registered views.
     *
     * @param logMessage The log message to set
     */
    public void setString(String logMessage) {
        this.d_log = logMessage;
        notifyAllViews(this);
    }

    /**
     * Main method for testing the LogEntryBuffer class.
     *
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        LogEntryBuffer l_lg = new LogEntryBuffer();
        Logger l_lw = new Logger(l_lg);
        l_lg.setString(" devdutt was here!");
    }

}