package com.company;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Main {

    public static void main(String[] args) throws InterruptedException {



        int numb = 8; // количесвто потоков
        int n = 100; //строки
        int m = 100;
        int p = 100;
        ParallelMatrixProduct computer = new  ParallelMatrixProduct(numb); //класс для перемножения матриц многопоточная

        UsualMatrix matrixA = new UsualMatrix(n,m);
        UsualMatrix matrixB = new UsualMatrix(m,p);


        long startTime = System.currentTimeMillis();


        UsualMatrix simpleResult = new UsualMatrix(n,p);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                for (int k = 0; k < m; k++) {
                    simpleResult.matrix[i][j] += matrixA.matrix[i][k] * matrixB.matrix[k][j];
                }
            }
        }

        long estimatedTime = System.currentTimeMillis() - startTime;

        System.out.println("Simple: " + estimatedTime + "ms");


        long startTime1 = System.currentTimeMillis();
        UsualMatrix multiplyResult = ParallelMatrixProduct.parallelProduct(matrixA,matrixB);
        long estimatedTime1 = System.currentTimeMillis() - startTime1;
        //System.out.print(multiResult);
        //  multiplyResult.Print();
        System.out.println("Multiply: " + estimatedTime1 + "ms");
    }

}