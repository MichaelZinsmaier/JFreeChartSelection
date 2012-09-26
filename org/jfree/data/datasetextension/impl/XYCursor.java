package org.jfree.data.datasetextension.impl;

import org.jfree.data.datasetextension.DatasetCursor;

public class XYCursor implements DatasetCursor {

	public int series;
	public int item;

	public XYCursor() {
	}
	
	public XYCursor(int series, int item) {
		this.series = series;
		this.item = item;
	}

	public void setPosition(int series, int item) {
		this.series = series;
		this.item = item;
	}
}
