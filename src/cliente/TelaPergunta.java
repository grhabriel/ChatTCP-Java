package cliente;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaPergunta extends JPanel {
    private JTextField campoNome;
    private JButton botaoEnviar;
    private JLabel labelNome;
    private String nome;
    private Cliente cliente;

    public TelaPergunta(){
        setLayout(null);
        telaPerguntaNomeIniciar();
    }

    public void telaPerguntaNomeIniciar(){	
		campoNome = new JTextField();
		campoNome.setBounds(184, 76, 114, 21);
		add(campoNome);
		campoNome.setColumns(10);
		
		labelNome = new JLabel("Digite seu nome de usuario:");
		labelNome.setBounds(12, 78, 178, 17);
		add(labelNome);
		
		botaoEnviar = new JButton("Entrar");
		botaoEnviar.setBounds(310, 73, 105, 27);
		add(botaoEnviar);
        botaoEnviar.addActionListener(e -> pegarNome());
        
    }
    private void pegarNome(){
        if(campoNome.getText().contains(" ")){
            return;
        }
        
        this.nome = campoNome.getText();
        System.out.printf("Topster: %s",this.nome);
        //Limpar Tela
        this.removeAll();
        this.revalidate();
        this.repaint();
        //Chamar funcao de preencher com a nova tela
    }

    public String getNome() {
        return nome;
    }
}
