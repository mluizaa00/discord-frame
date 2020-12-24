package com.luizaprestes.wrapper.gateway.request;

import lombok.AllArgsConstructor;
import okhttp3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;

@AllArgsConstructor
public class RequestType {

    protected static final OkHttpClient client = new OkHttpClient();

    protected static  final JSONParser parser = new JSONParser();

    public static String makePostRequest(String url, String content, String value) {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        final RequestBody body = RequestBody.create(content, JSON);

        final Request request = new Request.Builder()
          .url(url)
          .post(body)
          .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.toString());

            final JSONObject obj = (JSONObject) parser.parse(String.valueOf(response));
            return (String) obj.get(value);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String makeGetRequest(String url, String value, String token) {
        final Request request = new Request.Builder()
          .url(url)
          .addHeader("content-type", "application/json")
          .addHeader("authorization", token)
          .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.toString());

            final JSONObject obj = (JSONObject) parser.parse(String.valueOf(response));
            return (String) obj.get(value);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
