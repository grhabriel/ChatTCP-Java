package servidor;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ManipuladorCliente implements Runnable{

    public static ArrayList<ManipuladorCliente> clientes = new ArrayList<>();
    private Socket socket;
    private BufferedReader entrada; //input
    private BufferedWriter saida; //output
    private String nomeUsuario; 

    public ManipuladorCliente(Socket socket) throws IOException{
        try{
            this.socket = socket;
            this.saida = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nomeUsuario = entrada.readLine();
            clientes.add(this);
            saida.write(clientes.toString());
            saida.newLine();
            saida.flush();
            realizarBroadcast("[SERVIDOR]: " + this.nomeUsuario + " entrou no chat");
        }
        catch (Exception e){
            System.out.println("Erro: " + e.getMessage());
            fecharSala(socket, entrada, saida);
        }
    }

    @Override
    public void run(){
        String mensagem;

        while (true){
            try{
                mensagem = entrada.readLine();
                realizarBroadcast(mensagem);
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                fecharSala(socket, entrada, saida);
                break;
            }
        }
    }

    public void realizarBroadcast(String mensagem){
        for (ManipuladorCliente cliente : clientes){
            try{
                if (!cliente.nomeUsuario.equals(nomeUsuario)){
                    cliente.saida.write(mensagem);
                    cliente.saida.newLine();
                    cliente.saida.flush();
                }
            }
            catch (IOException e){
                System.out.println(e.getMessage());
                fecharSala(socket, entrada, saida);
            }
        }
    }

    public void removerUsuarioDoChat(){
        realizarBroadcast("[SERVIDOR]: " + this.nomeUsuario + " nao tankou e foi de comes e bebes");
        clientes.remove(this);
    }

    public void fecharSala(Socket socket, BufferedReader br, BufferedWriter bw){
        removerUsuarioDoChat();
        try{
            if (br != null)
                br.close();
            
            if (bw != null)
                bw.close();

            if (socket != null)
                socket.close();
        }
        catch (IOException e){
            System.out.println("Erro: ffdsfa" + e.getMessage());
        }
    }
}