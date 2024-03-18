package view;

import controller.GameEngine;
import models.LogEntryBuffer;
import models.Subject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Logger implements Observer {

    public void update_log(Subject model, GameEngine ge) {
        String outputDirectoryPath = ((LogEntryBuffer) model).getLogFolder() + "log_file";
        File outputFile = new File(outputDirectoryPath);
        String logMessage = ((LogEntryBuffer)model).getLog();

        try {
            boolean isCreated = outputFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile,true));
            writer.write("Current Phase : " + ge.getCurrentPhaseName() + " : " + logMessage + "\n");
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println("IOException: error in creating/loading log file");
            ge.d_renderer.renderError("IOException: error in creating/loading log file");
        }
    }
    public Logger(Subject model)
    {
        model.attachView(this);
    }

    @Override
    public abstract void update(Subject model);
}
