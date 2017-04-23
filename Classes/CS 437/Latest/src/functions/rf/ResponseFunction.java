package functions.rf;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import functions.dmf.DMF;
import types.Device;
import types.WeatherData;

/**
 * @author Tony Liang
 *
 */
public class ResponseFunction
{
	private final int PRIORITY_LEVELS = 10;
	private List<Device> originalDevices;

	/**
	 * Arbitrary distribution of power in percentage based on the number of priority levels in decreasing order
	 * (e.g., if the number of priority levels is 3, 10% for priority level 2, 30% for priority level 1, and 60%
	 * for priority level 0).
	 * <ul>
	 * 	<li>
	 * 		Only used for 1 - 10 priority levels.
	 * 	</li>
	 * </ul>
	 */
	private List<List<Integer>> adjustmentList = Arrays.asList(Arrays.asList(100), Arrays.asList(40, 60),
			Arrays.asList(10, 30, 60), Arrays.asList(10, 20, 30, 40), Arrays.asList(9, 10, 18, 27, 36),
			Arrays.asList(6, 10, 12, 18, 24, 30), Arrays.asList(4, 8, 12, 16, 16, 20, 24),
			Arrays.asList(3, 6, 9, 12, 15, 16, 18, 21), Arrays.asList(2, 5, 7, 10, 12, 12, 15, 17, 20),
			Arrays.asList(2, 4, 6, 8, 10, 10, 12, 14, 16, 18));

	private DMF dmf;

	/**
	 * Creates a new ResponseFunction given the Data Management Function.
	 *
	 * @param dmf				Data Management Function
	 * @throws SQLException
	 */
	public ResponseFunction(DMF dmf) throws SQLException
	{
		this.dmf = dmf;
		originalDevices = importanceSort();
	}

	public List<Device> getOriginalDevices()
	{
		return new ArrayList<Device>(originalDevices);
	}

	/**
	 * Returns the list of devices sorted by highest priority level (0) to lowest priority level (9).
	 *
	 * @return					the list of devices sorted by highest priority level to lowest priority level
	 * @throws SQLException
	 */
	public List<Device> importanceSort() throws SQLException
	{
		List<Device> sortedDevices = new ArrayList<Device>();

		for (int priorityLevel = 0; priorityLevel < PRIORITY_LEVELS; priorityLevel++)
		{
			for (Device device : dmf.getAllDevices())
			{
				if (device.getPriority() == priorityLevel)
				{
					sortedDevices.add(device);
				}
			}
		}

		return sortedDevices;
	}

