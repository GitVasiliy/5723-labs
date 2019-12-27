import java.util.LinkedList;
import java.util.ListIterator;

public class MyRandomList {
    protected LinkedList<Integer> list;
    public MyRandomList(){
         list = new LinkedList<Integer>();
         for(int i = 0; i < 10; i++)
             list.add((int)(Math.random() * 10));
    }

    public void addNumber(){
        list.add((int)(Math.random() * 10) + 1);
        System.out.print(list);

    }
    public void removeNumber(){
        list.remove((int)(Math.random() * list.size()));
        System.out.print(list);
    }

    public void calcZero() {
        ListIterator<Integer> i = list.listIterator();
        int sum = 0;
        while (i.hasNext()) {
            if (i.next() != 0)
                sum++;
        }
        System.out.print(sum);
    }

    public String toString(){
        return list.toString();
    }
}
