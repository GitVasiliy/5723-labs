import java.util.*;
import java.lang.*;

public class Pack {
    private int maxC = 0;
    private int maxW;
    private Item[] items;

    public Pack(int w, Item[] items){
        maxW = w;
        this.items = new Item[items.length];
        this.items = items;
    }

    public int getCountOfItems(){ return items.length; }
    public int getMaxCost(){ return maxC; }
    public int getCostOfItem(int index){ return items[index].getCost(); }
    public int getWeightOfItem(int index){ return items[index].getWeight(); }
    public int getMaxWeight(){ return maxW; }


    public void solvePack(int tcount) throws Exception{
        if(tcount > getCountOfItems() || tcount <= 0){throw new Exception("Incorrect amount of Threads!"); }
        SolveThread[] threadArray = new SolveThread[tcount];

        int beginIndex = 0;
        int itemPerThread = items.length / tcount;

        for(int i = 0; i < threadArray.length; i++){
            if(i == threadArray.length - 1) {
                itemPerThread = getCountOfItems() - beginIndex;
            }

            threadArray[i] = new SolveThread(this, beginIndex, beginIndex + itemPerThread);
            beginIndex += itemPerThread;
            threadArray[i].start();
        }

        for(int i = 0; i < threadArray.length; i++){
            threadArray[i].join();
            if(threadArray[i].getCost() > maxC){ maxC = threadArray[i].getCost(); }
        }
    }
}