	/**
	 * Returns the list of devices with their power usages updated after removing the current deficit.
	 *
	 * @return					list of updated devices
	 * @throws SQLException
	 */
	public List<Device> powerConsumption() throws SQLException
	{
		List<Device> adjustedDevices = importanceSort();

		if (dmf.getDeficit() != 0)
		{
			// Grid surplus
			if (dmf.getDeficit() < 0)
			{
				int remainingCurrentGridSurplus = dmf.getDeficit();

				// Calculates the distribution of power percentage usage for each priority level
				int[] distribution = distribution(totalPriorityLevels());

				// Gets the number of devices in each priority level
				int[] totalPrioritiesDevices = numOfDevicesForEachPriorityLevel();
				int deviceIndex = 0;
				int priorityLevel = 0;

				for (int index = 0; index < distribution.length; index++, priorityLevel++)
				{
					// Skips priority levels that have no devices
					while ((priorityLevel < PRIORITY_LEVELS) && (totalPrioritiesDevices[priorityLevel] == 0))
					{
						priorityLevel++;
					}

					if ((priorityLevel >= PRIORITY_LEVELS) || (remainingCurrentGridSurplus >= 0))
					{
						break;
					}

					// Surplus to add for the current priority level.
					int surplusToAdd = (int) (-remainingCurrentGridSurplus * distribution[index] * 0.01);

					if ((remainingCurrentGridSurplus < 0) && (surplusToAdd < 1))
					{
						surplusToAdd = remainingCurrentGridSurplus;
					}

					int surplus = 0;

					/* Decreases the surplus by increasing the devices' usages to the maximum. Devices with usages
					 * already maximized are skipped.
					 */
					for (int device = deviceIndex, devicesMaxedOutUsage = 0; (surplus < surplusToAdd) &&
							(devicesMaxedOutUsage < totalPrioritiesDevices[priorityLevel]); device++)
					{
						if (device == (deviceIndex + totalPrioritiesDevices[priorityLevel]))
						{
							device = deviceIndex;
						}

						if (originalDevices.get(device).getDeviceUsage() * 2 >
							adjustedDevices.get(device).getDeviceUsage())
						{
							adjustedDevices.get(device).setDeviceUsage(adjustedDevices.get(device).
									getDeviceUsage() + 1);
							surplus++;
						}
						else
						{
							devicesMaxedOutUsage++;
						}
					}

					deviceIndex += totalPrioritiesDevices[priorityLevel];
					remainingCurrentGridSurplus += surplus;
				}

				for (Device device : adjustedDevices)
				{
					dmf.modifyDevice(device);
				}
			}
			// Grid deficit
			else
			{
				if (dmf.getDeficit() >= totalDeviceUsage())
				{
					for (Device device : adjustedDevices)
					{
						device.setDeviceUsage(0);

						dmf.modifyDevice(device);
					}
				}
				else
				{
					int remainingCurrentGridDeficit = dmf.getDeficit();

					// Calculates the distribution of power percentage usage for each priority level
					int[] distribution = distribution(totalPriorityLevels());

					// Group all devices by priority level
					List<List<Device>> devicesByPriorityList = groupDevicesByPriority();

					// Gets the number of devices in each priority level
					int[] totalPrioritiesDevices = numOfDevicesForEachPriorityLevel();
					int priorityLevel = (totalPrioritiesDevices.length - 1);

					for (int index = (distribution.length - 1); (index >= 0) && (priorityLevel >= 0); index--,
							priorityLevel--)
					{
						// Skips priority levels that have no devices
						while ((priorityLevel >= 0) && (totalPrioritiesDevices[priorityLevel] == 0))
						{
							priorityLevel--;
						}

						if ((priorityLevel < 0) || (remainingCurrentGridDeficit < 1))
						{
							break;
						}

						// Deficit to remove for the current priority level.
						int deficitToRemove = (int) (remainingCurrentGridDeficit * (100 - distribution[index]) *
								0.01);

						if ((remainingCurrentGridDeficit > 0) && (deficitToRemove < 1))
						{
							deficitToRemove = remainingCurrentGridDeficit;
						}

						int deficitRemoved = 0;

						/* Decreases the deficit by decreasing the devices' usages to a minimum of 10 to keep all
						 * devices on. Devices with usages less than or equal to 10 are skipped.
						 */
						for (int device = devicesByPriorityList.get(priorityLevel).size() - 1; device >= 0;
								device--)
						{
							if (devicesByPriorityList.get(priorityLevel).get(device).getDeviceUsage() > 10)
							{
								int usageRemoved = devicesByPriorityList.get(priorityLevel).get(device).
										getDeviceUsage() - 10;

								if ((deficitRemoved + usageRemoved) < deficitToRemove)
								{
									devicesByPriorityList.get(priorityLevel).get(device).setDeviceUsage(10);
									deficitRemoved += usageRemoved;
								}
								else
								{
									devicesByPriorityList.get(priorityLevel).get(device).
									setDeviceUsage(devicesByPriorityList.get(priorityLevel).get(device).
											getDeviceUsage() - (deficitToRemove - deficitRemoved));
									deficitRemoved = deficitToRemove;
									break;
								}
							}
						}

						remainingCurrentGridDeficit -= deficitRemoved;
					}

					/*
					 * If there is still remaining current grid deficit, decrease the deficit. Some devices will
					 * have to shutdown (device's usage becomes 0)
					 */
					if (remainingCurrentGridDeficit > 0)
					{
						for (int priorityLvl = devicesByPriorityList.size() - 1; priorityLvl >= 0; priorityLvl--)
						{
							if (!devicesByPriorityList.get(priorityLvl).isEmpty())
							{
								int device = devicesByPriorityList.get(priorityLvl).size() - 1;

								while (device >= 0)
								{
									if (devicesByPriorityList.get(priorityLvl).get(device).getDeviceUsage() > 0)
									{
										// Decreases the deficit
										if (devicesByPriorityList.get(priorityLvl).get(device).getDeviceUsage() >=
												remainingCurrentGridDeficit)
										{
											devicesByPriorityList.get(priorityLvl).get(device).
												setDeviceUsage(devicesByPriorityList.get(priorityLvl).get(device).
													getDeviceUsage() - remainingCurrentGridDeficit);
											remainingCurrentGridDeficit = 0;
											break;
										}
										else
										{
											remainingCurrentGridDeficit -= devicesByPriorityList.get(priorityLvl).
													get(device).getDeviceUsage();
											devicesByPriorityList.get(priorityLvl).get(device).setDeviceUsage(0);
										}
									}

									device--;
								}
							}

							if (remainingCurrentGridDeficit == 0)
							{
								break;
							}
						}
					}

					adjustedDevices = new ArrayList<Device>();

					// Replaces the old devices with the newly updated devices
					for (List<Device> devicesList : devicesByPriorityList)
					{
						if (!devicesList.isEmpty())
						{
							for (Device device : devicesList)
							{
								adjustedDevices.add(device);

								// Updates device usage in database
								dmf.modifyDevice(device);
							}
						}
					}
				}
			}
		}

		return adjustedDevices;
	}

