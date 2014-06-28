/*
 * Lazarus: Credentials management library
 *     Copyright (C) 2014 Alfredo 'wisedevil' Mungo
 *
 *     This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package wisedevil.credentials.export;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;

import java.security.spec.InvalidParameterSpecException;

import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import javax.security.auth.DestroyFailedException;

import wisedevil.credentials.CredentialDatabase;
import wisedevil.credentials.TextPassword;

import static wisedevil.credentials.export.internal.WDCUtil.passToDigest;

/**
 * This class provides support for importing a CredentialDatabase from a
 * "WiseDevil Credentials" file.
 */
public class WDCImporter implements Importer {
	/**
	 * The decryption password.
	 */
	private final TextPassword pass;
	
	/**
	 * The encrypted data.
	 */
	private final WDCEncryptionRecord encdata;
	
	/**
	 * Initializes a new instance of this class.
	 *
	 * @param data The encrypted data
	 * @param pass The decryption password
	 *
	 * @throws NullPointerException If any of the arguments is null
	 */
	public WDCImporter(WDCEncryptionRecord data, TextPassword pass) {
		if(data == null || pass == null)
			throw new NullPointerException();
		
		this.pass = pass;
		encdata = data;
	}
	
	public void destroy() throws DestroyFailedException {
		pass.destroy();
	}
	
	public boolean isDestroyed() { return pass.isDestroyed(); }
	
	/**
	 * Imports the credentials.
	 *
	 * @return The imported credentials as a CredentialDatabase object
	 *
	 * @throws DatabaseImportException if an exception occurs during the process
	 */
	public CredentialDatabase importDatabase() throws DatabaseImportException {
		byte[] passdgst = null;
		byte[] raw = null;
		
		try {
			passdgst = passToDigest(pass.get());
			raw = decryptDatabase(encdata, passdgst);
			return deserializeDatabase(raw);
			
		} catch(Exception e) {
			throw new DatabaseImportException(e);
		} finally {
			if(passdgst != null)
				Arrays.fill(passdgst, (byte)0);
			
			if(raw != null)
				Arrays.fill(raw, (byte)0);
		}
	}
	
	/**
	 * Decrypts the database using AES/CBC/PKCS5Padding.
	 *
	 * @param enc The encrypted data to be decrypted
	 * @param pass The decryption password (hash)
	 *
	 * @return The decrypted database as an array of bytes
	 *
	 * @throws NoSuchAlgorithmException If the platform doesn't support AES/CBC
	 * @throws InvalidKeyException If the key is invalid
	 * @throws InvalidParameterException If an algorithm method parameter is invalid
	 * @throws InvalidParameterSpecException If an algorithm parameter spec is invalid
	 * @throws NoSuchPaddingException If the platform doesn't support PKCS5Padding
	 * @throws InvalidAlgorithmParameterException If an algorithm parameter is invalid
	 * @throws IllegalBlockSizeException If the data block size is not correct
	 * @throws BadPaddingException If the data is not properly padded
	 *
	 * @see <a href="https://gist.github.com/wisedevil/47fd55226a7a4cbcf4c6">Java AES CBC gist</a>
	 */
	private static byte[] decryptDatabase(WDCEncryptionRecord enc, byte[] pass)
		throws NoSuchAlgorithmException,
			NoSuchPaddingException,
			InvalidKeyException,
			InvalidParameterException,
			InvalidParameterSpecException,
			InvalidAlgorithmParameterException,
			IllegalBlockSizeException,
			BadPaddingException {
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKey seckey = new SecretKeySpec(pass, 0, pass.length, "AES");
		AlgorithmParameters parms = AlgorithmParameters.getInstance("AES");

		// Init
		parms.init(new IvParameterSpec(enc.getIV()));
		c.init(Cipher.DECRYPT_MODE, seckey, parms);

		// Decrypt
		return c.doFinal(enc.getData());
	}
	
	/**
	 * Deserializes the credential database.
	 *
	 * @param rawdb The database to deserialize as an array of bytes
	 *
	 * @return The deserialized credential database as a byte array
	 *
	 * @throws IOException If an output exception occurs during the deserialization process
	 * @throws ClassNotFoundException If the deserialized object is not a CredentialDatabase object
	 */
	private static CredentialDatabase deserializeDatabase(byte[] rawdb) throws IOException, ClassNotFoundException {
		try (
			ByteArrayInputStream bs = new ByteArrayInputStream(rawdb);
			ObjectInputStream os = new ObjectInputStream(bs)
		) {
			return (CredentialDatabase)os.readObject();
		}
	}
}
