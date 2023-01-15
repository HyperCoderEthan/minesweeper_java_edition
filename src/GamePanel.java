import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel {
    final int xCells, yCells, mineCount;
    GridBagConstraints locator;
    Minefield minefield;
    JPanel topPanel, bottomPanel;

    public GamePanel(int x, int y, int numOfMines) {
        locator = new GridBagConstraints();
        xCells = x;
        yCells = y;
        mineCount = numOfMines;
        minefield = new Minefield(xCells, yCells, mineCount);
        setLayout(new GridBagLayout());

        topPanel = new JPanel();
        topPanel.setSize(500, 100);
        locator.gridx = 0;
        locator.gridy = 0;
        add(topPanel);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        bottomPanel.setSize(500, 500);
        bottomPanel.add(minefield);
        locator.gridy = 1;
        add(bottomPanel);
    }

    public void addActionListener(ActionListener l) {
        minefield.addActionListener(l);
    }
}
