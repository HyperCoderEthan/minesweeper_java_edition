import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel{
    JButton easy, medium, hard;

    public MenuPanel() {
        easy = new JButton("easy");
        medium = new JButton("medium");
        hard = new JButton("hard");
        add(easy);
        add(medium);
        add(hard);
    }

    public void addActionListener(ActionListener e) {
        easy.addActionListener(e);
        medium.addActionListener(e);
        hard.addActionListener(e);
    }
}
