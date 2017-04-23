package functions.rf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import types.Device;

/**
 * @author Tony Liang
 *
 */
public class ResponsePackage
{
	int[] devicesPercentages;
	List<Device> devices;

	/**
	 * Creates a new ResponsePackage given the list of devices and the integer array of each device's power in
	 * percentage.
	 *
	 * @param devices				list of devices
	 * @param devicePercentages		array of each device's power in percentage
	 */
	public ResponsePackage(List<Device> devices, int[] devicePercentages)
	{
		this.devices = devices;
		this.devicesPercentages = devicePercentages;
	}

	/**
	 * Sends the response package to the Control Interface Function.
	 */
	public void sendsToCIF()
	{
	}

	public List<Device> getDevices()
	{
		return new ArrayList<Device>(devices);
	}

	public int[] getDevicesPercentages()
	{
		return Arrays.copyOf(devicesPercentages, devicesPercentages.length);
	}
}