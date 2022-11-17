import java.io.*;
import java.net.*;

import servidor.*;

public class AppServidor {
    public static void main(String[] args) throws IOException, UnknownHostException{
        Servidor server = new Servidor(8080);
        server.iniciar();
    }
}
