package com.company;
import java.util.Random;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        UsualMatrix matrix = getRandMatrix(10, 900, 800);
        Vector vector = getRandVector(10, 900);
        System.out.println("Without threads: ");
        ParallelMatrixProduct process = new ParallelMatrixProduct(matrix, vector, 1);
        long start = times();
        process.productParallel(1);
        timef(start);
        System.out.println("With threads: ");
        start = times();
        process.productParallel(3);
        timef(start);
    }



    public static long times(){
        long start = System.nanoTime();
        start = System.nanoTime();
        System.out.println();
        return start;

    }
    public static void timef(long start){
        long finish = System.nanoTime();
        System.out.println();
        finish = System.nanoTime();
        System.out.println("Time: " + (finish - start));
        System.out.println();
    }

    public static UsualMatrix getRandMatrix(int module, int rows, int columns) {
        UsualMatrix matrix = new UsualMatrix(rows, columns);
        Random rand = new Random();
        for(int i = 0; i < matrix.getRows(); i++) {
            for(int j = 0; j < matrix.getColumns(); j++) {
                matrix.setElement(i, j, rand.nextInt() % module);
            }
        }
        return matrix;
    }

    public static Vector getRandVector(int module, int n) {
        Vector vector = new Vector(n);
        Random rand = new Random();
        for(int i = 0; i < vector.getSize(); i++)
            vector.setElement(i, rand.nextInt() % module);
        return vector;
    }
}
