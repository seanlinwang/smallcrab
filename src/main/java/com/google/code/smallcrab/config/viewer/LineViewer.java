/**
 * 
 */
package com.google.code.smallcrab.config.viewer;

import com.google.code.smallcrab.protocol.LinePackege;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 * @threadsafe
 */
public interface LineViewer<L extends LinePackege> {

	/**
	 * View segment from line using pac.
	 * @param pac
	 * 
	 * @return seg
	 */
	String view(L pac);
}
