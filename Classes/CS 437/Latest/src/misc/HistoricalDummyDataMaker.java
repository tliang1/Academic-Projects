package misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import types.GridData;
import types.WeatherData;

/**
 * @author Michael Holloway
 *
 */
public class HistoricalDummyDataMaker
{
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
	{
		int startYear = 2009;
		int startMonth = 1;
		int startDay = 1;
		int currentYear = startYear;
		int currentMonth = startMonth;
		int currentDay = startDay;
		int currentHour = 0;
		int stopYear = 2014;
		int stopMonth = 3;
		int stopDay = 1;
		HashMap<Integer, Integer> months = initMonths();
		File historicalDummyDataFile = new File(System.getProperty("user.home") + "/Desktop",
				"HistoricalDummyData.txt");
		PrintWriter writer = new PrintWriter(historicalDummyDataFile, "UTF-8");

		int count = 0;

		while ((currentYear != stopYear) || (currentMonth != stopMonth) || (currentDay != stopDay))
		{
			if (currentHour >= 24)
			{
				currentHour = 0;
				currentDay++;
			}

			if (currentDay > months.get(currentMonth))
			{
				currentDay = 1;
				currentMonth++;
			}

			if (currentMonth > 12)
			{
				currentMonth = 1;
				currentYear++;
			}

			WeatherData weatherData = new WeatherData(currentMonth, currentDay, currentYear, currentHour);
			GridData gridData = new GridData(currentMonth, currentDay, currentYear, currentHour);

			writer.println(weatherData.toSqlEntry());

			count++;

			writer.println(gridData.toSqlEntry());

			count++;
			currentHour++;
		}

		System.out.println(String.format("%d sql commands printed", count));

		writer.close();
	}

	/**
	 * Returns a map of the 12 months and their number of days.
	 *
	 * @return	map of 12 months and their number of days
	 */
	private static HashMap<Integer, Integer> initMonths()
	{
		HashMap<Integer, Integer> months = new HashMap<Integer, Integer>();

		months.put(1, 31);
		months.put(2, 28);
		months.put(3, 31);
		months.put(4, 30);
		months.put(5, 31);
		months.put(6, 30);
		months.put(7, 31);
		months.put(8, 31);
		months.put(9, 30);
		months.put(10, 31);
		months.put(11, 30);
		months.put(12, 31);

		return months;
	}
}