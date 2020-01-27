package names;

public class Names {
    private String serverName = "Server";
    private String clientName = "Client";

    public String getServerName(){
        return serverName;
    }

    public String getClientName(){
        return clientName;
    }

    public void setClientName(String newName){
        clientName = newName;
    }

    public void setServerName(String newName){
        serverName = newName;
    }
}
