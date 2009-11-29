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

package org.xbmc.android.remote.business;

import org.xbmc.android.util.ConnectionManager;
import org.xbmc.api.business.DataResponse;
import org.xbmc.api.presentation.INotifiableController;
import org.xbmc.httpapi.client.MusicClient;
import org.xbmc.httpapi.client.VideoClient;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Super class for all the cache threads. Keeps common code
 * in one place.
 * 
 * @author Team XBMC
 */
abstract class AbstractThread extends Thread {
	
	protected Handler mHandler;
	
	/**
	 * Class constructor is protected, use get() in the child class.
	 * @param name
	 */
	protected AbstractThread(String name) {
		super(name);
	}
	
	/**
	 * Creates the handler and then loops.
	 */
	public void run() {
		Looper.prepare();
		mHandler = new Handler();
		Looper.loop();
	}
	
	/**
	 * Returns the MusicClient class
	 * @param context Application context
	 * @return
	 */
	protected MusicClient music(Context context) {
		return ConnectionManager.getHttpClient(context).music;
	}	
	
	/**
	 * Returns the VideoClient class
	 * @param context Application context
	 * @return
	 */
	protected VideoClient video(Context context) {
		return ConnectionManager.getHttpClient(context).video;
	}	
	
	/**
	 * Calls the UI thread's callback code.
	 * @param controller Controller reference
	 * @param response Response object
	 */
	protected void done(INotifiableController controller, DataResponse<?> response) {
		if (controller != null) {
			controller.runOnUI(response);
		}
	}
	
	/**
	 * Waits until the thread has completely started and we can be sure
	 * the response has been initialized.
	 * @param thread 
	 */
	protected static void waitForStartup(AbstractThread thread) {
		while (thread.mHandler == null) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}