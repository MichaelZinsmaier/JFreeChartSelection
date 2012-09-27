package org.jfree.chart.renderer.rendererextension;

import java.awt.Paint;

import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.datasetextension.DatasetCursor;
import org.jfree.data.datasetextension.DatasetSelectionExtension;
import org.jfree.data.datasetextension.impl.CategoryCursor;
import org.jfree.data.datasetextension.impl.CategoryDatasetSelectionExtension;
import org.jfree.data.datasetextension.impl.XYCursor;
import org.jfree.data.datasetextension.impl.XYDatasetSelectionExtension;

public class IRSUtilities {

	private IRSUtilities() {
		//static helper class
	}

	private static DatasetCursor cursor;
	private final static XYCursor xyCursor = new XYCursor();
	private final static CategoryCursor categoryCursor = new CategoryCursor();
	
	//Default methods for PaintIRS handling
	
	public static void setSelectedItemPaintIRS(final AbstractRenderer renderer, final DatasetSelectionExtension ext,
			final Paint fillPaint, final Paint itemPaint, final Paint outlinePaint) {
		
		PaintIRS irs = new DefaultPaintIRS(renderer) {
						
			public Paint getItemPaint(int row, int column) {
				if (itemPaint == null || !isSelected(row, column)) {
					return super.getItemPaint(row, column);
				} else {
					return itemPaint;
				}
			}
			
			public Paint getItemOutlinePaint(int row, int column) {
				if (outlinePaint == null || !isSelected(row, column)) {
					return super.getItemPaint(row, column);
				} else {
					return outlinePaint;
				}
			}
			
			public Paint getItemFillPaint(int row, int column) {
				if (fillPaint == null || !isSelected(row, column)) {
					return super.getItemPaint(row, column);
				} else {
					return fillPaint;
				}
			}
			
			private boolean isSelected(int row, int column) {
				//note that pie plots aren't based on the abstract renderer
				//=> threat only xy and category
				if (ext instanceof XYDatasetSelectionExtension) {
					xyCursor.setPosition(row, column);
					cursor = xyCursor;
				} else
				if (ext instanceof CategoryDatasetSelectionExtension) {
					CategoryDataset d = (CategoryDataset)ext.getDataset(); 
					categoryCursor.setPosition(d.getRowKey(row), d.getColumnKey(column));
					cursor = categoryCursor;
				} else { 
					return false;
				}

				return ext.isSelected(cursor);
			}
		};
		
		//this is where the magic happens
		renderer.setPaintIRS(irs);
	}
	
	public static void setSelectedItemFillPaint(final AbstractRenderer renderer, final DatasetSelectionExtension ext, final Paint fillPaint) {
		setSelectedItemPaintIRS(renderer, ext, fillPaint, null, null);
	}
	
	public static void setSelectedItemPaint(final AbstractRenderer renderer, final DatasetSelectionExtension ext, final Paint itemPaint) {
		setSelectedItemPaintIRS(renderer, ext, null, itemPaint, null);
	}
	
	public static void setSelectedItemOutlinePaint(final AbstractRenderer renderer, final DatasetSelectionExtension ext, final Paint outlinePaint) {
		setSelectedItemPaintIRS(renderer, ext, null, null, outlinePaint);
	}

	
}
