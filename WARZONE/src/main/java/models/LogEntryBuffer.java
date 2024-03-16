package models;

import view.Logger;

public class LogEntryBuffer extends Subject{
    private String log;

    private String LOG_FOLDER = "risk-emulated/WARZONE/src/main/resources/logs/";

    public String getLog()
    {
        return this.log;
    }

    public String getLogFolder()
    {
        return this.LOG_FOLDER;
    }

    public void setString(String logMessage)
    {
        this.log = logMessage;
        notifyAllViews(this);
    }

//    public static void main(String[] args) {
//        LogEntryBuffer lg = new LogEntryBuffer();
//        Logger lw = new Logger(lg);
//        lg.setString(" virat is the best");
//    }

}
