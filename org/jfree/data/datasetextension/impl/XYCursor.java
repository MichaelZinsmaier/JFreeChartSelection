package org.jfree.data.datasetextension.impl;

import org.jfree.data.datasetextension.DatasetCursor;

public class XYCursor implements DatasetCursor {

	private int series;
	private int item;
	
	public void setPosition(int series, int item) {
		this.series = series;
		this.item = item;
	}
	
	public int getSeries() {
		return this.series;
	}
	
	public int getItem() {
		return this.item;
	}
}
