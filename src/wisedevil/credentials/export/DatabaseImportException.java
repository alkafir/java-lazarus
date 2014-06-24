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
 * This exception is thrown during the credential import process if
 * an exception occurs.
 */
public class DatabaseImportException extends Exception {
	private static final long serialVersionUID = 0L;
	
	public DatabaseImportException() {
		super();
	}
	
	public DatabaseImportException(String message) {
		super(message);
	}
	
	public DatabaseImportException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DatabaseImportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public DatabaseImportException(Throwable cause) {
		super(cause);
	}
}