	/**
	 * Returns the sum of every device's usage.
	 *
	 * @return					the sum of every device's usage
	 * @throws SQLException
	 */
	public int totalDeviceUsage() throws SQLException
	{
		int totalUsage = 0;

		for (Device device : dmf.getAllDevices())
		{
			totalUsage += device.getDeviceUsage();
		}

		return totalUsage;
	}

	/**
	 * Returns the number of priority levels with at least one device.
	 *
	 * @return					the number of priority levels with at least one device
	 * @throws SQLException
	 */
	public int totalPriorityLevels() throws SQLException
	{
		int totalPriorities = 0;
		int priorityLevel = 0;

		for (Device device : importanceSort())
		{
			if (device.getPriority() >= priorityLevel)
			{
				if (device.getPriority() != priorityLevel)
				{
					priorityLevel = device.getPriority();
				}

				priorityLevel++;
				totalPriorities++;
			}
		}

		return totalPriorities;
	}

	/**
	 * Returns the integer array of percentages for all priority levels between 0 and (the total priority levels
	 * - 1).
	 * <ul>
	 * 	<li>
	 * 		If the integer argument is less than 1 or greater than 10, the array will default all priority level's
	 * 		percentage to 0 percent.
	 * 	</li>
	 * </ul>
	 *
	 * @param totalPriorities	number of priority levels
	 * @return					the integer array of percentages
	 */
	private int[] distribution(int totalPriorities)
	{
		int[] distribution = new int[totalPriorities];

		if ((totalPriorities > 0) && (totalPriorities <= PRIORITY_LEVELS))
		{
			int index = distribution.length - 1;

			for (double adjustment : adjustmentList.get(totalPriorities - 1))
			{
				distribution[index] = (int) adjustment;

				index--;

				if (index < 0)
				{
					break;
				}
			}
		}

		return distribution;
	}

	/**
	 * Returns the list of lists of devices group by highest priority level (0) to lowest priority level (9).
	 *
	 * @return					list of lists of devices group by highest priority level to lowest priority level
	 * @throws SQLException
	 */
	public List<List<Device>> groupDevicesByPriority() throws SQLException
	{
		List<List<Device>> devicesByPriority = new ArrayList<List<Device>>();

		for (int priority = 0; priority < PRIORITY_LEVELS; priority++)
		{
			devicesByPriority.add(new ArrayList<Device>());
		}

		int priorityLevel = 0;

		for (Device device : importanceSort())
		{
			if (device.getPriority() >= priorityLevel)
			{
				if (device.getPriority() > priorityLevel)
				{
					priorityLevel = device.getPriority();
				}

				devicesByPriority.get(priorityLevel).add(device);
			}
		}

		return devicesByPriority;
	}

