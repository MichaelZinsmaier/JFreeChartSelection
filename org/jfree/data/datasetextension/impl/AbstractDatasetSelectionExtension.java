package org.jfree.data.datasetextension.impl;

import javax.swing.event.EventListenerList;

import org.jfree.data.datasetextension.DatasetSelectionExtension;
import org.jfree.data.event.SelectionChangeEvent;
import org.jfree.data.event.SelectionChangeListener;

public abstract class AbstractDatasetSelectionExtension implements DatasetSelectionExtension {

    /** Storage for registered listeners. */
    private transient EventListenerList listenerList;

    private boolean notify;
    private boolean dirty;
    
    public void addChangeListener(SelectionChangeListener listener) {
    	this.notify = true;
    	this.listenerList = new EventListenerList();
   		this.listenerList.add(SelectionChangeListener.class, listener);
    }

    public void removeChangeListener(SelectionChangeListener listener) {
   		this.listenerList.remove(SelectionChangeListener.class, listener);
    }

    /**
     * Sets a flag that controls whether or not listeners receive
     * {@link SelectionChangeEvent} notifications.
     *
     * @param notify sets the notify flag if set to true an event should be automatically triggered
     * 				 to resolve queued up changes
     *
     */
    public void setNotify(boolean notify) {
    	if (this.notify != notify) {
    		if (notify == false) {
    			//switch notification temporary off
    			this.dirty = false;
    		} else {
    			//switch notification on
    			if (this.dirty == true) {
    				notifyListeners(new SelectionChangeEvent(this));	
    			}
    		}    		
    		this.notify = notify;
    	}
   }
   
   
   protected void notifiyIfRequired() {
	   if (this.notify) {
		   notifyListeners(new SelectionChangeEvent(this));
	   } else {
		   this.dirty = true;
	   }
   }
    
    
   private void notifyListeners(SelectionChangeEvent event) {
        Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == SelectionChangeListener.class) {
                ((SelectionChangeListener) listeners[i + 1]).selectionChanged(event);
            }
        }
   }
}
