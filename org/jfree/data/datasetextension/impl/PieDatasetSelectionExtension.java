package org.jfree.data.datasetextension.impl;

import java.util.HashMap;
import java.util.Iterator;

import org.jfree.data.datasetextension.DatasetCursor;
import org.jfree.data.datasetextension.DatasetIterator;
import org.jfree.data.datasetextension.optional.IterableSelection;
import org.jfree.data.event.SelectionChangeListener;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.PieDataset;


//TODO incomplete implementation missing handler for dataset changes ...

public class PieDatasetSelectionExtension extends AbstractDatasetSelectionExtension implements IterableSelection {

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
			
			if (Boolean.TRUE.equals(this.selectionData.get(c.key))) {
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
				this.selectionData.put(c.key, new Boolean(selected));
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
	
	
	//ITERATOR IMPLEMENTATION
	
	public DatasetIterator getIterator() {
		return new PieDatasetSelectionIterator();
	}

	public DatasetIterator getSelectionIterator(boolean selected) {
		return new PieDatasetSelectionIterator(selected);
	}

	private class PieDatasetSelectionIterator implements DatasetIterator {

		private int section = -1;
		private Boolean filter = null;
		
		public PieDatasetSelectionIterator() {
		}
		
		public PieDatasetSelectionIterator(boolean selected) {
			filter = new Boolean(selected);
		}

		
		public boolean hasNext() {
			if (nextPosition(section) != -1) {
				return true;
			} 			
			return false;
		}

		public Object next() {
			this.section = nextPosition(section);
			Comparable key = dataset.getKey(this.section);
			return new PieCursor(key);
		}

		public DatasetCursor nextCursor() {
			return (DatasetCursor)next();
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		private int nextPosition(int section) {
			while ((section+1) < dataset.getItemCount()) {
				if (filter != null) {
					Comparable key = dataset.getKey((section+1));
					if (!(filter.equals(selectionData.get(key)))) {
						section++;
						continue;
					}				
				}

				//success
				return (section+1);
			}

			return -1;
		}
	}
}
