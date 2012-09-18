package org.jfree.chart.renderer.contribution;

import java.awt.Stroke;

public interface StrokeIRS {

	public Stroke getItemStroke(int row, int column);
	
	 public Stroke getItemOutlineStroke(int row, int column);
	
}
