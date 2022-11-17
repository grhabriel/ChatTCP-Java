package servidor;
import java.io.*;
import java.net.*;

public class Servidor{
    private ServerSocket serverSocket;

    public Servidor(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
    public void iniciar(){
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("Nova conexão");
                ManipuladorCliente controladorCliente = new ManipuladorCliente(socket); 
                Thread thread = new Thread(controladorCliente);
                thread.start();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void fecharSocket(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    
}