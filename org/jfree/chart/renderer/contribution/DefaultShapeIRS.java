package org.jfree.chart.renderer.contribution;

import java.awt.Shape;

import org.jfree.chart.renderer.AbstractRenderer;

public class DefaultShapeIRS extends DefaultItemRenderingStrategy implements ShapeIRS {

	public DefaultShapeIRS(AbstractRenderer renderer) {
		super(renderer);
	}

	public Shape getItemShape(int row, int column) {
		return renderer.lookupSeriesShape(row);
	}

}
