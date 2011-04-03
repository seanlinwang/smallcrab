/**
 * 
 */
package com.google.code.smallcrab.scan.apache;

import com.google.code.smallcrab.utils.StringKit;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public abstract class AbstractContainApacheViewer extends AbstractApacheViewer {

	protected String contain;
	
	protected boolean contained;

	public String getContain() {
		return contain;
	}

	public void setContain(String contain) {
		this.contain = contain;
		if (StringKit.isNotEmpty(contain)) {
			this.contained = true;
		}
	}

	/**
	 * @param contain
	 */
	public AbstractContainApacheViewer(String contain) {
		super();
		this.setContain(contain);
	}

	/**
	 * 
	 */
	public AbstractContainApacheViewer() {
		super();
	}

	@Override
	public String view(ApacheSpliter spliter) {
		String allView = this.getAllView(spliter);
		String view = allView;
		if (contained && StringKit.isNotEmpty(allView)) { // need check allview contains
			if (!allView.contains(contain)) {
				view = null; // allview not contains, set view null
			}
		}
		return view;
	}

	protected abstract String getAllView(ApacheSpliter spliter);

}
