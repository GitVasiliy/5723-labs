package resourcePool;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Cafe {
    private int computerCount;
    private int touristCount;
    private Queue<Tourist> tourists;
    private Computer[] computers;

    public Cafe(int computerCount, int touristCount){
        this.computerCount = computerCount;
        this.touristCount = touristCount;

        computers = new Computer[this.computerCount];
        for(int i = 0; i < computers.length; i++){
            computers[i] = new Computer();
        }

        tourists = new LinkedList<>();
        Random rand = new Random();
        for(int i = 0; i < this.touristCount; i++){
            Tourist tmp = new Tourist(rand.nextInt(7200) + 900, i);
            tourists.add(tmp);
        }
    }

    public void distributComputers(){
        for(int i = 0; i < this.computerCount; i++){
            if(tourists.isEmpty()){
                return;
            }
            computers[i].setTourist(tourists.peek());
            tourists.remove().start();
        }

        while(!tourists.isEmpty()){
            FindComputer t = new FindComputer(computers);
            t.start();
            try {
                t.join();
            }
            catch (InterruptedException e){}

            Computer freeComp = t.getFreeFromThread();
            if(freeComp != null) {
                freeComp.setTourist(tourists.peek());
                tourists.remove().start();
            }
        }


        // ЧТОБЫ РАБОТАЛ СИНХРОНАЙЗД

//        while(!tourists.isEmpty()){
//            for(int i = 0; i < this.computerCount; i++){
//                if(tourists.isEmpty()){
//                    return;
//                }
//                computers[i].setTourist(tourists.peek());
//                tourists.remove().start();
//            }
//        }

    }
}

