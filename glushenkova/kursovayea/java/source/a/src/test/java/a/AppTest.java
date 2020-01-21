package a;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import a.Server;
/**
 * Unit test for simple App.
 */
public class AppTest 
{
    //private final int[][] board = new int[10][10];

    @Test
    public void checkWinnerTrue()
    {
    	Server.Board sb = new Server.Board();
    	sb.board = new int[10][10];
		for (int i = 0; i < 10; i++)
					for (int j = 0; j < 10; j++)
		    			sb.board[i][j] = 0;

    	sb.board[0][0] = 1;
    	sb.board[0][1] = 1;
    	sb.board[0][2] = 1;
    	sb.board[0][3] = 1;
    	sb.board[0][4] = 1;
    	//sb.checkWinnerEnd();
        assertEquals(1, sb.checkWinnerForTest(sb.board));
    }
    @Test
    public void checkMoveTrue()
    {
    	Server.Board sb = new Server.Board();
    	sb.board = new int[10][10];
		for (int i = 0; i < 10; i++)
					for (int j = 0; j < 10; j++)
		    			sb.board[i][j] = 0;

    	sb.board[0][0] = 1;
    	sb.board[0][1] = 1;
    	sb.board[0][2] = 1;
    	sb.board[0][3] = 1;
    	sb.board[0][4] = 1;
    	sb.board[0][5] = 2;
    	sb.board[1][1] = 2;
    	sb.board[2][2] = 2;
    	sb.board[1][3] = 2;
    	sb.board[2][4] = 2;
        assertTrue(sb.checkMoveForTest(sb.board, 5, 5));
        assertTrue(sb.checkMoveForTest(sb.board, 0, 8));
    }
}
