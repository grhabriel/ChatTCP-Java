package cliente;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;



public class Tela extends JPanel {
    private String ipDoServidor;
    private int porta;

    /********Primeira Tela*******/
    private JTextField campoNome;
    private JButton botaoEnviar;
    /****************************/

    /********Segunda Tela*******/
    private JTextArea campoDeMensagemChat; 
    private JButton botaoEnviarMensagem;
    private JTextArea areaDeMensagem;
    private JTextArea textoConectados;
    /****************************/
    
    
    private String nome;
    private Cliente cliente;

    public Tela(String ipDoServidor, int porta){
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
        campoDeMensagemChat = new JTextArea();
		campoDeMensagemChat.setBounds(12, 329, 351, 45);
		campoDeMensagemChat.setLineWrap(true);
		campoDeMensagemChat.setWrapStyleWord(true);
        add(campoDeMensagemChat);
		campoDeMensagemChat.setColumns(10);
        
		
		botaoEnviarMensagem = new JButton("Enviar");
		botaoEnviarMensagem.setBounds(375, 338, 105, 27);
		add(botaoEnviarMensagem);
        botaoEnviarMensagem.addActionListener(e -> enviar());
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(129, 54, 351, 263);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
		
		areaDeMensagem = new JTextArea();
		areaDeMensagem.setLineWrap(true);
		areaDeMensagem.setWrapStyleWord(true);
		scrollPane.setViewportView(areaDeMensagem);
		areaDeMensagem.setEditable(false);
		
        String msgBoasVindas = String.format("Seja Bem Vindo %s",this.nome);
		JLabel lblSejaBemVindo = new JLabel(msgBoasVindas);
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
        this.cliente = new Cliente(ipDoServidor, porta, nome,textoConectados,areaDeMensagem);

    }

    public void enviar(){
        String msg = campoDeMensagemChat.getText();
        if(msg == " " || msg ==""){
            return;
        }
        msg = nome+":"+msg;
        campoDeMensagemChat.setText("");
        cliente.enviarMensagem(msg);
    }
    private void pegarNome(){
        if(campoNome.getText().contains(" ")){
            campoNome.setText("Guest");;
        }
        
        this.nome = campoNome.getText();
        System.out.printf("Nome de Usuario: %s",this.nome);
        //Limpar Tela
        this.removeAll();
        this.revalidate();
        
        
        telaDeChatting();
        this.repaint();
        textoConectados.append(this.nome+"(você)\n");
    }

    public String getNome() {
        return nome;
    }
}
