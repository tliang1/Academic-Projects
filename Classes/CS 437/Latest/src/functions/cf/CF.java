package functions.cf;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import functions.dmf.DMF;
import types.Device;

/**
 * @author Laura Mann
 *
 */
public class CF
{
	private Container pane;

	// Start Card layout
	private CardLayout layout = new CardLayout();

	// Panels
	private JPanel mainPanel = new JPanel(new BorderLayout());
	private JPanel controlPanel = new JPanel(new BorderLayout());
	private JPanel addPanel = new JPanel(new BorderLayout());
	private JPanel removePanel = new JPanel(new BorderLayout());
	@SuppressWarnings("unused")
	private JPanel requestPanel;

	private final JTextField searchTextField = new JTextField(10);
	private final JTextField newInstruction = new JTextField(10);
	private JTextField removeInstruction = new JTextField(10);
	@SuppressWarnings("unused")
	private JTextField overrideUser, overridePass, resumeUser, resumePass,
			searchData;

	private JTextArea deviceOutput = new JTextArea(5, 20);
	private JTextArea currentInstructions = new JTextArea(5, 20);
	private JTextArea deviceInstructionsAdd = new JTextArea(20, 20);
	private JTextArea deviceInstructionsRemove = new JTextArea(20, 20);

	// Add buttons for sub panels that communicate with the Response Function
	private JButton controlsButton = new JButton("System Controls");
	private JButton mainButton = new JButton(new MainButtonAction("Return To Main Menu"));
	private JButton searchButton = new JButton(new SearchButtonAction("Search"));
	private JButton addInstructionButton = new JButton("Add Instruction");
	private JButton removeInstructionButton = new JButton("Remove Instruction");
	private JButton addButton = new JButton(new AddButtonAction("Add"));
	private JButton removeButton = new JButton("Remove");
	@SuppressWarnings("unused")
	private JButton submitButton = new JButton("Submit");
	@SuppressWarnings("unused")
	private JButton sendInstructionButton = new JButton("Send Instruction");

	private final JRadioButton RB_SEARCH_TYPE = new JRadioButton("Type");
	private final JRadioButton RB_SEARCH_PR = new JRadioButton("Priority");
	private final JRadioButton RB_ADD_TYPE = new JRadioButton("Type");
	private final JRadioButton RB_ADD_PR = new JRadioButton("Priority");

	private JComboBox<String> timeframe;

