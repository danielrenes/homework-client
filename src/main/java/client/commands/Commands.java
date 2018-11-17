package client.commands;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public abstract class Commands {
    protected static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    protected final OkHttpClient okHttpClient;
    protected final String serverIp;
    protected final int serverPort;
    protected String token;

    public Commands(OkHttpClient okHttpClient, String serverIp, int serverPort) {
        this.okHttpClient = okHttpClient;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public void updateToken(String token) {
        this.token = token;
    }
}