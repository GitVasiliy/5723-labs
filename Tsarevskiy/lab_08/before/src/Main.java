
public class Main {

    private class PmpThread extends Thread {

        private UsualMatrix m1;
        private UsualMatrix m2;
        private UsualMatrix resMatrix;
        private int firstCell;
        private int lastCell;
        private int m2Rows;

        public PmpThread(UsualMatrix f, UsualMatrix s, UsualMatrix res, int first, int last)
        {
            m1 = f;
            m2 = s;
            resMatrix = res;
            firstCell = first;
            lastCell = last;
            m2Rows = m2.getR();
        }

        public void CellCounting(int row, int column) {
            int t = 0;
            for (int i = 0; i < m2Rows; ++i) {
                t += m1.getVal(row, i) * m2.getVal(i, column);
            }
            resMatrix.setVal(row, column, t);
        }

        public void run() {
            int cols = m2.getC();
            for (int i = firstCell; i < lastCell; i++)
                CellCounting(i / cols, i % cols);
        }
    }

    public static void main (String args[]) {

        UsualMatrix m1 = new UsualMatrix(1000, 1000);
        UsualMatrix m2 = new UsualMatrix(1000, 1000);

        double startTime = System.currentTimeMillis();

        ParallelMatrixProduct p = new ParallelMatrixProduct(30);

        UsualMatrix res1 = p.prod(m1, m2);
        double middleTime = System.currentTimeMillis();
        System.out.println("Многопоточная программа выполнялась " + ((middleTime - startTime) / 1000) + " секунд");

        UsualMatrix res2 = m1.product(m2);

        double timeSpent = System.currentTimeMillis() - middleTime;
        System.out.println("Однопоточная программа выполнялась " + (timeSpent / 1000) + " секунд");

        System.out.println(res1.equals(res2));
    }
}
