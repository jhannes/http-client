package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

// A better name would be HttpController
public interface ControllerMcControllerface {
    void handle(HttpMessage request, Socket clientSocket) throws IOException, SQLException;
}
