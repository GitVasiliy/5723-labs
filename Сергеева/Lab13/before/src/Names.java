import java.util.*;

public class Names {
    private HashMap<String, ArrayList<String>> names = new HashMap<>();

    public Names() {}

    public synchronized void add(String name, String phone) {
		if(names.containsKey(name)){
			ArrayList<String> tmp = names.get(name);
			tmp.add(phone);
			names.put(name, tmp);
		}
		else{
			ArrayList<String> tmp = new ArrayList<String>();
			tmp.add(phone);
			names.put(name, tmp); 
		}
    }

    public synchronized void add(String name, ArrayList<String> phone) {
		names.put(name, phone);
	}

    public synchronized HashMap<String, ArrayList<String>> getNamesStrings() {
        
        return names;
    }

	public synchronized boolean contain(String name){
		return names.containsKey(name);
	}

    public synchronized void reset() {
        names.clear();
    }
}
