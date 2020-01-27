import java.util.*;

public class Note {
    public String Name;
    public ArrayList<String> numbers;

    public Note(String n, String num) {
        this.Name = n;
        this.numbers = new ArrayList<String>();
    }

    public void addNum(String num) {
        this.numbers.add(num);
    }

    public String toString() {
        String res = new String();
        res = this.Name + "   " + this.numbers.toString();
        return res;
    }
}