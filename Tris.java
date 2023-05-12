import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Tris extends Window {

    private boolean state = true; // state of the game
    private Player turn = Player.GiocatoreX;

    public String statusVittoria = "";

    /** Create a working TicTacToe game*/
    public Tris(String title) throws HeadlessException {
        super(title);

        mainText.setText(turn.getAbbreviation() + " - Turno");

        // this is needed for the keyEvent
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
        if (buttons[0].getText().equals(buttons[1].getText()) && buttons[2].getText().equals(buttons[0].getText()) && !buttons[0].getText().equals("")){
            return new int[]{1, 0, 1, 2};
        } else if (buttons[3].getText().equals(buttons[4].getText()) && buttons[5].getText().equals(buttons[3].getText()) && !buttons[3].getText().equals("")){
            return new int[]{1, 3, 4, 5};
        } else if (buttons[6].getText().equals(buttons[7].getText()) && buttons[8].getText().equals(buttons[6].getText()) && !buttons[6].getText().equals("")){
            return new int[]{1, 6, 7, 8};
        } else if (buttons[0].getText().equals(buttons[3].getText()) && buttons[6].getText().equals(buttons[0].getText()) && !buttons[0].getText().equals("")){
            return new int[]{1, 0, 3, 6};
        } else if (buttons[1].getText().equals(buttons[4].getText()) && buttons[7].getText().equals(buttons[1].getText()) && !buttons[1].getText().equals("")){
            return new int[]{1, 1, 4, 7};
        } else if (buttons[2].getText().equals(buttons[5].getText()) && buttons[8].getText().equals(buttons[2].getText()) && !buttons[2].getText().equals("")){
            return new int[]{1, 2, 5, 8};
        } else if (buttons[0].getText().equals(buttons[4].getText()) && buttons[8].getText().equals(buttons[0].getText()) && !buttons[0].getText().equals("")){
            return new int[]{1, 0, 4, 8};
        } else if (buttons[2].getText().equals(buttons[4].getText()) && buttons[6].getText().equals(buttons[2].getText()) && !buttons[2].getText().equals("")){
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
            if (turn == Player.GiocatoreX && button.getText().equals("")) {
                button.setForeground(Color.BLUE);
                button.setFont(new Font("duran", Font.PLAIN, 36));
                button.setText("X");

                // checking if X won
                if (win()[0] == 1) {
                    int[] pos = new int[]{win()[1], win()[2], win()[3]};

                    statusVittoria = "X - Ha Vinto";

                    mainText.setText(statusVittoria);
                    state = false;

                    // setting win combination to green
                    for (int po : pos){
                        buttons[po].setOpaque(true);
                        buttons[po].setBorderPainted(false);
                        buttons[po].setBackground(Color.GREEN);
                    }

                    popupReset();
                }
                // otherwise we go onto the next player after checking the tie
                else {
                    if (tie()) {
                        mainText.setForeground(Color.BLACK);
                        mainText.setText("Pareggio");
                        state = false;
                        popupReset();
                    }else {
                            turn = Player.GiocatoreO;
                        if (state){
                            mainText.setForeground(Color.RED);
                            mainText.setText(turn.getAbbreviation() + " - Turno");
                        }
                    }
                }

            }
            // same for the O Player
            else if (turn == Player.GiocatoreO && button.getText().equals("")) {
                button.setForeground(Color.RED);
                button.setText("O");

                if (win()[0] == 1) {
                    int[] pos = new int[]{win()[1], win()[2], win()[3]};

                    statusVittoria = "O - Ha Vinto";

                    mainText.setText(statusVittoria);
                    state = false;

                    for (int po : pos){
                        buttons[po].setOpaque(true);
                        buttons[po].setBorderPainted(false);
                        buttons[po].setBackground(Color.GREEN);
                    }

                    popupReset();
                } else {
                    if (tie()) {
                        mainText.setForeground(Color.BLACK);
                        mainText.setText("Pareggio");
                        popupReset();
                        state = false;
                    }
                    turn = Player.GiocatoreX;
                    if (state){
                        mainText.setForeground(Color.BLUE);
                        mainText.setText(turn.getAbbreviation() + " - Turno");
                    }
                }
            }
        }
    }

    /** Popup that ask for restarting the game*/
    public void popupReset() {
        String[] scelta = new String[] {"Si", "No"};
        String messaggio = "";
        if(tie()){
            messaggio = "Status: Pareggio" + "\n" + "Vuoi Riavviare la Partita?";
        } else if(win()[0] == 1){
            messaggio = "Status: " + statusVittoria + "\n" + "Vuoi Riavviare la Partita?";
        }
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showOptionDialog(this,
                messaggio,
                "Fine della Partita",
                dialogButton,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                scelta,
                scelta[0]);
        if (dialogResult == JOptionPane.YES_OPTION) {
            reset();
        }
    }

    /** Restart the game*/
    private void reset() {

        // reset the buttons
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setBackground(null);
        }
        turn = Player.GiocatoreX;

        state = true;
        mainText.setForeground(Color.BLUE);
        mainText.setText(turn.getAbbreviation() + " - Turno");
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
                if (!buttons[i].getText().equals(""))
                    emptyCase += 1;

            }
        }
        return emptyCase == 9;
    }
}





