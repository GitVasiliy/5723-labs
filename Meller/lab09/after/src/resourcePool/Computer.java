package resourcePool;

public class Computer {
    private boolean free;

    public void setFree(boolean free){
        this.free = free;
    }

    public boolean getFree(){
        return free;
    }

    public void setTourist(Tourist tourist){
        tourist.giveComputer(this);
    }
}
