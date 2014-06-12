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

import java.nio.charset.Charset;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
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
		
		// TextPassword -> byte[24]
		try {
			passBytes = Arrays.copyOf(passToDigest(), 24);
		} catch(Exception e) {
			e.printStackTrace();
			throw new DatabaseExportException(e);
		}
		
		return null;
	}
	
	public void destroy() throws DestroyFailedException {
		pass.destroy();
	}
	
	public boolean isDestroyed() { return pass.isDestroyed(); }
	
	/**
	 * Returns an SHA-256 digest of the database encryption password.
	 * <blockquote>This algorithm performs an SHA-256 hash on a UTF-8 representation of the password.</blockquote>
	 *
	 * @return The database encryption password digest
	 *
	 * @throws NoSuchAlgorithmException If the platform doesn't support SHA-256
	 */
	private byte[] passToDigest() throws NoSuchAlgorithmException {
		// FIXME: Enhance security for this method
		Charset cs = Charset.forName("UTF-8");
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		ByteBuffer bbuf = cs.encode(CharBuffer.wrap(pass.get()));
		byte[] bpass = Arrays.copyOf(bbuf.array(), bbuf.remaining());
		
		return md.digest(bpass);
	}
}
