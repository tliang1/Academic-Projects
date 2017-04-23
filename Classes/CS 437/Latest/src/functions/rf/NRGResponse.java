package functions.rf;

import java.sql.SQLException;
import java.util.Random;

import functions.dmf.DMF;

/**
 * @author Tony Liang
 *
 */
public class NRGResponse
{
	public static void mainDemo() throws SQLException
	{
		DMF dmf = new DMF();

	    ResponseFunction rf = new ResponseFunction(dmf);

	    dmf.setDeficit(randomCurrentDeficit(rf.totalDeviceUsage()));

	    new DevicesTable(rf, dmf);
	}

	/**
	 * Returns a random current deficit between 0 and the sum of every device's usage inclusive given the sum of
	 * every device's usage.
	 *
	 * @param totalDevicesUsages	the sum of every device's usage
	 * @return						a random current deficit
	 */
	private static int randomCurrentDeficit(int totalDevicesUsages)
	{
		Random random = new Random();
		int currentDeficit = random.nextInt(totalDevicesUsages + 1);

		return currentDeficit;
	}
}