import java.util.logging.Logger;

public class Queens {
    private Integer count = 0;
    private int dimension;
    private Logger log = Logger.getLogger(Queens.class.getName());
    private Thread[] threads;

    private Queens(int n) {
        dimension = n;
    }

    private int calcQueenNum(int threadNum) throws InterruptedException {
        count = 0;
        threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++)
            threads[i] = new Thread(new Runner(i));
        for (Thread thread: threads) {
            thread.start();
        }
        for (Thread thread: threads) {
            thread.join();
        }
        return count;
    }

    public static void main(String[] args) {
        Logger log = Logger.getLogger(Queens.class.getName());

        try {
            Queens c = new Queens(10);
            long begin = System.currentTimeMillis();
            int count1 = c.calcQueenNum(1);
            long end = System.currentTimeMillis();
            String resStr1 = "Количество = " + count1 + " Время на " + c.threads.length + " потоках: " + (end - begin) + "ms";
            log.info(resStr1);

            begin = System.currentTimeMillis();
            int count2 = c.calcQueenNum(4);
            end = System.currentTimeMillis();
            String resStr2 = "Количество = " + count2 + " Время на " + c.threads.length + " потоках: " + (end - begin) + "ms";
            log.info(resStr2);

            begin = System.currentTimeMillis();
            int count3 = c.calcQueenNum(8);
            end = System.currentTimeMillis();
            String resStr3 = "Количество = " + count1 + " Время на " + c.threads.length + " потоках: " + (end - begin) + "ms";
            log.info(resStr3);

            begin = System.currentTimeMillis();
            int count4 = c.calcQueenNum(10);
            end = System.currentTimeMillis();
            String resStr4 = "Количество = " + count1 + " Время на " + c.threads.length + " потоках: " + (end - begin) + "ms";
            log.info(resStr4);

            begin = System.currentTimeMillis();
            int count5 = c.calcQueenNum(16);
            end = System.currentTimeMillis();
            String resStr5 = "Количество = " + count1 + " Время на " + c.threads.length + " потоках: " + (end - begin) + "ms";
            log.info(resStr5);
        }

        catch (InterruptedException e) {
            log.info(e.getMessage());
        }
    }

    class Runner implements Runnable {
        int threadNumber;
        int[] state;

        Runner(int n) {
            state = new int[dimension];
            threadNumber = n;
        }

        private boolean check(int column) {
            for (int i = 0; i < column; i++) {
                if (state[column] == state[i] || Math.abs(state[column] - state[i]) == (column - i)) {
                    return false;
                }
            }
            return true;
        }

        private void stateCount(int column) {
            if (column < dimension) {
                for (int i = 0; i < dimension; i++) {
                    state[column] = i;
                    if (check(column)) {
                        stateCount(column + 1);
                    }
                }
            } else {
                synchronized (count) {
                    count++;
                }
            }
        }

        @Override
        public void run() {
            int sum = threadNumber;
            while (sum < dimension) {
                state[0] = sum;
                stateCount(1);
                sum += threads.length;
            }
        }
    }
}