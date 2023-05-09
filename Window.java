import javax.swing.*;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;


/**  Constructor to make a TicTacToe window with Nimbus style*/
public class Window extends JFrame {

    JButton[] buttons = new JButton[9];
    JPanel mainPanel = new JPanel();
    JLabel mainText;

    public Window(String title) {
        // Setting up the window
        super(title);

        // Window setup
        setSize(450,400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        mainPanel.setLayout(new GridLayout(3,3));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("duran", Font.PLAIN, 36));
            buttons[i].setFocusable(false); // making sure they can't be focused (like with tab)
            buttons[i].addActionListener(this::actionPerformed); // adding an action listener for when we press it
            mainPanel.add(buttons[i]); // adding buttons to our mainPanel 3 by 3 grid
        }

        add(mainPanel, BorderLayout.CENTER); // adding the mainPanel to the center of our window

        mainText = new JLabel("", SwingConstants.CENTER); // empty label for information
        mainText.setFont(new Font("Arial", Font.BOLD, 16));
        mainText.setPreferredSize(new Dimension(0,25));
        mainText.setForeground(Color.BLUE);
        add(mainText, BorderLayout.NORTH);

        setVisible(true);
    }

    /** Action to do when a button is pressed */
    protected void actionPerformed(ActionEvent e){

    }

}
