package org.jfree.chart.renderer.contribution;

import java.awt.Paint;

import org.jfree.chart.renderer.AbstractRenderer;

public class DefaultPaintIRS extends DefaultItemRenderingStrategy implements PaintIRS {

	public DefaultPaintIRS(AbstractRenderer renderer) {
		super(renderer);
	}

	public Paint getItemPaint(int row, int column) {
		return renderer.lookupSeriesPaint(row);
	}

	public Paint getItemFillPaint(int row, int column) {
		return renderer.lookupSeriesFillPaint(row);
	}

	public Paint getItemOutlinePaint(int row, int column) {
		return renderer.lookupSeriesOutlinePaint(row);
	}

}
