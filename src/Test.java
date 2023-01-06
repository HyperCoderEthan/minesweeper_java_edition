import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Test extends JFrame implements ActionListener {

    MenuPanel testPanel;
    JButton stupid;
    Minefield blah;

    public Test() {
        testPanel = new MenuPanel();
        testPanel.setVisible(true);
        testPanel.addActionListener(this);
        add(testPanel);
        setVisible(true);
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Test gerald = new Test();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (((JButton)e.getSource()).getText().equals("easy")){
            testPanel.setVisible(false);
            blah = new Minefield(20, 30, 18);
            //blah.setBackground(Color.BLUE);
            add(blah);
            repaint();
        } else {
            blah.remove(stupid);
            blah.add(new JLabel("replaced"));
            blah.updateUI();
            //testPanel.setVisible(true);
        }
    }
}
