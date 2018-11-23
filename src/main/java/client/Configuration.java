package client;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Configuration {
    private static final String CONFIG_FILE_PATH = "configuration.json";

    private String serverIp;
    private int serverPort;

    private static Configuration instance;

    private Configuration() throws ClientException {
        JSONObject jsonObject;
        try {
            jsonObject = load();
        } catch (IOException|URISyntaxException|NullPointerException e) {
            throw new ClientException("Could not load configuration file: " + e.getMessage());
        }
        serverIp = jsonObject.getString("serverIp");
        serverPort = jsonObject.getInt("serverPort");
    }

    private JSONObject load() throws IOException, URISyntaxException, NullPointerException {
        String content;

        try {
            // try to load from the same directory as the JAR is located
            content = new String(Files.readAllBytes(Paths.get(CONFIG_FILE_PATH)));
        } catch (IOException e) {
            // try to load from resources
            content = new String(Files.readAllBytes(
                    Paths.get(getClass().getClassLoader().getResource(CONFIG_FILE_PATH).toURI())));
        }

        return new JSONObject(content);
    }

    public static Configuration getInstance() throws ClientException {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public String getServerIp() {
        return serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }
}