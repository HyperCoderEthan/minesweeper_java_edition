import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.ArrayList;

public class Minefield extends JPanel implements ActionListener {
    Cell[][] cells;
    int rows;
    int columns;
    GridBagConstraints locator;
    int mineCount;
    boolean firstOpeningHappened = false;

    public Minefield(int xCells, int yCells, int mineCount) {
        this.mineCount = mineCount;

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

        rows = cells.length;
        columns = cells[0].length;
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

        if (!firstOpeningHappened) {
            Coordinate[] mines = generateRandomMines(y, x);
            for (int i = 0; i < mines.length; i++) {
                Coordinate c = mines[i];
                cells[c.getRow()][c.getColumn()].setMine(true);
            }
            setNums();
            firstOpeningHappened = true;
        }

        remove(cells[y][x]);

        locator.gridx = x;
        locator.gridy = y;
        if (cells[y][x].isMine()) {
            add(new JLabel("mine"), locator);
        } else {
            add(new JLabel(String.valueOf(cells[y][x].getNum())), locator);
        }
        updateUI();
    }

    public Coordinate[] generateRandomMines(int firstRow, int firstColumn) {
        Coordinate[] output = new Coordinate[mineCount];
        for (int i = 0; i < mineCount; i++)
            output[i] = new Coordinate(rows, columns);

        boolean valid;
        do {
            valid = true;
            for (int i = 0; i < mineCount && valid; i++) {
                for (int j = i; j < mineCount && valid; j++) {
                    Coordinate testC = output[i];
                    if (j != i && testC.equals(output[j]) || (testC.getRow() == firstRow && testC.getColumn() == firstColumn)) {
                        valid = false;
                        output[i] = new Coordinate(rows, columns);
                    }
                }
            }
        } while (!valid);
        return output;
    }

    public void setNums() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (!cells[i][j].isMine())
                    sumThisCell(i, j);
            }
        }
    }

    public void sumThisCell(int row, int column) {
        int output = 0;
        for (int j = row -1; j <= row + 1; j++) {
            for (int i = column - 1; i <= column + 1; i++) {
                if (checkThisCell(j, i))
                    output++;
            }
        }
        cells[row][column].setNum(output);
    }

    public boolean checkThisCell(int row, int column) {
        boolean output;
        try {
            output = cells[row][column].isMine();
        } catch (Exception e) {
            output = false;
        }
        return output;
    }
}
