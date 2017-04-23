package types;

/**
 * @author Michael Holloway, William Klein
 *
 */
public class Device
{
	private int id;
	private int usage;
	private int priority;
	private String description;
	private String owner;

	private final int PRIORITY_LEVELS = 10;

	/**
	 * Creates a new Device.
	 * <p>
	 * The device's id, usage, and priority level are randomly generated. The device's description defaults to
	 * "Appliance" and its owner defaults to "Someone".
	 */
	public Device()
	{
		id = (int) (Math.random() * 1000000);
		description = "Appliance";
		owner = "Someone";
		usage = (int) (Math.random() * 500);
		priority = (int) (Math.random() * PRIORITY_LEVELS);
	}

	/**
	 * Creates a new Device given the device's id, description, owner, usage, and priority level.
	 * <ul>
	 * 	<li>
	 * 		If the device's id is negative, then the device's id is randomly generated.
	 * 	</li>
	 * 	<li>
	 * 		If the device's usage is negative, then the device's usage defaults to 0.
	 * 	</li>
	 * 	<li>
	 * 		If the device's priority level is negative or invalid, then the device's priority level is randomly
	 * 		generated.
	 * 	</li>
	 * </ul>
	 *
	 * @param id			device's id
	 * @param description	device's description
	 * @param owner			device's owner
	 * @param usage			device's usage
	 * @param priority		device's priority level
	 */
	public Device(int id, String description, String owner, int usage, int priority)
	{
		this.id = (id >= 0) ? id : (int) (Math.random() * 1000000);
		this.description = description;
		this.owner = owner;
		this.usage = (usage >= 0) ? usage : 0;
		this.priority = priority;

		if ((priority < 0) || (priority >= PRIORITY_LEVELS))
		{
			this.priority = (int) (Math.random() * PRIORITY_LEVELS);
		}
	}

	/**
	 * Creates a new Device given the device's usage and another Device object.
	 * <ul>
	 * 	<li>
	 * 		If the device's usage is negative, then the device's usage defaults to 0.
	 * 	</li>
	 * </ul>
	 *
	 * @param usage		device's usage
	 * @param device	a device
	 */
	public Device(int usage, Device device)
	{
		id = device.getDeviceId();
		description = device.getDeviceDescription();
		owner = device.getDeviceOwner();
		this.usage = (usage >= 0) ? usage : 0;
		priority = device.getPriority();
	}

	public int getDeviceId()
	{
		return id;
	}

	/**
	 * Sets the device's id.
	 * <ul>
	 * 	<li>
	 * 		If the integer argument is negative, then the device's id is unchanged.
	 * 	</li>
	 * </ul>
	 *
	 * @param id	device's id
	 */
	public void setDeviceId(int id)
	{
		this.id = (id >= 0) ? id : this.id;
	}

	public String getDeviceDescription()
	{
		return description;
	}

	public void setDeviceDescription(String description)
	{
		this.description = description;
	}

	public String getDeviceOwner()
	{
		return owner;
	}

	public void setDeviceOwner(String owner)
	{
		this.owner = owner;
	}

	public int getDeviceUsage()
	{
		return usage;
	}

	/**
	 * Sets the device's usage.
	 * <ul>
	 * 	<li>
	 * 		If the integer argument is negative, then the device's usage is unchanged.
	 * 	</li>
	 * </ul>
	 *
	 * @param usage		device's usage
	 */
	public void setDeviceUsage(int usage)
	{
		this.usage = (usage >= 0) ? usage : this.usage;
	}

	public int getPriority()
	{
		return priority;
	}

	/**
	 * Sets the device's priority level.
	 * <ul>
	 * 	<li>
	 * 		If the integer argument is negative or invalid, then the device's priority level is unchanged.
	 * 	</li>
	 * </ul>
	 *
	 * @param priority	device's priority level
	 */
	public void setPriority(int priority)
	{
		this.priority = ((priority >= 0) && (priority < PRIORITY_LEVELS)) ? priority : this.priority;
	}

	public String toSqlEntry()
	{
		return "Insert Into Devices Values(" + id + ", '" + description + "', '" + owner + "', " + usage + ", " +
				priority + ");";
	}

	@Override
	public String toString()
	{
		return "Device ID: " + id + ", Description: " + description + ", Owner: " + owner + ", Usage: " + usage +
				", Priority: " + priority;
	}
}