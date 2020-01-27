package resourcePool;

public class Tourist extends Thread {
    private Computer computer;
    private int time;
    private int touristNumber;

    public Tourist(int time, int number){
        this.time = time;
        this.touristNumber = number;
    }

    public void giveComputer(Computer computer){
        this.computer = computer;
    }

    public void run(){
        computer.setFree(false);
        System.out.println("Tourist " + touristNumber + " waiting for turn.");
        synchronized (computer){
            System.out.println("Tourist " + touristNumber + " is online.");

            try{
                Thread.sleep(time);
            }
            catch(InterruptedException e){}
            System.out.println("Tourist " + touristNumber + " is done, having spent " + time / 60 + " minutes online.");
        }
        computer.setFree(true);
    }
}
