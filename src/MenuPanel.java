import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel{
    JButton easy, medium, hard;
    GridBagConstraints locator;

    public MenuPanel() {
        setLayout(new GridBagLayout());

        easy = new JButton("easy");
        medium = new JButton("medium");
        hard = new JButton("hard");

        easy.setActionCommand("e");
        medium.setActionCommand("m");
        hard.setActionCommand("h");

        locator = new GridBagConstraints();
        locator.insets = new Insets(20, 20, 20, 20);
        locator.gridx = 0;
        locator.gridy = 0;
        ImageIcon titleImg = new ImageIcon("resources/MINESWEEPER LOGO.png");
        JLabel title = new JLabel(titleImg);
        add(title, locator);

        locator.gridy = 1;
        add(easy, locator);
        locator.gridy = 2;
        add(medium, locator);
        locator.gridy = 3;
        add(hard, locator);



    }

    public void addActionListener(ActionListener e) {
        easy.addActionListener(e);
        medium.addActionListener(e);
        hard.addActionListener(e);
    }
}
