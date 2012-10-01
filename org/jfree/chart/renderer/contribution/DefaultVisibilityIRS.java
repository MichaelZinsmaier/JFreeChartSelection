package org.jfree.chart.renderer.contribution;

import org.jfree.chart.renderer.AbstractRenderer;

public class DefaultVisibilityIRS extends DefaultItemRenderingStrategy implements VisibilityIRS {

	public DefaultVisibilityIRS(AbstractRenderer renderer) {
		super(renderer);
	}

	public boolean getItemVisible(int row, int column) {
		return renderer.isSeriesVisible(row);
	}

}
