package org.jfree.data.datasetextension.impl;

import org.jfree.data.datasetextension.DatasetCursor;

/**
 * A DatasetCursor implementation for pie datasets.
 * @author zinsmaie
 */
public class PieCursor implements DatasetCursor {

	/** a generated serial id	 */
	private static final long serialVersionUID = -7031433882367850307L;
	
	/** stores the key of the section */
	public Comparable key;
	
	/**
	 * creates a cursor without assigned position (the cursor will only
	 * be valid if setPosition is called at some time)
	 */
	public PieCursor() {
	}
	
	/**
	 * Default pie cursor constructor. Sets the cursor position to the specified value.
	 * @param key
	 */
	public PieCursor(Comparable key) {
		this.key = key;
	}

	/**
	 * sets the cursor position to the specified value
	 * @param key
	 */
	public void setPosition(Comparable key) {
		this.key = key;
	}

}
