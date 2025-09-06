package inseder.web.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {


    public static Properties openProperties() {
        try {
            Properties properties = new Properties();
            FileInputStream input = new FileInputStream("src/main/resources/config.properties");
            properties.load(input);
            input.close();
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("config.properties okunmasi sirasinda hata var!", e);
        }
    }

    public static String getProperty(String key) {
        Properties properties = openProperties();
        return properties.getProperty(key);
    }

    public static int getPropertyInteger(String key) {
        Properties properties = openProperties();
        String value = properties.getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("'" + key + "' anahtarinin degeri bir int donusturulemedi: " + value);
        }
    }
}