	/**
	 * Returns the array of the number of devices for each priority level.
	 *
	 * @return					the array of the number of devices for each priority level
	 * @throws SQLException
	 */
	public int[] numOfDevicesForEachPriorityLevel() throws SQLException
	{
		int priorityLevel = 0;
		int[] numOfDevicesWithPriority = new int[PRIORITY_LEVELS];

		for (Device device : importanceSort())
		{
			if (!(device.getPriority() == priorityLevel))
			{
				priorityLevel = device.getPriority();
			}

			numOfDevicesWithPriority[priorityLevel]++;
		}

		return numOfDevicesWithPriority;
	}

	/**
	 * Updates priority levels for each device given the weather data.
	 *
	 * @param weatherData		weather data
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	public void adjustRanking(WeatherData weatherData) throws SQLException
	{
		for (Device device : importanceSort())
		{
			// Analyzes weather data and sets the device's priority level
			//device.setPriority(// Determine a value);
			//dmf.modifyDevice(device);
		}
	}

	/**
	 * Returns the response package of the list of devices.
	 *
	 * @return					the response package
	 * @throws SQLException
	 */
	public ResponsePackage responsePackage() throws SQLException
	{
		return new ResponsePackage(importanceSort(), wattsToPercent());
	}

	/**
	 * Returns the array of each device's power in percentage.
	 *
	 * @return					array of each device's power in percentage
	 * @throws SQLException
	 */
	public int[] wattsToPercent() throws SQLException
	{
		List<Device> sortedDevices = importanceSort();
		int[] devicesPercentages = new int[originalDevices.size()];

		for (int device = 0; device < originalDevices.size(); device++)
		{
			int percent =  (int) (Math.floor(((double) (sortedDevices.get(device).getDeviceUsage()) /
					(double) (originalDevices.get(device).getDeviceUsage())) * 100.0));
			devicesPercentages[device] = percent;
		}

		return devicesPercentages;
	}

	/**
	 * Sends wireless signal to the given device.
	 *
	 * @param device	a device
	 */
	@SuppressWarnings("unused")
	public void sendsWirelessSignal(Device device)
	{
		int deviceUsage = device.getDeviceUsage();

		// Sends the device usage value to the device
	}

	/**
	 * Receives instructions from the Control Interface Function.
	 *
	 * @param instructions	instructions
	 */
	public void receivesInstruction(Instruction instructions)
	{
		// If an instruction requires devices to turn on
		/*
		 * Gets the list of devices from the Instruction object and all values each device demands.
		 *
		 * Puts the values into a integer array of size = the total devices that need to be turn on.
		 *
		 * turnOn(devices, devicesUsages);
		 */

		// If an instruction requires devices to turn off
		/*
		 * Gets the list of devices from the Instruction object that needs to be turn off.
		 *
		 * turnOff(devices);
		 */
	}

	/**
	 * Turns on all devices given the list of devices and the integer array of each device's power usage.
	 * <ul>
	 * 	<li>
	 * 		If the integer array argument's size is less than the list argument's size, no devices will turn on.
	 * 	</li>
	 * </ul>
	 *
	 * @param devices			list of devices
	 * @param devicesUsages		array of each device's power usage
	 * @throws SQLException
	 */
	public void turnOn(List<Device> devices, int[] devicesUsages) throws SQLException
	{
		if (devicesUsages.length >= devices.size())
		{
			for (int device = 0; device < devices.size(); device++)
			{
				devices.get(device).setDeviceUsage(devicesUsages[device]);

				dmf.modifyDevice(devices.get(device));
			}
		}
	}

	/**
	 * Turns off all devices given the list of devices.
	 *
	 * @param devices			list of devices
	 * @throws SQLException
	 */
	public void turnOff(List<Device> devices) throws SQLException
	{
		for (Device device : devices)
		{
			device.setDeviceUsage(0);

			dmf.modifyDevice(device);
		}
	}
}