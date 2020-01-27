package com.company;

public class ParallelMatrixProduct {

    private UsualMatrix matrix;
    private Vector vector;
    private int countThreads;

    public ParallelMatrixProduct(UsualMatrix matrix, Vector vector, int countThreads) {
        this.matrix = matrix;
        this.vector = vector;
        this.countThreads = countThreads;
    }

    public int getCountThreads() {
        return this.countThreads;
    }


    public Vector productParallel(int countThreads) throws InterruptedException {
        this.countThreads = countThreads;
        if((this.countThreads <= 0) || (this.countThreads > vector.getSize())) {
            throw new RuntimeException("Incorrect amount of Threads!");
        }
        if(matrix.getRows() != vector.getSize()) {
            throw new RuntimeException("Incorrect matrix for product!");
        }
        Vector result = new Vector(matrix.getColumns());
        int beginIndex = 0;
        int countsElements = vector.getSize() / this.countThreads;
        MultiplicationCells[] threads = new MultiplicationCells[this.countThreads];
        for(int i = 0; i < threads.length; i++) {
            if(i == threads.length - 1) {
                countsElements = result.getSize()*result.getSize() - beginIndex;
            }
            threads[i] = new MultiplicationCells(matrix, vector, result, beginIndex, beginIndex + countsElements);
            threads[i].start();
            beginIndex = beginIndex + countsElements;
        }
        for (MultiplicationCells i : threads) {
            i.join();
        }
        return result;
    }
}
