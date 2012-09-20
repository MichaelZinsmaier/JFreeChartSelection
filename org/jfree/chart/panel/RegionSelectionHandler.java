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

package org.jfree.chart.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.util.ShapeUtilities;
/**
 * A mouse handler that allows data items to be selected.
 *
 * @since 1.0.14
 */
public class RegionSelectionHandler extends AbstractMouseHandler {

    /**
     * The selection path (in Java2D space).
     */
    private GeneralPath selection;

    /**
     * The last mouse point.
     */
    private Point2D lastPoint;

    /**
     * The outline stroke.
     */
    private Stroke outlineStroke;

    /**
     * The outline paint.
     */
    private Paint outlinePaint;

    /**
     * The fill paint.
     */
    private Paint fillPaint;

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
        this.selection = new GeneralPath();
        this.lastPoint = null;
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
    public void mousePressed(MouseEvent e) {
        ChartPanel panel = (ChartPanel) e.getSource();
        Rectangle2D dataArea = panel.getScreenDataArea();
        if (dataArea.contains(e.getPoint())) {

            JFreeChart chart = panel.getChart();
//            if (panel instanceof Selectable) {
//                Selectable s = (Selectable) panel;
//                if (!e.isShiftDown()) {
//                    s.clearSelection();
//                    chart.setNotify(true);
//                }
                Point pt = e.getPoint();
                this.selection.moveTo(pt.getX(), pt.getY());
                this.lastPoint = new Point(pt);
//            }
            
        }
    }

    /**
     * Handles a mouse dragged event.
     *
     * @param e  the event.
     */
    public void mouseDragged(MouseEvent e) {
        if (this.lastPoint == null) {
            return;  // we never started a selection
        }
        ChartPanel panel = (ChartPanel) e.getSource();
        Point pt = e.getPoint();
        Point2D pt2 = ShapeUtilities.getPointInRectangle(pt.x, pt.y,
                panel.getScreenDataArea());
        if (pt2.distance(this.lastPoint) > 5) {
            this.selection.lineTo(pt2.getX(), pt2.getY());
            this.lastPoint = pt2;
        }
        panel.setSelectionShape(selection);
        panel.setSelectionFillPaint(this.fillPaint);
        panel.setSelectionOutlinePaint(this.outlinePaint);
        panel.repaint();
    }

    public void mouseReleased(MouseEvent e) {
        if (this.lastPoint == null) {
            return;  // we never started a selection
        }
        ChartPanel panel = (ChartPanel) e.getSource();
        this.selection.closePath();

        // do something with the selection shape
       
//        Selectable p = (Selectable) panel;
//        if (p.canSelectByRegion()) {
//            p.select(this.selection, panel.getScreenDataArea(), panel);
//        }
        
        panel.setSelectionShape(null);
        this.selection.reset();
        this.lastPoint = null;
        panel.repaint();
        panel.clearLiveMouseHandler();
    }

}
