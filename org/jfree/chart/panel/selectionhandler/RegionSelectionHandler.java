/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2009, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * ---------------------
 * SelectionHandler.java
 * ---------------------
 * (C) Copyright 2009, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 19-Jun-2009 : Version 1 (DG);
 *
 */

package org.jfree.chart.panel.selectionhandler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

import org.jfree.chart.panel.AbstractMouseHandler;

/**
 * A mouse handler that allows data items to be selected.
 *
 * @since 1.0.14
 */
public abstract class RegionSelectionHandler extends AbstractMouseHandler {


    /**
     * The outline stroke.
     */
    protected Stroke outlineStroke;

    /**
     * The outline paint.
     */
    protected Paint outlinePaint;

    /**
     * The fill paint.
     */
    protected Paint fillPaint;

    /**
     * Creates a new default instance.
     */
    public RegionSelectionHandler() {
        this(new BasicStroke(1.0f), Color.darkGray, new Color(255, 0, 255, 50));
    }

    /**
     * Creates a new selection handler with the specified attributes.
     *
     * @param outlineStroke  the outline stroke.
     * @param outlinePaint  the outline paint.
     * @param fillPaint  the fill paint.
     */
    public RegionSelectionHandler(Stroke outlineStroke, Paint outlinePaint,
            Paint fillPaint) {
        super();
        this.outlineStroke = new BasicStroke(1.0f);
        this.outlinePaint = Color.darkGray;
        this.fillPaint = new Color(255, 0, 255, 50);
    }

    /**
     * Returns the fill paint.
     *
     * @return The fill paint (possibly <code>null</code>).
     *
     * @see #setFillPaint(java.awt.Paint)
     */
    public Paint getFillPaint() {
        return fillPaint;
    }

    /**
     * Sets the fill paint.
     *
     * @param fillPaint  the fill paint (<code>null</code> permitted).
     *
     * @see #getFillPaint()
     */
    public void setFillPaint(Paint fillPaint) {
        this.fillPaint = fillPaint;
    }

    public Paint getOutlinePaint() {
        return outlinePaint;
    }

    public void setOutlinePaint(Paint outlinePaint) {
        this.outlinePaint = outlinePaint;
    }

    public Stroke getOutlineStroke() {
        return outlineStroke;
    }

    public void setOutlineStroke(Stroke outlineStroke) {
        this.outlineStroke = outlineStroke;
    }

    /**
     * Handles a mouse pressed event.
     * 
     * @param e  the event.
     */
    public abstract void mousePressed(MouseEvent e);

    /**
     * Handles a mouse dragged event.
     *
     * @param e  the event.
     */
    public abstract void mouseDragged(MouseEvent e);
    
    public abstract void mouseReleased(MouseEvent e);
}