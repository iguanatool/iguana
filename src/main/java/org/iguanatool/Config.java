package org.iguanatool;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * User: phil
 * Date: 09-Sep-2006
 * Time: 11:47:42
 */
public class Config {

    /*
     *  locations
     */
    public static final String CONFIG_FILENAME = "iguana.config";
    public static final String IGUANA_SOURCE_DIR = "src/main/java";
    public static final String TEMPLATES_DIR = "templates";
    /*
     *  properties
     */
    public static final String CONFIG_FILE_PROPERTY = "config_file";
    public static final String IGUANA_HOME_PROPERTY = "iguana_home";
    public static final String JAVA_HOME_PROPERTY = "java_home";
    public static final String OS_PROPERTY = "os";
    public static final String PRINT_CONFIG_PROPERTY = "print_config";

    /*
     *  instance
     */
    private static Config instance;

    /*
     *  instance variables
     */
    private Map<String, String> config;

    public Config() {
        this(null);
    }

    public Config(String[] specifiedProperties) {
        config = new HashMap<String, String>();
        findIguanaPath();
        findJavaPath();
        findOS();

        File iguanaConfigFile = new File(getIguanaPath() + "/" + CONFIG_FILENAME);
        File localConfigFile = new File(CONFIG_FILENAME);

        if (iguanaConfigFile.exists()) {
            loadConfigFile(iguanaConfigFile);
        }

        if (localConfigFile.exists()) {
            loadConfigFile(localConfigFile);
        }

        if (specifiedProperties != null) {
            File specifiedConfigFile = getSpecifiedConfigFile(specifiedProperties);
            if (specifiedConfigFile != null) {
                loadConfigFile(specifiedConfigFile);
            }
            loadSpecifiedProperties(specifiedProperties);
        }

        if (getBooleanProperty(PRINT_CONFIG_PROPERTY)) printConfig();
        instance = this;
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    private void findIguanaPath() {
        String path = System.getenv("IGUANA_HOME");

        if (path == null) {
            String classPath = null;
            try {
                URL codeSourceURL = Config.class.getProtectionDomain().getCodeSource().getLocation();
                if (codeSourceURL.getProtocol().equals("file")) {
                    classPath = codeSourceURL.toURI().getPath();
                }
            } catch (URISyntaxException e) {
                throw new ConfigException("Cannot find path to Iguana - set IGUANA_HOME environment variable");
            }
            if (classPath == null) {
                throw new ConfigException("Cannot find path to Iguana - set IGUANA_HOME environment variable");
            }
            path = (new File(classPath)).getParent();
        }
        config.put(IGUANA_HOME_PROPERTY, path);
    }

    private void findJavaPath() {
        String path = getProperty(JAVA_HOME_PROPERTY);

        if (path == null) path = System.getenv("JAVA_HOME");
        if (path == null) path = System.getProperty("java.home");
        if (path == null) throw new ConfigPropertyMissingException(JAVA_HOME_PROPERTY);

        config.put(JAVA_HOME_PROPERTY, path);
    }

    private void findOS() {
        config.put(OS_PROPERTY, System.getProperty("os.name"));
    }

    private void loadConfigFile(File f) {
        Properties p = new Properties();

        try {
            p.load(new FileReader(f));
            for (String key : p.stringPropertyNames()) {
                config.put(key.trim(), p.getProperty(key).trim());
            }
        } catch (IOException e) {
            throw new ConfigException("Could not load config file \"" + f.getPath() + "\"");
        }
    }

    private void loadSpecifiedProperties(String[] properties) {
        for (String property : properties) {
            if (property.startsWith("-")) {
                int equalsCharPos = property.indexOf("=");
                String name = property.substring(1, equalsCharPos).trim();
                String value = property.substring(equalsCharPos + 1, property.length()).trim();
                setProperty(name, value);
            }
        }
    }

    private File getSpecifiedConfigFile(String[] properties) {
        String searchString = "-" + CONFIG_FILE_PROPERTY + "=";
        for (String property : properties) {
            if (property.startsWith(searchString)) {
                return new File(property.substring(searchString.length(), property.length()));
            }
        }
        return null;
    }

    public boolean getBooleanProperty(String name) {
        String value = config.get(name);
        return (value == null) ? false : value.equals("true");
    }

    public String getProperty(String name) {
        return config.get(name);
    }

    public String getRequiredProperty(String name) {
        if (!config.containsKey(name)) {
            throw new ConfigPropertyMissingException(name);
        }
        return getProperty(name);
    }

    private void setProperty(String name, String value) {
        config.put(name, value);
    }

    public Set<String> getProperties() {
        return config.keySet();
    }

    public File getIguanaPath() {
        return new File(getProperty(IGUANA_HOME_PROPERTY));
    }

    public File getIguanaSourcePath() {
        return new File(getIguanaPath() + "/" + IGUANA_SOURCE_DIR);
    }

    public File getTemplatesPath() {
        return new File(getIguanaPath() + "/" + TEMPLATES_DIR);
    }

    public File getJavaPath() {
        return new File(getProperty(JAVA_HOME_PROPERTY));
    }

    public String getOS() {
        return getProperty(OS_PROPERTY);
    }

    private void printConfig() {
        System.out.println("Configuration:");
        SortedSet<String> propertyNames = new TreeSet<String>(config.keySet());
        for (String name : propertyNames) {
            String value = getProperty(name);
            System.out.println(name + "=" + value);
        }
        System.exit(1);
    }
}

@SuppressWarnings("serial")
class ConfigException extends RuntimeException {

    public ConfigException(String message) {
        super(message);
    }
}

@SuppressWarnings("serial")
class ConfigPropertyMissingException extends ConfigException {

    public ConfigPropertyMissingException(String propertyName) {
        super("Could not find configuration value for property \"" + propertyName + "\"");
    }
}
