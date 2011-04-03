/**
 * 
 */
package com.google.code.smallcrab.scan;

/**
 * @author xalinx at gmail dot com
 * @date Dec 29, 2010
 */
public interface LineMatcher<L extends LineSpliter> {

	/**
	 * @param spliter
	 * @param segs
	 * @return
	 */
	boolean match(L spliter);

}
