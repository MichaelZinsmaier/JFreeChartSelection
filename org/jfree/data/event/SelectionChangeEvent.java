package org.jfree.data.event;

import java.util.EventObject;

import org.jfree.data.datasetextension.DatasetSelectionExtension;

public class SelectionChangeEvent extends EventObject {

	public SelectionChangeEvent(Object selectionExtension) {
		super(selectionExtension);
	}

    public DatasetSelectionExtension getSelectionExtension() {
        return (DatasetSelectionExtension)this.getSource();
    }

}
