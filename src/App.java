import cliente.*;

import javax.swing.JFrame;


public class App {

    public static void main(String[] args) {
        String ip = "192.168.1.7";
        int porta = 8080;
        
        Tela tela = new Tela(ip,porta);
        
        JFrame janela = new JFrame("Chat TCP");    
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(500,500);
        
        janela.add(tela);
            
        janela.setVisible(true);

    }
}
