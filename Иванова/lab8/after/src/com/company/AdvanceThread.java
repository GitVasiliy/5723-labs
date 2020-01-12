package com.company;

public class AdvanceThread implements Runnable{
    public int startRow;
    public int endRow;
    UsualMatrix a;
    UsualMatrix b;
    UsualMatrix result;

    public AdvanceThread(int startRow, int endRow, UsualMatrix a, UsualMatrix b, UsualMatrix result) { //sozdanie potoka
        this.startRow = startRow;
        this.endRow = endRow;
        this.a = a;
        this.b = b;
        this.result = result;
    }

    @Override
    public void run(){
        for (int j = 0; j < b.m; j++) {
            for (int k = 0; k < b.n; k++) {
                for (int stringNumb = startRow; stringNumb < endRow; stringNumb++) {
                    result.matrix[stringNumb][j] += a.matrix[stringNumb][k] * b.matrix[k][j];
                }

            }
        }

    }
}