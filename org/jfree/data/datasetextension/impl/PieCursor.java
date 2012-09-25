package org.jfree.data.datasetextension.impl;

import org.jfree.data.datasetextension.DatasetCursor;

public class PieCursor implements DatasetCursor {

	private Comparable key;
	
	public void setPosition(Comparable key) {
		this.key = key;
	}
	
	public Comparable getKey() {
		return this.key;
	}
		
}
