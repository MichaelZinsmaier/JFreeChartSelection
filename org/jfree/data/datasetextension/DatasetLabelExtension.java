package org.jfree.data.datasetextension;

import org.jfree.data.event.LabelChangeListener;
import org.jfree.data.event.SelectionChangeEvent;

public interface DatasetLabelExtension extends DatasetExtension {
	
	public final int NO_LABEL = -1;
	
	public int getLabel(DatasetCursor cursor);
	
	public void setLabel(DatasetCursor cursor, int label);
	

	//Listener
	
	public void addChangeListener(LabelChangeListener listener);
	
	public void removeChangeListener(LabelChangeListener listener);

    /**
     * Sets a flag that controls whether or not listeners receive
     * {@link SelectionChangeEvent} notifications.
     *
     * @param notify if the flag is set to true and some changes occurred an event should be triggered. 
     */
    public void setNotify(boolean notify);
}
