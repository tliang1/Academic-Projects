package functions.cf;

import java.util.List;

import types.Device;

/**
 * @author Laura Mann
 *
 */
public class ControlDevice
{
	private boolean swtich;
	private String dueDate;
	private String type;
	private List<Device> controlDevices;

	public ControlDevice()
	{
	}

	/**
	 * Creates a new ControlDevice given the switch, type, due date, and list of devices.
	 *
	 * @param swtich	switch
	 * @param type		type
	 * @param dueDate	due date
	 * @param devices	list of devices
	 */
	public ControlDevice(boolean swtich, String type, String dueDate, List<Device> devices)
	{
		controlDevices = devices;
		this.swtich = swtich;
		this.dueDate = dueDate;
		this.type = type;
	}

	public boolean isSwtich()
	{
		return swtich;
	}

	public void setSwtich(boolean swtich)
	{
		this.swtich = swtich;
	}

	public String getDueDate()
	{
		return dueDate;
	}

	public void setDueDate(String dueDate)
	{
		this.dueDate = dueDate;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public List<Device> getControlDevices()
	{
		return controlDevices;
	}

	public void setControlDevices(List<Device> controlDevices)
	{
		this.controlDevices = controlDevices;
	}
}