import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.ArrayList;

public class Minefield extends JPanel implements ActionListener {
    Cell[][] cells;
    Dimension cellSize = new Dimension(25, 25);
    Color color1 = new Color(236, 232, 221);
    Color color2 = new Color(248, 244, 234);
    int rows;
    int columns;
    GridBagConstraints locator;
    int mineCount, nonMineCount, dugCellCount;
    boolean firstOpeningHappened = false;
    ArrayList<ActionListener> listeners;

    public Minefield(int xCells, int yCells, int mineCount) {
        this.mineCount = mineCount;

        setLayout(new GridBagLayout());
        locator = new GridBagConstraints();
        locator.insets = new Insets(0,0,0,0);

        cells = new Cell[yCells][xCells];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j, cellSize);
                cells[i][j].addActionListener(this);
                locator.gridx = j;
                locator.gridy = i;
                add(cells[i][j], locator);
                add(Box.createHorizontalStrut(cellSize.width), locator);
                add(Box.createVerticalStrut(cellSize.height), locator);
            }
        }

        rows = cells.length;
        columns = cells[0].length;

        setSize(columns * cellSize.width, rows * cellSize.height);
        setPreferredSize(new Dimension(columns * cellSize.width, rows * cellSize.height));

        nonMineCount = (rows * columns) - mineCount;
        dugCellCount = 0;

        listeners = new ArrayList<>();
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

        digCell(y, x);
        revalidate();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage bg = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bg.createGraphics();
        g2.setColor(Color.GRAY);
        for (int i = 0; i < cells.length; i ++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (i % 2 == 1) {
                    if (j % 2 == 1) {
                        g2.setColor(color1);
                    } else {
                        g2.setColor(color2);
                    }
                } else {
                    if (j % 2 == 1) {
                        g2.setColor(color2);
                    } else {
                        g2.setColor(color1);
                    }
                }
                g2.fillRect(cells[i][j].getX(), cells[i][j].getY(), cellSize.width, cellSize.height);
            }
        }
        g2.dispose();
        g.drawImage(bg, 0, 0, this);
    }

    public Color getNumColor(int num) {
        Color output;
        switch (num) {
            case 1:
                output = new Color(173, 142, 112);
                break;
            case 2:
                output = new Color(255, 110, 49);
                break;
            case 3:
                output = new Color(36, 55, 99);
                break;
            case 4:
                output = new Color(148, 180, 159);
                break;
            case 5:
                output = new Color(223, 120, 97);
                break;
            case 6:
                output = new Color(163, 26, 203);
                break;
            case 7:
                output = new Color(235, 69, 95);
                break;
            default:
                output = Color.BLACK;
        }

        return output;
    }

    public void digCell(int row, int column) {
        remove(cells[row][column]);

        locator.gridx = column;
        locator.gridy = row;
        if (cells[row][column].isMine()) {
            ImageIcon mineImage = new ImageIcon("resources/bomb.png");

            add(new JLabel(mineImage), locator);
            cells[row][column].setRemoved(true);
            setGameToLost();
        } else if (cells[row][column].getNum() != 0){
            Color labelColor = getNumColor(cells[row][column].getNum());

            Font labelFont = new Font("Ebrima Bold", Font.BOLD, 16);
            JLabel numLabel = new JLabel(String.valueOf(cells[row][column].getNum()));
            numLabel.setFont(labelFont);
            numLabel.setForeground(labelColor);

            numLabel.setSize(cellSize);
            numLabel.setHorizontalTextPosition(JLabel.CENTER);
            add(numLabel, locator);
            dugCellCount++;
        } else {
            HashSet<Cell> emptyCells = getAllContiguousCells(row, column);
            Iterator iter = emptyCells.iterator();
            while (iter.hasNext()) {
                Cell c = (Cell) iter.next();
                if (!c.equals(cells[row][column])) {
                    remove(c);
                }
            }
            dugCellCount += emptyCells.size();
            digSurroundingCells(emptyCells);
        }
        cells[row][column].setRemoved(true);

        if (dugCellCount == nonMineCount) {
            setGameToWon();
        }
    }

    public void setGameToWon() {
        fireActionEvent("w");
    }

    public void setGameToLost() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].isMine()) {
                    remove(cells[i][j]);
                    locator.gridy = i;
                    locator.gridx = j;
                    ImageIcon mineImage = new ImageIcon("resources/bomb.png");
                    add(new JLabel(mineImage), locator);
                    cells[i][j].setRemoved(true);
                } else {
                    cells[i][j].setEnabled(false);
                }
            }
        }
        fireActionEvent("l");
    }

    public void digSurroundingCells (HashSet<Cell> set) {
        Iterator iter = set.iterator();
        int row, column;
        while (iter.hasNext()) {
            Cell c = (Cell) iter.next();
            row = c.getRowIndex();
            column = c.getColumnIndex();
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = column - 1; j <= column + 1; j++) {
                    try {
                        if (!cells[i][j].isRemoved()) {
                            digCell(i, j);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            }
        }
    }

    public HashSet<Cell> getAllContiguousCells(int row, int column) {
        HashSet<Cell> output = new HashSet<>();
        ArrayList<Cell> buffer = new ArrayList<>();
        output.add(cells[row][column]);
        int setSize;
        boolean valid;
        do {
            setSize = output.size();
            valid = true;
            Iterator iter = output.iterator();
            while (iter.hasNext()) {
                Cell c =(Cell) iter.next();
                if(!c.isRemoved()) {
                    c.setRemoved(true);
                    addEmptyCells(c, buffer);
                }
            }
            ListIterator bufferIter = buffer.listIterator();
            while (bufferIter.hasNext()) {
                output.add((Cell) bufferIter.next());
            }
            if (setSize != output.size())
                valid = false;
        } while (!valid);
        return output;
    }

    public void addEmptyCells(Cell c, ArrayList<Cell> list) {
        int row = c.getRowIndex();
        int column = c.getColumnIndex();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                try {
                    if (cells[i][j].getNum() == 0 && !cells[i][j].isRemoved()) {
                        list.add(cells[i][j]);
                    }
                } catch (ArrayIndexOutOfBoundsException e){}
            }
        }
    }

    public Coordinate[] generateRandomMines(int firstRow, int firstColumn) {
        Coordinate[] output = new Coordinate[mineCount];
        for (int i = 0; i < mineCount; i++)
            output[i] = new Coordinate(rows, columns, true);

        boolean valid;
        do {
            valid = true;
            for (int i = 0; i < mineCount && valid; i++) {
                for (int j = i; j < mineCount && valid; j++) {
                    Coordinate testC = output[i];
                    if (j != i && testC.equals(output[j]) || (testC.getRow() == firstRow && testC.getColumn() == firstColumn)) {
                        valid = false;
                        output[i] = new Coordinate(rows, columns, true);
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

    public void fireActionEvent(String command) {
        ListIterator iter = listeners.listIterator();
        while (iter.hasNext()) {
            ActionListener listener =(ActionListener) iter.next();
            ActionEvent action = new ActionEvent(this, 1, command);

            listener.actionPerformed(action);
        }
    }

    public void addActionListener(ActionListener listen) {
        listeners.add(listen);
    }
}
