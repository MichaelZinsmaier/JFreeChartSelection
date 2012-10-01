/* -------------------
 * SelectionDemo1.java
 * -------------------
 * (C) Copyright 2009, by Object Refinery Limited.
 *
 */

package org.jfree.expdemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Paint;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.panel.selectionhandler.FreeRegionSelectionHandler;
import org.jfree.chart.panel.selectionhandler.RegionSelectionHandler;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.contribution.DefaultPaintIRS;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.datasetextension.DatasetSelectionExtension;
import org.jfree.data.datasetextension.impl.DatasetExtensionManager;
import org.jfree.data.datasetextension.impl.XYCursor;
import org.jfree.data.datasetextension.impl.XYDatasetSelectionExtension;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.selection.EntitySelectionManager;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.NumberCellRenderer;
import org.jfree.ui.RefineryUtilities;

public class SelectionDemo1 extends ApplicationFrame
        implements DatasetChangeListener {

    JTable table;

    DefaultTableModel model;

    TimeSeriesCollection dataset;

    /**
     * A demonstration application showing how to create a simple time series
     * chart.  This example uses monthly data.
     *
     * @param title  the frame title.
     */
    public SelectionDemo1(String title) {
        super(title);
        ChartPanel chartPanel = (ChartPanel) createDemoPanel();
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

        JFreeChart chart = chartPanel.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        this.dataset = (TimeSeriesCollection) plot.getDataset();
        this.dataset.addChangeListener(this);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.add(chartPanel);

        this.model = new DefaultTableModel(new String[] {"Series:", "Item:", "Period:", "Value:"}, 0);
        this.table = new JTable(this.model);
        TableColumnModel tcm = this.table.getColumnModel();
        tcm.getColumn(3).setCellRenderer(new NumberCellRenderer());
        JPanel p = new JPanel(new BorderLayout());
        JScrollPane scroller = new JScrollPane(this.table);
        p.add(scroller);
        p.setBorder(BorderFactory.createCompoundBorder(
                new TitledBorder("Selected Items: "),
                new EmptyBorder(4, 4, 4, 4)));
        split.add(p);
        setContentPane(split);
    }

    /**
     * The dataset changed, so we announce that the table model changed
     * too (even if maybe it didn't - demo shortcut).
     *
     * @param event  ignored.
     */
    public void datasetChanged(DatasetChangeEvent event) {
        while (this.model.getRowCount() > 0) {
            this.model.removeRow(0);
        }
        int seriesCount = this.dataset.getSeriesCount();
        for (int s = 0; s < seriesCount; s++) {
            int itemCount = this.dataset.getItemCount(s);
            for (int i = 0; i < itemCount; i++) {
//IMPL NEEDED            	
//                if (!this.dataset.isSelected(s, i)) {
//                    continue;
//                }
//                Comparable seriesKey = this.dataset.getSeriesKey(s);
//                RegularTimePeriod p = this.dataset.getSeries(s).getTimePeriod(i);
//                Number value = this.dataset.getY(s, i);
//                this.model.addRow(new Object[] { seriesKey, new Integer(i), p,
//                    value});
            }
        }
    }

    /**
     * Creates a chart.
     *
     * @param dataset  a dataset.
     *
     * @return A chart.
     */
    private static JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Stock Prices",  // title
            "Date",             // x-axis label
            "Price Per Unit",   // y-axis label
            dataset,            // data
            true,               // create legend?
            true,               // generate tooltips?
            false               // generate URLs?
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
        r.setBaseShapesVisible(true);
        r.setBaseShapesFilled(true);
        r.setUseFillPaint(true);
        r.setSeriesFillPaint(0, r.lookupSeriesPaint(0));
        r.setSeriesFillPaint(1, r.lookupSeriesPaint(1));
        return chart;

    }

    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return The dataset.
     */
    private static XYDataset createDataset() {

        TimeSeries s1 = new TimeSeries("S1");
        s1.add(new Month(1, 2009), 181.8);
        s1.add(new Month(2, 2009), 167.3);
        s1.add(new Month(3, 2009), 153.8);
        s1.add(new Month(4, 2009), 167.6);
        s1.add(new Month(5, 2009), 158.8);
        s1.add(new Month(6, 2009), 148.3);
        s1.add(new Month(7, 2009), 153.9);
        s1.add(new Month(8, 2009), 142.7);
        s1.add(new Month(9, 2009), 123.2);
        s1.add(new Month(10, 2009), 131.8);
        s1.add(new Month(11, 2009), 139.6);
        s1.add(new Month(12, 2009), 142.9);
        s1.add(new Month(1, 2010), 138.7);
        s1.add(new Month(2, 2010), 137.3);
        s1.add(new Month(3, 2010), 143.9);
        s1.add(new Month(4, 2010), 139.8);
        s1.add(new Month(5, 2010), 137.0);
        s1.add(new Month(6, 2010), 132.8);

        TimeSeries s2 = new TimeSeries("S2");
        s2.add(new Month(1, 2009), 129.6);
        s2.add(new Month(2, 2009), 123.2);
        s2.add(new Month(3, 2009), 117.2);
        s2.add(new Month(4, 2009), 124.1);
        s2.add(new Month(5, 2009), 122.6);
        s2.add(new Month(6, 2009), 119.2);
        s2.add(new Month(7, 2009), 116.5);
        s2.add(new Month(8, 2009), 112.7);
        s2.add(new Month(9, 2009), 101.5);
        s2.add(new Month(10, 2009), 106.1);
        s2.add(new Month(11, 2009), 110.3);
        s2.add(new Month(12, 2009), 111.7);
        s2.add(new Month(1, 2010), 111.0);
        s2.add(new Month(2, 2010), 109.6);
        s2.add(new Month(3, 2010), 113.2);
        s2.add(new Month(4, 2010), 111.6);
        s2.add(new Month(5, 2010), 108.8);
        s2.add(new Month(6, 2010), 101.6);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);
        dataset.addSeries(s2);

        return dataset;

    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel() {
    	XYDataset data = createDataset();
        JFreeChart chart = createChart(data);
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);


        //extend the panel with a selection handler
        RegionSelectionHandler selectionHandler = new FreeRegionSelectionHandler();
        panel.addMouseHandler(selectionHandler);
        
        //extend the dataset with selection storage
        DatasetExtensionManager dExManager = new DatasetExtensionManager();
        final DatasetSelectionExtension ext = new XYDatasetSelectionExtension(data); 
        dExManager.registerDatasetExtension(ext);
                
        //extend the renderer
        final XYCursor cursor = new XYCursor();
        AbstractRenderer renderer = (AbstractRenderer)((XYPlot)chart.getPlot()).getRenderer();
        renderer.setPaintIRS(new DefaultPaintIRS(renderer) {
        	public Paint getItemFillPaint(int row, int column) {
        		cursor.setPosition(row, column);
        		if (ext.isSelected(cursor)) {
        			return Color.white;
        		} else {
        			return super.getItemFillPaint(row, column);
        		}
        	}
        });
        
        panel.setSelectionManager(new EntitySelectionManager(panel, new Dataset[]{data}, dExManager));
        return panel;
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {
        SelectionDemo1 demo = new SelectionDemo1(
                "JFreeChart: SelectionDemo1");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}
