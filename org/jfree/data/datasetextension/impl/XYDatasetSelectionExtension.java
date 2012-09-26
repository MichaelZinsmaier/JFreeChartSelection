package org.jfree.data.datasetextension.impl;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.datasetextension.DatasetCursor;
import org.jfree.data.datasetextension.DatasetIterator;
import org.jfree.data.datasetextension.optional.IterableSelection;
import org.jfree.data.event.SelectionChangeListener;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.XYDataset;

//TODO incomplete implementation missing handler for dataset changes ...

public class XYDatasetSelectionExtension extends
		AbstractDatasetSelectionExtension implements IterableSelection {

	private final XYDataset dataset;
	private List[] selectionData;

	public XYDatasetSelectionExtension(XYDataset dataset) {
		this.dataset = dataset;
		selectionData = new ArrayList[dataset.getSeriesCount()];

		initSelection();
	}

	public XYDatasetSelectionExtension(XYDataset dataset,
			SelectionChangeListener initialListener) {
		this(dataset);
		addChangeListener(initialListener);
	}

	public Dataset getDataset() {
		return this.dataset;
	}

	public boolean isSelected(DatasetCursor cursor) {
		if (cursor instanceof XYCursor) {
			// anything else is an implementation error
			XYCursor c = (XYCursor) cursor;
			if (((Boolean) selectionData[c.series].get(c.item)).booleanValue()) {
				return true;
			} else {
				return false;
			}
		}

		// implementation error
		return false;
	}

	public void setSelected(DatasetCursor cursor, boolean selected) {
		if (cursor instanceof XYCursor) {
			// anything else is an implementation error
			XYCursor c = (XYCursor) cursor;
			selectionData[c.series].set(c.item, new Boolean(selected));
			notifiyIfRequired();
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
		notifiyIfRequired();
	}

	// ITERATOR IMPLEMENTATION

	public DatasetIterator getIterator() {
		return new XYDatasetSelectionIterator();
	}

	public DatasetIterator getSelectionIterator(boolean selected) {
		return new XYDatasetSelectionIterator(selected);
	}

	private class XYDatasetSelectionIterator implements DatasetIterator {
		private int series = 0;
		private int item = -1;
		private Boolean filter = null;

		public XYDatasetSelectionIterator() {
		}

		public XYDatasetSelectionIterator(boolean selected) {
			this.filter = new Boolean(selected);
		}

		public boolean hasNext() {
			if (nextPosition(this.series, this.item)[0] != -1) {
				return true;
			}
			return false;
		}

		public Object next() {
			int[] newPos = nextPosition(this.series, this.item);
			this.series = newPos[0];
			this.item = newPos[1];
			return new XYCursor(this.series, this.item);
		}

		public DatasetCursor nextCursor() {
			return (DatasetCursor) next();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		private int[] nextPosition(int pSeries, int pItem) {
			while (pSeries < selectionData.length) {
				if ((pItem+1) >= selectionData[pSeries].size()) {
					pSeries++;
					pItem = -1;
					continue;
				}				
				if (filter != null) {
					if (!(filter.equals(selectionData[pSeries].get(pItem + 1)))) {
						pItem++;
						continue;
					}
				}
				
				//success
				return new int[] {pSeries, (pItem+1)};
			}
			
			return new int[] {-1,-1};
		}
	}

}
