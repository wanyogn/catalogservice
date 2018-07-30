package com.zhixie.catalog.helper;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;

public class ESClientFactory {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 9200;
    private static final String SCHEMA = "http";

    private static HttpHost httpHost = null;
    private static RestHighLevelClient client = null;

    static {
        init();
    }

    public static void init(){
        httpHost = new HttpHost(HOST, PORT, SCHEMA);
        client = new RestHighLevelClient(RestClient.builder(httpHost));
    }
    public static RestHighLevelClient getClient(){
        return client;
    }
    public static void close() {
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
    }
}
