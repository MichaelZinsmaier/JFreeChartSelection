package org.jfree.data.selection;

import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.entity.DataItemEntity;
import org.jfree.chart.entity.EntityCollection;

public class EntitySelectionManager implements SelectionManager {

	private final ChartPanel renderSourcePanel;

	public EntitySelectionManager(ChartPanel renderSourcePanel) {
		this.renderSourcePanel = renderSourcePanel;
	}

	public void select(double x, double y) {

		if (this.renderSourcePanel.getChartRenderingInfo() != null) {
			EntityCollection entities = this.renderSourcePanel
					.getChartRenderingInfo().getEntityCollection();

			Iterator iter = entities.getEntities().iterator();

			while (iter.hasNext()) {
				Object o = iter.next();

				if (o instanceof DataItemEntity) {
				
					DataItemEntity e = (DataItemEntity) o;

					//simple check if the entity shape area contains the point
					if (e.getArea().contains(new Point2D.Double(x,y))) {
						select(e);
					}
				}
			}
		}
	}

	public void select(Rectangle2D selection) {

		if (this.renderSourcePanel.getChartRenderingInfo() != null) {
			EntityCollection entities = this.renderSourcePanel
					.getChartRenderingInfo().getEntityCollection();

			Iterator iter = entities.getEntities().iterator();

			while (iter.hasNext()) {
				Object o = iter.next();

				if (o instanceof DataItemEntity) {
				
					DataItemEntity e = (DataItemEntity) o;
					boolean covered = false; 
					
					if (e.getArea() instanceof Rectangle2D) {
						//use fast rectangle to rectangle test
						covered = selection.contains((Rectangle2D)e.getArea());
					} else {
						//general shape test
						Area selectionShape = new Area(selection);
						Area entityShape = new Area(e.getArea());

						entityShape.subtract(selectionShape);
						covered = entityShape.isEmpty();
					}
					
					if (covered) {
						select(e);
					}
				}
			}
		}
	}

	public void select(GeneralPath selection) {

		if (this.renderSourcePanel.getChartRenderingInfo() != null) {
			EntityCollection entities = this.renderSourcePanel
					.getChartRenderingInfo().getEntityCollection();

			Iterator iter = entities.getEntities().iterator();

			while (iter.hasNext()) {
				Object o = iter.next();

				if (o instanceof DataItemEntity) {

					DataItemEntity e = (DataItemEntity) o;

					Area selectionShape = new Area(selection);
					Area entityShape = new Area(e.getArea());

					entityShape.subtract(selectionShape);

					if (entityShape.isEmpty()) {
						select(e);
					}
				}
			}
		}
	}

	
	private void select(DataItemEntity e) {
		System.out.println(e.getShapeCoords());
	}
	


}
