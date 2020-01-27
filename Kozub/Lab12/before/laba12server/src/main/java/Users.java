import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Users {
    public HashMap<String, Socket> Users = new HashMap<String, Socket>() ;

    //public ArrayList<Socket> Users  = new ArrayList<Socket>();
    public void add(String name, Socket s) {
        if(Users.get(name) == null) {

            Users.put(name, s);
        }
    }
    public void setName(Socket s,String name){
        if(Users.containsValue(s)){
            Iterator it = Users.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry mapElement = (Map.Entry) it.next();
                if(mapElement.getValue().equals(s)){
                   it.remove();
                }
            }
            this.add(name, s);
        }
    }
    public HashMap<String, Socket> getUsers(){
        return this.Users;
    }
    public int getNumOfUsers() {
        return this.Users.size();
    }
    public String getName(Socket s) {
        if(Users.containsValue(s)){
            Iterator it = Users.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry mapElement = (Map.Entry) it.next();
                if(mapElement.getValue().equals(s)){
                    return (String)mapElement.getKey();
                }
            }
        }
        return "NoSuchUserException";

    }
}

