package com.zhixie.catalog.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHandler {
    public static String httpPostCall(String url, String requestBody)
            throws IOException
    {
        String ret;
        HttpURLConnection connection = null;
        try {
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("content-type","application/json");
            connection.setConnectTimeout(1000);
            connection.getOutputStream().write(requestBody.getBytes());
            InputStream inStm = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStm));
            ret = reader.readLine();
        }finally {
            if(connection!=null)connection.disconnect();
        }
        return ret;
    }
}
