import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Minesweeper extends JFrame implements ActionListener {
    MenuPanel menu;
    GamePanel game;
    GridBagConstraints locator;

    public Minesweeper() {
        locator = new GridBagConstraints();
        menu = new MenuPanel();
        setLayout(new GridBagLayout());
        locator.gridx = 0;
        locator.gridy = 0;
        add(menu);
        menu.addActionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("e")) {
            game = new GamePanel(9, 9, 10);
        } else if (actionCommand.equals("m")) {
            game = new GamePanel(16, 16, 40);
        } else {
            game = new GamePanel(30, 16, 99);
        }
        menu.setVisible(false);
        add(game);
        repaint();
    }

    public static void main(String[] args) {
        Minesweeper firstGame = new Minesweeper();
        firstGame.setSize(700, 700);
        firstGame.setVisible(true);
    }
}
