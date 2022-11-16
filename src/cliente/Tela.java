package cliente;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;



public class Tela extends JPanel {

    /********Primeira Tela*******/
    private JTextField campoNome;
    private JButton botaoEnviar;
    /****************************/

    /********Segunda Tela*******/
    private JTextField campoDeMensagemChat; 
    private JButton botaoEnviarMensagem;
    private JTextArea areaDeMensagem;
    private JTextArea textoConectados;
    /****************************/
    
    
    private String nome;
    private Cliente cliente;

    public Tela(){
        setLayout(null);
        telaPerguntaNomeIniciar();
    }

    public void telaPerguntaNomeIniciar(){	
		campoNome = new JTextField();
		campoNome.setBounds(184, 76, 114, 21);
		add(campoNome);
		campoNome.setColumns(10);
		
        JLabel labelNome = new JLabel("Digite seu nome de usuario:");
		labelNome.setBounds(12, 78, 178, 17);
		add(labelNome);
		
		botaoEnviar = new JButton("Entrar");
		botaoEnviar.setBounds(310, 73, 105, 27);
		add(botaoEnviar);
        botaoEnviar.addActionListener(e -> pegarNome());
        
    }
    public void telaDeChatting(){
        campoDeMensagemChat = new JTextField();
		campoDeMensagemChat.setBounds(12, 329, 351, 45);
		add(campoDeMensagemChat);
		campoDeMensagemChat.setColumns(10);
		
		botaoEnviarMensagem = new JButton("Enviar");
		botaoEnviarMensagem.setBounds(375, 338, 105, 27);
		add(botaoEnviarMensagem);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(129, 54, 351, 263);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
		
		areaDeMensagem = new JTextArea();
		areaDeMensagem.setLineWrap(true);
		areaDeMensagem.setWrapStyleWord(true);
		scrollPane.setViewportView(areaDeMensagem);
		areaDeMensagem.setEditable(false);
		
		JLabel lblSejaBemVindo = new JLabel("Seja Bem Vindo");
		lblSejaBemVindo.setBounds(143, 27, 234, 17);
		add(lblSejaBemVindo);
		
		JLabel labelConectados = new JLabel("Conectados");
		labelConectados.setBounds(12, 29, 95, 17);
		add(labelConectados);
		
		JScrollPane scrollPaneConectados = new JScrollPane();
		scrollPaneConectados.setBounds(12, 55, 105, 172);
		add(scrollPaneConectados);
		
		textoConectados = new JTextArea();
        textoConectados.setEditable(false);
		scrollPaneConectados.setViewportView(textoConectados);
    }

    private void pegarNome(){
        if(campoNome.getText().contains(" ")){
            return;
        }
        
        this.nome = campoNome.getText();
        System.out.printf("Nome de Usuario: %s",this.nome);
        //Limpar Tela
        this.removeAll();
        this.revalidate();
        telaDeChatting();
        this.repaint();
        //Chamar funcao de preencher com a nova tela
    }

    public String getNome() {
        return nome;
    }
}
