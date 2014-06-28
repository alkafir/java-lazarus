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

import java.util.Iterator;
import java.util.Observable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Spliterator;
import java.util.stream.Stream;

/**
 * This class provides a pooling facility for related credentials.
 */
public class Keyring extends Observable implements IKeyring {
	/**
	 * Serialization version number.
	 */
	private static final long serialVersionUID = 0L;
	
	/**
	 * The credentials of the keyring.
	 */
	private Set<Credential> keys;
	
	/**
	 * The credential display name.
	 */
	private String title;
	
	/**
	 * The credential description.
	 */
	private String description;
	
	/**
	 * True if the instance has been modified.
	 */
	transient boolean modified = false;
	
	/**
	 * Initializes a new instance of this class.
	 *
	 * @param title The title of the keyring
	 *
	 * @throws NullPointerException if <code>value</code> is null.
	 * @throws IllegalArgumentException if <code>value</code> is an empty string.
	 */
	public Keyring(String title) {
		keys = new LinkedHashSet<Credential>();
		setTitle(title);
		setDescription(null);
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
	 * Sets the keyring's title.
	 *
	 * <p>When the keyring's title changes, observers are notified and the old title string is passed to them.</p>
	 *
	 * @param value The new value
	 *
	 * @throws NullPointerException if <code>value</code> is null.
	 * @throws IllegalArgumentException if <code>value</code> is an empty string.
	 */
	public void setTitle(String value) {
		if(value == null)
			throw new NullPointerException("Keyring title cannot be null");
		
		if(value.isEmpty())
			throw new IllegalArgumentException("Keyring title cannot be empty");
		
		final String oldTitle = title;
		
		this.title = value;
		
		if(!hasChanged()) {
			setChanged();
			notifyObservers(oldTitle);
			modified = true;
		}
	}
	
	/**
	 * Sets the keyring's description.
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
	 * Adds a credential to the keyring.
	 * <blockquote>Credentials added more than one times are shown once.</blockquote>
	 *
	 * @param value The value to add
	 *
	 * @return True if the value has been added
	 */
	public boolean add(Credential value) {
		if(keys.add(value)) {
			modified = true;
			return true;
		} else return false;
	}
	
	/**
	 * Removes a credential from the keyring.
	 *
	 * @param value The value to remove
	 *
	 * @return True if the value has been removed
	 */
	public boolean remove(Credential value) {
		if(keys.remove(value)) {
			modified = true;
			return true;
		} else return false;
	}
	
	/**
	 * Returns true if the instance has been modified.
	 *
	 * @return The modified state of this object
	 */
	boolean isModified() { return modified; }
	
	/**
	 * @see java.lang.Object#hashCode
	 */
	@Override
	public int hashCode() {
		return title.hashCode();
	}
	
	public Iterator<Credential> iterator() {
		return keys.iterator();
	}
	
	/**
	 * Returns a stream containing the keys of this keyring.
	 *
	 * @return A Stream object
	 */
	public Stream<Credential> stream() {
		return keys.stream();
	}

	public Spliterator<Credential> spliterator() {
		return keys.spliterator();
	}
}
