import java.io.*;
public class KnockKnockProtocol {
    public String processInput() {

        return new String("Welcome to the chat, buddy");

    }
    public int analyse(String s){
        if(s.startsWith("@sendUser")){
            return 1;
        }
        if(s.indexOf("@setName") == 0){
            return 2;
        }
        return 0;
    }
    public String argOfFunc(int mode, String s) {
        String res = new String(s);
        if(mode == 1){
                res = s.substring(10);
                res = res.substring(0, res.indexOf(' '));
            return res;
        }
        if(mode == 2) {
            res = s.substring(s.indexOf(' ')+ 1);
            //res = res.substring(0, res.indexOf(' '));
            return res;
        }


        if(mode == 3){
            res = s.substring(s.indexOf(' ')+ 1);
            res = res.substring(res.indexOf(' '));
            return res;
            //break; add changeName method
        }
        return res;
    }

    public String processInput(String s) {


        return s ;
    }


}
