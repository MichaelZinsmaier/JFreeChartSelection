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

import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
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
public class RectangularRegionSelectionHandler extends RegionSelectionHandler {

    /**
     * The selection rectangle (in Java2D space).
     */
    private Rectangle selection;

    /**
     * The last mouse point.
     */
    private Point2D startPoint;

    /**
     * Creates a new default instance.
     */
    public RectangularRegionSelectionHandler() {
    	super();
        this.selection = null;
        this.startPoint = null;        
    }

    /**
     * Creates a new selection handler with the specified attributes.
     *
     * @param outlineStroke  the outline stroke.
     * @param outlinePaint  the outline paint.
     * @param fillPaint  the fill paint.
     */
    public RectangularRegionSelectionHandler(Stroke outlineStroke, Paint outlinePaint,
            Paint fillPaint) {
        super(outlineStroke, outlinePaint, fillPaint);
        this.selection = null;
        this.startPoint = null;
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
//NOT IMPLEMENTED            
//            if (panel instanceof Selectable) {
//                Selectable s = (Selectable) panel;
//                if (!e.isShiftDown()) {
//                    s.clearSelection();
//                    chart.setNotify(true);
//                }
                Point pt = e.getPoint();
                this.startPoint = new Point(pt);
//            }
            
        }
    }

    /**
     * Handles a mouse dragged event.
     *
     * @param e  the event.
     */
    public void mouseDragged(MouseEvent e) {
        if (this.startPoint == null) {
            return;  // we never started a selection
        }
        ChartPanel panel = (ChartPanel) e.getSource();
        Point pt = e.getPoint();
        Point2D pt2 = ShapeUtilities.getPointInRectangle(pt.x, pt.y,
                panel.getScreenDataArea());

        
        selection = getRect(startPoint, pt2);
        panel.setSelectionShape(selection);
        panel.setSelectionFillPaint(this.fillPaint);
        panel.setSelectionOutlinePaint(this.outlinePaint);
        panel.repaint();
    }

    public void mouseReleased(MouseEvent e) {
        if (this.startPoint == null) {
            return;  // we never started a selection
        }
        ChartPanel panel = (ChartPanel) e.getSource();

        // do something with the selection shape
       
//        Selectable p = (Selectable) panel;
//        if (p.canSelectByRegion()) {
//            p.select(this.selection, panel.getScreenDataArea(), panel);
//        }
        
        panel.setSelectionShape(null);
        this.selection = null;
        this.startPoint = null;
        panel.repaint();
        panel.clearLiveMouseHandler();
    }


    
    private Rectangle getRect(Point2D p1, Point2D p2) {
        int minX, minY;
        int w, h;
        
        //process x and w
        if (p1.getX() < p2.getX()) {
        	minX = (int)p1.getX();
        	w = (int)p2.getX() - minX;
        } else {
        	minX = (int)p2.getX();
        	w = (int)p1.getX() - minX;
        	if (w <= 0) {
        		w = 1;
        	}
        }
        
        //process y and h
        if (p1.getY() < p2.getY()) {
        	minY = (int)p1.getY();
        	h = (int)p2.getY() - minY;
        } else {
        	minY = (int)p2.getY();
        	h = (int)p1.getY() - minY;
        	if (h <= 0) {
        		h = 1;
        	}
        }
        
        return new Rectangle(minX, minY, w, h);
    }
}
