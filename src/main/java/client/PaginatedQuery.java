package client;

import model.JsonCreator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaginatedQuery<T, C extends JsonCreator<T>> {
    private OkHttpClient okHttpClient = new OkHttpClient();

    private C creator;
    private final String url;
    private final String key;
    private final String token;

    public PaginatedQuery(C creator, String url, String key, String token) {
        this.creator = creator;
        this.url = url;
        this.key = key;
        this.token = token;
    }

    public List<T> execute() throws ClientException {
        List<T> result = new ArrayList<>();
        String nextUrl = next(url, result);

        while (nextUrl != null) {
            nextUrl = next(nextUrl, result);
        }

        return result;
    }

    private String next(String url, List<T> result) throws ClientException {
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + token)
                .get()
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
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(creator.fromJson(jsonArray.getJSONObject(i)));
            }
            if (!jsonObject.isNull("next")) {
                return jsonObject.getString("next");
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new ClientException("Could not process response body");
        }
    }
}
