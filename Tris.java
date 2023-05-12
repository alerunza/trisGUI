import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Tris extends Window {

    private boolean state = true; // state of the game
    public String statusVittoria = "";

    public Tris(String title) {
        super(title);

        if(turno == Giocatore.gX){
            testo.setForeground(Color.BLUE);
        } else if(turno == Giocatore.gO){
            testo.setForeground(Color.RED);
        }
        testo.setText(turno.getAbbreviation() + " - Turno");

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
    public int[] win() {
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
    protected void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();


        // while the game is running
        if (state) {
                // if GiocatoreX played  an empty button then make the move
            if (turno == Giocatore.gX && button.getText().equals("")) {
                button.setForeground(Color.BLUE);
                button.setFont(new Font("Arial", Font.PLAIN, 36));
                button.setText("X");

                // checking if X won
                if (win()[0] == 1) {
                    int[] pos = new int[]{win()[1], win()[2], win()[3]};

                    statusVittoria = "X - Ha Vinto";

                    testo.setText(statusVittoria);
                    state = false;

                    // setting win combination to green
                    for (int po : pos){
                        btn[po].setOpaque(true);
                        btn[po].setBorderPainted(false);
                        btn[po].setBackground(Color.GREEN);
                    }

                    popupReset();
                }
                // otherwise we go onto the next player after checking the tie
                else {
                    if (tie()) {
                        testo.setForeground(Color.BLACK);
                        testo.setText("Pareggio");
                        state = false;
                        popupReset();
                    }else {
                            turno = Giocatore.gO;
                        if (state){
                            testo.setForeground(Color.RED);
                            testo.setText(turno.getAbbreviation() + " - Turno");
                        }
                    }
                }

            }
            // same for the O Player
            else if (turno == Giocatore.gO && button.getText().equals("")) {
                button.setForeground(Color.RED);
                button.setText("O");

                if (win()[0] == 1) {
                    int[] pos = new int[]{win()[1], win()[2], win()[3]};

                    statusVittoria = "O - Ha Vinto";

                    testo.setText(statusVittoria);
                    state = false;

                    for (int po : pos){
                        btn[po].setOpaque(true);
                        btn[po].setBorderPainted(false);
                        btn[po].setBackground(Color.GREEN);
                    }

                    popupReset();
                } else {
                    if (tie()) {
                        testo.setForeground(Color.BLACK);
                        testo.setText("Pareggio");
                        popupReset();
                        state = false;
                    }
                    turno = Giocatore.gX;
                    if (state){
                        testo.setForeground(Color.BLUE);
                        testo.setText(turno.getAbbreviation() + " - Turno");
                    }
                }
            }
        }
    }

    /** Popup that ask for restarting the game*/
    public void popupReset() {
        String[] scelte = new String[] {"Si", "No"};
        String messaggio = "";
        if(tie()){
            messaggio = "Status: Pareggio" + "\n" + "Vuoi Riavviare la Partita?";
        } else if(win()[0] == 1){
            messaggio = "Status: " + statusVittoria + "\n" + "Vuoi Riavviare la Partita?";
        }
        int btn = JOptionPane.YES_NO_OPTION;
        int scelta = JOptionPane.showOptionDialog(this, messaggio, "Fine della Partita", btn, JOptionPane.INFORMATION_MESSAGE, null, scelte, scelte[0]);
        if (scelta == JOptionPane.YES_OPTION) {
            reset();
        }
    }

    /** Restart the game*/
    private void reset() {

        // reset the buttons
        for (int i = 0; i < 9; i++) {
            btn[i].setText("");
            btn[i].setBackground(null);
        }
        sceltaGiocatore();

        if(turno == Giocatore.gX){
            testo.setForeground(Color.BLUE);
        } else if(turno == Giocatore.gO){
            testo.setForeground(Color.RED);
        }

        state = true;
        testo.setText(turno.getAbbreviation() + " - Turno");
    }


    /** Handle the keyboard input, currently supporting "R" to restart and "A" to toggle the AI*/
    private void keyHandler(KeyEvent e) {
        int tasto = e.getKeyCode();

        // Se è premuto R, il gioco si riavvierà
        if (tasto == KeyEvent.VK_R) {
            reset();
        }
    }

    /** retuning if it's tie or not*/
    public boolean tie(){
        int emptyCase = 0;
        if (win()[0] == 0) {
            for (int i = 0; i < 9; i++) {
                if (!btn[i].getText().equals(""))
                    emptyCase += 1;

            }
        }
        return emptyCase == 9;
    }
}





