package org.jfree.chart.renderer.rendererextension;

import org.jfree.chart.renderer.AbstractRenderer;

/*
 * if necessary this can be improved wrt speed by implementing the
 * default cases direct in the abstract renderer.
 */

public abstract class DefaultItemRenderingStrategy {

	protected final AbstractRenderer renderer;

	public DefaultItemRenderingStrategy(AbstractRenderer renderer) {
		this.renderer = renderer;
	}
	
}
