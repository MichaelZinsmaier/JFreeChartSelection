package org.jfree.chart.renderer.contribution;

import java.awt.Font;

import org.jfree.chart.labels.ItemLabelPosition;

public interface LabelIRS {

	public Font getItemLabelFont(int row, int column);
	
	public boolean isItemLabelVisible(int row, int column);
	
	public ItemLabelPosition getPositiveItemLabelPosition(int row, int column);
	
	public ItemLabelPosition getNegativeItemLabelPosition(int row, int column);
}
