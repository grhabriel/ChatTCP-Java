import cliente.*;

import javax.swing.JFrame;


public class App {

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        Tela tela = new Tela(cliente);
        
        JFrame janela = new JFrame("Chat TCP");    
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(500,500);
        
        janela.add(tela);
            
        janela.setVisible(true);

    }
}
