package org.jfree.data.datasetextension.impl;

import org.jfree.data.datasetextension.DatasetCursor;

public class CategoryCursor implements DatasetCursor {

	private Comparable rowKey;
	private Comparable columnKey;
	
	public void setPosition(Comparable rowKey, Comparable columnKey) {
		this.rowKey = rowKey;
		this.columnKey = columnKey;
	}
	
	public Comparable getRowKey() {
		return this.rowKey;
	}
	
	public Comparable getColumnKey() {
		return this.columnKey;
	}
	
}
