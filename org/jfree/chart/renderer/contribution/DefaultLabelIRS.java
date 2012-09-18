package org.jfree.chart.renderer.contribution;

import java.awt.Font;

import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.renderer.AbstractRenderer;

public class DefaultLabelIRS extends DefaultItemRenderingStrategy implements LabelIRS {

	public DefaultLabelIRS(AbstractRenderer renderer) {
		super(renderer);
	}

	public Font getItemLabelFont(int row, int column) {
        Font result = renderer.getItemLabelFont();
        if (result == null) {
            result = renderer.getSeriesItemLabelFont(row);
            if (result == null) {
                result = renderer.getBaseItemLabelFont();
            }
        }
        return result;
	}

	public boolean isItemLabelVisible(int row, int column) {
		return renderer.isSeriesItemLabelsVisible(row);
	}

	public ItemLabelPosition getPositiveItemLabelPosition(int row, int column) {
		return renderer.getSeriesPositiveItemLabelPosition(row);
	}

	public ItemLabelPosition getNegativeItemLabelPosition(int row, int column) {
		return renderer.getSeriesNegativeItemLabelPosition(row);
	}

	
	
}
