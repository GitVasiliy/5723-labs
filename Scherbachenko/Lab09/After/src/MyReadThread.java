public class MyReadThread extends Thread {
        MyRandomList l;
        public MyReadThread(MyRandomList MRL){
            l = MRL;
        }
        public void run() {
           l.calcZero();
        }
}
