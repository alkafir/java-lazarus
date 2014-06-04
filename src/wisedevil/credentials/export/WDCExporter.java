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

import wisedevil.credentials.CredentialDatabase;

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
	 * Initializes a new instance of this class.
	 *
	 * @param db The CredentialDatabase to export
	 *
	 * @throws NullPointerException If <code>db</code> is null
	 */
	public WDCExporter(CredentialDatabase db) throws NullPointerException {
		if(db == null)
			throw new NullPointerException();
			
		this.db = db;
	}
	
	/**
	 * Exports the credentials.
	 *
	 * @return The exported credentials in WDC format
	 * @throws CredentialsExportException if an exception occurs during the process
	 */
	public byte[] export() throws CredentialsExportException {
		return null;
	}
}
