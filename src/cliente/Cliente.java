package cliente;


import java.io.*;
import java.net.*;

import javax.swing.JTextArea;

public class Cliente{
    /*Informações do cliente **/
    private Socket socket;
    private BufferedReader entrada;
    private BufferedWriter saida;
    private String nomeCliente;
    
    /*****Interface grafica*/
    private JTextArea areaConectados; 
    private JTextArea areaMensagem;

    public Cliente(Socket socket,String nome, JTextArea areaConectados, JTextArea areaMensagem){
        this.nomeCliente = nome;
        this.areaMensagem = areaMensagem;
        this.areaConectados = areaConectados;
        
        try {
            this.socket = socket;
            this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.saida = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            enviarMensagem(nomeCliente);
        } catch (Exception e) {
            System.out.println(e);
            fecharConexao(entrada, saida, socket);
            System.exit(1);
        }
    }

    public void enviarMensagem(String msg){
        try {
            saida.write(msg);
            saida.newLine();
            saida.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void escutarMensagem(){
        new Thread(new Runnable() {
            @Override
            public void run(){
                String mensagem_do_grupo;
                while (socket.isConnected()) {
                    try {
                        mensagem_do_grupo = entrada.readLine();
                        System.out.println(mensagem_do_grupo);
                        areaMensagem.append(mensagem_do_grupo+"\n");
                    } catch (Exception e) {
                        fecharConexao(entrada, saida, socket);
                        System.out.println(e);
                    }
                }
            }
        }).start();
    }

    public void fecharConexao(BufferedReader leitor, BufferedWriter saida, Socket socket){
        try {
            leitor.close();
            saida.close();
            socket.close();
                
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}