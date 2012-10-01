package org.jfree.chart;

import org.jfree.data.selection.SelectionManager;

/**
 * A <code>RenderingSource</code> is an object that calls the 
 * <code>draw(...)</code> method in the {@link JFreeChart} class.  An example
 * is the {@link ChartPanel} class.
 */
public interface RenderingSource {

    /**
     * Returns the selection manager, if any, that this source is maintaining
     *
     * @return The selection manager (possibly <code>null</code>).
     */
    public SelectionManager getSelectionManager();

    public void setSelectionManager(SelectionManager manager);

}
