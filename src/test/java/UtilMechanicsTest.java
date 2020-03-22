import java.util.ArrayList;
import java.util.List;

import com.sun.jdi.Value;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import console.ConsolePrint;
import util.Board;
import util.Digit.ValueType;
import util.Highlight;
import util.Mechanics;

public class UtilMechanicsTest {
	
	Board board1;
	Mechanics mechanics;
	ArrayList<Highlight> h;
	
	@Before
	public void setUp(){
		board1 = new Board(4,4, "Sudokus/4_4_4.txt");
		mechanics = new Mechanics(board1);
		
	}
	
	@Test
	public void CheckRowTest(){
		ArrayList<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(5);
		list.add(6);
		list.add(9);
		list.add(10);
		list.add(11);
		list.add(12);
		list.add(14);
		list.add(15);
		list.add(16);
		board1.getDigit(4, 0).setValue(mechanics.checkRowAt(4, 0, 4));
		List<Integer> list2 = board1.getDigit(4, 0).getValue();
		Boolean same = list.equals(list2);
		Assert.assertEquals(true, same);
		
	}
	@Test
	public void CheckColumnTest(){
		ArrayList<Integer> list = new ArrayList<>();
		list.add(2);
		list.add(4);
		list.add(7);
		list.add(8);
		list.add(11);
		list.add(12);
		list.add(13);
		list.add(16);
		board1.getDigit(4, 0).setValue(mechanics.checkColumnAt(4, 0, 4));
		List<Integer> list2 = board1.getDigit(4, 0).getValue();
		Boolean same = list.equals(list2);
		Assert.assertEquals(true, same);
		
	}
	@Test
	public void CheckBoxTest(){
		ArrayList<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(4);
		list.add(9);
		list.add(11);
		list.add(12);
		list.add(15);
		list.add(16);
		board1.getDigit(4, 0).setValue(mechanics.checkBoxAt(4, 0, 4));
		List<Integer> list2 = board1.getDigit(4, 0).getValue();
		Boolean same = list.equals(list2);
		Assert.assertEquals(true, same);
	}
	@Test
	public void CheckAtTest(){
		ArrayList<Integer> list = new ArrayList<>();
		list.add(2);
		list.add(11);
		list.add(12);
		list.add(16);
		mechanics.checkAt(4, 0);
		List<Integer> list2 = board1.getDigit(4, 0).getValue();
		Boolean same = list.equals(list2);
		Assert.assertEquals(true, same);
		
	}
	@Test
	public void ScanAtTest(){
		mechanics.checkAll();
		boolean found = mechanics.scanAt(2, 0);
		Assert.assertEquals(true, found);
	}
	@Test
	public void ScanAllTest(){
		mechanics.checkAll();
		mechanics.scanAll();
		mechanics.scanAll();
		int value = board1.getDigit(2, 0).getValue().get(0);
		ValueType type = board1.getDigit(2, 0).getType();
		Assert.assertEquals(11, value);
		Assert.assertEquals(ValueType.NUMBER, type);
	}
	@Test
	public void SolvedAtTest(){
		mechanics.checkAll();
		boolean solved = mechanics.solvedAt(40);
		int value = board1.getDigit(40).getValue().get(0);
		ValueType type = board1.getDigit(40).getType();
		Assert.assertEquals(true, solved);
		Assert.assertEquals(4, value);
		Assert.assertEquals(ValueType.NUMBER, type);
	}

	@After
	public void print(){
		ConsolePrint print = new ConsolePrint(board1);
		print.PrintAllValue(4);
	}
}
