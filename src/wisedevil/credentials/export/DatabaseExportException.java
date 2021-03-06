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
 * This exception is thrown during the credential export process if
 * an exception occurs.
 */
public class DatabaseExportException extends Exception {
	/**
	 * Serialization version number.
	 */
	private static final long serialVersionUID = 0L;
	
	/**
	 * Initializes a new instance of this class.
	 */
	public DatabaseExportException() {
		super();
	}
	
	/**
	 * Initializes a new instance of this class.
	 *
	 * @param message The message
	 */
	public DatabaseExportException(String message) {
		super(message);
	}
	
	/**
	 * Initializes a new instance of this class.
	 *
	 * @param message The message
	 * @param cause The cause
	 */
	public DatabaseExportException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Initializes a new instance of this class.
	 *
	 * @param message The message
	 * @param cause The cause
	 * @param enableSuppression whether or not suppression is enabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	public DatabaseExportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	/**
	 * Initializes a new instance of this class.
	 *
	 * @param cause The cause
	 */
	public DatabaseExportException(Throwable cause) {
		super(cause);
	}
}
