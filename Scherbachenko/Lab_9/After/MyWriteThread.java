
public class MyWriteThread {
    protected static MyWriteThread add = new MyWriteThread();
    protected static MyWriteThread remove = new MyWriteThread();

    public static class MyAddThread extends Thread {
        MyRandomList l;
        public MyAddThread(MyRandomList MRL){
            l = MRL;
        }
        public void run() {
            l.addNumber();
        }
    }

    public static class MyRemoveThread extends Thread {
        MyRandomList l;
        public MyRemoveThread(MyRandomList MRL) {
            l = MRL;
        }
        public void run() {
                    l.removeNumber();
            }
    }
}
