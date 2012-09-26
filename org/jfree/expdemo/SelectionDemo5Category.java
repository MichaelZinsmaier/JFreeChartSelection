package org.jfree.expdemo;

/**
 * based on BarChartDemo1 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.panel.selectionhandler.EntitySelectionManager;
import org.jfree.chart.panel.selectionhandler.MouseClickSelectionHandler;
import org.jfree.chart.panel.selectionhandler.RectangularRegionSelectionHandler;
import org.jfree.chart.panel.selectionhandler.RegionSelectionHandler;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.contribution.DefaultPaintIRS;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.datasetextension.DatasetIterator;
import org.jfree.data.datasetextension.DatasetSelectionExtension;
import org.jfree.data.datasetextension.impl.CategoryCursor;
import org.jfree.data.datasetextension.impl.CategoryDatasetSelectionExtension;
import org.jfree.data.datasetextension.impl.DatasetExtensionManager;
import org.jfree.data.event.SelectionChangeEvent;
import org.jfree.data.event.SelectionChangeListener;
import org.jfree.data.general.Dataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public class SelectionDemo5Category extends ApplicationFrame implements SelectionChangeListener {

	JTable table;

	DefaultTableModel model;

	CategoryDataset dataset;
	
	
	    public SelectionDemo5Category(String title) {
	        super(title);
	        JPanel chartPanel = createDemoPanel();
	        chartPanel.setPreferredSize(new Dimension(500, 270));
	        
			JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			split.add(chartPanel);

			this.model = new DefaultTableModel(new String[] { "row:", "column:",
					"value:"}, 0);
			this.table = new JTable(this.model);
			TableColumnModel tcm = this.table.getColumnModel();
			JPanel p = new JPanel(new BorderLayout());
			JScrollPane scroller = new JScrollPane(this.table);
			p.add(scroller);
			p.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(
					"Selected Items: "), new EmptyBorder(4, 4, 4, 4)));
			split.add(p);
			setContentPane(split);
	    }

	    /**
	     * Returns a sample dataset.
	     *
	     * @return The dataset.
	     */
	    private static CategoryDataset createDataset() {

	        // row keys...
	        String series1 = "First";
	        String series2 = "Second";
	        String series3 = "Third";

	        // column keys...
	        String category1 = "Category 1";
	        String category2 = "Category 2";
	        String category3 = "Category 3";
	        String category4 = "Category 4";
	        String category5 = "Category 5";

	        // create the dataset...
	        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	        dataset.addValue(1.0, series1, category1);
	        dataset.addValue(4.0, series1, category2);
	        dataset.addValue(3.0, series1, category3);
	        dataset.addValue(5.0, series1, category4);
	        dataset.addValue(5.0, series1, category5);

	        dataset.addValue(5.0, series2, category1);
	        dataset.addValue(7.0, series2, category2);
	        dataset.addValue(6.0, series2, category3);
	        dataset.addValue(8.0, series2, category4);
	        dataset.addValue(4.0, series2, category5);

	        dataset.addValue(4.0, series3, category1);
	        dataset.addValue(3.0, series3, category2);
	        dataset.addValue(2.0, series3, category3);
	        dataset.addValue(3.0, series3, category4);
	        dataset.addValue(6.0, series3, category5);

	        return dataset;

	    }

	    
	    private static JFreeChart createChart(CategoryDataset dataset) {

	        // create the chart...
	        JFreeChart chart = ChartFactory.createBarChart(
	            "Bar Chart Demo 1",       // chart title
	            "Category",               // domain axis label
	            "Value",                  // range axis label
	            dataset,                  // data
	            PlotOrientation.VERTICAL, // orientation
	            true,                     // include legend
	            true,                     // tooltips?
	            false                     // URLs?
	        );

	        CategoryPlot plot = (CategoryPlot) chart.getPlot();
	        plot.setDomainGridlinesVisible(true);
	        plot.setRangeCrosshairVisible(true);
	        plot.setRangeCrosshairPaint(Color.blue);

	        // set the range axis to display integers only...
	        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

	        // disable bar outlines...
	        BarRenderer renderer = (BarRenderer) plot.getRenderer();
	        renderer.setDrawBarOutline(false);

	        // set up gradient paints for series...
	        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue,
	                0.0f, 0.0f, new Color(0, 0, 64));
	        GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green,
	                0.0f, 0.0f, new Color(0, 64, 0));
	        GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red,
	                0.0f, 0.0f, new Color(64, 0, 0));
	        renderer.setSeriesPaint(0, gp0);
	        renderer.setSeriesPaint(1, gp1);
	        renderer.setSeriesPaint(2, gp2);

	        renderer.setLegendItemToolTipGenerator(
	                new StandardCategorySeriesLabelGenerator("Tooltip: {0}"));

	        CategoryAxis domainAxis = plot.getDomainAxis();
	        domainAxis.setCategoryLabelPositions(
	                CategoryLabelPositions.createUpRotationLabelPositions(
	                        Math.PI / 6.0));

	        return chart;

	    }


	    public JPanel createDemoPanel() {
	    	dataset = createDataset();
	        JFreeChart chart = createChart(dataset);
	        ChartPanel panel = new ChartPanel(chart);
	        panel.setFillZoomRectangle(true);
	        panel.setMouseWheelEnabled(true);


	        //extend the panel with a selection handler
	        RegionSelectionHandler selectionHandler = new RectangularRegionSelectionHandler();
	        panel.addMouseHandler(selectionHandler);
	        panel.addAuxiliaryMouseHandler(new MouseClickSelectionHandler());
	        
	        //extend the dataset with selection storage
	        DatasetExtensionManager dExManager = new DatasetExtensionManager();
	        final DatasetSelectionExtension ext = new CategoryDatasetSelectionExtension(dataset, chart.getPlot());
	        ext.addChangeListener(this);
	        dExManager.registerDatasetExtension(ext);
	                
	        //extend the renderer
	        final CategoryCursor cursor = new CategoryCursor();
	        AbstractRenderer renderer = (AbstractRenderer)((CategoryPlot)chart.getPlot()).getRenderer();
	        
	        renderer.setPaintIRS(new DefaultPaintIRS(renderer) {
	        	public Paint getItemPaint(int row, int column) {
	        		cursor.setPosition(dataset.getRowKey(row), dataset.getColumnKey(column));
	        		if (ext.isSelected(cursor)) {
	        			return Color.white;
	        		} else {
	        			return super.getItemPaint(row, column);
	        		}
	        	}
	        });
	        
	        panel.setSelectionManager(new EntitySelectionManager(panel, new Dataset[]{dataset}, dExManager));
	        return panel;
	    }


	    
	    
	    public static void main(String[] args) {

	        SelectionDemo5Category demo = new SelectionDemo5Category("selection demo");
	        demo.pack();
	        RefineryUtilities.centerFrameOnScreen(demo);
	        demo.setVisible(true);

	    }

		public void selectionChanged(SelectionChangeEvent event) {
	    	while (this.model.getRowCount() > 0) {
	            this.model.removeRow(0);
	        }

	    	CategoryDatasetSelectionExtension ext = (CategoryDatasetSelectionExtension)event.getSelectionExtension(); 
	    	DatasetIterator iter = ext.getSelectionIterator(true);
	    	
	    	while (iter.hasNext()) {
	    		CategoryCursor dc = (CategoryCursor)iter.nextCursor();
	    		this.model.addRow(new Object[] { dc.rowKey, dc.columnKey, dataset.getValue(dc.rowKey, dc.columnKey)});
	    	}
		}

	}

