package matrixesPack;

import java.lang.StringBuilder;

public class UsualMatrix {

    private int[][] matrix;

    public UsualMatrix(int rows, int columns) {

        this.matrix = new int[rows][columns];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                this.matrix[i][j] = 1;
            }
        }
    }

    public int getRows() {
        return this.matrix.length;
    }

    public int getColumns() {
        return this.matrix[0].length;
    }

    public int getElement(int row, int column) {
        if(row >= getRows() || row < 0 || column >= getColumns() || column < 0) {
            throw new RuntimeException("ERROR! In method " + getClass().getName()
                    + ".getElement(int, int) bad range!");
        }

        return this.matrix[row][column];
    }

    public void setElement(int row, int column, int value) {
        if(row >= getRows() || row < 0 || column >= getColumns() || column < 0) {
            throw new RuntimeException("ERROR! In method " + getClass().getName()
                    + ".setElement(int, int) bad range!");
        }

        this.matrix[row][column] = value;
    }

    public UsualMatrix product(UsualMatrix other) {
        if(getColumns() != other.getRows()) {
            throw new ArithmeticException("Incorrect sizes of matrix");
        }
        UsualMatrix result = new UsualMatrix(getRows(), other.getColumns());
        for(int i = 0; i < result.getRows(); i++) {
            for(int j = 0; j < result.getColumns(); j++) {
                for(int k = 0; k < getColumns(); k++) {
                    result.setElement(i, j, result.getElement(i, j) + (getElement(i, k) * other.getElement(k, j)));
                }
            }
        }
        return result;
    }

    public String toString () {
        StringBuilder res = new StringBuilder ();
        for (int i = 0; i < getRows (); i++) {
            for (int j = 0; j < getColumns (); j++) {
                res.append (getElement (i, j));
                res.append (" ");
            }
            res.append ("\n");
        }
        return res.toString ();
    }

    public boolean equals (Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        UsualMatrix other = (UsualMatrix) obj;
        for (int i = 0; i < getRows(); i++)
            for (int j = 0; j < getColumns(); j++)
                if (getElement (i, j) != other.getElement(i, j))
                    return false;
        return true;
    }

}
