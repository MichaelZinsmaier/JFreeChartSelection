package org.jfree.chart.renderer.contribution;

import java.awt.Paint;

public interface PaintIRS {

	public Paint getItemPaint(int row, int column);
	
	public Paint getItemFillPaint(int row, int column);
	
	public Paint getItemOutlinePaint(int row, int column);
}
