import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.ArrayList;

public class Minefield extends JPanel implements ActionListener {
    Cell[][] cells;
    GridBagConstraints locator;

    public Minefield(int xCells, int yCells, int mineCount) {
        setLayout(new GridBagLayout());
        locator = new GridBagConstraints();
        locator.insets = new Insets(0,0,0,0);

        cells = new Cell[yCells][xCells];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j);
                cells[i][j].addActionListener(this);
                locator.gridx = j;
                locator.gridy = i;
                add(cells[i][j], locator);
            }
        }
    }

    public static void main(String[] args) {
        JFrame test = new JFrame();
        Minefield greg = new Minefield(20, 10, 18);
        test.add(greg);
        test.setSize(600, 600);
        test.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Cell pressedCell = (Cell)e.getSource();
        int y = pressedCell.getRowIndex();
        int x = pressedCell.getColumnIndex();

        remove(cells[y][x]);

        locator.gridx = x;
        locator.gridy = y;
        add(new JLabel("poop"), locator);
        updateUI();
    }
}
