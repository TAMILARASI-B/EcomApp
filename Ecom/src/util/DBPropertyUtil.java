package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
    public static String getPropertyString(String filename) {
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream(filename)) {
            props.load(input);
            return "jdbc:mysql://" + props.getProperty("host") + ":" + props.getProperty("port") + "/" + props.getProperty("dbname") +
                   "?user=" + props.getProperty("username") + "&password=" + props.getProperty("password");
        } catch (IOException e) {
            return null;
        }
    }
}