package backpack;

public class Item {
    private int cost;
    private int weight;

    public Item(int cost, int weight){
        this.cost = cost;
        this.weight = weight;
    }

    public int getWeight(){ return weight; }
    public int getCost() { return cost; }
}
