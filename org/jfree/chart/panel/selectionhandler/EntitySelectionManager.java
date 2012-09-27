package org.jfree.chart.panel.selectionhandler;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.DataItemEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.data.datasetextension.DatasetCursor;
import org.jfree.data.datasetextension.DatasetSelectionExtension;
import org.jfree.data.datasetextension.impl.CategoryCursor;
import org.jfree.data.datasetextension.impl.DatasetExtensionManager;
import org.jfree.data.datasetextension.impl.PieCursor;
import org.jfree.data.datasetextension.impl.XYCursor;
import org.jfree.data.general.Dataset;

public class EntitySelectionManager implements SelectionManager {
	
	private boolean intersectionMode;
	private final ChartPanel renderSourcePanel;
	private final DatasetExtensionManager extensionManager;
	private final Dataset[] datasets;

	// instantiated only once to increase execution speed
	private final XYCursor xyCursor = new XYCursor();
	private final CategoryCursor categoryCursor = new CategoryCursor();
	private final PieCursor pieCursor = new PieCursor();

	public EntitySelectionManager(ChartPanel renderSourcePanel,
			Dataset[] datasets) {
		this.renderSourcePanel = renderSourcePanel;
		this.datasets = datasets;
		// initialize an extension manager without registered extensions
		this.extensionManager = new DatasetExtensionManager();
		this.intersectionMode = false;
	}

	public EntitySelectionManager(ChartPanel renderSourcePanel,
			Dataset[] datasets, DatasetExtensionManager extensionManager) {
		this.renderSourcePanel = renderSourcePanel;
		this.datasets = datasets;
		// set an extension manager that may manager helper objects to extend
		// old datasets
		this.extensionManager = extensionManager;
		this.intersectionMode = false;
	}
	
	public void setIntersectionSelection(boolean on) {
		this.intersectionMode = on;
	}

	public void select(double x, double y) {

		//scale if necessary
		double scaleX = this.renderSourcePanel.getScaleX(); 
		double scaleY = this.renderSourcePanel.getScaleY();		
		if (scaleX != 1.0d || scaleY != 1.0d) {
			x = x / scaleX;
			y = y / scaleY;
		} 
		
		if (this.renderSourcePanel.getChartRenderingInfo() != null) {
			EntityCollection entities = this.renderSourcePanel
					.getChartRenderingInfo().getEntityCollection();

			Iterator iter = entities.getEntities().iterator();

			while (iter.hasNext()) {
				Object o = iter.next();

				if (o instanceof DataItemEntity) {
					DataItemEntity e = (DataItemEntity) o;

					// simple check if the entity shape area contains the point
					if (e.getArea().contains(new Point2D.Double(x, y))) {
						select(e);
					}
				}
			}
		}
	}

	public void select(Rectangle2D pSelection) {
		//scale if necessary
		Rectangle2D selection;
		double scaleX = this.renderSourcePanel.getScaleX(); 
		double scaleY = this.renderSourcePanel.getScaleY();		
		if (scaleX != 1.0d || scaleY != 1.0d) {
			AffineTransform st = AffineTransform.getScaleInstance(1.0 / scaleX, 1.0 / scaleY);
			Area selectionArea = new Area(pSelection);
			selectionArea.transform(st);
			selection = selectionArea.getBounds2D();
		}  else {
			selection = pSelection;
		}
		
		if (this.renderSourcePanel.getChartRenderingInfo() != null) {			
			muteAll();
			{

				EntityCollection entities = this.renderSourcePanel
						.getChartRenderingInfo().getEntityCollection();

				Iterator iter = entities.getEntities().iterator();

				while (iter.hasNext()) {
					Object o = iter.next();

					if (o instanceof DataItemEntity) {

						DataItemEntity e = (DataItemEntity) o;
						boolean match = false;

						if (e.getArea() instanceof Rectangle2D) {
							Rectangle2D entityRect = (Rectangle2D) e.getArea();
							// use fast rectangle to rectangle test							
							if (this.intersectionMode) {
								match = selection.intersects(entityRect);
							} else {
								match = selection.contains(entityRect);
							}
						} else {
							// general shape test
							Area selectionShape = new Area(selection);
							Area entityShape = new Area(e.getArea());

							if (this.intersectionMode) {
								//test if the shapes intersect
								entityShape.intersect(selectionShape);
								match = !entityShape.isEmpty();
							} else {
								//test if the entity shape is completely covered by the selection
								entityShape.subtract(selectionShape);
								match = entityShape.isEmpty();			
							}
						}

						if (match) {
							select(e);
						}
					}
				}
			}
			unmuteAndTrigger();
		}
	}

