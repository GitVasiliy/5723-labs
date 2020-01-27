package backpack;

public class SolveThread extends Thread {
    private Pack pack;
    private int beginIndex;
    private int bestCost = 0;
    private int endIndex;
    private long counter = 0;
    private int[] visit;


    public SolveThread(Pack p, int beginIndex, int endIndex){
        this.pack = p;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        visit = new int[pack.getCountOfItems()];
    }

    public void solve(int vol, int cost, int count){

        if(count >= pack.getCountOfItems()){
//            System.out.println(++counter);
            if(bestCost < cost){ bestCost = cost; }
            return;
        }
        for(int i = 0; i < pack.getCountOfItems(); i++){
            if(visit[i] == 0){
                visit[i] = 1;
                count++;
                if(vol - pack.getWeightOfItem(i) >= 0) {
                    solve(vol - pack.getWeightOfItem(i), cost + pack.getCostOfItem(i), count);
                }
                else{
                    solve(vol, cost, count);
                }
                visit[i] = 0;
                count--;
                if(bestCost < cost){ bestCost = cost; }
            }
            if(visit[i] == 1){
                continue;
            }
        }
    }

    public void run(){
        int k = 0;
        for(int i = beginIndex; i < endIndex; i++) {
            visit[i] = 1;
            if(pack.getMaxWeight() - pack.getWeightOfItem(i) >= 0) {
                solve(pack.getMaxWeight() - pack.getWeightOfItem(i), pack.getCostOfItem(i), 1);
            }
            else{
                solve(pack.getMaxWeight(), 0, 1);
            }
            visit[i] = 0;
        }
    }
    public int getCost(){
        return bestCost;
    }
}
