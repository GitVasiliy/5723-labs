import auxiliary.WorkWithDirectory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class WorkWithDirectoryTest {

    @Test
    public void testGetListOfFiles(){
        String[] expected = new String[3];
        expected[0] = "Rasp";
        expected[1] = "dft.m";
        expected[2] = "emp3.m";

        String[] actual = WorkWithDirectory.getListOfFiles("Rasp");

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testIsDirectoryEmpty(){
        String[] data = new String[3];
        data[0] = "emp32.m";
        data[1] = "dft.m";
        data[2] = "emp3.m";

        boolean res = WorkWithDirectory.isDirectoryEmpty(data);
        Assertions.assertFalse(res);
    }

    @Test
    public void testGetAllAvailableDirectoriesForUserWithoutMod(){
        ArrayList<String> expected = new ArrayList<>();
        ArrayList<String> actual;

        expected.add("Rasp");

        actual = WorkWithDirectory.getAllAvailableDirectoriesForUserWithoutMod("Rasp");

        Assertions.assertTrue(actual.equals(expected));
    }

    @Test
    public void testHasUserThisDirectory(){
        //user = Rasp, dir = Rasp, expected = true

        Assertions.assertTrue(WorkWithDirectory.hasUserThisDirectory("Rasp", "Rasp"));
    }

    @Test
    public void testDoSearch(){
        //user = Rasp, files = dft.m, emp3m
        Assertions.assertEquals("dft.m", WorkWithDirectory.doSearch("Rasp", "d").get(0));
    }

    @Test
    public void testIsDirectoryEditable() {
        Assertions.assertTrue(WorkWithDirectory.isDirectoryEditable("admin", "admin"));
    }

    @Test
    public void testGetAllEditableDirectoryForUsers(){
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Rasp");

        ArrayList<String> actual = WorkWithDirectory.getAllEditableDirectoryForUser("Rasp");

        Assertions.assertTrue(actual.equals(expected));
    }

    @Test
    public void testPrepareDownloadFileWithLink(){
        String[] expected = new String[2];
        expected[0] = "Napomosh";
        expected[1] = "dft.m";

        String[] actual = WorkWithDirectory.prepareDownloadFileWithLink("EK2XjlF2lZuh4jh2hojraf0P_UJfX8RWY7KLeoHEGpEFwa1rVGXjk");
        Assertions.assertTrue(expected[0].equals(actual[0]) && expected[1].equals(actual[1]));
    }

    @Test
    public void testHasUserThisFile(){
        boolean expected = true;

        Assertions.assertEquals(WorkWithDirectory.hasUserThisFile("dft.m", "Rasp"), expected);
    }
}