	// List of time
	private String[] hours = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
			"21", "22", "23", "24" };

	private List<Device> devices;
	private List<ControlDevice> allInstructions = new ArrayList<ControlDevice>();
	private DMF dmf;

	/**
	 * Creates a new Control Interface Function.
	 *
	 * @throws SQLException
	 */
	public CF() throws SQLException
	{
		dmf = new DMF();

		devices = dmf.getAllDevices();

		@SuppressWarnings("unused")
		JLabel userNameLabel = new JLabel("Username: ");
		@SuppressWarnings("unused")
		JLabel passwordLabel = new JLabel("Password: ");
		@SuppressWarnings("unused")
		JLabel searchLabel = new JLabel("Search: ");

		mainPanel.setBackground(Color.WHITE);

		// Main - North
		JLabel welcomeLabel = new JLabel("Control Interface", JLabel.CENTER);
		welcomeLabel.setFont(new Font("Serif", Font.BOLD, 48));

		mainPanel.add(welcomeLabel, BorderLayout.NORTH);

		// Main - Center
		// Graphs
		TemperatureGraph temperature = new TemperatureGraph();
		WindGraph windSpeed = new WindGraph();
		DeficitGraph deficit = new DeficitGraph();
		SolarRadiationGraph radiation = new SolarRadiationGraph();

		JPanel graphPanel = new JPanel(new GridLayout(2, 2));
		graphPanel.add(temperature.getPanel());
		graphPanel.add(windSpeed.getPanel());
		graphPanel.add(deficit.getPanel());
		graphPanel.add(radiation.getPanel());

		mainPanel.add(graphPanel, BorderLayout.CENTER);

		// Main - South (exclude east/west)
		JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 20, 20));
		buttonPanel.add(controlsButton);

		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		// Control - North
		JLabel instructionsLabel = new JLabel("Instruction Interface", JLabel.CENTER);
		instructionsLabel.setFont(new Font("Serif", Font.BOLD, 48));

		controlPanel.add(instructionsLabel, BorderLayout.NORTH);

		// Control - Center
		deviceOutput.append("\nDataBase List: --------------------\n");

		for (Device device : devices)
		{
			deviceOutput.append(device.getDeviceId() + ", " + device.getDeviceDescription() + ", " +
					device.getDeviceOwner() + ", " + device.getDeviceUsage() + ", " + device.getPriority() + "\n");
		}

		deviceOutput.setEditable(false);

		JScrollPane deviceOutputScroll = new JScrollPane(deviceOutput);
		deviceOutputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		deviceOutputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		displayInstructions(currentInstructions, allInstructions);

		currentInstructions.setEditable(false);

		JScrollPane currentInstructionsScroll = new JScrollPane(currentInstructions);
		currentInstructionsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		currentInstructionsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		JPanel centerControlPanel = new JPanel(new GridLayout(2, 0));
		centerControlPanel.add(deviceOutputScroll);
		centerControlPanel.add(currentInstructionsScroll);

		controlPanel.add(centerControlPanel, BorderLayout.CENTER);

		// Control - South
		ButtonGroup searchChoices = new ButtonGroup();
		searchChoices.add(RB_SEARCH_TYPE);
		searchChoices.add(RB_SEARCH_PR);
		RB_SEARCH_TYPE.setSelected(true);

		JPanel bottomControlPanel = new JPanel(new FlowLayout());
		bottomControlPanel.add(searchTextField);
		bottomControlPanel.add(searchButton);
		bottomControlPanel.add(RB_SEARCH_TYPE);
		bottomControlPanel.add(RB_SEARCH_PR);
		bottomControlPanel.add(addInstructionButton);
		bottomControlPanel.add(removeInstructionButton);
		bottomControlPanel.add(new JButton(new MainButtonAction("Return To Main Menu")));

		controlPanel.add(bottomControlPanel, BorderLayout.SOUTH);

		// Add - North
		JLabel addInstructionsLabel = new JLabel("Add Instructions", JLabel.CENTER);
		addInstructionsLabel.setFont(new Font("Serif", Font.BOLD, 48));

		addPanel.add(addInstructionsLabel, BorderLayout.NORTH);

		// Add - Center
		deviceInstructionsAdd.append("\n");
		deviceInstructionsAdd.append("Current Instructions: --------------------\n");

		if (!allInstructions.isEmpty())
		{
			for (@SuppressWarnings("unused") ControlDevice device : allInstructions)
			{
				deviceInstructionsAdd.append("");
			}
		}

		deviceInstructionsAdd.setEditable(false);

		JScrollPane deviceInstructionsAddScroll = new JScrollPane(deviceInstructionsAdd);
		deviceInstructionsAddScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		deviceInstructionsAddScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		addPanel.add(deviceInstructionsAddScroll, BorderLayout.CENTER);

		// Add - South
		JLabel timestampLabel = new JLabel("Select timeframe for instruction: ");

		timeframe = new JComboBox<String>(hours);
		ButtonGroup chooseType = new ButtonGroup();
		chooseType.add(RB_ADD_TYPE);
		chooseType.add(RB_ADD_PR);
		RB_ADD_TYPE.setSelected(true);

		JPanel bottomAddPanel = new JPanel(new FlowLayout());
		bottomAddPanel.add(timestampLabel);
		bottomAddPanel.add(timeframe);
		bottomAddPanel.add(new JLabel("Select Group: "));
		bottomAddPanel.add(newInstruction);
		bottomAddPanel.add(new JLabel("By: "));
		bottomAddPanel.add(RB_ADD_TYPE);
		bottomAddPanel.add(RB_ADD_PR);
		bottomAddPanel.add(addButton);
		bottomAddPanel.add(new JButton(new MainButtonAction("Return To Main Menu")));

		addPanel.add(bottomAddPanel, BorderLayout.SOUTH);

		// Remove - North
		JLabel removeInstructionsLabel = new JLabel("Remove Instructions", JLabel.CENTER);
		removeInstructionsLabel.setFont(new Font("Serif", Font.BOLD, 48));

		removePanel.add(removeInstructionsLabel, BorderLayout.NORTH);

		// Remove - Center
		deviceInstructionsRemove.setEditable(false);

		JScrollPane deviceInstructionsRemoveScroll = new JScrollPane(deviceInstructionsRemove);
		deviceInstructionsRemoveScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		deviceInstructionsRemoveScroll.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		displayInstructions(deviceInstructionsRemove, allInstructions);

		removePanel.add(deviceInstructionsRemoveScroll, BorderLayout.CENTER);

		// Remove - South
		JPanel bottomRemovePanel = new JPanel(new FlowLayout());
		bottomRemovePanel.add(new JLabel("Select id to remove: "));
		bottomRemovePanel.add(removeInstruction);
		bottomRemovePanel.add(removeButton);
		bottomRemovePanel.add(mainButton);

		removePanel.add(bottomRemovePanel, BorderLayout.SOUTH);

		// //////////////////////////////////////////////////////////////////////////////////////////
		controlsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				layout.show(pane, "Control");
			}
		});

		addInstructionButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				layout.show(pane, "Add");
			}
		});

		removeInstructionButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				layout.show(pane, "Remove");
			}
		});

		removeButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				removeDeviceList(allInstructions, Integer.parseInt(removeInstruction.getText().trim()));

				displayInstructions(deviceInstructionsAdd, allInstructions);
				displayInstructions(currentInstructions, allInstructions);
				displayInstructions(deviceInstructionsRemove, allInstructions);

				layout.show(pane, "Remove");
			}
		});
	}

	private class AddButtonAction extends AbstractAction
	{
		private static final long serialVersionUID = 7236351505483640063L;

		/**
		 * Creates a new AddButtonAction given the name of the button.
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
			if (RB_ADD_TYPE.isSelected())
			{
				try
				{
					if (!setDeviceList(newInstruction.getText().trim()).isEmpty())
					{
						allInstructions.add(new ControlDevice(false, "device",
								setDueDate((String) timeframe.getSelectedItem()),
								setDeviceList(newInstruction.getText().trim())));
					}
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
			}
			else if (RB_ADD_PR.isSelected())
			{
				boolean isInt = true;

				try
				{
					Integer.parseInt(newInstruction.getText().trim());
				}
				catch (NumberFormatException nfe)
				{
					isInt = false;
				}

				try
				{
					if (!setDeviceList(Integer.parseInt(newInstruction.getText().trim())).isEmpty() && isInt)
					{
						allInstructions.add(new ControlDevice(false, "device",
								setDueDate((String) timeframe.getSelectedItem()),
								setDeviceList(Integer.parseInt(newInstruction.getText().trim()))));
					}
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
			}

			displayInstructions(deviceInstructionsAdd, allInstructions);;
			displayInstructions(currentInstructions, allInstructions);
			displayInstructions(deviceInstructionsRemove, allInstructions);

			layout.show(pane, "Add");
		}
	}

	private class SearchButtonAction extends AbstractAction
	{
		private static final long serialVersionUID = 1367232521736658385L;

		/**
		 * Creates a new SearchButtonAction given the name of the button.
		 *
		 * @param name	name of button
		 */
		public SearchButtonAction(String name)
		{
			super(name);
		}

		@Override
		public void actionPerformed(ActionEvent actionEvent)
		{
			deviceOutput.setText("");

			if (!searchTextField.getText().trim().equals(""))
			{
				if (RB_SEARCH_TYPE.isSelected())
				{
					String description = searchTextField.getText().trim();

					deviceOutput.append("Results by type \n");

					for (Device device : devices)
					{
						if (description.equalsIgnoreCase(device.getDeviceDescription()))
						{
							deviceOutput.append(device.getDeviceId() + ", "
									+ device.getDeviceDescription() + ", "
									+ device.getDeviceOwner() + ", "
									+ device.getDeviceUsage() + ", "
									+ device.getPriority() + "\n");
						}
					}
				}
				else if (RB_SEARCH_PR.isSelected())
				{
					boolean isInt = true;

					try
					{
						Integer.parseInt(searchTextField.getText().trim());
					}
					catch (NumberFormatException nfe)
					{
						isInt = false;
					}

					if (isInt)
					{
						int priorityLevel = Integer.parseInt(searchTextField.getText().trim());

						deviceOutput.append("Results by priority \n");

						for (Device device : devices)
						{
							if (priorityLevel == device.getPriority())
							{
								deviceOutput.append(device.getDeviceId() + ", " + device.getDeviceDescription() +
										", " + device.getDeviceOwner() + ", " + device.getDeviceUsage() + ", " +
										device.getPriority() + "\n");
							}
						}
					}
					else
					{
						deviceOutput.append("All entries \n");

						for (Device device : devices)
						{
							deviceOutput.append(device.getDeviceId() + ", " + device.getDeviceDescription() +
									", " + device.getDeviceOwner() + ", " + device.getDeviceUsage() + ", " +
									device.getPriority() + "\n");
						}
					}
				}
			}
		}
	}

	private class MainButtonAction extends AbstractAction
	{
		private static final long serialVersionUID = 1971550846567015895L;

		/**
		 * Creates a new MainButtonAction given the name of the button.
		 *
		 * @param name	name of button
		 */
		public MainButtonAction(String name)
		{
			super(name);
		}

		@Override
		public void actionPerformed(ActionEvent actionEvent)
		{
			layout.show(pane, "Main");
		}
	}

	/**
	 * Returns the due date given the number of hours to add to the current time.
	 * <ul>
	 * 	<li>
	 * 		If the string argument is not a nonzero positive integer value, the due date defaults to an hour after
	 * 		the current time.
	 * 	</li>
	 * </ul>
	 *
	 * @param addHours	hours to add to the current time
	 * @return			the due date
	 */
	private String setDueDate(String addHours)
	{
		int hours = Integer.parseInt(addHours);

		if (hours < 1)
		{
			hours = 1;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, hours);

		SimpleDateFormat str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return str.format(cal.getTime());
	}

	/**
	 * Returns a list of devices with the given device type.
	 *
	 * @param type				device type
	 * @return					list of devices of the device type
	 * @throws SQLException
	 */
	private List<Device> setDeviceList(String type) throws SQLException
	{
		List<Device> devices = new ArrayList<Device>();

		for (Device device : dmf.getAllDevices())
		{
			if (device.getDeviceDescription().equalsIgnoreCase(type))
			{
				devices.add(device);
			}
		}

		return devices;
	}

	/**
	 * Returns a list of devices with priority level greater than or equal to the given priority level.
	 *
	 * @param priorityLevel		device priority level
	 * @return					list of devices with priority level greater than or equal to the priority level
	 * @throws SQLException
	 */
	private List<Device> setDeviceList(int priorityLevel) throws SQLException
	{
		List<Device> devices = new ArrayList<Device>();

		for (Device device : dmf.getAllDevices())
		{
			if (device.getPriority() >= priorityLevel)
			{
				devices.add(device);
			}
		}

		return devices;
	}

	/**
	 * Removes a control device given the index of the list of control devices.
	 * <ul>
	 * 	<li>
	 * 		If the integer argument is not between 0 and the list's (size - 1) inclusive, no control device is
	 * 		removed.
	 * 	</li>
	 * </ul>
	 *
	 * @param controlDevices	list of control devices
	 * @param index				index of device
	 */
	private void removeDeviceList(List<ControlDevice> controlDevices, int index)
	{
		if ((index > 0) || (index <= controlDevices.size()))
		{
			int device = 0;

			for (Iterator<ControlDevice> iter = controlDevices.iterator(); iter.hasNext();)
			{
				@SuppressWarnings("unused")
				ControlDevice controlDevice = iter.next();

				if (device == index)
				{
					iter.remove();
					break;
				}

				device++;
			}
		}
	}

	/**
	 * Adds a list of control devices and their devices' information given the text area and the list of control
	 * devices.
	 *
	 * @param instructions		instructions texts
	 * @param controlDevices	list of control devices
	 */
	private void displayInstructions(JTextArea instructions, List<ControlDevice> controlDevices)
	{
		instructions.setText("");
		instructions.append("\nCurrent Instructions: --------------------\n");

		if (!controlDevices.isEmpty())
		{
			for (ControlDevice controlDevice : controlDevices)
			{
				instructions.append("#: " + controlDevices.indexOf(controlDevice) + "\n");
				instructions.append("Type: " + controlDevice.getType() + " ");

				if (controlDevice.isSwtich())
				{
					instructions.append("Switch is: on ");
				}
				else
				{
					instructions.append("Switch is: off ");
				}

				instructions.append("Due Date: " + controlDevice.getDueDate() + "\n");

				for (Device device : controlDevice.getControlDevices())
				{
					instructions.append(device.getDeviceId() + ", " + device.getDeviceDescription() +
							", " + device.getDeviceOwner() + ", " + device.getDeviceUsage() + ", " +
							device.getPriority() + "\n");
				}

				instructions.append("\n");
			}
		}
	}

	/**
	 * Displays the Control Interface.
	 */
	public void displayFrame()
	{
		JFrame frame = new JFrame("Control Panel");
		frame.setLayout(layout);

		pane = frame.getContentPane();
		pane.add("Main", mainPanel);
		pane.add("Control", controlPanel);
		pane.add("Add", addPanel);
		pane.add("Remove", removePanel);

		frame.pack();
		frame.setSize(new Dimension(960, 640));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public static void main(String[] arg) throws SQLException
	{
		CF cf = new CF();
		cf.displayFrame();
	}

	public static void mainDemo() throws SQLException
	{
		CF cf = new CF();
		cf.displayFrame();

		System.out.println("\nReturning to main menu...\n");
	}
}