package utils;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is responsible for managing the logging of the application.
 */
public class LogManager {

    private static final String PROJECT_NAME = "alexandria";
    private static final DateTimeFormatter FILE_TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
    private static final DateTimeFormatter LOG_ENTRY_TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static LogManager instance;
    private BufferedWriter writer;

    /**
     * Singleton pattern to initialize the logger.
     * Create a file log with a name based on the project name and the current date/time.
     */
    private LogManager() {
        try {
            LocalDateTime now = LocalDateTime.now();
            String fileName = PROJECT_NAME + "_" + now.format(FILE_TIMESTAMP_FORMATTER) + ".log";
            FileWriter fileWriter = new FileWriter(Path.of("log").resolve(fileName).toString(), StandardCharsets.UTF_8);
            this.writer = new BufferedWriter(fileWriter);
        } catch (FileNotFoundException e) {
            System.out.println("CRITICAL ERROR: The log file was not found.");
            this.writer = null;
        } catch (IOException e) {
            System.out.println("Error while creating or opening the log file: " + e.getMessage());
            this.writer = null;
        }
    }

    /**
     * @return LogManager instance.
     */
    public static LogManager getInstance() {
        if (instance == null) instance = new LogManager();
        return instance;
    }

    /**
     * Method to write a log entry into the file.
     *
     * @param level   The log level (e.g., "INFO", "WARN", "ERROR").
     * @param message The message to log.
     */
    private void writeLogInternal(String level, String message) {
        if (this.writer == null) return;

        try {
            LocalDateTime now = LocalDateTime.now();
            String timestamp = now.format(LOG_ENTRY_TIMESTAMP_FORMATTER);
            String logLine = String.format("%s - [%s] : %s%n", timestamp, level, message);

            writer.write(logLine);
            writer.flush();
        } catch (FileNotFoundException e) {
            System.out.println("CRITICAL ERROR: The log file was not found.");
            this.writer = null;
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    /**
     * Log an entry with the INFO level.
     *
     * @param msg The message to log.
     */
    public void info(String msg) {
        writeLogInternal("INFO", msg);
    }

    /**
     * Log an entry with the WARN level.
     *
     * @param msg The message to log.
     */
    public void warn(String msg) {
        writeLogInternal("WARN", msg);
    }

    /**
     * Log an entry with the ERROR level.
     *
     * @param msg The message to log.
     */
    public void error(String msg) {
        writeLogInternal("ERROR", msg);
    }

    /**
     * Close the log file when the application shuts down.
     */
    public void shutdown() {
        if (this.writer != null) {
            try {
                writeLogInternal("INFO", "################ FIN DES LOGS ################");
                writer.close();
                System.out.println("Log file successfully created.");
            } catch (FileNotFoundException e) {
                System.out.println("CRITICAL ERROR: The log file was not found.");
                this.writer = null;
            } catch (IOException e) {
                System.err.println("Error while closing the log file: " + e.getMessage());
            }
        }
    }
}