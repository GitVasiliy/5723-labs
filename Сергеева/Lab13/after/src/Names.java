import java.util.*;

public class Names {
    private HashMap<String, ArrayList<String>> names = new HashMap<>();
    private HashMap<String, ArrayList<String>> group = new HashMap<>();
    public Names() {
        group.put("col", new ArrayList<>());
        group.put("fri", new ArrayList<>());
        group.put("fam", new ArrayList<>());
    }
    public synchronized void add(String name, String phone) {
        if(names.containsKey(name)){
            ArrayList<String> tmp = names.get(name);
            tmp.add(phone);
            names.put(name, tmp);
        }
        else{
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add(phone);
            names.put(name, tmp);
        }
    }

    public synchronized void add(String name, ArrayList<String> phone) {
        names.put(name, phone);
    }

    public synchronized void addGroup(String name, String group) {
	
        if(!group.equals("All")){
            ArrayList<String> tmp = this.group.get(group);
            tmp.add(name);
            this.group.put(group, tmp);
        }
    }

    public synchronized HashMap<String, ArrayList<String>> getNamesStrings(String group) {
        if(group.equals("All"))
            return names;
        HashMap<String, ArrayList<String>> tmp = new HashMap<>();
        for(String n: this.group.get(group)){
            tmp.put(n,names.get(n));
        }
        return tmp;
    }

    public synchronized boolean contain(String name) {
        return names.containsKey(name);
    }

    public synchronized void reset() {
        names.clear();
    }
}
