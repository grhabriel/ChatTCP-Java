package servidor;
import java.io.*;
import java.net.*;

public class Servidor{
    private ServerSocket serverSocket;

    public Servidor(int porta){
        try {
            serverSocket = new ServerSocket(porta);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    public void iniciar(){
        while (!serverSocket.isClosed()) {
            System.out.println("Servidor escutando na porta: "+serverSocket.getInetAddress());
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Novo cliente se conectou");
                ManipuladorCliente manipulador = new ManipuladorCliente(socket);
                Thread thread = new Thread(manipulador);
                thread.start();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

 
    
}