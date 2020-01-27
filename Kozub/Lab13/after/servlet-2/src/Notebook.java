import java.util.*;
public class  Notebook {
private ArrayList<Note> contacts = new ArrayList<Note>();
private Note usr = new Note("Vasya", "8 800 555 3535");
public Notebook() {
        this.contacts.add(this.usr);
        }
public synchronized void add(String n, String num) {
         Note u = new Note(n,num);
         this.contacts.add (u);
        }
public synchronized String[] getNamesStrings() {
        String[] array = new String [contacts.size()];
        array = this.contacts.toArray(array);
        return array;
        }
public synchronized void reset() {
        this.contacts.clear();
        }
        }