	public void select(GeneralPath pSelection) {
		//scale if necessary
		GeneralPath selection;
		double scaleX = this.renderSourcePanel.getScaleX(); 
		double scaleY = this.renderSourcePanel.getScaleY();		
		if (scaleX != 1.0d || scaleY != 1.0d) {
			AffineTransform st = AffineTransform.getScaleInstance(1.0 / scaleX, 1.0 / scaleY);
			Area selectionArea = new Area(pSelection);
			selectionArea.transform(st);
			selection = new GeneralPath(selectionArea);
		}  else {
			selection = pSelection;
		}
				
		if (this.renderSourcePanel.getChartRenderingInfo() != null) {
			muteAll();
			{

				EntityCollection entities = this.renderSourcePanel
						.getChartRenderingInfo().getEntityCollection();

				Iterator iter = entities.getEntities().iterator();

				while (iter.hasNext()) {
					Object o = iter.next();

					if (o instanceof DataItemEntity) {
						DataItemEntity e = (DataItemEntity) o;
						Area selectionShape = new Area(selection);
						Area entityShape = new Area(e.getArea());

						if (this.intersectionMode) {
							//test if the shapes intersect
							entityShape.intersect(selectionShape);
							if (!entityShape.isEmpty()) {
								select(e);
							}
						} else {
							//test if the entity shape is completely covered by the selection
							entityShape.subtract(selectionShape);
							if (entityShape.isEmpty()) {
								select(e);
							}							
						}
					}
				}
			}
			unmuteAndTrigger();
		}
	}

	public void clearSelection() {
		for (int i = 0; i < this.datasets.length; i++) {
			if (this.extensionManager.supports(this.datasets[i],
					DatasetSelectionExtension.class)) {
				DatasetSelectionExtension selectionExtension = (DatasetSelectionExtension) this.extensionManager
						.getExtension(this.datasets[i],
								DatasetSelectionExtension.class);

				selectionExtension.clearSelection();
			}
		}
	}
	
	
	private void select(DataItemEntity e) {
		// to support propper clear functionality we must maintain
		// all datasets that we change!
		boolean handled = false;
		for (int i = 0; i < this.datasets.length; i++) {
			if (datasets[i].equals(e.getGeneralDataset())) {
				handled = true;
				break;
			}
		}

		if (handled) {
			if (this.extensionManager.supports(e.getGeneralDataset(),
					DatasetSelectionExtension.class)) {

				DatasetSelectionExtension selectionExtension = (DatasetSelectionExtension) this.extensionManager
						.getExtension(e.getGeneralDataset(),
								DatasetSelectionExtension.class);

				// set the position to the correct element
				DatasetCursor cursor = null;

				if (e instanceof XYItemEntity) {
					this.xyCursor.setPosition(
							((XYItemEntity) e).getSeriesIndex(),
							((XYItemEntity) e).getItem());
					cursor = this.xyCursor;
				} else if (e instanceof CategoryItemEntity) {
					this.categoryCursor.setPosition(
							((CategoryItemEntity) e).getRowKey(),
							((CategoryItemEntity) e).getColumnKey());
					cursor = this.categoryCursor;
				} else if (e instanceof PieSectionEntity) {
					this.pieCursor.setPosition(((PieSectionEntity) e)
							.getSectionKey());
					cursor = this.pieCursor;
				}

				// work on the data
				if (cursor != null) {
					selectionExtension.setSelected(cursor, true);
				}
			}
		}
	}

	private void muteAll() {
		setNotifyOnListenerExtensions(false);
	}

	private void unmuteAndTrigger() {
		setNotifyOnListenerExtensions(true);
	}

	private void setNotifyOnListenerExtensions(boolean notify) {
		for (int i = 0; i < this.datasets.length; i++) {
			if (this.extensionManager.supports(datasets[i],
					DatasetSelectionExtension.class)) {
				DatasetSelectionExtension selectionExtension = (DatasetSelectionExtension) this.extensionManager
						.getExtension(datasets[i],
								DatasetSelectionExtension.class);

				selectionExtension.setNotify(notify);
			}
		}
	}

}
