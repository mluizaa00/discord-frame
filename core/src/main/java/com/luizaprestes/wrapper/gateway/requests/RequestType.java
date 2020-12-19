package com.luizaprestes.wrapper.gateway.requests;

import lombok.AllArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 @author luiza
 @version-implemented 0.0.1
 @date 12.19.2020
 */
@AllArgsConstructor
public enum RequestType {

    POST (HttpPost.class),
    GET (HttpGet.class),
    DELETE (HttpDelete.class),
    PATCH (HttpPatch.class);

    static final HttpClient CLIENT = HttpClients.createDefault();

    final Class<? extends HttpUriRequest> requestClass;

    public String makeRequest(String url, BasicNameValuePair... headers) {
        try {
            final HttpUriRequest request = this.requestClass.getConstructor(String.class).newInstance(url);

            for (BasicNameValuePair header : headers) {
                request.addHeader(header.getName(), header.getValue());
            }

            final HttpResponse response = CLIENT.execute(request);
            return responseMethod(response, url);
        } catch (
          IOException | NoSuchMethodException | IllegalAccessException |
            InvocationTargetException | InstantiationException exception
        ) {
            exception.printStackTrace();
            return null;
        }
    }

    public String makeRequest(String url, HttpEntity entity, BasicNameValuePair... headers) {
        try {
            if (
              HttpEntityEnclosingRequestBase.class.isAssignableFrom(this.requestClass)
            ) {
                final HttpEntityEnclosingRequestBase request = (HttpEntityEnclosingRequestBase)
                  this.requestClass.getConstructor(String.class).newInstance(url);

                for (BasicNameValuePair header : headers) {
                    request.addHeader(header.getName(), header.getValue());
                }

                request.setEntity(entity);

                final HttpResponse response = CLIENT.execute(request);
                return responseMethod(response, url);
            } else {
                System.out.println("Tried to attach HTTP to invalid type");
            }
        } catch (
          IOException | NoSuchMethodException | IllegalAccessException |
            InvocationTargetException | InstantiationException exception
        ) {
            exception.printStackTrace();
        }
        return null;
    }

    private String responseMethod(final  HttpResponse response, String url) throws IOException {
        final int responseCode = response.getStatusLine().getStatusCode();

        switch (responseCode) {
            case 404:
                System.out.println("Not found.");
            case 403:
                System.out.println("Unable to make request to " + url);
            case 502:
                System.out.println("Gateway unavailable.");
            case 204:
                return null;
        }

        return EntityUtils.toString(response.getEntity());
    }
}
