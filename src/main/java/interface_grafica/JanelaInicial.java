package interface_grafica;

import componentes.Cronometro;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import javax.swing.*;

public class JanelaInicial extends JFrame {  //é a única janela do programa

    private JButton fechar;
    private JButton reset;
    private JButton iniciarParar;
    private JTextField minutos;
    private JTextField segundos;
    private JTextField milissegundos;
    private Container camposTexto;  //vai segurar os campos de texto
    private GridLayout estiloCamposTexto;  //layout do container camposTexto
    private GridBagLayout estiloJanela;  //layout da janela
    private iniciarListener listener;  //é preciso guardar esse listener para a resetá-lo depois.
    java.util.Timer timer;
    Cronometro cronometro;

    public JanelaInicial() {
        //defife o título da janela:
        super("Cronômetro");

        //cria os três botões da janela:
        fechar = new JButton("Fechar");
        fechar.addActionListener(new fecharListener());
        
        reset = new JButton("Reset");
        reset.addActionListener(new resetListener());
        reset.setEnabled(false);  //resetar fica indisponível a principio
        
        iniciarParar = new JButton("Iniciar");  //Esse botão é criado como "Iniciar", mas depois de iniciada a contagem, será alterado para "Parar"
        listener = new iniciarListener();
        iniciarParar.addActionListener(listener);

        //cria os campos de texto:
        minutos = new JTextField(2);
        minutos.setText("00");
        segundos = new JTextField(2);
        segundos.setText("00");
        milissegundos = new JTextField(4);
        milissegundos.setText("000");

        //define os campos de texto como não editáveis:
        minutos.setEditable(false);
        segundos.setEditable(false);
        milissegundos.setEditable(false);

        //instancia o layout da janela e o define:
        estiloJanela = new GridBagLayout();
        this.setLayout(estiloJanela);

        //cria um container para abrigar os campos de texto:
        camposTexto = new Container();

        //instancia o layout do cotainer:
        estiloCamposTexto = new GridLayout(1, 3, 10, 0);
        camposTexto.setLayout(estiloCamposTexto);

        //adiciona os campos ao container:
        camposTexto.add(minutos);
        camposTexto.add(segundos);
        camposTexto.add(milissegundos);

        //adiciona o container à janela:        
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.gridwidth = 3;
        gbc1.weighty = 1.0;
        add(camposTexto);
        this.getContentPane().add(camposTexto, gbc1);

        //adiciona os botões à janela:
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        gbc2.weighty = 0.5;
        gbc2.weightx = 0.2;
        this.getContentPane().add(fechar, gbc2);

        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 1;
        gbc3.gridy = 1;
        gbc3.weightx = 0.2;
        this.getContentPane().add(reset, gbc3);

        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.gridx = 2;
        gbc4.gridy = 1;
        gbc4.weightx = 0.2;
        this.getContentPane().add(iniciarParar, gbc4);

        //define características da janela e adiciona os componentes criados:
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 100);
        this.setResizable(false);
        this.setVisible(true);
    }

    class fecharListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            JanelaInicial.this.dispose();  //fecha a janela
        }

    }

    class resetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //zera o visor:
            JanelaInicial.this.milissegundos.setText("000");
            JanelaInicial.this.segundos.setText("00");
            JanelaInicial.this.minutos.setText("00");
            
            listener.min = 0;
            listener.sec = 0;
            listener.msec = 0;
        }

    }

    class iniciarListener implements ActionListener {
        //Variáveis para guardar o estado anterior do TimerTask:
        private int min = 0;
        private int sec = 0;
        private int msec = 0;

        @Override
        public void actionPerformed(ActionEvent ae) {

            if (JanelaInicial.this.iniciarParar.getText().equals("Iniciar") == true) {
                //define os comandos a serem executados na thread:
                cronometro = new Cronometro(min, sec, msec);
                cronometro.setGui(JanelaInicial.this);
                timer = new java.util.Timer(true);

                //inicia a thread:
                timer.scheduleAtFixedRate(cronometro, 0, 1);

                //muda o texto do botão:
                JanelaInicial.this.iniciarParar.setText("Parar");
                
                reset.setEnabled(false);  //resetar bloqueado
            } else {
                min = cronometro.getMinutos();
                sec = cronometro.getSegundos();
                msec = cronometro.getMilissegundos();
                timer.cancel();
                JanelaInicial.this.iniciarParar.setText("Iniciar");
                reset.setEnabled(true);  //resetar agora é uma opção
            }
        }

    }

    public JTextField getMinutos() {
        return minutos;
    }

    public JTextField getSegundos() {
        return segundos;
    }

    public JTextField getMilissegundos() {
        return milissegundos;
    }

}
