package com.company;


public class UsualMatrix
{
    public int n;
    public int m;
    public int[][] matrix ;

    public UsualMatrix(int n, int m) {
        this.n = n;
        this.m = m;
        matrix = new int[n][m];
        for (int i=0;i<n;i++)
        {
            for(int j =0;j<m;j++)
            {
                matrix[i][j] = i+j; //(int)(Math.random()*1000)%100; //Р С–Р ВµР Р…Р ВµРЎР‚Р В°РЎвЂ Р С‘РЎРЏ Р Т‘Р Р†РЎС“Р В·Р В°Р Р…РЎвЂЎР С–Р Р…РЎвЂ№РЎвЂ¦ РЎвЂЎР С‘РЎРѓР ВµР В»
            }
        }
    }

    public void Print()
    {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}