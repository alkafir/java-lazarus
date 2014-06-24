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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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
 * This class provides support for exporting a CredentialDatabase as a
 * "WiseDevil Credentials" file format.
 */
public class WDCExporter implements Exporter<WDCEncryptionRecord> {
	/**
	 * The CredentialDatabase to export.
	 */
	private final CredentialDatabase db;
	
	/**
	 * The export password to encrypt the database.
	 */
	private final TextPassword pass;
	
	/**
	 * Initializes a new instance of this class.
	 *
	 * @param db The CredentialDatabase to export
	 * @param pass The password that will be used to encrypt the database
	 *
	 * @throws NullPointerException If <code>db</code> is null
	 */
	public WDCExporter(CredentialDatabase db, TextPassword pass) throws NullPointerException {
		if(db == null || pass == null)
			throw new NullPointerException();
			
		this.db = db;
		this.pass = pass;
	}
	
	/**
	 * Exports the credentials.
	 *
	 * @return The exported credentials in WDC format
	 *
	 * @throws DatabaseExportException if an exception occurs during the process
	 */
	public WDCEncryptionRecord exportDatabase() throws DatabaseExportException {
		byte[] passBytes = null;
		byte[] dataBytes = null;
		WDCEncryptionRecord encData;
		
		try {
			passBytes = passToDigest(pass.get());
			dataBytes = serializeDatabase();
			encData = encryptDatabase(dataBytes, passBytes);
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new DatabaseExportException(e);
		} finally {
			if(dataBytes != null)
				Arrays.fill(dataBytes, (byte)0);
				
			if(passBytes != null)
				Arrays.fill(passBytes, (byte)0);
		}
		
		return null;
	}
	
	public void destroy() throws DestroyFailedException {
		pass.destroy();
	}
	
	public boolean isDestroyed() { return pass.isDestroyed(); }

	/**
	 * Serializes the credential database as a byte array.
	 *
	 * @return The serialized credential database
	 *
	 * @throws IOException If an output exception occurs during the serialization process
	 */
	private byte[] serializeDatabase() throws IOException {
		try (
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bs);
		) {
			os.writeObject(db);
			
			return bs.toByteArray();
		}
	}
	
	/**
	 * Encrypts the database using AES/CBC/PKCS5Padding with SecureRandom generated IV.
	 *
	 * @param data The plain data to be encrypted
	 * @param pass The encryption password (hash)
	 *
	 * @return The encrypted database and relative IV as a {@link WDCEncryptionRecord} object
	 *
	 * @see https://gist.github.com/wisedevil/47fd55226a7a4cbcf4c6
	 */
	private static WDCEncryptionRecord encryptDatabase(byte[] data, byte[] pass)
		throws	NoSuchAlgorithmException,
			NoSuchPaddingException,
			InvalidKeyException,
			InvalidParameterException,
			InvalidParameterSpecException,
			InvalidAlgorithmParameterException,
			IllegalBlockSizeException,
			BadPaddingException {
		byte[] iv = new byte[16];
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecureRandom rnd = new SecureRandom();
		SecretKey seckey = new SecretKeySpec(pass, 0, pass.length, "AES");
		AlgorithmParameters parms = AlgorithmParameters.getInstance("AES");

		// Init
		rnd.nextBytes(iv);
		parms.init(new IvParameterSpec(iv));
		c.init(Cipher.ENCRYPT_MODE, seckey, parms);

		// Encrypt
		byte[] res = c.doFinal(data);

		return new WDCEncryptionRecord(res, iv);
	}
}
