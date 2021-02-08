package com.luizaprestes.frame.gateway.request;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.utils.Constants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.*;

@RequiredArgsConstructor
@Getter
public class RequestCreator {

    private final String content;
    private final String url;
    private final RequestType type;

    private HttpURLConnection connection;

    private final boolean sendLoginHeaders = true;
    private final boolean getResponse = true;
    private int code = 200;

    private final Frame frame;

    public String makeRequest() {
        if (type == RequestType.PATCH) {
            try {
                final String[] parts = url.split("/", 4);
                final boolean isHttps = parts[0].startsWith("https");

                final Socket clientSocket;
                if (isHttps) {
                    final SocketFactory sslf = SSLSocketFactory.getDefault();
                    clientSocket = sslf.createSocket(parts[2], 443);
                } else {
                    clientSocket = new Socket(parts[2], 80);
                }


                final DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                outputStream.writeBytes(
                  "PATCH /" + parts[3] + " HTTP/1.1\n" +
                  "Host: " + parts[2] + "\n" +
                  "Connection: close\n" +
                  "Content-Length: " + content.length() + "\n" +
                  "Origin: http://discordapp.com\n" +
                  "User-Agent: " + Constants.GITHUB + " " + Constants.VERSION + "\n" +
                  "Content-Type: application/json\n" +
                  "Accept: */*\n" +
                  "authorization: " + frame.getToken() + "\n\n" + content
                );

                if (getResponse) {
                    final DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                    final StringBuilder builder = new StringBuilder();

                    final byte[] buffer = new byte[100];

                    int read;
                    while ((read = inputStream.read(buffer)) >= 0) builder.append(new String(buffer, 0, read));

                    outputStream.close();
                    inputStream.close();
                    clientSocket.close();

                    final String[] responseParts = builder.toString().split("\\r\\n\\r\\n");
                    if (responseParts.length > 1) return responseParts[1];

                    return null;
                }

                outputStream.close();
                clientSocket.close();
                return null;

            } catch (Exception exception) {
                frame.getLogger().atWarning().log("Error while trying to receive message from PATCH http. Value: %s", exception.getMessage());
                return null;
            }
        }

        try {
            final URL obj = new URL(url);
            connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod(type.toString());

            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Content-Length", Integer.toString(content.getBytes().length));
            connection.setRequestProperty("User-Agent", Constants.GITHUB + " " + Constants.VERSION);
            connection.setDoOutput(getResponse);

            if (sendLoginHeaders) connection.addRequestProperty("authorization", frame.getToken());

            if (!(content.getBytes().length == 0)) {
                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.write(content.getBytes());
                outputStream.flush();
                outputStream.close();
            }

            code = connection.getResponseCode();
            switch (code) {
                case 200:
                case 201: {
                    if (getResponse) {
                        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        final StringBuilder response = new StringBuilder();

                        String inputLine;
                        while ((inputLine = bufferedReader.readLine()) != null) response.append(inputLine);

                        bufferedReader.close();

                        return response.toString();
                    }

                    return null;
                }
                default: {
                    return null;
                }
            }
        } catch (IOException exception) {
            frame.getLogger().atWarning().log("Error while trying to receive message from %s http. Value: %s", type.toString(), exception.getMessage());
            return null;
        }
    }

}
