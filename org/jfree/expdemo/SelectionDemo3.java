/* -------------------
 * SelectionDemo3.java
 * -------------------
 * (C) Copyright 2009, by Object Refinery Limited.
 *
 */

package org.jfree.expdemo;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Random;

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
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.panel.selectionhandler.RectangularRegionSelectionHandler;
import org.jfree.chart.panel.selectionhandler.RegionSelectionHandler;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.selection.EntitySelectionManager;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.NumberCellRenderer;
import org.jfree.ui.RefineryUtilities;

/**
 * A demo scatter plot.
 */
public class SelectionDemo3 extends ApplicationFrame implements DatasetChangeListener {

    JTable table;

    DefaultTableModel model;

    XYSeriesCollection dataset;

    /**
     * A demonstration application showing a scatter plot.
     *
     * @param title  the frame title.
     */
    public SelectionDemo3(String title) {
        super(title);
        ChartPanel chartPanel = (ChartPanel) createDemoPanel();
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

        JFreeChart chart = chartPanel.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        this.dataset = (XYSeriesCollection) plot.getDataset();
        this.dataset.addChangeListener(this);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.add(chartPanel);

        this.model = new DefaultTableModel(new String[] {"Series:", "Item:", "X:", "Y:"}, 0);
        this.table = new JTable(this.model);
        TableColumnModel tcm = this.table.getColumnModel();
        tcm.getColumn(2).setCellRenderer(new NumberCellRenderer());
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

    private static JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createScatterPlot("SelectionDemo3",
                "X", "Y", dataset, PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setNoDataMessage("NO DATA");

        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);

        plot.setDomainGridlineStroke(new BasicStroke(0.0f));
        plot.setRangeGridlineStroke(new BasicStroke(0.0f));

        plot.setDomainMinorGridlinesVisible(true);
        plot.setRangeMinorGridlinesVisible(true);

        //XYItemRenderer r = plot.getRenderer();
        XYDotRenderer r = new XYDotRenderer();
       r.setDotHeight(2);
        r.setDotWidth(2);

        r.setSeriesPaint(0, Color.blue);
        r.setSeriesPaint(1, Color.green);
        r.setSeriesPaint(2, Color.yellow);
        r.setSeriesPaint(3, Color.orange);
        plot.setRenderer(r);
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);

        domainAxis.setTickMarkInsideLength(2.0f);
        domainAxis.setTickMarkOutsideLength(2.0f);

        domainAxis.setMinorTickCount(2);
        domainAxis.setMinorTickMarksVisible(true);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickMarkInsideLength(2.0f);
        rangeAxis.setTickMarkOutsideLength(2.0f);
        rangeAxis.setMinorTickCount(2);
        rangeAxis.setMinorTickMarksVisible(true);
        return chart;
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
//                Number x = this.dataset.getX(s, i);
//                Number y = this.dataset.getY(s, i);
//                this.model.addRow(new Object[] { seriesKey, new Integer(i), x,
//                    y});
            }
        }
    }

    public static XYDataset createDataset() {
        String[] names = {"JFreeChart", "Eastwood", "Orson", "JCommon"};
        Random rgen = new Random();
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (int s = 0; s < 4; s++) {
            XYSeries series = new XYSeries(names[s]);
            for (int i = 0; i < 5000; i++) {
                double x = rgen.nextGaussian() * 200;
                double y = rgen.nextGaussian() * 200;
                series.add(x, y);
            }
            dataset.addSeries(series);
        }
        return dataset;
    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);

        chartPanel.removeMouseHandler(chartPanel.getZoomHandler());

        RegionSelectionHandler selectionHandler = new RectangularRegionSelectionHandler();
        //selectionHandler.setModifier(InputEvent.SHIFT_MASK);
        chartPanel.addMouseHandler(selectionHandler);
        chartPanel.setSelectionManager(new EntitySelectionManager(chartPanel));
        return chartPanel;
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {
        SelectionDemo3 demo = new SelectionDemo3(
                "JFreeChart: SelectionDemo3.java");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}

