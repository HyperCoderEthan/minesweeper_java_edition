import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel {
    static int xCells, yCells, mineCount;

    public GamePanel(int x, int y, int numOfMines) {
        xCells = x;
        yCells = y;
        mineCount = numOfMines;
    }
}
