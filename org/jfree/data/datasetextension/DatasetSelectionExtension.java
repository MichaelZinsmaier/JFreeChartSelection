package org.jfree.data.datasetextension;

import org.jfree.data.event.SelectionChangeEvent;
import org.jfree.data.event.SelectionChangeListener;

/**
 * Extends a dataset such that each data item has an additional selection state (either true or false)
 *  
 * @author zinsmaie
 *
 */
public interface DatasetSelectionExtension extends DatasetExtension {
	
	/**
	 * @param cursor specifies the position of the data item
	 * @return true if the data item is selected
	 */
	public boolean isSelected(DatasetCursor cursor);
	
	/**
	 * sets the selection state of a data item
	 * @param cursor specifies the position of the data item
	 */
	public void setSelected(DatasetCursor cursor, boolean selected);
	
	/**
	 * sets all data items to unselected
	 */
	public void clearSelection();

	//Listener

	/**
	 * adds a label selection listener to the dataset extension<br>
	 * <br>
	 * the listener is triggered if a data item changes its selection state except 
	 * the notify flag is set to false (@link #setNotify(boolean)).
	 * In the latter case a change event should be triggered as soon as the notify flag is set to true again.
	 *  
	 * @param listener
	 */
	public void addChangeListener(SelectionChangeListener listener);
	
	/**
	 * removes a label change listener from the dataset extension<br>
	 *  
	 * @param listener
	 */
	public void removeChangeListener(SelectionChangeListener listener);

    /**
     * Sets a flag that controls whether or not listeners receive
     * {@link SelectionChangeEvent} notifications.
     *
     * @param notify If the flag is set to false the listeners are no longer informed about changes.
     *  If the flag is set to true and some changes occurred an event should be triggered. 
     */
    public void setNotify(boolean notify);
    
    /**
     * @return true if the notification flag is active 
     */
    public boolean isNotify();
}
