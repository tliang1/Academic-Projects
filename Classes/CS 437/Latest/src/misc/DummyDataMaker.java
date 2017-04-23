package misc;

import java.util.ArrayList;
import java.util.List;

import types.Device;

/**
 * @author William Klein
 *
 */
public class DummyDataMaker
{
	private List<Device> devices = new ArrayList<Device>();

	/**
	 * Creates a new DummyDataMaker given the number of entries.
	 * <ul>
	 * 	<li>
	 * 		If the integer argument is less than 1, the number of entries defaults to 20.
	 * 	</li>
	 * </ul>
	 *
	 * @param entries	number of entries
	 */
	public DummyDataMaker(int entries)
	{
		int total = (entries < 1) ? 20 : entries;

		for (int entry = 0; entry < total; entry++)
		{
			devices.add(new Device());
		}
	}

	public List<Device> getDevices()
	{
		return new ArrayList<Device>(devices);
	}
}