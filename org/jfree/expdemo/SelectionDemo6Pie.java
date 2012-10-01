package org.jfree.expdemo;

import java.awt.Color;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.panel.selectionhandler.EntitySelectionManager;
import org.jfree.chart.panel.selectionhandler.FreeRegionSelectionHandler;
import org.jfree.chart.panel.selectionhandler.MouseClickSelectionHandler;
import org.jfree.chart.panel.selectionhandler.RegionSelectionHandler;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.datasetextension.DatasetSelectionExtension;
import org.jfree.data.datasetextension.impl.DatasetExtensionManager;
import org.jfree.data.datasetextension.impl.PieCursor;
import org.jfree.data.datasetextension.impl.PieDatasetSelectionExtension;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.general.SelectionChangeEvent;
import org.jfree.data.general.SelectionChangeListener;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


/*
 * based on PieChartDemo2
 */
public class SelectionDemo6Pie extends ApplicationFrame {

	public SelectionDemo6Pie(String title) {
		super(title);
		JPanel chartPanel = createDemoPanel();
		chartPanel.setPreferredSize(new java.awt.Dimension(700, 500));
		setContentPane(chartPanel);
	}

	private static PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("One", 43.2);
		dataset.setValue("Two", 10.0);
		dataset.setValue("Three", 27.5);
		dataset.setValue("Four", 17.5);
		dataset.setValue("Five", 11.0);
		dataset.setValue("Six", 19.4);
		return dataset;
	}

	private static JFreeChart createChart(PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart("Pie Chart Demo 2", // chart
																			// title
				dataset, // dataset
				true, // include legend
				true, false);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionPaint("One", new Color(160, 160, 255));
		plot.setSectionPaint("Two", new Color(128, 128, 255 - 32));
		plot.setSectionPaint("Three", new Color(96, 96, 255 - 64));
		plot.setSectionPaint("Four", new Color(64, 64, 255 - 96));
		plot.setSectionPaint("Five", new Color(32, 32, 255 - 128));
		plot.setSectionPaint("Six", new Color(0, 0, 255 - 144));

		plot.setNoDataMessage("No data available");

		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0} ({2} percent)"));
		plot.setLabelBackgroundPaint(new Color(220, 220, 220));

		plot.setLegendLabelToolTipGenerator(new StandardPieSectionLabelGenerator(
				"Tooltip for legend item {0}"));
		plot.setSimpleLabels(true);
		plot.setInteriorGap(0.1);
		return chart;
	}

	public static JPanel createDemoPanel() {
		final PieDataset data = createDataset();
		JFreeChart chart = createChart(data);
		ChartPanel panel = new ChartPanel(chart);
		panel.setMouseWheelEnabled(true);

		// extend the panel with a selection handler
		RegionSelectionHandler selectionHandler = new FreeRegionSelectionHandler();
		panel.addMouseHandler(selectionHandler);
		panel.addAuxiliaryMouseHandler(new MouseClickSelectionHandler());
		
		// extend the dataset with selection storage
		final DatasetExtensionManager dExManager = new DatasetExtensionManager();
		final DatasetSelectionExtension ext = new PieDatasetSelectionExtension(
				data);
		dExManager.registerDatasetExtension(ext);

		// extend the plot
		final PieCursor cursor = new PieCursor();
		final PiePlot p = (PiePlot) chart.getPlot();

		ext.addChangeListener(new SelectionChangeListener() {
			public void selectionChanged(SelectionChangeEvent event) {				
				for (int i = 0; i < data.getItemCount(); i++) {
					cursor.setPosition(data.getKey(i));
					if (event.getSelectionExtension().isSelected(cursor)) {
						p.setExplodePercent(cursor.getKey(), 0.15);
					} else {
						p.setExplodePercent(cursor.getKey(), 0.0);
					}
				}
				
			}
		});
		
		panel.setSelectionManager(new EntitySelectionManager(panel,
				new Dataset[] { data }, dExManager));
		return panel;
	}

	public static void main(String[] args) {

		SelectionDemo6Pie demo = new SelectionDemo6Pie("SelectionDemo6Pie");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}

}
