import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ServerHandler implements HttpHandler {
    private static final Catalogue CATALOGUE;

    static {
        try {
            CATALOGUE = new Catalogue();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws java.io.IOException {
        Map<String, String> query = this.queryToMap(exchange.getRequestURI().getQuery());
        if ("GET".equals(exchange.getRequestMethod())) {
            this.GETHandler(exchange, query);
        } else if ("PUT".equals(exchange.getRequestMethod())) {
            this.PUTHandler(exchange, query);
        } else if ("POST".equals(exchange.getRequestMethod())) {
            this.POSTHandler(exchange, query);
        } else if ("DELETE".equals(exchange.getRequestMethod())) {
            this.DELETEHandler(exchange, query);
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }

    private void GETHandler(HttpExchange exchange, Map<String, String> query) throws IOException {
        System.out.println("Handling GET request");
        String response;
        if (query.containsKey("id")) {
            int id = Integer.parseInt(query.get("id"));
            Producto producto = CATALOGUE.getItem(id);
            response = new Gson().toJson(producto);
        } else {
            response = new Gson().toJson(CATALOGUE.catalogue);
        }
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        System.out.println(response);
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }

    private void PUTHandler(HttpExchange exchange, Map<String, String> query) throws IOException {
        // Parse the item from the request body
        System.out.println("Handling PUT request");
        Producto producto = new Gson().fromJson(new InputStreamReader(exchange.getRequestBody()), Producto.class);
        CATALOGUE.updateItem(producto);
        exchange.sendResponseHeaders(200, -1);
    }

    private void POSTHandler(HttpExchange exchange, Map<String, String> query) throws IOException {
        // Parse the item from the request body
        System.out.println("Handling POST request");
        Producto producto = new Gson().fromJson(new InputStreamReader(exchange.getRequestBody()), Producto.class);
        CATALOGUE.addItem(producto);
        exchange.sendResponseHeaders(201, -1);
    }

    private void DELETEHandler(HttpExchange exchange, Map<String, String> query) throws IOException {
        // Parse the item ID from the query parameters
        System.out.println("Handling DELETE request");
        int id = Integer.parseInt(query.get("id"));
        CATALOGUE.deleteItem(id);
        exchange.sendResponseHeaders(204, -1);
    }

    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                } else {
                    result.put(entry[0], "");
                }
            }
        }
        System.out.println("Query request: " + query);
        return result;
    }
}
