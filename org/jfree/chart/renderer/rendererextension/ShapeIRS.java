package org.jfree.chart.renderer.rendererextension;

import java.awt.Shape;

import org.jfree.chart.renderer.AbstractRenderer;

/**
 * Defines an interface to control the shape of individual items during rendering.
 * Implementing classes can be used together with subclasses of {@link AbstractRenderer}
 * to control the rendering process.<br>
 * Works however only if the descendant of {@link AbstractRenderer} uses the per item method {@link AbstractRenderer#getItemShape(int, int)}
 * 
 * 
 * @author zinsmaie
 */
public interface ShapeIRS {
	
	/**
	 * Specifies an individual item by row, column and returns its shape
	 * 
     * @param row  the row (or series) index (zero-based).
     * @param column  the column (or category) index (zero-based).
	 * 
	 * @return a shape (never <code>null<code>)
	 */
	public Shape getItemShape(int row, int column);
	
}
