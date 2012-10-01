package org.jfree.data.datasetextension.optional;

import org.jfree.data.datasetextension.DatasetIterator;

public interface IterableSelection {

	public DatasetIterator getIterator();
	
	public DatasetIterator getSelectionIterator(boolean selected);
	
}
