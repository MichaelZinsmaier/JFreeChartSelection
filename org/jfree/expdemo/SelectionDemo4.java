/* -------------------
 * SelectionDemo4.java
 * -------------------
 * (C) Copyright 2004-2009, by Object Refinery Limited.
 *
 */

package org.jfree.expdemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Paint;
import java.io.IOException;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.panel.selectionhandler.EntitySelectionManager;
import org.jfree.chart.panel.selectionhandler.RectangularRegionSelectionHandler;
import org.jfree.chart.panel.selectionhandler.RegionSelectionHandler;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.contribution.DefaultPaintIRS;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.datasetextension.DatasetSelectionExtension;
import org.jfree.data.datasetextension.impl.DatasetExtensionManager;
import org.jfree.data.datasetextension.impl.XYCursor;
import org.jfree.data.datasetextension.impl.XYDatasetSelectionExtension;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.NumberCellRenderer;
import org.jfree.ui.RefineryUtilities;

/**
 * A demo of the {@link HistogramDataset} class.
 */
public class SelectionDemo4 extends ApplicationFrame {

    JTable table;

    /**
     * This table model will have bad performance for large datasets - it is
     * just for a demo, not for real use.
     */
    static class CustomTableModel extends AbstractTableModel implements
            DatasetChangeListener {

        SimpleHistogramDataset dataset;

        public CustomTableModel(SimpleHistogramDataset dataset) {
            this.dataset = dataset;
            this.dataset.addChangeListener(this);
        }

        /**
         * Returns the row count, which is equal to the number of items
         * that are selected in the dataset.
         *
         * @return The row count.
         */
        public int getRowCount() {
            int selectionCount = 0;
//NOT YET IMPLEMENTED            
//            for (int i = 0; i < this.dataset.getItemCount(0); i++) {
//                if (this.dataset.isSelected(0, i)) {
//                    selectionCount++;
//                }
//            }
            return selectionCount;
        }

        public int getColumnCount() {
            return 4;
        }

        private int getItemForRow(int rowIndex) {
            int item = 0;
            int countDown = rowIndex;
            while (item < this.dataset.getItemCount(0)) {
//NOT IMPLEMENTED            	
//                if (dataset.isSelected(0, item)) {
//                    if (countDown == 0) {
//                        return item;
//                    }
//                    else {
//                        countDown--;
//                        item++;
//                    }
//                }
//                else {
//                    item++;
//                }
            }
            return item;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            int item = getItemForRow(rowIndex);
            if (columnIndex == 0) {
                return new Integer(item);
            }
            else if (columnIndex == 1) {
                return this.dataset.getStartX(0, item);
            }
            else if (columnIndex == 2) {
                return this.dataset.getEndX(0, item);
            }
            else if (columnIndex == 3) {
                return this.dataset.getY(0, item);
            }
            else {
                return "Error!";
            }
        }

        /**
         * The dataset changed, so we announce that the table model changed
         * too (even if maybe it didn't - demo shortcut).
         *
         * @param event  ignored.
         */
        public void datasetChanged(DatasetChangeEvent event) {
            fireTableDataChanged();
        }

    }

    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public SelectionDemo4(String title) {
        super(title);
        ChartPanel chartPanel = (ChartPanel) createDemoPanel();
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.add(chartPanel);

        this.table = new JTable(new CustomTableModel(
                (SimpleHistogramDataset) chartPanel.getChart().getXYPlot().getDataset()));
        TableColumnModel tcm = this.table.getColumnModel();
        tcm.getColumn(0).setHeaderValue("Item:");
        tcm.getColumn(1).setHeaderValue("Bin Start:");
        tcm.getColumn(2).setHeaderValue("Bin End:");
        tcm.getColumn(3).setHeaderValue("Value:");
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
     * Creates a sample {@link HistogramDataset}.
     *
     * @return the dataset.
     */
    private static IntervalXYDataset createDataset() {
        SimpleHistogramDataset dataset = new SimpleHistogramDataset("H1");
        double lower = 0.0;
        for (int i = 0; i < 100; i++) {
            double upper = (i + 1) / 10.0;
            SimpleHistogramBin bin = new SimpleHistogramBin(lower, upper, true,
                    false);
            dataset.addBin(bin);
            lower = upper;
        }
        double[] values = new double[1000];
        Random generator = new Random(12345678L);
        for (int i = 0; i < 1000; i++) {
            values[i] = generator.nextGaussian() + 5;
        }
        dataset.addObservations(values);
//        values = new double[1000];
//        for (int i = 0; i < 1000; i++) {
//            values[i] = generator.nextGaussian() + 7;
//        }
//        dataset.addSeries("H2", values, 100, 4.0, 10.0);
        return dataset;
    }

    /**
     * Creates a chart.
     *
     * @param dataset  a dataset.
     *
     * @return The chart.
     */
    private static JFreeChart createChart(IntervalXYDataset dataset) {
        JFreeChart chart = ChartFactory.createHistogram(
            "SelectionDemo4",
            null,
            null,
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        plot.setForegroundAlpha(0.85f);
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(true);
        renderer.setBaseOutlinePaint(Color.red);
//IMPL NEEDED        
//        
//       renderer.selectedItemPaint = Color.white;
        // flat bars look best...
        renderer.setBarPainter(new StandardXYBarPainter());
        renderer.setShadowVisible(false);
        return chart;
    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel() {
    	IntervalXYDataset data = createDataset();
        JFreeChart chart = createChart(data);
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        panel.removeMouseHandler(panel.getZoomHandler());

        //extend the panel with a selection handler
        RegionSelectionHandler selectionHandler = new RectangularRegionSelectionHandler();
        panel.addMouseHandler(selectionHandler);
        
        //extend the dataset with selection storage
        DatasetExtensionManager dExManager = new DatasetExtensionManager();
        final DatasetSelectionExtension ext = new XYDatasetSelectionExtension(data); 
        dExManager.registerDatasetExtension(ext);
                
        //extend the renderer
        final XYCursor cursor = new XYCursor();
        AbstractRenderer renderer = (AbstractRenderer)((XYPlot)chart.getPlot()).getRenderer();
        renderer.setPaintIRS(new DefaultPaintIRS(renderer) {
        	public Paint getItemPaint(int row, int column) {
        		cursor.setPosition(row, column);
        		if (ext.isSelected(cursor)) {
        			return Color.white;
        		} else {
        			return super.getItemPaint(row, column);
        		}
        	}
        });
        
        panel.setSelectionManager(new EntitySelectionManager(panel, new Dataset[]{data}, dExManager));
        return panel;
    }

    /**
     * The starting point for the demo.
     *
     * @param args  ignored.
     *
     * @throws IOException  if there is a problem saving the file.
     */
    public static void main(String[] args) throws IOException {

        SelectionDemo4 demo = new SelectionDemo4(
                "JFreeChart: SelectionDemo4.java");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}

