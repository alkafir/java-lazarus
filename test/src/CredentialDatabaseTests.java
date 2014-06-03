import wisedevil.test.annotation.*;
import wisedevil.test.result.*;
import static wisedevil.test.Assert.*;

import java.util.Iterator;
import wisedevil.credentials.*;

@Name("CredentialDatabase methods")
@ResultManager(ConsoleResultManager.class)
public class CredentialDatabaseTests {
	CredentialDatabase cd;
	
	public void setup() {
		cd = new CredentialDatabase();
		
		Credential c1, c2, c3;
		Keyring k1, k2;
		
		c1 = new Credential("First");
		c2 = new Credential("Second");
		c3 = new Credential("Third");
		
		k1 = new Keyring("First");
		k2 = new Keyring("Second");
		
		cd.add(c1);
		cd.add(c2);
		cd.add(c3);
		
		cd.add(k1);
		cd.add(k2);
	}
	
	public void cleanup() {
		cd = null;
	}
	
	@Test
	public void ismodified_test() {
		assert cd.isModified();
	}
	
	@Test
	public void credential_invalid_rename_test() {
		Iterator<Credential> ci = cd.getCredentials().iterator();
		Credential c1 = ci.next();
		Credential c2 = ci.next();
		
		c1.setTitle(c2.getTitle());
		
		// Count
		{
			long cnt = cd.getCredentialsStream().filter(x -> ((Credential)x).getTitle().equals(c2.getTitle())).count();
			assert cnt == 1: "Multiple item count is: " + cnt;
		}
		
		// Count
		{
			String oldTitle = c1.getTitle();
			
			c1.setTitle("Ciccio");
		
			long cnt1 = cd.getCredentialsStream().filter(x -> ((Credential)x).getTitle().equals("Ciccio")).count();
			long cnt2 = cd.getCredentialsStream().filter(x -> ((Credential)x).getTitle().equals(oldTitle)).count();
			assert cnt1 == 1 && cnt2 == 0: "Renamed item count is: " + cnt1 + ", Old-named item count is: " + cnt2;
			
			c1.setTitle(oldTitle);
		}
	}
	
	@Test
	public void keyring_invalid_rename_test() {
		Iterator<Keyring> ci = cd.getKeyrings().iterator();
		Keyring c1 = ci.next();
		Keyring c2 = ci.next();
		
		c1.setTitle(c2.getTitle());
		
		// Count
		{
			long cnt = cd.getKeyringsStream().filter(x -> ((Keyring)x).getTitle().equals(c2.getTitle())).count();
			assert cnt == 1: "Multiple item count is: " + cnt;
		}
		
		// Count
		{
			String oldTitle = c1.getTitle();
			
			c1.setTitle("Ciccio");
		
			long cnt1 = cd.getKeyringsStream().filter(x -> ((Keyring)x).getTitle().equals("Ciccio")).count();
			long cnt2 = cd.getKeyringsStream().filter(x -> ((Keyring)x).getTitle().equals(oldTitle)).count();
			assert cnt1 == 1 && cnt2 == 0: "Renamed item count is: " + cnt1 + ", Old-named item count is: " + cnt2;
			
			c1.setTitle(oldTitle);
		}
	}
	
	@Test
	public void remove_test() {
		final Credential c = new Credential("remove_test_credential");
		final Keyring k = new Keyring("remove_test_keyring");
		final long n = cd.getCredentialsStream().count();
		final long nk = cd.getKeyringsStream().count();
		
		cd.add(c);
		assert cd.getCredentialsStream().count() == (n + 1);
		
		cd.remove(c);
		assert cd.getCredentialsStream().count() == n;
		
		cd.getCredentials().forEach(e -> {
			if(e == c)
				fail();
		});
		
		cd.add(k);
		assert cd.getKeyringsStream().count() == (nk + 1);
		
		cd.remove(k);
		assert cd.getKeyringsStream().count() == nk;
		
		cd.getKeyrings().forEach(e -> {
			if(e == k)
				fail();
		});
	}
}
