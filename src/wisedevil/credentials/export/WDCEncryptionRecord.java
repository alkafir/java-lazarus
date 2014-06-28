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

/**
 * WDC encryption output and decryption input data.
 *
 * @see WDCExporter
 */
public class WDCEncryptionRecord {

	/**
	 * The encrypted data.
	 */
	private final byte[] data;
	
	/**
	 * The AES encryption IV.
	 */
	private final byte[] iv;
	
	/**
	 * Initializes a new instance of this class.
	 *
	 * @param data The encrypted data
	 * @param iv The encryption IV
	 *
	 * @throws NullPointerException if any of the arguments is null
	 */
	public WDCEncryptionRecord(byte[] data, byte[] iv) {
		if(data == null || iv == null)
			throw new NullPointerException();
		
		this.data = data;
		this.iv = iv;
	}
	
	/**
	 * Returns the encrypted data.
	 *
	 * @return The data
	 */
	public byte[] getData() { return data; }
	
	/**
	 * Returns the encryption IV.
	 *
	 * @return The encryption initialization vector
	 */
	public byte[] getIV() { return iv; }
}