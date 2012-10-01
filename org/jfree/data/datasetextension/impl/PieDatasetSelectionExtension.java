package org.jfree.data.datasetextension.impl;

import java.util.HashMap;
import java.util.Iterator;

import org.jfree.data.datasetextension.DatasetCursor;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.general.SelectionChangeListener;


//TODO incomplete implementation missing handler for dataset changes ...

public class PieDatasetSelectionExtension extends AbstractDatasetSelectionExtension {

	private final PieDataset dataset;
	private HashMap selectionData;
	
	public PieDatasetSelectionExtension(PieDataset dataset) {
		this.dataset = dataset;
		initSelection();
	}
	
	public PieDatasetSelectionExtension(PieDataset dataset, SelectionChangeListener initialListener) {
		this(dataset);
		addChangeListener(initialListener);
	}
	
	public Dataset getDataset() {
		return this.dataset;
	}

	public boolean isSelected(DatasetCursor cursor) {
		if (cursor instanceof PieCursor) {
			//anything else is an implementation error
			PieCursor c = (PieCursor) cursor;		
			
			if (Boolean.TRUE.equals(this.selectionData.get(c.getKey()))) {
				return true;
			} else {
				return false;
			}
		} 
		
		//implementation error
		return false;
	}

	public void setSelected(DatasetCursor cursor, boolean selected) {
		if (cursor instanceof PieCursor) {
			//anything else is an implementation error
			PieCursor c = (PieCursor) cursor;
			if (selected) {
				this.selectionData.put(c.getKey(), new Boolean(selected));
			}
			notifiyIfRequired();
		} 
	}

	public void clearSelection() {
		initSelection();
	}
	
	private void initSelection() {
		this.selectionData = new HashMap();
		Iterator iter = this.dataset.getKeys().iterator();
		
		while (iter.hasNext()) {
			//all keys are comparable
			Comparable key = (Comparable) iter.next();
			this.selectionData.put(key, new Boolean(false));
		}
		notifiyIfRequired();
	}
}
