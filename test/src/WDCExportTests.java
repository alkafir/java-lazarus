import wisedevil.test.MethodInspector;
import wisedevil.test.annotation.*;
import wisedevil.test.result.*;

import static wisedevil.test.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import wisedevil.credentials.Credential;
import wisedevil.credentials.Keyring;
import wisedevil.credentials.CredentialDatabase;
import wisedevil.credentials.TextPassword;
import wisedevil.credentials.export.WDCExporter;
import wisedevil.credentials.export.DatabaseExportException;

import static wisedevil.credentials.export.internal.WDCUtil.passToDigest;

@Name("WDCExport test case")
@Description("Tests for the WDC export feature")
@ResultManager(ConsoleResultManager.class)
public class WDCExportTests {
	CredentialDatabase cd;
	
	public void setup() {
		cd = new CredentialDatabase();
		
		Credential c1, c2, c3;
		c1 = new Credential("first");
		c2 = new Credential("second");
		c3 = new Credential("third");
		
		Keyring cs1 = new Keyring("first");
		Keyring cs2 = new Keyring("second");
		
		cs1.add(c1);
		cs1.add(c3);
		cs2.add(c2);
		cs2.add(c3);
		
		cd.add(c1);
		cd.add(c2);
		cd.add(c3);
		cd.add(cs1);
		cd.add(cs2);
	}
	
	public void cleanup() {
		cd = null;
	}
	
	@Test
	public void password_digest_test() {
		final TextPassword pass = new TextPassword("this is a test password".toCharArray());
		final WDCExporter wdc = new WDCExporter(cd, pass);
		byte[] passHash = generateHash("f1112a8ccc35a8aa87c57c237cd12c9b");
		
		try {
			assert Arrays.equals(passHash, Arrays.copyOf(passToDigest(pass.get()), 16)): printHash(passHash) + " " + printHash(Arrays.copyOf(passToDigest(pass.get()), 16));
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail("NoSuchAlgorithmException thrown in passToDigest() method");
		}
	}
	
	private byte[] generateHash(String hash) {
		final int hash_size = 16;

		char[] chash = hash.toUpperCase().toCharArray();
		byte[] res = new byte[hash_size];
		byte b = 0;
		int j = 0;
		
		for(int i = 0; i < hash_size * 2; i++) {
			if(i % 2 == 0)
				b = 0;
			
			if(chash[i] >= 48 && chash[i] <= 57)
				b += (byte)chash[i] - 48;
			else if(chash[i] >= 65 && chash[i] <= 90)
				b += ((byte)chash[i] - 55);
			else throw new RuntimeException("Invalid hash");
			
			if(i % 2 == 0)
				b <<= 4;
			else
				res[j++] = b;
		}
		
		return res;
	}
	
	private String printHash(byte[] hash) {
		StringBuffer res = new StringBuffer();
		
		for(int i = 0; i < hash.length; i++)
			res.append(String.format("%x", hash[i]));
		
		return res.toString();
	}
}
