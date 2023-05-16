import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener  {

    public String turno = Giocatore.gX;

    JButton[] btn = new JButton[9];
    JPanel panel = new JPanel(), sezioneInfo = new JPanel(), sezionePunteggio = new JPanel();
    JLabel testo, infoTesto1, infoTesto2, imgX, imgO, puntiX, puntiO;

    public int punteggioX = 0, punteggioO = 0;

    public Window(String title) {
        super(title);

        // Setup Finestra
        setSize(450,400);
        // Centra la posizione nello schermo
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        sezioneInfo.setLayout(new BorderLayout());
        sezionePunteggio.setLayout(new GridLayout(2, 2));
        setResizable(false);
        panel.setLayout(new GridLayout(3,3));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Rende più "bello" il tutto
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
            unsupportedLookAndFeelException.printStackTrace();
        }

        sceltaGiocatore();

        for (int i = 0; i < 9; i++) {
            btn[i] = new JButton("");
            btn[i].setBackground(null);
            btn[i].setFont(new Font("Arial", Font.PLAIN, 36));
            btn[i].setFocusable(false); // making sure they can't be focused (like with tab)
            btn[i].addActionListener(this); // adding an action listener for when we press it
            panel.add(btn[i]); // adding buttons to our mainPanel 3 by 3 grid
        }

        add(panel, BorderLayout.CENTER); // adding the mainPanel to the center of our window

        infoTesto1 = new JLabel(" Info Comandi: ", JLabel.LEFT);
        infoTesto2 = new JLabel("R - Riavvia la Partita | A - Arrenditi | C - Resetta Punteggio ", JLabel.LEFT);

        imgX = new JLabel("X: ");
        imgX.setFont(new Font("Arial", Font.BOLD, 24));
        imgX.setForeground(Color.BLUE);
        puntiX = new JLabel(String.valueOf(punteggioX));
        puntiX.setFont(new Font("Arial", Font.PLAIN, 20));

        imgO = new JLabel("O: ");
        imgO.setFont(new Font("Arial", Font.BOLD, 24));
        imgO.setForeground(Color.RED);
        puntiO = new JLabel(String.valueOf(punteggioO));
        puntiO.setFont(new Font("Arial", Font.PLAIN, 20));

        infoTesto1.setFont(new Font("Arial", Font.BOLD, 14));
        infoTesto1.setForeground(Color.RED);

        infoTesto2.setFont(new Font("Arial", Font.BOLD, 12));
        infoTesto2.setForeground(Color.BLACK);

        sezioneInfo.add(infoTesto1, BorderLayout.WEST);
        sezioneInfo.add(infoTesto2, BorderLayout.EAST);

        sezionePunteggio.add(imgX);
        sezionePunteggio.add(puntiX);
        sezionePunteggio.add(imgO);
        sezionePunteggio.add(puntiO);

        testo = new JLabel("", JLabel.CENTER);

        testo.setFont(new Font("Arial", Font.BOLD, 16));
        testo.setForeground(Color.BLUE);

        add(sezioneInfo, BorderLayout.NORTH);
        add(sezionePunteggio, BorderLayout.EAST);
        add(testo, BorderLayout.SOUTH);

        setVisible(true);
    }

    /** Action to do when a button is pressed */
    public void actionPerformed(ActionEvent e){

    }

    public void sceltaGiocatore() {
        String[] scelte = new String[] {"X", "O"};
        String messaggio = "Scegli chi deve iniziare";
        int btn = JOptionPane.YES_NO_OPTION;
        int scelta = JOptionPane.showOptionDialog(this, messaggio, "Inizio Partita", btn, JOptionPane.INFORMATION_MESSAGE, null, scelte, scelte[0]);

        if(scelta == JOptionPane.CLOSED_OPTION){
            sceltaGiocatore();
        }
        if(scelta == JOptionPane.YES_OPTION){
            turno = Giocatore.gX;
        } else if(scelta == JOptionPane.NO_OPTION){
            turno = Giocatore.gO;
        }
    }
}
