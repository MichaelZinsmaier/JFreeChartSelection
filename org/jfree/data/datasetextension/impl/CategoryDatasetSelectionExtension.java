package org.jfree.data.datasetextension.impl;

import org.jfree.data.DefaultKeyedValues2D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.datasetextension.DatasetCursor;
import org.jfree.data.datasetextension.DatasetIterator;
import org.jfree.data.datasetextension.optional.IterableSelection;
import org.jfree.data.event.SelectionChangeListener;
import org.jfree.data.general.Dataset;

//TODO incomplete implementation missing handler for dataset changes ...

public class CategoryDatasetSelectionExtension extends AbstractDatasetSelectionExtension implements IterableSelection {

	private final CategoryDataset dataset;
	
	//could improve here by using own bool data structure
	private DefaultKeyedValues2D selectionData;
	private final Number TRUE = new Byte((byte) 1);
	private final Number FALSE = new Byte((byte) 0);
	
	public CategoryDatasetSelectionExtension(CategoryDataset dataset) {
		this.dataset = dataset;
		initSelection();
	}
	
	public CategoryDatasetSelectionExtension(CategoryDataset dataset, SelectionChangeListener initialListener) {
		this(dataset);
		addChangeListener(initialListener);
	}
	
	public Dataset getDataset() {
		return this.dataset;
	}

	public boolean isSelected(DatasetCursor cursor) {
		if (cursor instanceof CategoryCursor) {
			//anything else is an implementation error
			CategoryCursor c = (CategoryCursor) cursor;
			if (TRUE == this.selectionData.getValue(c.rowKey, c.columnKey)) {
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
				selectionData.setValue(TRUE, c.rowKey, c.columnKey);
			} else {
				selectionData.setValue(FALSE, c.rowKey, c.columnKey);
			}
			notifiyIfRequired();
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
		notifiyIfRequired();
	}

	
	//ITERATOR
	
	public DatasetIterator getIterator() {
		return new CategoryDatasetSelectionIterator();
	}

	public DatasetIterator getSelectionIterator(boolean selected) {
		return new CategoryDatasetSelectionIterator(selected);
	}

	private class CategoryDatasetSelectionIterator implements DatasetIterator {

		private int row = 0;
		private int column = -1;
		private Boolean filter = null;
		
		public CategoryDatasetSelectionIterator() {
		}
		
		public CategoryDatasetSelectionIterator(boolean selected) {
			filter = new Boolean(selected);
		}

		
		public boolean hasNext() {
			if (nextPosition(row, column)[0] != -1) {
				return true;
			} 			
			return false;
		}

		public Object next() {
			int[] newPos = nextPosition(row, column);
			row = newPos[0];
			column = newPos[1];
			return new CategoryCursor(dataset.getRowKey(row), dataset.getColumnKey(column));
		}

		public DatasetCursor nextCursor() {
			return (DatasetCursor)next();
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		private int[] nextPosition(int pRow, int pColumn) {
			while (pRow < dataset.getRowCount()) {
				if ((pColumn+1) >= selectionData.getColumnCount()) {
					pRow++;
					pColumn = -1;
					continue; 
				}
				if (filter != null) {
					if (!(  (filter.equals(Boolean.TRUE) && TRUE.equals(selectionData.getValue(pRow, (pColumn+1)))) || 
						  	(filter.equals(Boolean.FALSE) && FALSE.equals(selectionData.getValue(pRow, (pColumn+1))))
					)) {
						pColumn++;
						continue;
					}
				}
				
				//success
				return new int[]{pRow, (pColumn+1)};
			}
			
			return new int[]{-1,-1};
		}
	}
	
}
