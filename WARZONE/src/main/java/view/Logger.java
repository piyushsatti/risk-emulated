package view;

import controller.GameEngine;
import models.LogEntryBuffer;
import models.Subject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Logger class handles logging messages to a file.
 */
public class Logger implements Observer {
    /**
     * Updates the logger with the latest log message.
     *
     * @param p_model The subject containing the log message
     */
    public void update(Subject p_model) {
        GameEngine l_ge = new GameEngine();
        String l_outputDirectoryPath = ((LogEntryBuffer) p_model).getLogFolder() + "log_file";
        File l_outputFile = new File(l_outputDirectoryPath);
        String l_logMessage = ((LogEntryBuffer) p_model).getD_log();
        try {
            boolean l_isCreated = l_outputFile.createNewFile();
            BufferedWriter l_writer = new BufferedWriter(new FileWriter(l_outputFile, true));
            l_writer.write(l_logMessage + "\n");
            l_writer.close();
        } catch (IOException e) {
            l_ge.d_renderer.renderError("IOException: error in creating/loading log file");
        }
    }

    /**
     * Constructs a Logger object and attaches it to the specified subject.
     *
     * @param p_model The subject to attach the logger to
     */
    public Logger(Subject p_model) {
        p_model.attachView(this);
    }
}
