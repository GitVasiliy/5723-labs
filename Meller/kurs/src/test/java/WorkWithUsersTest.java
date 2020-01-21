import auxiliary.WorkWithUsers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorkWithUsersTest {
    @Test
    public void testIsUserRegistered(){
        boolean expected = true;

        Assertions.assertEquals(WorkWithUsers.isUserRegistered("admin"), expected);
    }

    @Test
    public void testIsAuthDataValid(){
        boolean expected = false;

        Assertions.assertEquals(WorkWithUsers.isAuthDataValid("admin", "62734"), expected);
    }

    @Test
    public void testFindForbiddenSymbols(){
        String expected = "Your login mustn't contains symbols & and #!";
        String actual = WorkWithUsers.findForbiddenSymbols("#admin");

        Assertions.assertTrue(expected.equals(actual));
    }
}