import org.junit.Before;
import org.junit.Test;

import util.Board;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class UtilBoardTest {
	
	Board board1;
	
	@Before
	public void setUp(){
		board1 = new Board(3,3, "Sudokus/3_3_0.txt");
	}

	@Test
	public void GetPosTest(){
		int pos1 = board1.getPos(3, 7);
		int pos2 = board1.getPos(0, 3);
		int pos3 = board1.getPos(5, 0);
		Assert.assertEquals(66, pos1);
		Assert.assertEquals(27, pos2);
		Assert.assertEquals(5, pos3);
	}
	@Test
	public void CopyTest(){
		Board board2 = board1.copy();
		boolean same = board1.equals(board2);
		Assert.assertEquals(false, same);
		
	}
}
