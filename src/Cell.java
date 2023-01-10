import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class Cell extends JButton {
    int rowIndex, columnIndex, num;
    boolean mine, flagged, removed;

    public Cell(int row, int column, Dimension cellSize) {
        rowIndex = row;
        columnIndex = column;
        flagged = false;
        mine = false;
        setPreferredSize(cellSize);
        setSize(cellSize);

        ImageIcon flag = new ImageIcon("C:\\Users\\Dennis\\Desktop\\flag.png");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (isEnabled()) {
                        setEnabled(false);
                        setIcon(flag);
                        setDisabledIcon(flag);
                        flagged = true;
                    } else {
                        setEnabled(true);
                        setIcon(null);
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
