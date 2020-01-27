package resourcePool;

public class FindComputer extends Thread {
    private Computer[] computers;
    public Computer free;

    public FindComputer(Computer[] computers){
        this.computers = computers;
    }
    public void run(){
        for(int i = 0; i < computers.length; i++){
            if(computers[i].getFree()){
                free = computers[i];
                interrupt();
            }
        }
    }

    public Computer getFreeFromThread(){
        return free;
    }
}
