package org.jfree.data.datasetextension;

import org.jfree.data.general.SelectionChangeEvent;
import org.jfree.data.general.SelectionChangeListener;

public interface DatasetSelectionExtension extends DatasetExtension {
	
	public boolean isSelected(DatasetCursor cursor);
	
	public void setSelected(DatasetCursor cursor, boolean selected);
	
	public void clearSelection();

	//Listener
	
	public void addChangeListener(SelectionChangeListener listener);
	
	public void removeChangeListener(SelectionChangeListener listener);

    /**
     * Sets a flag that controls whether or not listeners receive
     * {@link SelectionChangeEvent} notifications.
     *
     * @param notify if the flag is set to true and some changes occurred an event should be triggered. 
     */
    public void setNotify(boolean notify);
}
