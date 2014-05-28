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
package wisedevil.credentials;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.stream.Stream;

/**
 * This class provide a facility for maintaining
 * a database of credentials and keyrings.
 */
public class CredentialDatabase implements Observer {
	/**
	 * The credentials.
	 */
	private Set<Credential> credentials;
	
	/**
	 * The keyrings.
	 */
	private Set<Keyring> keyrings;
	
	/**
	 * Initializes a new instance of this class.
	 */
	public CredentialDatabase() {
		credentials = new LinkedHashSet<Credential>();
		keyrings = new LinkedHashSet<Keyring>();
	}
	
	/**
	 * Adds a credential to the database.
	 *
	 * @param value The value to add
	 *
	 * @return True if the value is successfully added to the database
	 */
	public boolean add(Credential value) {
		return credentials.add(value);
	}
	
	/**
	 * Adds a keyring to the database.
	 *
	 * @param value The value to add
	 *
	 * @return True if the value is successfully added to the database
	 */
	public boolean add(Keyring value) {
		return keyrings.add(value);
	}
	
	/**
	 * Removes a credential from the database.
	 *
	 * @param value The value to remove
	 *
	 * @return True if the value is successfully removed from the database
	 */
	public boolean remove(Credential value) {
		return credentials.remove(value);
	}
	
	/**
	 * Removes a keyring from the database.
	 *
	 * @param value The value to remove
	 *
	 * @return True if the value is successfully removed from the database
	 */
	public boolean remove(Keyring value) {
		return keyrings.remove(value);
	}
	
	/**
	 * This callback method is called whenever a key in the database has changed.
	 *
	 * @param o The Observable record that has changed
	 * @param arg The old value of the changed key
	 */
	public void update(Observable o, Object arg) {
		if(o instanceof Credential)
			updateCredentials((Credential)o, (String)arg);
		else if(o instanceof Keyring)
			updateKeyrings((Keyring)o, (String)arg);
	}
	
	/**
	 * This callback method is called whenever a Credential key in the database has changed.
	 *
	 * @param c The Credential record that has changed
	 * @param arg The old value of the changed key
	 */
	private void updateCredentials(Credential c, String oldTitle) {
		if(credentials.stream().filter(x -> x.getTitle() == c.getTitle()).count() > 1)
			c.setTitle(oldTitle);
	}
	
	/**
	 * This callback method is called whenever a Keyring key in the database has changed.
	 *
	 * @param c The Keyring record that has changed
	 * @param arg The old value of the changed key
	 */
	private void updateKeyrings(Keyring c, String oldTitle) {
		if(keyrings.stream().filter(x -> x.getTitle() == c.getTitle()).count() > 1)
			c.setTitle(oldTitle);
	}
	
	/**
	 * Returns <code>true</code> if the database has been modified.
	 *
	 * @return True if the database has been modified
	 */
	public boolean isModified() {
		return (
			credentials.stream().filter(x -> x.isModified()).count()
			+ keyrings.stream().filter(x -> x.isModified()).count()
		) > 0;
	}
	
	/**
	 * Returns an iterator for the credentials of the database
	 *
	 * @return An iterator for the credentials of the dataabse
	 */
	public Iterable<Credential> getCredentials() {
		return credentials;
	}
	
	/**
	 * Returns an iterator for the keyrings of the database
	 *
	 * @return An iterator for the keyrings of the dataabse
	 */
	public Iterable<Keyring> getKeyrings() {
		return keyrings;
	}
	
	/**
	 * Returns a stream for the credentials of the database
	 *
	 * @return A stream for the credentials of the dataabse
	 */
	public Stream getCredentialsStream() {
		return credentials.stream();
	}
	
	/**
	 * Returns a stream for the keyrings of the database
	 *
	 * @return A stream for the keyrings of the dataabse
	 */
	public Stream getKeyringsStream() {
		return keyrings.stream();
	}
}
