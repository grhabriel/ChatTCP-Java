import cliente.*;

import javax.swing.JFrame;


public class App {

    public static void main(String[] args) {
        Tela tela = new Tela();
        JFrame janela = new JFrame("Chat TCP");    
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(500,500);
        
        janela.add(tela);
            
        janela.setVisible(true);

    }
}
