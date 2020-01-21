package auxiliary;

/*
Работа с пользователями
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class WorkWithUsers {
    private static synchronized void writeInDB(Object message) throws IOException {
        BufferedWriter dataBaseWriter = new BufferedWriter (new FileWriter
                (WorkWithDirectory.dbPath + "\\users.txt", true));

        dataBaseWriter.write(message.toString());
        dataBaseWriter.close();
    }

    public static boolean isUserRegistered(String name){
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(WorkWithDirectory.dbPath + "\\users.txt"));

            String login;
            while ((login = reader.readLine()) != null) {
                login = login.split("&")[0];
                if (login.equals(name)) {
                    reader.close();
                    return true;
                }
            }

            reader.close();
        }
        catch (IOException e) {
             System.out.println(e.getMessage());
         }
        return false;
    }

    public static boolean isAuthDataValid(String name, String password){
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(WorkWithDirectory.dbPath + "\\users.txt"));

            String accountData;
            while ((accountData = reader.readLine()) != null) {
                if (accountData.split("&")[0].equals(name) && accountData.split("&")[1].equals(password)){
                    reader.close();
                    return true;
                }
            }

            reader.close();
        }
        catch (IOException e) {
           System.out.println(e.getMessage());
        }
        return false;
    }

    public static void addNewUser(String name, String password){
        StringBuilder data = new StringBuilder();
        data.append(name).append("&").append(password).append("\n");

        try {
            writeInDB(data);
        }
        catch (IOException e) {
            WorkWithDirectory.writeInLog(new Date().toString() + " In method addNewUser in class WorkWithUsers");
            System.out.println(e.getMessage());
        }

        String pathToNewDir = WorkWithDirectory.dirPath + "\\" + name;
        new File(pathToNewDir).mkdir();

        data.delete(0, data.length());
        data.append(name).append("&").append(name).append("#").append("readAndMod").append("\n");
        try {
            WorkWithDirectory.writeInDB(data, false);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        WorkWithDirectory.addDirectoryToUser(name, "admin", "readAndMod");
    }

    public static ArrayList<String> getAllUsers(){
        BufferedReader reader;
        ArrayList<String> res = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(WorkWithDirectory.dbPath + "\\directoriesOfUsers.txt"));

            String tmp;
            while ((tmp = reader.readLine()) != null) {
                tmp = tmp.split("&")[0];
                res.add(tmp);
            }

            reader.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            WorkWithDirectory.writeInLog(new Date().toString() + " In method getAllUsers in class WorkWithUsers");
//            res.add("Somthing was wrong!");
        }
        return res;
    }

    public static String findForbiddenSymbols(String line){
        if(line.contains("&") || line.contains("#")){
            return "Your login mustn't contains symbols & and #!";
        }
        return "Login is OK";
    }
}
