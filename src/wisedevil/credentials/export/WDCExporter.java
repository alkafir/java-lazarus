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

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import java.nio.charset.Charset;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;

import javax.security.auth.DestroyFailedException;

import wisedevil.credentials.CredentialDatabase;
import wisedevil.credentials.TextPassword;

/**
 * This class provides support for exporting a CredentialDatabase as a
 * "WiseDevil Credentials" file format.
 */
public class WDCExporter implements Exporter<byte[]> {
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
	public byte[] export() throws DatabaseExportException {
		byte[] passBytes;
		byte[] dataBytes = null;
		
		// TextPassword -> byte[24]
		try {
			passBytes = passToDigest();
			dataBytes = serializeDatabase();
		} catch(Exception e) {
			e.printStackTrace();
			throw new DatabaseExportException(e);
		} finally {
			if(dataBytes != null)
				Arrays.fill(dataBytes, (byte)0);
		}
		
		return null;
	}
	
	public void destroy() throws DestroyFailedException {
		pass.destroy();
	}
	
	public boolean isDestroyed() { return pass.isDestroyed(); }
	
	/**
	 * Returns an MD-5 digest of the database encryption password.
	 * <blockquote>This algorithm performs an MD-5 hash on a UTF-8 representation of the password.</blockquote>
	 *
	 * @return The database encryption password digest
	 *
	 * @throws NoSuchAlgorithmException If the platform doesn't support MD-5
	 */
	private byte[] passToDigest() throws NoSuchAlgorithmException {
		// FIXME: Enhance security for this method
		Charset cs = Charset.forName("UTF-8");
		MessageDigest md = MessageDigest.getInstance("MD5");
		ByteBuffer bbuf = cs.encode(CharBuffer.wrap(pass.get()));
		byte[] bpass = Arrays.copyOf(bbuf.array(), bbuf.remaining());
		
		return md.digest(bpass);
	}
	
	/**
	 * Serializes the credential database as a byte array.
	 *
	 * @return The serialized credential database
	 *
	 * @throws IOException If an output exception occurs during the serialization process
	 */
	private byte[] serializeDatabase() throws IOException {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bs);
		
		os.writeObject(db);
		
		return bs.toByteArray();
	}
	
	/**
	 * Encrypts the database data.
	 *
	 * @param data The plain data to be encrypted
	 * @param pass The encryption password
	 *
	 * @return The encrypted database
	 */
	private static byte[] encryptDatabase(byte[] data, byte[] pass) {
		return null;
	}
}
