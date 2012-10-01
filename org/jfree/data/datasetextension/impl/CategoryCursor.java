package org.jfree.data.datasetextension.impl;

import org.jfree.data.datasetextension.DatasetCursor;

public class CategoryCursor implements DatasetCursor {

	public Comparable rowKey;
	public Comparable columnKey;

	public CategoryCursor() {
	}
	
	public CategoryCursor(Comparable rowKey, Comparable columnKey) {
		this.rowKey = rowKey;
		this.columnKey = columnKey;
	}

	public void setPosition(Comparable rowKey, Comparable columnKey) {
		this.rowKey = rowKey;
		this.columnKey = columnKey;
	}
	
}
