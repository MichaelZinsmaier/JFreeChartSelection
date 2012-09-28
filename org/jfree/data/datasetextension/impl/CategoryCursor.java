package org.jfree.data.datasetextension.impl;

import org.jfree.data.datasetextension.DatasetCursor;

/**
 * A DatasetCursor implementation for category datasets.
 * @author zinsmaie
 */
public class CategoryCursor implements DatasetCursor {

	/** a generated serial id. */
	private static final long serialVersionUID = 7086987028899208483L;
	
	/** stores the key of the row position */
	public Comparable rowKey;
	
	/** stores the key of the column position */
	public Comparable columnKey;

	/**
	 * creates a cursor without assigned position (the cursor will only
	 * be valid if setPosition is called at some time)
	 */
	public CategoryCursor() {
	}
	
	/**
	 * Default category cursor constructor. Sets the cursor position to the specified values.
	 * @param rowKey
	 * @param columnKey
	 */
	public CategoryCursor(Comparable rowKey, Comparable columnKey) {
		this.rowKey = rowKey;
		this.columnKey = columnKey;
	}

	/**
	 * sets the cursor position to the specified values
	 * @param rowKey
	 * @param columnKey
	 */
	public void setPosition(Comparable rowKey, Comparable columnKey) {
		this.rowKey = rowKey;
		this.columnKey = columnKey;
	}
	
}
