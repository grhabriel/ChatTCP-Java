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
            enviarLista();
            String novaConexao = "SERVIDOR: " +this.nome+" se conectou";
            fazerBroadcast(novaConexao);
            lista.add(this);
            

        } catch (Exception e) {
            //fecharConexao(leitor, saida, socket);
        }
    }
    private void enviarLista(){
        String listaStripada = "LISTA:";
        for (ManipuladorCliente cliente : lista) {
            listaStripada+=cliente.nome+",";
        }

        try {
            this.saida.write(listaStripada);
            this.saida.newLine();
            this.saida.flush();
        } catch (IOException e) {
            e.printStackTrace();
            fecharConexao(leitor, saida, socket);
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

    public synchronized void removerCliente(){
        if(lista.remove(this)){
            System.out.println("Cliente se desconectou");
            System.out.println(nome);
            fazerBroadcast("SERVIDOR: "+nome+" se desconectou");
        }
    }

    public void fecharConexao(  BufferedReader leitor, BufferedWriter saida, Socket socket){
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