/*
 *      Copyright (C) 2005-2009 Team XBMC
 *      http://xbmc.org
 *
 *  This Program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2, or (at your option)
 *  any later version.
 *
 *  This Program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with XBMC Remote; see the file license.  If not, write to
 *  the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 *  http://www.gnu.org/copyleft/gpl.html
 *
 */

package org.xbmc.httpapi.data;
import java.util.Formatter;

/**
 * The song class contains everything to know about a song. It's basically a
 * data container with some smallish formatting methods 
 * @author freezy <f3k@hosts.ch>
 */
public class Song implements Comparable<Song> {
	
	/**
	 * Constructor
	 * @param title		Song title
	 * @param artist	Song artist
	 * @param track		XBMC track number format (first 2 bytes = disc, last 2 bytes = track)
	 * @param duration	Duration in seconds
	 * @param path		Path to song (without filename)
	 * @param filename	Filename
	 */
	public Song(String title, String artist, int track, int duration, String path, String filename) {
		this.title = title;
		this.artist = artist;
		this.track = track & 0xffff;
		this.disc = track >> 16;
		this.duration = duration;
		this.path = path + filename;
		this.filename = filename;
	}
	
	/**
	 * Returns the duration in a nice format ([h:]mm:ss)
	 * @return Formatted duration
	 */
	public String getDuration() {
		StringBuilder sb = new StringBuilder();
		int d = duration;
		if (d > 3600) {
			sb.append(Math.floor(d / 3600));
			sb.append(":");
			d %= 3600;
		}
		int min = (int)Math.floor(d / 60);
		Formatter f = new Formatter();
		if (sb.length() > 0) {
			sb.append(f.format("%02d:", min));
		} else {
			sb.append(min + ":");
		}
		d %= 60;
		sb.append(f.format("%02d", d));
		return sb.toString();
	}

	/**
	 * Something readable
	 */
	public String toString() {
		return "[" + track + "/" + disc + "] " + artist + " - " + title;
	}
	
	/**
	 * Sort by filename, since track/disc numbers aren't always accurate
	 */
	public int compareTo(Song t) {
		return filename.compareTo(t.filename); // filename comparison is more accurate
		// return t.disc == disc ? (t.number > number ? -1 : 1) : (t.disc > disc ? -1 : 1)
	}
	
	/**
	 * Song title
	 */
	public String title;
	/**
	 * Song artist
	 */
	public String artist;
	/**
	 * Track number
	 */
	public int track;
	/**
	 * Disc number
	 */
	public int disc;
	/**
	 * Song duration in seconds
	 */
	public int duration;
	/**
	 * Absolute path from XBMC (incl filename)
	 */
	public String path;
	/**
	 * Filename of song
	 */
	public String filename;	
}