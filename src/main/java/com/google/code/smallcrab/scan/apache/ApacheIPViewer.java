/**
 * 
 */
package com.google.code.smallcrab.scan.apache;

/**
 * @author xalinx at gmail dot com
 * @date Dec 31, 2010
 */
public class ApacheIPViewer extends AbstractContainApacheViewer {

	/**
	 * 
	 */
	public ApacheIPViewer() {
		super();
	}

	/**
	 * @param contain
	 */
	public ApacheIPViewer(String contain) {
		super(contain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheViewer#getAllView(com.google.code.smallcrab.scan.apache.ApacheSpliter)
	 */
	@Override
	protected String getAllView(ApacheSpliter spliter) {
		return spliter.getIP();
	}

}
