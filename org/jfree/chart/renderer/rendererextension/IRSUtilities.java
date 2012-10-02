package org.jfree.chart.renderer.rendererextension;

import java.awt.Paint;

import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.datasetextension.DatasetCursor;
import org.jfree.data.datasetextension.DatasetSelectionExtension;
import org.jfree.data.datasetextension.impl.CategoryCursor;
import org.jfree.data.datasetextension.impl.XYCursor;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.XYDataset;

/**
 * A helper class to define simple item rendering strategies.
 * 
 * Currently provides only support for paint manipulations based on the selection state of items.
 * 
 * @author zinsmaie
 */
public class IRSUtilities {

	private IRSUtilities() {
		//static helper class
	}

	//Reusable cursors for fast access
	private static DatasetCursor cursor;
	private final static XYCursor xyCursor = new XYCursor();
	private final static CategoryCursor categoryCursor = new CategoryCursor();
	
	//Default methods for PaintIRS handling
	
	/**
	 * A helper method that installs a {@link PaintIRS} on the specified renderer.
	 * Highlights selected items by rendering them with the specified paint.
	 * Works only if the renderer determines the outline paint {@link AbstractRenderer#getItemFillPaint(int, int) per item} 
	 * 
	 * @param renderer renders the dataset and is enhanced with the created IRS
	 * @param ext access to the selection state the dataset items
	 * @param fillPaint (might be null) defines a highlight color that should be used for the interior of selected items
	 * @param itemPaint (might be null) defines a highlight color that should be used for selected items
	 * @param outlinePaint (might be null) defines a highlight color that should be used for the outline of selected items
	 */
	public static void setSelectedItemPaintIRS(final AbstractRenderer renderer, final DatasetSelectionExtension ext,
			final Paint fillPaint, final Paint itemPaint, final Paint outlinePaint) {
		
		PaintIRS irs = new DefaultPaintIRS(renderer) {
						
			/** a generated serial id */
			private static final long serialVersionUID = -7838213904327581272L;

			
			private final Dataset dataset = ext.getDataset();
			
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
				
				if (this.dataset instanceof XYDataset) {
					xyCursor.setPosition(row, column);
					cursor = xyCursor;
				} else
				if (this.dataset instanceof CategoryDataset) {
					CategoryDataset d = (CategoryDataset)dataset; 
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

	/**
	 * A helper method that installs a {@link PaintIRS} on the specified renderer.
	 * Highlights selected items by rendering their interior with the specified paint.
	 * Works only if the renderer determines the outline paint {@link AbstractRenderer#getItemFillPaint(int, int) per item} 
	 * 
	 * @param renderer renders the dataset and is enhanced with the created IRS
	 * @param ext access to the selection state the dataset items
	 * @param fillPaint defines a highlight color that should be used for the interior of selected items
	 */
	public static void setSelectedItemFillPaint(final AbstractRenderer renderer, final DatasetSelectionExtension ext, final Paint fillPaint) {
		setSelectedItemPaintIRS(renderer, ext, fillPaint, null, null);
	}
	
	/**
	 * A helper method that installs a {@link PaintIRS} on the specified renderer.
	 * Highlights selected items by rendering them with the specified paint.
	 * Works only if the renderer determines the item paint {@link AbstractRenderer#getItemPaint(int, int) per item} 
	 * 
	 * @param renderer renders the dataset and is enhanced with the created IRS
	 * @param ext access to the selection state the dataset items
	 * @param itemPaint defines a highlight color that should be used for selected items
	 */
	public static void setSelectedItemPaint(final AbstractRenderer renderer, final DatasetSelectionExtension ext, final Paint itemPaint) {
		setSelectedItemPaintIRS(renderer, ext, null, itemPaint, null);
	}
	
	/**
	 * A helper method that installs a {@link PaintIRS} on the specified renderer.
	 * Highlights selected items by rendering their outline with the defined paint.
	 * Works only if the renderer determines the outline paint {@link AbstractRenderer#getItemOutlinePaint(int, int) per item} 
	 * 
	 * @param renderer renders the dataset and is enhanced with the created IRS
	 * @param ext access to the selection state the dataset items
	 * @param outlinePaint defines a highlight color that should be used for the outline of selected items
	 */
	public static void setSelectedItemOutlinePaint(final AbstractRenderer renderer, final DatasetSelectionExtension ext, final Paint outlinePaint) {
		setSelectedItemPaintIRS(renderer, ext, null, null, outlinePaint);
	}

	
}
