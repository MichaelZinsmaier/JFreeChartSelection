package org.jfree.data.datasetextension.impl;

import org.jfree.data.DefaultKeyedValues2D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.datasetextension.DatasetCursor;
import org.jfree.data.datasetextension.DatasetSelectionExtension;
import org.jfree.data.general.Dataset;

//TODO incomplete implementation missing handler for dataset changes ...

public class CategoryDatasetSelectionExtension implements DatasetSelectionExtension {

	private final CategoryDataset dataset;
	
	//could improve here by using own bool data structure
	private DefaultKeyedValues2D selectionData;
	private final Number TRUE = new Byte((byte) 1);
	private final Number FALSE = new Byte((byte) 0);
	
	public CategoryDatasetSelectionExtension(CategoryDataset dataset) {
		this.dataset = dataset;
		initSelection();
	}
	
	public Dataset getDataset() {
		return this.dataset;
	}

	public boolean isSelected(DatasetCursor cursor) {
		if (cursor instanceof CategoryCursor) {
			//anything else is an implementation error
			CategoryCursor c = (CategoryCursor) cursor;
			if (TRUE == this.selectionData.getValue(c.getRowKey(), c.getColumnKey())) {
				return true;
			} else {
				return false;
			}
		} 
		
		//implementation error
		return false;
	}

	public void setSelected(DatasetCursor cursor, boolean selected) {
		if (cursor instanceof CategoryCursor) {
			//anything else is an implementation error
			CategoryCursor c = (CategoryCursor) cursor;
			if (selected) {
				selectionData.setValue(TRUE, c.getRowKey(), c.getColumnKey());
			} else {
				selectionData.setValue(FALSE, c.getRowKey(), c.getColumnKey());
			}
		} 
	}

	public void clearSelection() {
		initSelection();		
	}
	
	private void initSelection() {
		selectionData = new DefaultKeyedValues2D();
		for (int i = 0; i < dataset.getRowCount(); i++) {
			for (int j= 0; j < dataset.getColumnCount(); j++) {
				if (dataset.getValue(i, j) != null) {
					selectionData.addValue(FALSE, dataset.getRowKey(i), dataset.getColumnKey(j));
				}
			}
		}
	}

}
