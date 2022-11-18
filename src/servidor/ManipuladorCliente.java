package servidor;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ManipuladorCliente implements Runnable{
    
    public static ArrayList<ManipuladorCliente>lista = new ArrayList<ManipuladorCliente>();
    private Socket socket;
    private BufferedReader leitor;
    private BufferedWriter saida;
    private String nome;


    public ManipuladorCliente(Socket socket){
        try {
            this.socket = socket;
            this.saida = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.leitor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nome = leitor.readLine();

            lista.add(this);
            String novaConexao = "SERVIDOR: " +this.nome+" se conectou";
            fazerBroadcast(novaConexao);
        } catch (Exception e) {
            //fecharConexao(leitor, saida, socket);
        }
    }
    public void fazerBroadcast(String msg){
        for (ManipuladorCliente cliente : lista) {
            try {
                cliente.saida.write(msg);
                cliente.saida.newLine();
                cliente.saida.flush();
                
            } catch (Exception e) {
                fecharConexao(leitor, saida, socket);
            }
        }
    }

    public void removerCliente(){
        lista.remove(this);
        System.out.println("Cliente se desconectou");
        fazerBroadcast("SERVIDOR: "+nome+" se desconectou");
    }

    public void fecharConexao(BufferedReader leitor, BufferedWriter saida, Socket socket){
        removerCliente();
        try {
            leitor.close();
            saida.close();
            socket.close();
                
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    @Override
    public void run() {
        String mensagemDoCliente = "";
        while(socket.isConnected()){
            try {
                mensagemDoCliente = leitor.readLine();
                System.out.println(mensagemDoCliente);
                fazerBroadcast(mensagemDoCliente);

            } catch (Exception e) {
                System.out.println(e);
                fecharConexao(leitor, saida, socket);
                break;
            }
        }
    }

}