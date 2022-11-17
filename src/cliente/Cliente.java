package cliente;


import java.io.*;
import java.net.*;

import javax.swing.JTextArea;

public class Cliente{
    /*******Gerar conexão*****/
    private String ipDoServidor;
    private int portaServidor;
    
    /*Informações do cliente **/
    private Socket socket;
    private BufferedReader entrada;
    private BufferedWriter saida;
    private String nomeCliente;
    
    /*****Interface grafica*/
    private JTextArea areaConectados; 
    private JTextArea areaMensagem;

    public Cliente(String ipDoServidor, int portaDoServidor, String nome, JTextArea areaConectados, JTextArea areaMensagem){
        this.ipDoServidor = ipDoServidor;
        this.portaServidor = portaDoServidor;
        this.nomeCliente = nome;
        try {
            this.socket = new Socket(ipDoServidor,portaDoServidor);
            this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.saida = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e) {
            System.out.println(e);
        }
        //acenoAoServidor();
    }

    private void acenoAoServidor(){
        String listaDeConectados = null;
        try {
            String mensagem = String.format("Conexao:%s",nomeCliente);
            saida.write(mensagem);
            saida.newLine();
            saida.flush();
            System.out.println("first");
            listaDeConectados = entrada.readLine();
            System.out.println("eu");
        } catch (Exception e) {
            System.out.println(e);
        }
        
        tratarLista(listaDeConectados); 
    }

    private void tratarLista(String lista){
        if(lista == null){
            System.exit(1);
        }
        String[] array = lista.split(",");
        for (String pessoa : array) {
            areaConectados.append(pessoa+"\n");
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
                        areaMensagem.append(mensagem_do_grupo+"\n");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }).start();
    }
}