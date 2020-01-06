package componentes;

import interface_grafica.JanelaInicial;
import java.text.DecimalFormat;
import java.util.TimerTask;

public class Cronometro extends TimerTask {  //realiza a contagem do cronômetro

    private int minutos;
    DecimalFormat fmt1;
    DecimalFormat fmt2;

    public Cronometro(int minutos, int segundos, int milissegundos) {
        this.fmt2 = new DecimalFormat("00");
        this.fmt1 = new DecimalFormat("000");
        this.minutos = minutos;
        this.segundos = segundos;
        this.milissegundos = milissegundos;
    }
    private int segundos;
    private int milissegundos;
    private JanelaInicial gui;

    public void setGui(JanelaInicial gui) {
        this.gui = gui;
    }    

    public int getMinutos() {
        return minutos;
    }

    public int getSegundos() {
        return segundos;
    }

    public int getMilissegundos() {
        return milissegundos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public void setMilissegundos(int milissegundos) {
        this.milissegundos = milissegundos;
    }

    public void resetar() {
        this.minutos = 0;
        this.segundos = 0;
        this.milissegundos = 0;
    }

    @Override
    public void run() {
        
        //lógica do visor que deve aparecer na interface (deve ser executada a cada 1 milissegundo):
        if (milissegundos < 999) {
            milissegundos++;
            gui.getMilissegundos().setText(fmt2.format(milissegundos));
        } else {
            milissegundos = 0;
            gui.getMilissegundos().setText(fmt2.format(milissegundos));
            
            if (segundos < 59) {
                segundos++;
                gui.getSegundos().setText(fmt2.format(segundos));
            } else {
                segundos = 0;
                gui.getSegundos().setText(fmt2.format(segundos));
                minutos++;
                gui.getMinutos().setText(fmt2.format(minutos));
            }
        }
    }
}
