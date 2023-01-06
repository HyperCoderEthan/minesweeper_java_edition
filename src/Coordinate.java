import java.util.*;

//this class is probably unnecessary, it just makes it easy to compared if mine coordinates are duplicates

public class Coordinate {
    int row, column;

    public Coordinate(int r, int c) {
        Random generator = new Random();
        row = generator.nextInt(r);
        column = generator.nextInt(c);
    }

    @Override
    public boolean equals(Object e) {
        boolean output;
        if (e instanceof Coordinate) {
            Coordinate c = (Coordinate)e;
            if (c.getRow() == row && c.getColumn() == column) {
                output = true;
            } else {
                output = false;
            }
        } else {
            output = false;
        }
        return output;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
