package view;

import controller.GameEngine;
import models.LogEntryBuffer;
import models.Subject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger implements Observer{
    public void update(Subject model) {
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
            ge.renderer.renderError("IOException: error in creating/loading log file");
        }
    }
    public Logger(Subject model)
    {
        model.attachView(this);
    }
}
