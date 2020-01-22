package com.company;

public class MultiplicationCells extends Thread {

    private UsualMatrix matrix;
    private Vector vector;
    private Vector result;
    private int beginIndex;
    private int endIndex;

    public MultiplicationCells(UsualMatrix matrix, Vector vector, Vector result, int beginIndex, int endIndex)
    {
        this.matrix = matrix;
        this.vector = vector;
        this.result = result;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        if(matrix.getRows() != vector.getSize()) {
            throw new RuntimeException("In thread incorrect matrix!");
        }

        if(result.getSize() != matrix.getColumns()) {
            throw new RuntimeException("In thread incorrect matrix");
        }

        int lastIndex = result.getSize() * result.getSize();
        for(int index = beginIndex; (index < endIndex) && (index < lastIndex); index++) {
            for(int j = 0; j < matrix.getColumns(); j++) {
                int i = index / result.getSize();
                result.setElement(i, result.getElement(i) + (matrix.getElement(j, i) * vector.getElement(i)));
            }
        }
    }

}
