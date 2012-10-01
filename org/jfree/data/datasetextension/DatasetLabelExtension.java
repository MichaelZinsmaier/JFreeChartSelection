package org.jfree.data.datasetextension;

import org.jfree.data.event.LabelChangeEvent;
import org.jfree.data.event.LabelChangeListener;

/**
 * Extends a dataset such that each data item has an additional label attribute
 * that assigns a class to this item. Each item is part of exactly one label class.
 * 
 * @author zinsmaie
 *
 */
public interface DatasetLabelExtension extends DatasetExtension {
	
	/** default class for not labeled data items. */
	public final int NO_LABEL = -1;
	
	/**
	 * @param cursor specifies the position of the data item
	 * @return the label attribute of the data item
	 */
	public int getLabel(DatasetCursor cursor);
	
	/**
	 * @param cursor specifies the position of the data item
	 * @param label the new label value for the data item
	 */
	public void setLabel(DatasetCursor cursor, int label);
	

	//Listener

	/**
	 * adds a label change listener to the dataset extension<br>
	 * <br>
	 * the listener is triggered if a data item label changes except 
	 * the notify flag is set to false (@link #setNotify(boolean)).
	 * In the latter case a change event should be triggered as soon as the notify flag is set to true again.
	 *  
	 * @param listener
	 */
	public void addChangeListener(LabelChangeListener listener);
	
	/**
	 * removes a label change listener from the dataset extension<br>
	 *  
	 * @param listener
	 */
	public void removeChangeListener(LabelChangeListener listener);

    /**
     * Sets a flag that controls whether or not listeners receive
     * {@link LabelChangeEvent} notifications.
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
