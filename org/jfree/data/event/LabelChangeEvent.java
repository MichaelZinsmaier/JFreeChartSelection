package org.jfree.data.event;

import java.util.EventObject;

import org.jfree.data.datasetextension.DatasetLabelExtension;
import org.jfree.data.datasetextension.DatasetSelectionExtension;

/**
 * A change event that notifies about a change to the labeling of data items
 *
 * @author zinsmaie
 *
 */
public class LabelChangeEvent extends EventObject {

	/** a generated serial id */
	private static final long serialVersionUID = 2289987249349231381L;

    /**
     * Constructs a new event. 
     *
     * @param source the source of the event aka the label extension that changed (the object has to be
     * of type DatasetLabelExtension and must not be null!)
     */
	public LabelChangeEvent(Object labelExtension) {
		super(labelExtension);
	}

	/**
	 * @return the label selection extension that triggered the event.
	 */
    public DatasetSelectionExtension getLabelExtension() {
    	if (this.getSource() instanceof DatasetLabelExtension) {
    		return (DatasetSelectionExtension)this.getSource();
    	}
    	
    	//implementation error
    	return null;
    }

}
