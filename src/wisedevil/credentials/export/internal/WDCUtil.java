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
package wisedevil.credentials.export.internal;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import java.nio.charset.Charset;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;

/**
 * Contains utilities for the WDC import/export classes.
 *
 * @see wisedevil.credentials.export.WDCExporter
 */
public class WDCUtil {
	/**
	 * Returns an MD-5 digest of the database encryption password.
	 * <blockquote>This algorithm performs an MD-5 hash on a UTF-8 representation of the password.</blockquote>
	 *
	 * @param pass The plain encryption password
	 *
	 * @return The database encryption password digest
	 *
	 * @throws NoSuchAlgorithmException If the platform doesn't support MD-5
	 */
	public static byte[] passToDigest(char[] pass) throws NoSuchAlgorithmException {
		// FIXME: Enhance security for this method
		Charset cs = Charset.forName("UTF-8");
		MessageDigest md = MessageDigest.getInstance("MD5");
		ByteBuffer bbuf = cs.encode(CharBuffer.wrap(pass));
		byte[] bpass = Arrays.copyOf(bbuf.array(), bbuf.remaining());
		
		return md.digest(bpass);
	}
}
