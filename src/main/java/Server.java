import com.sun.net.httpserver.*;

import java.io.*;
import java.net.*;

public class Server {
    HttpServer server;
    int port;

    Server(int port) throws IOException {
        this.port = port;
        server = HttpServer.create(new InetSocketAddress(this.port), 0);
        server.createContext("/tienda", new ServerHandler());
        server.setExecutor(null);
    }

    void start() {
        server.start();
        System.out.println("Servidor iniciado en el puerto " + this.port);
    }
}