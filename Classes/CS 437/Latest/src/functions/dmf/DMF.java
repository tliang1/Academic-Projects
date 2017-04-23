package functions.dmf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import types.Device;
import types.GridData;
import types.WeatherData;

/**
 * @author William Klein
 *
 */
public class DMF
{
	private String url;
	private String username;
	private String password;
	private Connection connection;
	private int currentGridDeficit;

	/**
	 * Creates a new Data Management Function.
	 */
	public DMF()
	{
		url = "jdbc:mysql://localhost:3306/nrg";
		username = "root";
		password = "";

		try
		{
			connection = DriverManager.getConnection(url, username, password);
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}

	/**
	 * Closes the connection to the database.
	 *
	 * @throws SQLException
	 */
	public void close() throws SQLException
	{
		connection.close();
	}

	/**
	 * Returns a list of all weather data in the database.
	 *
	 * @return					list of all weather data
	 * @throws SQLException
	 */
	public List<WeatherData> getAllWeatherData() throws SQLException
	{
		String query = "Select * from WeatherData";
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		rs.next();

		List<WeatherData> resultData = new ArrayList<WeatherData>();

		while (!rs.isAfterLast())
		{
			resultData.add(new WeatherData(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
					rs.getInt(5), rs.getInt(6)));
			rs.next();
		}

		return resultData;
	}

	/**
	 * Returns a list of all grid data in the database.
	 *
	 * @return					list of all grid data
	 * @throws SQLException
	 */
	public List<GridData> getAllGridData() throws SQLException
	{
		String query = "Select * from GridData";
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		rs.next();

		List<GridData> resultData = new ArrayList<GridData>();

		while (!rs.isAfterLast())
		{
			resultData.add(new GridData(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
			rs.next();
		}

		return resultData;
	}

	/**
	 * Returns a list of all devices in the database.
	 *
	 * @return					list of all devices
	 * @throws SQLException
	 */
	public List<Device> getAllDevices() throws SQLException
	{
		String query = "Select * from Devices";
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		rs.next();

		List<Device> resultData = new ArrayList<Device>();

		while (!rs.isAfterLast())
		{
			resultData.add(new Device(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
			rs.next();
		}

		return resultData;
	}

	/**
	 * Updates changes to the given device's data into the database.
	 *
	 * @param device			a device
	 * @throws SQLException
	 */
	public void modifyDevice(Device device) throws SQLException
	{
		String query = "Delete from Devices where DeviceID = " + device.getDeviceId();
		String query2 = "Insert into Devices values(" + device.getDeviceId() + ", '" +
				device.getDeviceDescription() + "', '" + device.getDeviceOwner() + "', " +
				device.getDeviceUsage() + ", " + device.getPriority() + ");";

		Statement stmt = connection.createStatement();
		stmt.execute(query);
		stmt.execute(query2);
	}

	/**
	 * Inserts the given weather data into the database.
	 *
	 * @param weatherData		weather data
	 * @throws SQLException
	 */
	public void insertWeatherData(WeatherData weatherData) throws SQLException
	{
		String query = "Insert into WeatherData values(" + weatherData.getLocality() + ", " +
				weatherData.getTimestamp() + ", " + weatherData.getTemperature() + ", " +
				weatherData.getWindSpeed() + ", " + weatherData.getWindDirection() + ", " +
				weatherData.getSolarRadiation() + ");";

		System.out.println(query);

		Statement stmt = connection.createStatement();
		stmt.execute(query);
	}

	/**
	 * Inserts the given grid data into the database.
	 *
	 * @param gridData			grid data
	 * @throws SQLException
	 */
	public void insertGridData(GridData gridData) throws SQLException
	{
		String query = "Insert into GridData values(\"" + gridData.getLocality() + "\", \"" +
				gridData.getTimestamp() + "\", " + gridData.getCapacity() + ", " + gridData.getDemand() + ");";

		System.out.println(query);

		Statement stmt = connection.createStatement();
		stmt.execute(query);
	}

	/**
	 * Inserts the given device into the database.
	 *
	 * @param device			the device
	 * @throws SQLException
	 */
	public void insertDevice(Device device) throws SQLException
	{
		String query = "Insert into Devices values(" + device.getDeviceId() + ", '" +
				device.getDeviceDescription() + "', '" + device.getDeviceOwner() + "', " +
				device.getDeviceUsage() + ", " + device.getPriority() + ");";

		System.out.println(query);

		Statement stmt = connection.createStatement();
		stmt.execute(query);
	}

	public String insertDeviceExample(Device device)
	{
		return "Insert into Devices values(" + device.getDeviceId() + ", " + device.getDeviceDescription() +
				", " + device.getDeviceOwner() + ", " + device.getDeviceUsage() + ", " + device.getPriority() +
				");";
	}

	/**
	 * Deletes all weather data entries prior to the given time in milliseconds.
	 *
	 * @param time				time in milliseconds
	 * @throws SQLException
	 */
	public void purgeOldWeatherData(long time) throws SQLException
	{
		String query = "Delete from WeatherData where WeatherTimeStamp < '" + milliToTimeStamp(time) + "';";
		Statement stmt = connection.createStatement();
		stmt.execute(query);
	}

	public String examplePurgeWeather(long time)
	{
		return "Delete from WeatherData where WeatherTimeStamp < '" + milliToTimeStamp(time) + "';";
	}

	/**
	 * Deletes all grid data entries prior to the given time in milliseconds.
	 *
	 * @param time				time in milliseconds
	 * @throws SQLException
	 */
	public void purgeOldGridData(long time) throws SQLException
	{
		String query = "Delete from GridData where GridTimeStamp < '" + milliToTimeStamp(time) + "';";
		Statement stmt = connection.createStatement();
		stmt.execute(query);
	}

	/**
	 * Deletes all device entries.
	 *
	 * @throws SQLException
	 */
	public void purgeOldDevices() throws SQLException
	{
		String query = "Truncate Table Devices;";
		Statement stmt = connection.createStatement();
		stmt.execute(query);
	}

	public int getDeficit()
	{
		return currentGridDeficit;
	}

	public void setDeficit(int deficit)
	{
		currentGridDeficit = deficit;
	}

	public static void mainDemo()
	{
		System.out.println(milliToTimeStamp(System.currentTimeMillis()));

		try
		{
			DMF dmf = new DMF();
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);

			sc.nextLine();

			System.out.println("Example device insert\n\n");
			System.out.println(dmf.insertDeviceExample(new Device()));

			sc.nextLine();

			System.out.println("Example purge old data statement\n\n");
			System.out.println(dmf.examplePurgeWeather(System.currentTimeMillis()));

			sc.nextLine();

			System.out.println("Get a list of all devices from the database");

			sc.nextLine();

			List<Device> devicesList = dmf.getAllDevices();

			for (Device device: devicesList)
			{
				System.out.println(device.toString());
			}

			sc.nextLine();

			System.out.println("\n\n\nGet all of the grid data from the database");

			sc.nextLine();

			List<GridData> gridDataList = dmf.getAllGridData();

			for (GridData gridData: gridDataList)
			{
				System.out.println(gridData.toString());
			}

			sc.nextLine();

			System.out.println("\n\n\nGet all of the weather data from the database");

			sc.nextLine();

			List<WeatherData> weatherDataList = dmf.getAllWeatherData();

			for (WeatherData weatherData: weatherDataList)
			{
				System.out.println(weatherData.toString());
			}

			System.out.println("\n\nPress Enter to return.");

			sc.nextLine();

			dmf.close();
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}

	/**
	 * Returns the timestamp in GMT-8 given the time in milliseconds.
	 * <ul>
	 * 	<li>
	 * 		If the time is negative, the timestamp defaults to "1970-01-01 00:00:00".
	 * 	</li>
	 * </ul>
	 *
	 * @param mils	milliseconds
	 * @return		timestamp
	 */
	public static String milliToTimeStamp(long mils)
	{
		if (mils < 0)
		{
			return "1970-01-01 00:00:00";
		}

		long time = mils - (60 * 60 * 1000 * 8);
		int sec, min, hour, day, month, year;

		sec = (int) ((time / 1000) % 60);
		time /= 60000;

		min = (int) (time % 60);
		time /= 60;

		hour = (int) (time % 24);
		time /= 24;

		day = (int) (time % 365);
		year = (int) (time / 365);

		day -= (year - 2) / 4;

		int[] monthLength = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

		if ((year % 4) == 2)
		{
			monthLength[1] = 29;
		}

		month = 1;

		while (day > monthLength[month - 1])
		{
			day -= monthLength[month - 1];
			month++;
		}

		year += 1970;

		String timestamp = year + "-";

		if (month < 10)
		{
			timestamp += "0";
		}

		timestamp += month + "-";

		if (day < 10)
		{
			timestamp += "0";
		}

		timestamp += day + " ";

		if (hour < 10)
		{
			timestamp += "0";
		}

		timestamp += hour + ":";

		if (min < 10)
		{
			timestamp += "0";
		}

		timestamp += min + ":";

		if (sec < 10)
		{
			timestamp += "0";
		}

		timestamp += sec + "";

		return timestamp;
	}
}