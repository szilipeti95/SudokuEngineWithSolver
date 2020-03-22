import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import util.Digit;
import util.Digit.ValueType;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class UtilDigitTest {
	Digit digit;
	
	@Before
	public void startUp(){
		digit = new Digit();
	}
	
	@Test
	public void AddTest(){
		digit.add(0, ValueType.CLUE);
		Assert.assertEquals(0, digit.size());
		Assert.assertEquals(ValueType.EMPTY, digit.getType());
		digit.add(1, ValueType.GUESS);
		digit.add(2, ValueType.GUESS);
		Assert.assertEquals(2, digit.size());
		Assert.assertEquals(1, digit.get(0));
		Assert.assertEquals(2, digit.get(1));
		Assert.assertEquals(ValueType.GUESS, digit.getType());
		digit.add(3, ValueType.NUMBER);
		Assert.assertEquals(1, digit.size());
		Assert.assertEquals(3, digit.get(0));
		Assert.assertEquals(ValueType.NUMBER, digit.getType());
		digit.add(4, ValueType.NUMBER);
		Assert.assertEquals(1, digit.size());
		Assert.assertEquals(4, digit.get(0));
		Assert.assertEquals(ValueType.NUMBER, digit.getType());
	}
	@Test
	public void UnionTest(){
		digit.add(4, ValueType.NUMBER);
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		digit.intersection(list);
		Assert.assertEquals(1, digit.size());
		Assert.assertEquals(4, digit.get(0));
	}
	@Test
	public void RemoveTest(){
		digit.add(4, ValueType.NUMBER);
		Assert.assertEquals(1, digit.size());
		Assert.assertEquals(4, digit.get(0));
		digit.remove(4);
		Assert.assertEquals(0, digit.size());
		Assert.assertEquals(ValueType.EMPTY, digit.getType());
	}
}
