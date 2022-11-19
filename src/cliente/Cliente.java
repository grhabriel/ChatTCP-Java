package cliente;


import java.io.*;
import java.net.*;

import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

public class Cliente{
    /*Informações do cliente **/
    private Socket socket;
    private BufferedReader entrada;
    private BufferedWriter saida;
    private String nomeCliente;
    
    /*****Interface grafica*/
    private DefaultListModel<String> areaConectados; 
    private JTextArea areaMensagem;

    public Cliente(Socket socket,String nome, DefaultListModel<String> areaConectados, JTextArea areaMensagem){
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
            //fecharConexao(entrada, saida, socket);
            System.exit(1);
        }
    }

    public void enviarMensagem(String msg){
        try {
            saida.write(msg);
            saida.newLine();
            saida.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1); 
        }
    }


    public void escutarMensagem(){
        new Thread(new Runnable() {
            @Override
            public void run(){
                
                while (socket.isConnected()) {
                    String mensagem_do_grupo = "";
                    try {
                        mensagem_do_grupo = entrada.readLine();
                        
                        tratarMensagem(mensagem_do_grupo);
                        if(!mensagem_do_grupo.startsWith("LISTA:")){
                            areaMensagem.append(mensagem_do_grupo+"\n");
                        }
                    } catch (Exception e) {
                        fecharConexao(entrada, saida, socket);
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
            }
        }).start();
    }
    private void tratarMensagem(String msg){
        if(msg.startsWith("SERVIDOR:")){
            if (msg.endsWith(" se conectou")) {
                adicionarConectados(msg);
            }
            else if(msg.endsWith(" se desconectou")){
                retirarDesconectados(msg);
            }
        }
        if (msg.startsWith("LISTA:")) {
            tratarListaDeConectados(msg);
        }
    }

    private void tratarListaDeConectados(String msg){
        String[] msgSplit =  msg.replace("LISTA:","").split(",");
        for (String nome : msgSplit) {
            areaConectados.addElement(nome);
        }
        
    }
    
    private void adicionarConectados(String msg){
        String nome = msg.replace("SERVIDOR:","");
        nome = nome.replace(" se conectou", "");
        areaConectados.addElement(nome);
    }
    private void retirarDesconectados(String msg){
        String nome = msg.replace("SERVIDOR:","");
        nome = nome.replace(" se desconectou", "");
        areaConectados.removeElement(nome);
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