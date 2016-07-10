package tools;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Vladyslav Usenko on 29.06.2016.
 */
public class Tools {
    public static void loggerInit(FileHandler fileHandler, String LOG_FILE, Logger LOGGER){
        try {
            fileHandler = new FileHandler(LOG_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();

        try{
            fileHandler.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidUUID(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
