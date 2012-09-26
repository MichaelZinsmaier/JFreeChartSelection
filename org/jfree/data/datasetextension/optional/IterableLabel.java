package org.jfree.data.datasetextension.optional;

import org.jfree.data.datasetextension.DatasetIterator;

public interface IterableLabel {

	public DatasetIterator getIterator();
	
	public DatasetIterator getLabelIterator(int label);
	
}
