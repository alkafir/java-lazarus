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

import java.io.Serializable;
import java.util.Observable;

/**
 * Contains credential information.
 *
 * @see Password
 */
public class Credential extends Observable implements Serializable {
	private static final long serialVersionUID = 0L;
	
	/**
	 * The credential display name.
	 */
	private String title;
	
	/*
	 * The credential description.
	 */
	private String description;
	
	/**
	 * The username.
	 */
	private String user;
	
	/**
	 * The password.
	 */
	private Password password;
	
	/**
	 * True if the instance has been modified.
	 */
	transient boolean modified = false;
	
	/**
	 * Initializes a new instance of this class.
	 *
	 * @param title The credential's title
	 *
	 * @throws NullPointerException if <code>title</code> is null.
	 * @throws IllegalArgumentException if <code>title</code> is an empty string.
	 */
	public Credential(String title) throws NullPointerException, IllegalArgumentException {
		setTitle(title);
	}
	
	/**
	 * Initializes a new instance of this class.
	 *
	 * @param title The credential's title
	 * @param user The credential's username
	 * @param pass The credential's password
	 *
	 * @throws NullPointerException if <code>title</code> is null.
	 * @throws IllegalArgumentException if <code>title</code> is an empty string.
	 */
	public Credential(String title, String user, Password pass) throws NullPointerException, IllegalArgumentException {
		setTitle(title);
		setUser(user);
		setPassword(pass);
	}
	
	/**
	 * Returns the credential's title.
	 *
	 * @return The title
	 */
	public String getTitle() { return title; }
	
	/**
	 * Returns the credential's description.
	 *
	 * @return The description
	 */
	public String getDescription() { return description; }
	
	/**
	 * Returns the credential's username.
	 *
	 * @return The username
	 */
	public String getUser() { return user; }
	
	/**
	 * Returns the credential's password or <code>null</code> if none is set.
	 *
	 * @return The password
	 */
	public Password getPassword() { return password; }
	
	/**
	 * Sets the credential's title.
	 *
	 * <p>When the credential's title changes, observers are notified and the old title string is passed to them.</p>
	 *
	 * @param value The new value
	 *
	 * @throws NullPointerException if <code>value</code> is null.
	 * @throws IllegalArgumentException if <code>value</code> is an empty string.
	 */
	public void setTitle(String value) throws NullPointerException, IllegalArgumentException {
		if(value == null)
			throw new NullPointerException("Credential title cannot be null");
		
		if(value.isEmpty())
			throw new IllegalArgumentException("Credential title cannot be empty");
		
		final String oldTitle = title;
		
		this.title = value;
		
		if(!hasChanged()) {
			setChanged();
			notifyObservers(oldTitle);
			modified = true;
		}
	}
	
	/**
	 * Sets the credential's description.
	 *
	 * @param value The new value
	 */
	public void setDescription(String value) {
		if(value == null)
			value = new String();
			
		this.description = value;
		modified = true;
	}
	
	/**
	 * Sets the credential's username.
	 *
	 * @param value The new value
	 */
	public void setUser(String value) {
		if(value == null)
			value = new String();
			
		this.user = value;
		modified = true;
	}
	
	/**
	 * Sets the credential's password.
	 *
	 * @param value The new value
	 */
	public void setPassword(Password value) {
		this.password = value;
		modified = true;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Credential) {
			Credential c = (Credential)o;
			
			return c.title.equals(title);
		} else return false;
	}
	
	/**
	 * Returns true if the instance has been modified.
	 *
	 * @return The modified state of this object
	 */
	boolean isModified() { return modified; }
	
	@Override
	public int hashCode() {
		return title.hashCode();
	}
}
