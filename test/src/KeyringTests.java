import wisedevil.test.result.*;
import wisedevil.test.annotation.*;
import static wisedevil.test.Assert.*;

import wisedevil.credentials.*;

@Name("Keyring methods test case")
@ResultManager(ConsoleResultManager.class)
public class KeyringTests {
	@Test
	public void add_remove_test() {
		Credential c1 = new Credential("c1");
		Credential c2 = new Credential("c2");
		Keyring k = new Keyring("k");
		
		k.add(c1);
		k.add(c2);
		assert k.stream().count() == 2;
		
		k.remove(c1);
		assert k.stream().count() == 1;
		
		k.remove(c2);
		assert k.stream().count() == 0;
	}

	@Test
	public void gets_sets_test() {
		Keyring k = new Keyring("k");
		assert k.getTitle() == "k";
		
		k.setTitle("j");
		assert k.getTitle() == "j";
		
		assert k.getDescription().equals(new String());
		
		k.setDescription("foo bar");
		assert k.getDescription().equals("foo bar");
	}
	
	@Test
	public void exceptions_test() {
		assertException(new Runnable() {
			public void run() {
				new Keyring(null);
			}
		}, NullPointerException.class);
		
		assertException(new Runnable() {
			public void run() {
				new Keyring("");
			}
		}, IllegalArgumentException.class);
	}
	
	@Test
	public void hashcode_test() {
		String title = "This is a test title";
		Keyring k = new Keyring(title);
		
		assert title.hashCode() == k.hashCode();
	}
}
