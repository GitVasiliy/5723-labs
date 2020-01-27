
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class ReaderWriter {

    private HashMap<String, Integer> map = new HashMap<>();
    private FThread[] fThreads;
    private String[] fileNames;
    public ReaderWriter(String[] filenames) {
        fileNames = filenames;
        fThreads = new FThread[filenames.length];
    }

    public void scan() throws InterruptedException {
        for (int i = 0; i < fileNames.length; i++) {
            fThreads[i] = new FThread(new File(fileNames[i]));
            fThreads[i].start();
        }

        for (int i = 0; i < fThreads.length; i++) {
            fThreads[i].join();
        }

        for (int i = 0; i < fThreads.length; i++) {
            HashMap<String, Integer> temp = fThreads[i].getMap();

            for (Map.Entry entry : temp.entrySet()) {
                if (map.containsKey(entry.getKey())) {
                    map.put((String) entry.getKey(), (int)entry.getValue() + map.get(entry.getKey()));
                } else {
                    map.put((String)entry.getKey(), (Integer) entry.getValue());
                }
            }
        }

    }



    public void printMap() {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }



}
