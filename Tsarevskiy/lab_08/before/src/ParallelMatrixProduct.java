public class ParallelMatrixProduct {

    //=======================================================================

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

    //=======================================================================

    private int threads;

    public ParallelMatrixProduct (int n) {
        threads = n;
    }

    public UsualMatrix prod (UsualMatrix m1, UsualMatrix m2) {

        if (m1.getC() != m2.getR()) {
            throw new MyException("Bad sizes");
        }

        PmpThread[] multiplierThreads = new PmpThread[threads];
        UsualMatrix res = new UsualMatrix(m1.getR(), m2.getC());
        int cellsNum = ((m1.getR() * m2.getC()) / threads);
        int start = 0;

        for (int i = 0; i < threads; i++){
            int end = start + cellsNum;

            if (i == threads - 1) {
                end = m1.getR() * m2.getC();
            }
            multiplierThreads[i] = new PmpThread(m1, m2, res, start, end);
            multiplierThreads[i].start();
            start = end;
        }

        try {
            for (PmpThread multiplierThread : multiplierThreads)
                multiplierThread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return res;
    }
}