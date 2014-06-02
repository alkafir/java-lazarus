import wisedevil.test.annotation.*;
import wisedevil.test.result.*;
import static wisedevil.test.Assert.*;

import wisedevil.credentials.*;

@Name("Credential class tests")
@ResultManager(ConsoleResultManager.class)
public class CredentialTests {

	@Test
	public void gets_and_sets_test() {
		Credential c1, c2, c3;
		
		c1 = new Credential("First", "god", new TextPassword(new char[] {'o', 'n', 'e'}));
		c2 = new Credential("First");
		c3 = new Credential("Third", "god", new TextPassword(new char[] {'o', 'n', 'e'}));
		
		assert c1.equals(c2): "Credential.equals() doesn't check title only";
		assert !c1.equals(c3): "Credential.equals() doesn't work properly";
		
		// Gets
		test_gets(c1, "First", "", "god", "one");
		
		// Sets
		c1.setTitle("Old");
		c1.setUser("sprout");
		c1.setPassword(new TextPassword(new char[] {'y', 'e', 'a', 'h'}));
		c1.setDescription("Doesn't matter");
		
		test_gets(c1, "Old", "Doesn't matter", "sprout", "yeah");
	}
	
	private void test_gets(Credential c, String title, String description, String user, String password) {
		assert c.getTitle() == title: "Wrong title";
		assert c.getUser() == user: "Wrong user";
		assert new String(((TextPassword)c.getPassword()).get()).equals(password): "Wrong password";
		assert c.getDescription().equals(description): "Wrong description";
	}
	
	@Test
	public void exceptions_test() {
		assertException(new Runnable() {
			public void run() {
				new Credential(null);
			}
		}, NullPointerException.class);
		
		assertException(new Runnable() {
			public void run() {
				new Credential("");
			}
		}, IllegalArgumentException.class);
	}
	
	@Test
	public void hashcode_test() {
		String title = "This is a test string";
		Credential c = new Credential(title);
		
		assert title.hashCode() == c.hashCode(): "Wrong hashcode";
	}
}
