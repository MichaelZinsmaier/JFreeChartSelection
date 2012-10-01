package org.jfree.data.datasetextension.impl;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.datasetextension.DatasetCursor;
import org.jfree.data.datasetextension.DatasetSelectionExtension;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.XYDataset;

//TODO incomplete implementation missing handler for dataset changes ...

public class XYDatasetSelectionExtension implements DatasetSelectionExtension {

	private final XYDataset dataset;
	private List[] selectionData;
	
	public XYDatasetSelectionExtension(XYDataset dataset) {
		this.dataset = dataset;		
		selectionData = new ArrayList[dataset.getSeriesCount()];
		
		initSelection();
	}
	
	public Dataset getDataset() {
		return this.dataset;
	}

	public boolean isSelected(DatasetCursor cursor) {
		if (cursor instanceof XYCursor) {
			//anything else is an implementation error
			XYCursor c = (XYCursor) cursor;
			if (((Boolean)selectionData[c.getSeries()].get(c.getItem())).booleanValue()) {
				return true;
			} else {
				return false;
			}
		} 
		
		//implementation error
		return false;
	}

	public void setSelected(DatasetCursor cursor, boolean selected) {
		if (cursor instanceof XYCursor) {
			//anything else is an implementation error
			XYCursor c = (XYCursor) cursor;
			selectionData[c.getSeries()].set(c.getItem(), new Boolean(selected));
		} 
	}

	public void clearSelection() {
		initSelection();		
	}
	
	private void initSelection() {
		for (int i = 0; i < dataset.getSeriesCount(); i++) {
			selectionData[i] = new ArrayList(dataset.getItemCount(i));
			for (int j = 0; j < dataset.getItemCount(i); j++) {
				selectionData[i].add(new Boolean(false));
			}
		}
	}


}
