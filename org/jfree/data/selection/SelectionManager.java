package org.jfree.data.selection;

import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

public interface SelectionManager {

	public void select(double x, double y);
	
	public void select(Rectangle2D selection);
	
	public void select(GeneralPath selection);

	public void clearSelection();
	
}
