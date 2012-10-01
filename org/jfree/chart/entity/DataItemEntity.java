package org.jfree.chart.entity;

import java.awt.Shape;

import org.jfree.data.general.Dataset;


public abstract class DataItemEntity extends ChartEntity {

    /**
     * Creates a new chart entity.
     *
     * @param area  the area (<code>null</code> not permitted).
     */
    public DataItemEntity(Shape area) {
        super(area);
    }

    /**
     * Creates a new chart entity.
     *
     * @param area  the area (<code>null</code> not permitted).
     * @param toolTipText  the tool tip text (<code>null</code> permitted).
     */
    public DataItemEntity(Shape area, String toolTipText) {
       super(area, toolTipText, null);
    }

    /**
     * Creates a new entity.
     *
     * @param area  the area (<code>null</code> not permitted).
     * @param toolTipText  the tool tip text (<code>null</code> permitted).
     * @param urlText  the URL text for HTML image maps (<code>null</code>
     *                 permitted).
     */
    public DataItemEntity(Shape area, String toolTipText, String urlText) {
        super(area, toolTipText, urlText);
    }

	public abstract Dataset getGeneralDataset();
}
