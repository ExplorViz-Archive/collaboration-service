package net.explorviz.vr.logging;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

public class CsvLogger {

    @ConfigProperty(name = "custom.logging.enableCsv")
    String enabled;

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvLogger.class);

    private String timestamp;

    public CsvLogger() {
        this.timestamp = String.valueOf(new Date().getTime());

        this.addEventHeader();
    }

    public void addEventHeader() {
        try (PrintWriter writer = new PrintWriter(new File(this.timestamp + "_socket_log.csv"))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Time");
            sb.append(',');
            sb.append("Event");
            sb.append(',');
            sb.append("Content");
            sb.append('\n');

            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void logEventToCsv(String event, String message) {
        String currentTime = String.valueOf(new Date().getTime());

        try (FileWriter fileWriter = new FileWriter(this.timestamp + "_log.csv", true);
             PrintWriter writer = new PrintWriter(fileWriter)) {

            StringBuilder sb = new StringBuilder();
            sb.append(currentTime);
            sb.append(',');
            sb.append(event);
            sb.append(',');
            sb.append(message);
            String csvLine = sb.toString();

            writer.println(csvLine);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
