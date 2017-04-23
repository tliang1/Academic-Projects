package functions.cf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 * @author Laura Mann
 *
 */
public class SolarRadiationGraph
{
	/** The time series data. */
    private TimeSeries series;

    /** The most recent value added. */
    private double lastValue = 100.0;

    private final ChartPanel chartPanel;
    private final JButton addButton;

    /**
     * Creates a new SolarRadiationGraph.
     */
    public SolarRadiationGraph()
    {
    	series = new TimeSeries("Random Data");

        final TimeSeriesCollection dataset = new TimeSeriesCollection(series);

        final JFreeChart chart = createChart(dataset);
        chart.setBackgroundPaint(Color.CYAN);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));

        addButton = new JButton(new AddButtonAction("Add New Data Item"));
        addButton.setActionCommand("ADD_DATA");
    }

    /**
     * Returns a time series chart given the dataset.
     *
     * @param dataset	dataset
     * @return			a time series chart
     */
    private JFreeChart createChart(final XYDataset dataset)
    {
        final JFreeChart result = ChartFactory.createTimeSeriesChart("Solar Radiation", "Time", "Level", dataset,
        		true, true, false);

        final XYPlot plot = result.getXYPlot();
        plot.setBackgroundPaint(Color.darkGray);

        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(60000.0);  // 60 seconds
        axis = plot.getRangeAxis();
        axis.setRange(0.0, 200.0);

        return result;
    }

    private class AddButtonAction extends AbstractAction
	{
		private static final long serialVersionUID = -3640315073742765348L;

		/**
		 * Creates a new AddButtonAction given the name.
		 *
		 * @param name	name of button
		 */
		public AddButtonAction(String name)
		{
			super(name);
		}

		@Override
		public void actionPerformed(ActionEvent actionEvent)
		{
			if (actionEvent.getActionCommand().equals("ADD_DATA"))
	        {
	            final double factor = 0.90 + (0.2 * Math.random());
	            lastValue *= factor;
	            series.add(new Millisecond(), lastValue);
	        }
		}
	}

    /**
     * Returns the solar radiation graph panel.
     *
     * @return	solar radiation graph panel
     */
    public JPanel getPanel()
    {
    	JPanel solarRadiationGraphPanel = new JPanel(new BorderLayout());

    	solarRadiationGraphPanel.add(chartPanel);
        solarRadiationGraphPanel.add(addButton, BorderLayout.SOUTH);

        solarRadiationGraphPanel.setVisible(true);

    	return solarRadiationGraphPanel;
    }
}