import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cell extends JButton {
    int rowIndex, columnIndex, num;
    boolean mine, flagged;

    public Cell(int row, int column) {
        rowIndex = row;
        columnIndex = column;
        flagged = false;
        mine = false;
        setPreferredSize(new Dimension(20, 20));
        setSize(20, 20);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (isEnabled()) {
                        setEnabled(false);
                        flagged = true;
                    } else {
                        setEnabled(true);
                        flagged = false;
                    }
                }
            }
        });

    }

    public int getRowIndex() {return rowIndex;}

    public int getColumnIndex() {return columnIndex;}

    public int getNum() {return num;}

    public boolean isMine() {return mine;}

    public boolean isFlagged() {return flagged;}

    public void setNum(int num) {this.num = num;}

    public void setMine(boolean m) {mine = m;}
}
