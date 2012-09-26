package org.jfree.chart.panel.selectionhandler;

import java.awt.event.MouseEvent;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.panel.AbstractMouseHandler;

public class MouseClickSelectionHandler extends AbstractMouseHandler {
	    	
	    public void mouseClicked(MouseEvent e) {
	    	if (!(e.getSource() instanceof ChartPanel)) {
				return;
			}
	    	
	    	ChartPanel panel = (ChartPanel)e.getSource();
	    	SelectionManager selectionManager = panel.getSelectionManager();
	    	if (selectionManager != null) {
				if (!e.isShiftDown()) {
					   selectionManager.clearSelection();
					   panel.getChart().fireChartChanged();
				}
	    		selectionManager.select(e.getX(), e.getY());
	    		panel.getChart().fireChartChanged();
	    	}
	    }

	
}
