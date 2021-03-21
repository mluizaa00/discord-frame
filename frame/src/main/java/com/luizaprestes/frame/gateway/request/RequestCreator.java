package com.luizaprestes.frame.gateway.request;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.exception.InvalidRequestException;
import com.luizaprestes.frame.util.Constants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

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

    @SneakyThrows
    public String create() {
        if (type == RequestType.PATCH) {
            try {
                // TODO: Create OkHTTP request
            } catch (Exception exception) {
                frame.getLogger().atWarning().log(
                        "Error while trying to receive message from PATCH http. Value: %s",
                        exception.getMessage()
                );
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
                        while ((inputLine = bufferedReader.readLine()) != null) {
                            response.append(inputLine);
                        }

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
            throw new InvalidRequestException(
                    "Error while trying to receive message from %s http. Value: " + type.toString(),
                    exception
            );
        }
    }

}
