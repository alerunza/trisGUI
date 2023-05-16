import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class Giocatore {
    public static final String gX = "X";
    public static final String gO = "O";
}

public class Tris extends Window {

    private boolean statusPartita = true;
    private boolean resa = false;
    private int resaConta = 0;
    public String statusVittoria = "";

    public Tris(String title) {
        super(title);

        if(turno.equals(Giocatore.gX)){
            testo.setForeground(Color.BLUE);
        } else if(turno.equals(Giocatore.gO)){
            testo.setForeground(Color.RED);
        }
        testo.setText(turno + " - Turno");

        // è necessario per il KeyEvent
        this.setFocusable(true);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyHandler(e);
            }
        });
    }



   /** fa il controllo se ha vinto. Resituisce nella posizone 0, 1 per verificare la vittoria e poi le altre 3
    posizioni sono la combinazione vincente.  */
    public int[] vittoria() {
        if (btn[0].getText().equals(btn[1].getText()) && btn[2].getText().equals(btn[0].getText()) && !btn[0].getText().equals("")){
            return new int[]{1, 0, 1, 2};
        } else if (btn[3].getText().equals(btn[4].getText()) && btn[5].getText().equals(btn[3].getText()) && !btn[3].getText().equals("")){
            return new int[]{1, 3, 4, 5};
        } else if (btn[6].getText().equals(btn[7].getText()) && btn[8].getText().equals(btn[6].getText()) && !btn[6].getText().equals("")){
            return new int[]{1, 6, 7, 8};
        } else if (btn[0].getText().equals(btn[3].getText()) && btn[6].getText().equals(btn[0].getText()) && !btn[0].getText().equals("")){
            return new int[]{1, 0, 3, 6};
        } else if (btn[1].getText().equals(btn[4].getText()) && btn[7].getText().equals(btn[1].getText()) && !btn[1].getText().equals("")){
            return new int[]{1, 1, 4, 7};
        } else if (btn[2].getText().equals(btn[5].getText()) && btn[8].getText().equals(btn[2].getText()) && !btn[2].getText().equals("")){
            return new int[]{1, 2, 5, 8};
        } else if (btn[0].getText().equals(btn[4].getText()) && btn[8].getText().equals(btn[0].getText()) && !btn[0].getText().equals("")){
            return new int[]{1, 0, 4, 8};
        } else if (btn[2].getText().equals(btn[4].getText()) && btn[6].getText().equals(btn[2].getText()) && !btn[2].getText().equals("")){
            return new int[]{1, 2, 4, 6};
        }
        //se non ha vinto resituisce 0, ovvero che ha perso
        return new int[]{0};
    }


    /** Actions to do when the button is pressed */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();


        // while the game is running
        if (statusPartita) {
                // if GiocatoreX played  an empty button then make the move
            if (turno.equals(Giocatore.gX) && button.getText().equals("")) {
                button.setForeground(Color.BLUE);
                button.setFont(new Font("Arial", Font.PLAIN, 36));
                button.setText("X");

                // checking if X won
                if (vittoria()[0] == 1) {
                    int[] pos = new int[]{vittoria()[1], vittoria()[2], vittoria()[3]};

                    statusVittoria = "X - Ha Vinto";

                    punteggioX++;
                    //System.out.println(punteggioX);
                    puntiX.setText(String.valueOf(punteggioX));

                    testo.setText(statusVittoria);
                    statusPartita = false;

                    // setting win combination to green
                    for (int p : pos){ // for-each
                        btn[p].setOpaque(true);
                        btn[p].setBorderPainted(false);
                        btn[p].setBackground(Color.GREEN);
                    }

                    popupReset();
                }
                // otherwise we go onto the next player after checking the tie
                else {
                    if (pareggio()) {
                        testo.setForeground(Color.BLACK);
                        statusVittoria = "Pareggio";
                        testo.setText(statusVittoria);
                        statusPartita = false;
                        popupReset();
                    } else{
                            turno = Giocatore.gO;
                        if (statusPartita){
                            testo.setForeground(Color.RED);
                            testo.setText(turno + " - Turno");
                        }
                    }
                }

            }
            // same for the O Player
            else if (turno.equals(Giocatore.gO) && button.getText().equals("")) {
                button.setForeground(Color.RED);
                button.setText("O");

                if (vittoria()[0] == 1) {
                    int[] pos = new int[]{vittoria()[1], vittoria()[2], vittoria()[3]};

                    statusVittoria = "O - Ha Vinto";

                    punteggioO++;
                    //System.out.println(punteggioO);
                    puntiO.setText(String.valueOf(punteggioO));

                    testo.setText(statusVittoria);
                    statusPartita = false;

                    for (int p : pos){
                        btn[p].setOpaque(true);
                        btn[p].setBorderPainted(false);
                        btn[p].setBackground(Color.GREEN);
                    }

                    popupReset();
                } else {
                    if (pareggio()) {
                        testo.setForeground(Color.BLACK);
                        statusVittoria = "Pareggio";
                        testo.setText(statusVittoria);
                        popupReset();
                        statusPartita = false;
                    }
                    turno = Giocatore.gX;
                    if (statusPartita){
                        testo.setForeground(Color.BLUE);
                        testo.setText(turno + " - Turno");
                    }
                }
            }
        }
    }

    /** Popup that ask for restarting the game*/
    public void popupReset() {
        String[] scelte = new String[] {"Si", "No"};
        String messaggio = "", riavvioMes = "Vuoi Riavviare la Partita?";
        if(pareggio()){
            messaggio = "Status: "+ statusVittoria + "\n" + riavvioMes;
        } else if(vittoria()[0] == 1){
            messaggio = "Status: " + statusVittoria + "\n" + riavvioMes;
        }
        if(resa){
            messaggio = "Status: " + statusVittoria + "\n" + riavvioMes;
        }
        int btn = JOptionPane.YES_NO_OPTION;
        int scelta = JOptionPane.showOptionDialog(this, messaggio, "Fine della Partita", btn, JOptionPane.INFORMATION_MESSAGE, null, scelte, scelte[0]);
        if (scelta == JOptionPane.YES_OPTION) {
            reset();
        }
    }

    /* Restart the game */
    private void reset() {

        // reset the buttons
        for (int i = 0; i < 9; i++) {
            btn[i].setText("");
            btn[i].setBackground(null);
        }

        sceltaGiocatore();

        if(turno.equals(Giocatore.gX)){
            testo.setForeground(Color.BLUE);
        } else if(turno.equals(Giocatore.gO) ){
            testo.setForeground(Color.RED);
        }

        statusPartita = true;
        resa = false;
        resaConta = 0;
        testo.setText(turno + " - Turno");
    }


    /* Handle the keyboard input, currently supporting "R" to restart */
    private void keyHandler(KeyEvent e) {
        int tasto = e.getKeyCode();

        // Se è premuto R, il gioco si riavvierà
        if (tasto == KeyEvent.VK_R) {
            reset();
        }
        if (tasto == KeyEvent.VK_A) {
            resa = true;
            resaConta++;
            resaDeiConti();
        }
        if (tasto == KeyEvent.VK_C) {
            punteggioX = 0;
            puntiX.setText(String.valueOf(punteggioX));
            punteggioO = 0;
            puntiO.setText(String.valueOf(punteggioO));
            reset();
        }
    }

    /** retuning if it's tie or not*/
    public boolean pareggio(){
        int caselleVuote = 0;
        if (vittoria()[0] == 0) {
            for (int i = 0; i < 9; i++) {
                if (!btn[i].getText().equals(""))
                    caselleVuote += 1;

            }
        }
        return caselleVuote == 9;
    }

    public void resaDeiConti(){
        if(turno.equals(Giocatore.gX)){
            if(resaConta > 1){
                statusVittoria = "Ti sei già Arreso!";
            } else{
                statusVittoria = "O - Ha Vinto; X si è Arreso";
            }
            testo.setForeground(Color.BLACK);
            testo.setText(statusVittoria);
        } else if(turno.equals(Giocatore.gO)){
            if(resaConta > 1){
                statusVittoria = "Ti sei già Arreso!";
            } else{
                statusVittoria = "X - Ha Vinto; O si è Arreso";
            }
            testo.setForeground(Color.BLACK);
            testo.setText(statusVittoria);
        }
        statusPartita = false;
        popupReset();
    }
}





