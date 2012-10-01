package org.jfree.data.datasetextension.impl;

import org.jfree.data.datasetextension.DatasetCursor;

public class PieCursor implements DatasetCursor {

	public Comparable key;
	
	public PieCursor() {
		
	}
	
	public PieCursor(Comparable key) {
		this.key = key;
	}

	public void setPosition(Comparable key) {
		this.key = key;
	}

}
