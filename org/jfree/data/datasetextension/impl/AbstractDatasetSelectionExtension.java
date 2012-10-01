package org.jfree.data.datasetextension.impl;

import javax.swing.event.EventListenerList;

import org.jfree.data.datasetextension.DatasetSelectionExtension;
import org.jfree.data.event.SelectionChangeEvent;
import org.jfree.data.event.SelectionChangeListener;

/**
 * Base class for separate selection extension implementations. Provides notification handling
 * and listener support. 
 *  
 * @author zinsmaie
 *
 */
public abstract class AbstractDatasetSelectionExtension implements DatasetSelectionExtension {

    /** a generated serial id */
	private static final long serialVersionUID = 4206903652292146757L;

	/** Storage for registered listeners. */
    private transient EventListenerList listenerList = new EventListenerList();

    /** notify flag {@link #isNotify()} */
    private boolean notify;
    
    /** dirty flag true if changes occurred is used to trigger a queued change event 
     *  if notify is reset to true.
     */
    private boolean dirty;
    
    /** 
     * {@link DatasetSelectionExtension#addChangeListener(org.jfree.data.event.LabelChangeListener)
     */
    public void addChangeListener(SelectionChangeListener listener) {
    	this.notify = true;
   		this.listenerList.add(SelectionChangeListener.class, listener);
    }

    /**
	 * {@link DatasetSelectionExtension#removeChangeListener(org.jfree.data.event.LabelChangeListener)
     */
    public void removeChangeListener(SelectionChangeListener listener) {
   		this.listenerList.remove(SelectionChangeListener.class, listener);
    }


    /**
     * {@link DatasetSelectionExtension#setNotify(boolean)}
     */
    public void setNotify(boolean notify) {
    	if (this.notify != notify) {
    		if (notify == false) {
    			//switch notification temporary off
    			this.dirty = false;
    		} else {
    			//switch notification on
    			if (this.dirty == true) {
    				notifyListeners();	
    			}
    		}    		
    		this.notify = notify;
    	}
   }
   
    /**
     * {@link DatasetSelectionExtension#isNotify()}
     */
   public boolean isNotify() {
	   return this.notify;
   }
   
   /**
    * can be called by subclasses to trigger notify events depending on the
    * notify flag.
    */
   protected void notifiyIfRequired() {
	   if (this.notify) {
		   notifyListeners();
	   } else {
		   this.dirty = true;
	   }
   }
    
   /**
    * notifies all registered listeners 
    * @param event
    */
   private void notifyListeners() {
        Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == SelectionChangeListener.class) {
                ((SelectionChangeListener) listeners[i + 1]).selectionChanged(new SelectionChangeEvent(this));
            }
        }
   }
}
