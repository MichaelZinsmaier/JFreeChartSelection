package org.jfree.data.event;

import java.util.EventObject;

import org.jfree.data.datasetextension.DatasetLabelExtension;
import org.jfree.data.datasetextension.DatasetSelectionExtension;

public class LabelChangeEvent extends EventObject {

	public LabelChangeEvent(Object labelExtension) {
		super(labelExtension);
	}

    public DatasetSelectionExtension getLabelExtension() {
    	if (this.getSource() instanceof DatasetLabelExtension) {
    		return (DatasetSelectionExtension)this.getSource();
    	}
    	
    	//implementation error
    	return null;
    }

}
