package client;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
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
        String content = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(CONFIG_FILE_PATH).toURI())));
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