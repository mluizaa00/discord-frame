package com.luizaprestes.wrapper.http.requests;

import com.luizaprestes.info.Constants;
import com.luizaprestes.wrapper.Wrapper;
import lombok.Data;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

@Data
public class RequestManager {


    protected String data;
    protected String url;

    protected RequestType type;

    protected HttpURLConnection connection;
    protected boolean sendLoginHeaders = true;
    protected int code;

    protected boolean response = true;

    private final Wrapper api;
    public RequestManager(Wrapper api) {
        this.api = api;
    }
    public String makeRequest() {
        try {
            if (type == RequestType.PATCH) {
                try {
                    String[] urlParts = url.split("/", 4);
                    boolean isHttps = urlParts[0].startsWith("https");

                    Socket clientSocket;

                    if (isHttps) {
                        SocketFactory factory = SSLSocketFactory.getDefault();
                        clientSocket = factory.createSocket(urlParts[2], 443);
                    } else {
                        clientSocket = new Socket(urlParts[2], 80);
                    }

                    DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

                    out.writeBytes(
                      "PATCH " + urlParts[3] + " HTTP/1.1\n" +
                        "Host: " + urlParts[2] + "\n" +
                        "Connection: keep-alive\n" +
                        "Content-Length: " + data.length() + "\n" +
                        "Origin: http://discordapp.com\n" +
                        "User-Agent: " + Constants.GITHUB + " " + Constants.VERSION + "\n" +
                        "Content-Type: application/json\n" +
                        "Accept: */*\n" +
                        "authorization: " + api.getAuthToken() + "\n\n" + data
                    );

                    if (response) {
                        final DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                        final StringBuilder builder = new StringBuilder();

                        final byte[] buffer = new byte[100];
                        int read;

                        while ((read = input.read(buffer)) >= 0) {
                            builder.append(new String(buffer, 0, read));
                        }

                        out.close();
                        input.close();
                        clientSocket.close();

                        final String[] responseParts = builder.toString().split("\\r\\n\\r\\n");

                        if (responseParts.length > 1) {
                            return responseParts[1];
                        }

                        return null;
                    }
                    out.close();
                    clientSocket.close();

                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            final URL obj = new URL(url);
            connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod(type.toString().equals("PATCH") ? "POST" : type.toString());

            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Content-Length", Integer.toString(data.getBytes().length));
            connection.setRequestProperty("User-Agent", Constants.GITHUB + " " + Constants.VERSION);
            connection.setDoOutput(true);

            if (sendLoginHeaders) connection.addRequestProperty("authorization", api.getAuthToken());

            if (!(data.getBytes().length == 0)) {
                DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                output.write(data.getBytes());
                output.flush();
                output.close();
            }

            this.code = connection.getResponseCode();

            switch(code) {
                case 200:
                case 201: {
                    if (response) {
                        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        final StringBuilder response = new StringBuilder();

                        String inputLine;

                        while ((inputLine = reader.readLine()) != null) {
                            response.append(inputLine);
                        }

                        reader.close();
                        return response.toString();
                    }
                    return null;
                }
                case 204:
                    return "";
                default: {
                    System.err.println("Error Queuing " + type + " " + url + "\n\tPayload: " + data + "\n\tResponse: " + code);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
