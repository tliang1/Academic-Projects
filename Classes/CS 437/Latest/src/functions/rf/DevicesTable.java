package functions.rf;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import functions.dmf.DMF;
import types.Device;

/**
 * @author Tony Liang
 *
 */
public class DevicesTable
{
	private JFrame frame;
	private JPanel panel;

	private JLabel totalDevicesUsagesLabel;
	private JLabel currentDeficitLabel;
	private JLabel totalPriorityLevelsLabel;

	private JTable devicesData;
	private JScrollPane scrollPane;
	private JButton limitButton = new JButton(new LimitButtonAction("Limit Power"));

	private ResponseFunction rf;
	private DMF dmf;
	private List<Device> devices;

	/**
	 * Creates a DevicesTable given the Response Function and the Data Management Function.
	 *
	 * @param rf				Response Function
	 * @param dmf				Data Management Function
	 * @throws SQLException
	 */
	public DevicesTable(ResponseFunction rf, DMF dmf) throws SQLException
	{
		this.dmf = dmf;
		this.rf = rf;
	    devices = rf.importanceSort();

		generateFrame(0);
	}

	/**
	 * Displays a frame given the frame number.
	 * <p>
	 * Frame number:
	 * <ul>
	 * 	<li>
	 * 		0 for displaying initial devices.
	 * 	</li>
	 * 	<li>
	 * 		Any other number for displaying devices after removing the current deficit.
	 * 	</li>
	 * </ul>
	 *
	 * @param frameNumber		frame number
	 * @throws SQLException
	 */
	private void generateFrame(int frameNumber) throws SQLException
	{
		frame = new JFrame("Devices Table");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		panel = new JPanel();

		String[][] deviceRecords = new String[devices.size()][3];
		String[] columnLabels = null;

		if (frameNumber == 0)
		{
			for (int record = 0; record < deviceRecords.length; record++)
			{
				deviceRecords[record][0] = String.valueOf(devices.get(record).getDeviceId());
				deviceRecords[record][1] = String.valueOf(devices.get(record).getDeviceUsage());
				deviceRecords[record][2] = String.valueOf(devices.get(record).getPriority());
			}

			columnLabels = new String[]{"Device ID", "Device Usage", "Priority"};

			frame.add(BorderLayout.SOUTH, limitButton);
		}
		else
		{
			for (int record = 0; record < deviceRecords.length; record++)
			{
				deviceRecords[record][0] = String.valueOf(devices.get(record).getDeviceId());
				deviceRecords[record][1] = String.valueOf(rf.wattsToPercent()[record]);
				deviceRecords[record][2] = String.valueOf(devices.get(record).getPriority());
			}

			columnLabels = new String[]{"Device ID", "Device Usage Percentage", "Priority"};

			currentDeficitLabel = new JLabel("Current Deficit Removed: " + dmf.getDeficit());

			panel.add(currentDeficitLabel);
		}

		devicesData = new JTable(deviceRecords, columnLabels);
		devicesData.setAutoCreateRowSorter(true);
		scrollPane = new JScrollPane(devicesData);

		totalDevicesUsagesLabel = new JLabel("Total Device Usage: " + rf.totalDeviceUsage());
		totalPriorityLevelsLabel = new JLabel("Total different priority levels: " + rf.totalPriorityLevels());

		panel.add(totalDevicesUsagesLabel);
		panel.add(totalPriorityLevelsLabel);

		frame.add(BorderLayout.NORTH, panel);
		frame.add(BorderLayout.CENTER, scrollPane);

		frame.pack();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private class LimitButtonAction extends AbstractAction
	{
		private static final long serialVersionUID = 160365296505695658L;

		/**
		 * Creates a new LimitButtonAction given the name of the button.
		 *
		 * @param name	name of button
		 */
		public LimitButtonAction(String name)
		{
			super(name);
		}

		@Override
		public void actionPerformed(ActionEvent actionEvent)
		{
			try
			{
				frame.dispose();
				devices = rf.powerConsumption();
				generateFrame(1);
				dmf.setDeficit(0);
				frame.revalidate();
				dmf.close();
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}
	}
}