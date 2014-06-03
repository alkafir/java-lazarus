import wisedevil.test.result.*;
import wisedevil.test.annotation.*;
import static wisedevil.test.Assert.*;

import java.util.Arrays;
import wisedevil.credentials.*;

@Name("TextPassword methods test case")
@ResultManager(ConsoleResultManager.class)
public class TextPasswordTests {
	@Test
	public void equals_test() {
		TextPassword t1, t2, t3, t4;
		
		// Remember using strings for passwords is bad practice
		t1 = new TextPassword("secret".toCharArray());
		t2 = new TextPassword("segrat".toCharArray());
		t3 = new TextPassword("Secret".toCharArray());
		t4 = new TextPassword("secret".toCharArray());
		
		assert !t1.equals(t2): "Error detecting difference";
		assert !t1.equals(t3): "Error detecting case";
		assert t1.equals(t4): "Error detecting equality";
	}
	
	@Test
	public void get_test() {
		TextPassword t1, t2;
		
		t1 = new TextPassword("first".toCharArray());
		t2 = new TextPassword(new char[0]);
		
		assert Arrays.equals(t1.get(), "first".toCharArray());
		assert t2.get().length == 0;
	}
	
	@Test
	public void exceptions_test() {
		assertException(new Runnable() {
			public void run() {
				new TextPassword(null);
			}
		}, NullPointerException.class);
		
		assertNoException(new Runnable() {
			public void run() {
				new TextPassword("".toCharArray());
			}
		});
	}
}