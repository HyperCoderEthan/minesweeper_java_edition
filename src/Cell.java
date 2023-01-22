import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cell extends JButton {
    int rowIndex, columnIndex, num;
    boolean mine, flagged, removed;

    public Cell(int row, int column, Dimension cellSize) {
        rowIndex = row;
        columnIndex = column;
        flagged = false;
        mine = false;

        setIcon(new ImageIcon("resources/buttonIcon.png"));
        setPressedIcon(new ImageIcon("resources/pressedButtonIcon.png"));
        ImageIcon flag = new ImageIcon("resources/flag.png");
        setPreferredSize(cellSize);
        setSize(cellSize);
        setFocusable(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (isEnabled()) {
                        setDisabledIcon(flag);
                        setEnabled(false);
                        flagged = true;
                    } else {
                        setDisabledIcon(null);
                        setEnabled(true);
                        flagged = false;
                    }
                }
            }
        });
        //setIcon();
    }

    public int getRowIndex() {return rowIndex;}

    public int getColumnIndex() {return columnIndex;}

    public int getNum() {return num;}

    public boolean isMine() {return mine;}

    public boolean isFlagged() {return flagged;}

    public boolean isRemoved() {return removed;}

    public void setNum(int num) {this.num = num;}

    public void setMine(boolean m) {mine = m;}

    public void setRemoved(boolean r) {removed = r;}
}
