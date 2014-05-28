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

import java.util.Arrays;
import javax.security.auth.DestroyFailedException;

/**
 * This class contains text password information.
 */	
public class TextPassword implements Password {
	/**
	 * The password data.
	 */
	private final char[] data;
	
	/**
	 * Initializes a new instance of this class.
	 *
	 * @param pass The password
	 */
	public TextPassword(char[] pass) {
		if(pass == null)
			pass = new char[0];
		
		data = pass;
	}
	
	/**
	 * Returns the password data.
	 * <blockquote>NOTE: This method returns A COPY of the original data which must be manually destroyed.</blockquote>
	 *
	 * @return A copy of the password data
	 */
	public char[] get() { return Arrays.copyOf(data, data.length); }
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof TextPassword) {
			TextPassword p = (TextPassword)o;
			
			return Arrays.equals(data, p.data);
		} else return false;
	}
	
	public void destroy() throws DestroyFailedException {
		Arrays.fill(data, '\0');
	}
	
	public boolean isDestroyed() {
		for(int i = 0; i < data.length; i++)
			if(data[i] != 0)
				return false;
		
		return true;
	}
}
