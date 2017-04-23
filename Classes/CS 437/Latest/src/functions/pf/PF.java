package functions.pf;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import functions.dmf.DMF;
import types.GridData;
import types.WeatherData;

/**
 * @author Michael Holloway
 *
 */
public class PF
{
	private DMF _dmf;

	/**
	 * Creates a new Predictive Function.
	 */
	public PF()
	{
		_dmf = new DMF();
	}

	/**
	 * Sends weather data to the Data Management Function given the weather data.
	 *
	 * @param weatherData	weather data
	 */
	public void sendWeatherDataToDMF(WeatherData weatherData)
	{
		try
		{
			System.out.println("Sending weather data to DMF...");

			_dmf.insertWeatherData(weatherData);

			System.out.println("Weather data successfully sent.");
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}

	/**
	 * Sends grid data to the Data Management Function given the grid data.
	 *
	 * @param gridData	grid data
	 */
	public void sendGridDataToDMF(GridData gridData)
	{
		try
		{
			System.out.println("Sending grid data to DMF...");

			_dmf.insertGridData(gridData);

			System.out.println("Grid data successfully sent.");
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}

	/**
	 *	Calculates and sends grid deficit prediction to the Data Management Function.
	 */
	public void sendGridDeficitPredictionToDMF()
	{
		try
		{
			Date started = new Date();

			System.out.println("Time started: " + started.toString());
			System.out.println();
			System.out.println("Pulling historical data from DMF...");
			System.out.println();

			// Requests data to analyze from DMF
			List<WeatherData> weatherData = _dmf.getAllWeatherData();
			List<GridData> gridData = _dmf.getAllGridData();

			System.out.println("Mapping data by timestamp...");
			System.out.println();

			// Maps weather and grid data by timestamp
			HashMap<WeatherData, GridData> map = mapData(weatherData, gridData);

			System.out.println("Data mapped. Querying past years...");
			System.out.println();

			// Grabs past years of days to detect pattern
			HashMap<WeatherData, GridData> pastYears = getPastYears(map);

			System.out.println("Calculating capacity and demand...");
			System.out.println();

			double totalCapacity = 0;
			double totalDemand = 0;

			for (GridData gd : pastYears.values())
			{
				totalCapacity += gd.getCapacity();
				totalDemand += gd.getDemand();
			}

			System.out.println(String.format("Total capacity: " + totalCapacity));
			System.out.println(String.format("Total demand: " + totalDemand));
			System.out.println();

			// Calculates average of grid capacity and demand for past years
			double averageCapacity = totalCapacity / pastYears.size();
			double averageDemand = totalDemand / pastYears.size();

			System.out.println(String.format("\nAverage capacity: %.2f", averageCapacity));
			System.out.println(String.format("Average demand: %.2f", averageDemand));
			System.out.println();

			// Overestimates demand by 1% to maintain a small supply buffer
			double difference = averageCapacity - (averageDemand * 1.01);

			System.out.println(String.format("\nCapacity - Demand * 1.01: %.2f", difference));

			if (difference > 0.0)
			{
				System.out.println("There's a predicted surplus\n");
			}
			else
			{
				System.out.println("There's a predicted deficit\n");
			}

			System.out.println();
			System.out.println("Sending prediction to DMF...\n");
			System.out.println();

			// Sends expected grid data to DMF
			_dmf.setDeficit(0 - ((int) difference));

			System.out.println("Prediction sent successfully\n");
			System.out.println();
			System.out.println("Time ended: " + new Date().toString());
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
		catch (ParseException pe)
		{
			pe.printStackTrace();
		}
	}

	/**
	 * Returns a map of weather data and grid data given the list of weather data and grid data.
	 *
	 * @param weatherData	list of weather data
	 * @param gridData		list of grid data
	 * @return				map of weather data and grid data
	 */
	private HashMap<WeatherData, GridData> mapData(List<WeatherData> weatherData, List<GridData> gridData)
	{
		HashMap<WeatherData, GridData> map = new HashMap<WeatherData, GridData>();

		for (WeatherData wd : weatherData)
		{
			for (GridData gd : gridData)
			{
				if (wd.getTimestamp().equals(gd.getTimestamp()))
				{
					map.put(wd, gd);
					break;
				}
			}
		}

		return map;
	}

	/**
	 * Returns a map of past weather data and grid data that match the same month, day, and (hour + 1) of the
	 * current time given the map of weather data and grid data.
	 *
	 * @param map				map of weather data and grid data
	 * @return					map of past weather data and grid data
	 * @throws ParseException
	 */
	@SuppressWarnings({ "static-access" })
	private HashMap<WeatherData, GridData> getPastYears(HashMap<WeatherData, GridData> map) throws ParseException
	{
		HashMap<WeatherData, GridData> data = new HashMap<WeatherData, GridData>();

		for (WeatherData weatherData : map.keySet())
		{
			String target = weatherData.getTimestamp();
			DateFormat df = new SimpleDateFormat(WeatherData.TimestampFormat, Locale.ENGLISH);

			Calendar dataTime = Calendar.getInstance();
			dataTime.setTime(df.parse(target));

			Calendar currentTime = Calendar.getInstance();
			currentTime.add(Calendar.HOUR, 1);

			if (dataTime.get(dataTime.MONTH) == currentTime.get(currentTime.MONTH) &&
					dataTime.get(dataTime.DAY_OF_MONTH) == currentTime.get(currentTime.DAY_OF_MONTH) &&
					dataTime.get(dataTime.HOUR_OF_DAY) == currentTime.get(currentTime.HOUR_OF_DAY))
			{
				data.put(weatherData, map.get(weatherData));
			}
		}

		return data;
	}

	public static void mainDemo()
	{
		PF _pf = new PF();
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		_pf.sendGridDeficitPredictionToDMF();

		System.out.println("Press Enter to return.");

		sc.nextLine();
	}
}