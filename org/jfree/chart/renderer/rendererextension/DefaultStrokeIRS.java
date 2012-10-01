package org.jfree.chart.renderer.rendererextension;

import java.awt.Stroke;

import org.jfree.chart.renderer.AbstractRenderer;

public class DefaultStrokeIRS extends DefaultItemRenderingStrategy implements StrokeIRS {

	public DefaultStrokeIRS(AbstractRenderer renderer) {
		super(renderer);
	}

	public Stroke getItemStroke(int row, int column) {
		return renderer.lookupSeriesStroke(row);
	}

	public Stroke getItemOutlineStroke(int row, int column) {
		return renderer.lookupSeriesOutlineStroke(row);
	}

}
