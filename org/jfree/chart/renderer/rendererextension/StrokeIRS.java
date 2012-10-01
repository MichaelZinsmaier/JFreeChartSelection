package org.jfree.chart.renderer.rendererextension;

import java.awt.Stroke;

public interface StrokeIRS {

	public Stroke getItemStroke(int row, int column);
	
	 public Stroke getItemOutlineStroke(int row, int column);
	
}
