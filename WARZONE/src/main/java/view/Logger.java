package view;

import controller.GameEngine;
import controller.statepattern.Phase;
import models.LogEntryBuffer;
import models.Subject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Logger class handles logging messages to a file.
 */
public class Logger implements Observer{
    /**
     * Updates the logger with the latest log message.
     *
     * @param model The subject containing the log message
     */
    public void update(Subject model) {
        //GameEngine ge = new GameEngine();
        GameEngine ge = new GameEngine();
        String outputDirectoryPath = ((LogEntryBuffer)model).getLogFolder() +"log_file";
        File outputFile = new File(outputDirectoryPath);
        String logMessage = ((LogEntryBuffer)model).getLog();
        try{
            boolean isCreated = outputFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile,true));
            writer.write(logMessage + "\n");
            writer.close();
        }
        catch(IOException e)
        {
            //ge.renderer.renderError("IOException: error in creating/loading log file");
            System.out.println("IOException: error in creating/loading log file");
            ge.d_renderer.renderError("IOException: error in creating/loading log file");
        }
    }
    /**
     * Constructs a Logger object and attaches it to the specified subject.
     *
     * @param model The subject to attach the logger to
     */
    public Logger(Subject model)
    {
        model.attachView(this);
    }
}
