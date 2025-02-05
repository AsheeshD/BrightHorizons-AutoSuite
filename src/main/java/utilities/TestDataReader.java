//package utilities;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//
//public class TestDataReader {
//
//    // Use classpath-based file loading instead of absolute file path
//    private static final String CONFIG_FILE_PATH = "/config";
//
//    // Method to load properties from the config file
//    public static Properties loadProperties() throws IOException {
//        Properties properties = new Properties();
//        System.out.println("In test data file 1 ");
//        // Load the file from the classpath
//        try (InputStream inputStream = TestDataReader.class.getResourceAsStream(CONFIG_FILE_PATH)) {
//            if (inputStream == null) {
//                throw new IOException("Property file not found in the classpath: " + CONFIG_FILE_PATH);
//            }
//            properties.load(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new IOException("Unable to load properties file: " + CONFIG_FILE_PATH);
//        }
//        return properties;
//    }
//
//    // Method to get a specific property value
//    public static String getProperty(String key) throws IOException {
//        Properties properties = loadProperties();
//        return properties.getProperty(key);
//    }
//}
package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestDataReader {

    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream input = new FileInputStream("src/main/resources/config");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fetch the base URL from the config file
    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    // Fetch the search query from the config file
    public static String getSearchQuery() {
        return properties.getProperty("search.query");
    }

    // Fetch the location from the config file
    public static String getLocation() {
        return properties.getProperty("location");
    }

    // Fetch timeout duration from the config file
    public static int getTimeoutDuration() {
        return Integer.parseInt(properties.getProperty("timeout.duration"));
    }
}
