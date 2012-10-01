package org.jfree.chart.renderer.rendererextension;

import java.awt.Stroke;

import org.jfree.chart.renderer.AbstractRenderer;


/**
 * Defines an interface to control the stroke for individual items during rendering.
 * Implementing classes can be used together with subclasses of {@link AbstractRenderer}
 * to control the rendering process.<br>
 * Works however only if the descendant of {@link AbstractRenderer} uses per item methods like {@link AbstractRenderer#getItemStroke(int, int)}
 * 
 * 
 * @author zinsmaie
 */
public interface StrokeIRS {

	/**
	 * Specifies an individual item by row, column and returns the item stroke
	 * 
     * @param row  the row (or series) index (zero-based).
     * @param column  the column (or category) index (zero-based).
	 * 
	 * @return a stroke (never <code>null<code>)
	 */
	public Stroke getItemStroke(int row, int column);
	
	/**
	 * Specifies an individual item by row, column and returns the outline stroke
	 * 
     * @param row  the row (or series) index (zero-based).
     * @param column  the column (or category) index (zero-based).
	 * 
	 * @return a stroke (never <code>null<code>)
	 */
	 public Stroke getItemOutlineStroke(int row, int column);
	
}
