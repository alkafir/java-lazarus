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

import javax.security.auth.Destroyable;

/**
 * This interface provides support for credentials exporters.
 * <p>A credential exporter is a class whose goal is to export the whole
 * credential database in a particular format.</p>
 */
public interface Exporter<T> extends Destroyable {
	/**
	 * Exports the credentials.
	 *
	 * @return The exported credentials
	 * @throws DatabaseExportException if an exception occurs during the process
	 */
	T exportDatabase() throws DatabaseExportException;
}
