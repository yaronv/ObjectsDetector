package yv.recipe.Properties;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by yaron on 01/06/15.
 */
public class Properties {

    static Logger logger = Logger.getLogger(Properties.class.getName());

    private static Properties instance;

    public static Properties instance() {
        if(instance == null) {
            instance = new Properties();
        }
        return instance;
    }

    private java.util.Properties propFile;

    /**
     * private c'tor
     */
    private Properties() {
        try {
            propFile = new java.util.Properties();
            propFile.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error on reading properties file", e);
        }
    }

    /**
     * get property int value
     * @param key the property key
     * @return string value
     */
    public int getPropertyInt(String key) {
        return Integer.valueOf(propFile.getProperty(key));
    }

    /**
     * get property string value
     * @param key the property key
     * @return string value
     */
    public String getPropertyString(String key) {
        return propFile.getProperty(key);
    }

    /**
     * get property boolean value
     * @param key the property key
     * @return boolean value
     */
    public Boolean getPropertyBoolean(String key) {
        return Boolean.valueOf(propFile.getProperty(key));
    }

    public double getPropertyDouble(String key) {
        return Double.valueOf(propFile.getProperty(key));
    }
}
