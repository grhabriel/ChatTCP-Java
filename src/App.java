
import javax.swing.JFrame;
import cliente.*;

public class App {
    public static void main(String[] args) {
        TelaPergunta perguntaNome = new TelaPergunta();
        JFrame janela = new JFrame("Chat TCP");    
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(500,500);
        janela.setVisible(true);
        

        janela.add(perguntaNome);
        
        //Troca de Paineis
        janela.getContentPane().removeAll();
        //janela.getContentPane().add();
        
        
            
    }
}
