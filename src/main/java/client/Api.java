package client;

import client.commands.*;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

public class Api {
    private final String serverIp;
    private final int serverPort;

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private String credential;
    private String token;

    private CommandRepository commandRepository = new CommandRepository();

    private static Api instance;

    private Api(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;

        commandRepository.put(AdministratorCommands.class,
                new AdministratorCommands(okHttpClient, serverIp, serverPort));
        commandRepository.put(TeacherCommands.class,
                new TeacherCommands(okHttpClient, serverIp, serverPort));
        commandRepository.put(StudentCommands.class,
                new StudentCommands(okHttpClient, serverIp, serverPort));
    }

    public static Api getInstance() throws ClientException {
        if (instance == null) {
            Configuration configuration = Configuration.getInstance();
            instance = new Api(configuration.getServerIp(), configuration.getServerPort());
        }
        return instance;
    }

    private boolean checkToken() throws ClientException {
        if (token == null) {
            if (credential == null) {
                throw new ClientException("Token and credential are null");
            }
        } else {
            Request request = new Request.Builder()
                    .url(String.format("http://%s:%d/api/v1/auth/token", serverIp, serverPort))
                    .header("Authorization", "Bearer " + token)
                    .get()
                    .build();

            Response response;

            try {
                response = okHttpClient.newCall(request).execute();
            } catch (IOException e) {
                throw new ClientException("Could not execute request");
            }

            if (response.code() != 200) {
                getToken();
                return false;
            }
        }

        return true;
    }

    private void getToken() throws ClientException {
        if (credential == null) {
            throw new ClientException("Credential needed for basic auth");
        }

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/auth/token", serverIp, serverPort))
                .header("Authorization", credential)
                .post(RequestBody.create(null, new byte[0]))
                .build();

        Response response;

        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new ClientException("Could not execute request");
        }

        if (response.code() != 200 || response.body() == null) {
            throw new ClientException("Request failed");
        }

        try {
            JSONObject jsonObject = new JSONObject(response.body().string());
            token = jsonObject.getString("token");
        } catch (IOException e) {
            throw new ClientException("Could not process response body");
        }
    }

    public void getToken(String username, String password) throws ClientException {
        credential = Credentials.basic(username, password);
        getToken();

        for (Class<? extends Commands> type : Arrays.asList(AdministratorCommands.class,
                                                            TeacherCommands.class,
                                                            StudentCommands.class)) {
            commandRepository.get(type).updateToken(token);
        }
    }

    public <T extends Commands> T getCommands(Class<T> type) throws ClientException {
        T commands = commandRepository.get(type);
        if (!checkToken()) {
            commands.updateToken(token);
        }
        return commands;
    }
}