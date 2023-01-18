package com.bridgelabz;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Handlers {
    public static class RootHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            String responce = "<h1>Server Start Success if you see this message</h1>" + "<h1>Port: "+ SimpleHttpServer.port+"</h1>";
            exchange.sendResponseHeaders(200,responce.length());
            OutputStream os = exchange.getResponseBody();
            os.write(responce.getBytes());
            os.close();
        }
    }

    public static class EchoHeaderHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            Headers headers = exchange.getRequestHeaders();
            Set<Map.Entry<String, List<String>>> entries =headers.entrySet();
            String responce = " ";
            for (Map.Entry<String, List<String>> entrie : entries) {
                responce += entrie.toString() + "\n";
                exchange.sendResponseHeaders(200,responce.length());
                OutputStream os = exchange.getResponseBody();
                os.write(responce.getBytes());
                os.close();
            }
        }
    }

    public static class EchoGetHandlers implements HttpHandler{
        public void handle(HttpExchange exchange) throws IOException {
            Map<String, Object> parameters = new HashMap<>();
            URI requestedUri = exchange.getRequestURI();
            String query = requestedUri.getRawQuery();
            parseQuery(query,parameters);
            String responce = " ";
            for (String key: parameters.keySet())
                responce += key + " = " +parameters.get(key) + "\n";
            exchange.sendResponseHeaders(200,responce.length());
            OutputStream os = exchange.getResponseBody();
            os.write(responce.getBytes());
            os.close();
        }
    }

    public static class EchoPostHandlers implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            Map<String, Object> parameters = new HashMap<>();
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String query = br.readLine();
            parseQuery(query, parameters);
            String responce = "";
            for (String key: parameters.keySet())
                responce += key + " = " +parameters.get(key) + "\n";
            exchange.sendResponseHeaders(200,responce.length());
            OutputStream os = exchange.getResponseBody();
            os.write(responce.getBytes());
            os.close();
        }
    }
    public static void parseQuery (String query, Map<String, Object> parameters) throws UnsupportedEncodingException {
        if (query != null){
            String pairs [] = query.split("[&]");
            for (String pair: pairs) {
                String param[] = pair.split("[=]");
                String key = null;
                String value = null;
                if (param.length > 0){
                    key = URLDecoder.decode(param[0],System.getProperty("file.encoding"));
                }
                if (param.length > 1){
                    value = URLDecoder.decode(param[1],System.getProperty("file.encoding"));
                }
                parameters.put(key,value);
            }
        }
    }
}
