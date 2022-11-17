import java.io.*;
import java.net.*;

import servidor.*;

public class AppServidor {
    public static void main(String[] args) throws IOException, UnknownHostException{
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(1234);
            Servidor server = new Servidor(serverSocket);
            server.iniciar();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
        