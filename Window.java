import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.*;
import java.awt.event.ActionEvent;

public class Window extends JFrame {

    public Giocatore turno = Giocatore.gX;

    JButton[] btn = new JButton[9];
    JPanel panel = new JPanel(), sezioneInfo = new JPanel();;
    JLabel testo, infoTesto1, infoTesto2;

    public Window(String title) {
        super(title);

        // Setup Finestra
        setSize(450,400);
        // Centra la posizione nello schermo
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        sezioneInfo.setLayout(new BorderLayout());
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
            btn[i].setFont(new Font("duran", Font.PLAIN, 36));
            btn[i].setFocusable(false); // making sure they can't be focused (like with tab)
            btn[i].addActionListener(this::actionPerformed); // adding an action listener for when we press it
            panel.add(btn[i]); // adding buttons to our mainPanel 3 by 3 grid
        }

        add(panel, BorderLayout.CENTER); // adding the mainPanel to the center of our window

        infoTesto1 = new JLabel(" Info Comandi: ", JLabel.LEFT);
        infoTesto2 = new JLabel("R - Riavvia la Partita | A - Arrenditi ", JLabel.LEFT);

        infoTesto1.setFont(new Font("Arial", Font.BOLD, 16));
        infoTesto1.setForeground(Color.RED);

        infoTesto2.setFont(new Font("Arial", Font.BOLD, 16));
        infoTesto2.setForeground(Color.BLACK);

        sezioneInfo.add(infoTesto1, BorderLayout.WEST);
        sezioneInfo.add(infoTesto2, BorderLayout.EAST);


        testo = new JLabel("", JLabel.CENTER);

        testo.setFont(new Font("Arial", Font.BOLD, 16));
        testo.setForeground(Color.BLUE);

        add(sezioneInfo, BorderLayout.NORTH);

        add(testo, BorderLayout.SOUTH);

        setVisible(true);
    }

    /** Action to do when a button is pressed */
    protected void actionPerformed(ActionEvent e){

    }

    public void sceltaGiocatore() {
        String[] scelte = new String[] {"X", "O"};
        String messaggio = "Scegli chi deve iniziare";
        int btn = JOptionPane.YES_NO_OPTION;
        int scelta = JOptionPane.showOptionDialog(this, messaggio, "Inizio Partita", btn, JOptionPane.INFORMATION_MESSAGE, null, scelte, scelte[0]);

        if(scelta == JOptionPane.YES_OPTION){
            turno = Giocatore.gX;
        } else if(scelta == JOptionPane.NO_OPTION){
            turno = Giocatore.gO;
        }
    }
}
