public class Main {
    public static void main(String[] args){
        MyRandomList l = new MyRandomList();
        System.out.print(l);
        MyWriteThread.MyAddThread adder = new MyWriteThread.MyAddThread(l);
        MyWriteThread.MyRemoveThread remover = new MyWriteThread.MyRemoveThread(l);
        MyReadThread calc = new MyReadThread(l);
        //adder.start();
        //remover.start();
        synchronized (calc) {
            adder.start();
            remover.start();
        }
        synchronized (remover){
            synchronized (adder){
                calc.start();
            }
        }
        /*try {
            adder.join();
            remover.join();
            calc.start();
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }*/
        //System.out.print(l);

    }
}
