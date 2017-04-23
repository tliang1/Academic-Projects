package types;

/**
 * @author Michael Holloway, William Klein
 *
 */
public class WeatherData
{
	private int temperature;
	private int windSpeed;
	private int windDirection;
	private int solarRadiation;
	private String locality;
	private String timestamp;

	private static final String TimestampStringFormat = "%s-%s-%s %s:00:00";
	public static final String TimestampFormat = "yyyy-MM-dd hh:mm:ss";

	/**
	 * Creates a new WeatherData given the weather's day and hour.
	 * <p>
	 * The weather's locality defaults to "Los Angeles". The weather's temperature, wind speed, wind direction,
	 * and solar radiation are randomly generated.
	 * <ul>
	 * 	<li>
	 * 		If the weather's day is not between 1 and 31 inclusive, then the weather's day defaults to 1.
	 * 	</li>
	 * 	<li>
	 * 		If the weather's hour is not between 0 and 23 inclusive, then the weather's hour defaults to 0.
	 * 	</li>
	 * </ul>
	 *
	 * @param day	weather's day
	 * @param hour	weather's hour
	 */
	public WeatherData(int day, int hour)
	{
		day = ((day < 1) || (day > 31)) ? 1 : day;
		hour = ((hour < 0) || (hour > 23)) ? 0 : hour;

		if (hour < 10)
		{
			if (day < 10)
			{
				timestamp = "2014-01-0" + Integer.toString(day) + " 0" + Integer.toString(hour) + ":00:00";
			}
			else
			{
				timestamp = "2014-01-" + Integer.toString(day) + " 0" + Integer.toString(hour) + ":00:00";
			}
		}
		else
		{
			if (day < 10)
			{
				timestamp = "2014-01-0" + Integer.toString(day) + " " + Integer.toString(hour) + ":00:00";
			}
			else
			{
				timestamp = "2014-01-" + Integer.toString(day) + " " + Integer.toString(hour) + ":00:00";
			}
		}

		locality = "Los Angeles";
		temperature = (int) (Math.random() * 20 + 10);
		windSpeed = (int) (Math.random() * 15);
		windDirection = (int) (Math.random() * 360);
		solarRadiation = (int) (Math.random() * 50) + 50;
	}

	/**
	 * Creates a new WeatherData given the weather's month, day, year, and hour.
	 * <p>
	 * The weather's locality defaults to "Los Angeles". The weather's temperature, wind speed, wind direction,
	 * and solar radiation are randomly generated.
	 * <ul>
	 * 	<li>
	 * 		If the weather's month is not between 1 and 12 inclusive, then the weather's month defaults to 1.
	 * 	</li>
	 * 	<li>
	 * 		If the weather's day is not between 1 and 31 inclusive, then the weather's day defaults to 1.
	 * 	</li>
	 * 	<li>
	 * 		If the weather's year is negative, then the weather's year defaults to 0.
	 * 	</li>
	 * 	<li>
	 * 		If the weather's hour is not between 0 and 23 inclusive, then the weather's hour defaults to 0.
	 * 	</li>
	 * </ul>
	 *
	 * @param month		weather's month
	 * @param day		weather's day
	 * @param year		weather's year
	 * @param hour		weather's hour
	 */
	public WeatherData(int month, int day, int year, int hour)
	{
		month = ((month < 1) || (month > 12)) ? 1 : month;
		day = ((day < 1) || (day > 31)) ? 1 : day;
		year = (year < 0) ? 0 : year;
		hour = ((hour < 0) || (hour > 23)) ? 0 : hour;
		String sHour = Integer.toString(hour);
		String sDay = Integer.toString(day);
		String sMonth = Integer.toString(month);

		if (hour < 10)
		{
			sHour = "0" + sHour;
		}

		if (day < 10)
		{
			sDay = "0" + sDay;
		}

		if (month < 10)
		{
			sMonth = "0" + sMonth;
		}

		timestamp = String.format(TimestampStringFormat, year, sMonth, sDay, sHour);
		locality = "Los Angeles";
		temperature = (int) (Math.random() * 20 + 10);
		windSpeed = (int) (Math.random() * 15);
		windDirection = (int) (Math.random() * 360);
		solarRadiation = (int) (Math.random() * 50) + 50;
	}

	/**
	 * Creates a new WeatherData given the weather's locality, timestamp, temperature, wind speed, wind direction,
	 * and solar radiation.
	 * <ul>
	 * 	<li>
	 * 		If the weather's temperature is not between 10 and 29 inclusive, then the weather's temperature is
	 * 		randomly generated.
	 * 	</li>
	 * 	<li>
	 * 		If the weather's wind speed is not between 0 and 14 inclusive, then the weather's wind speed is
	 * 		randomly generated.
	 * 	</li>
	 * 	<li>
	 * 		If the weather's wind direction is not between 0 and 359 inclusive, then the weather's wind direction
	 * 		is randomly generated.
	 * 	</li>
	 * 	<li>
	 * 		If the weather's solar radiation is not between 50 and 99 inclusive, then the weather's solar radiation
	 * 		is randomly generated.
	 * 	</li>
	 * </ul>
	 *
	 * @param locality			weather's locality
	 * @param timestamp			weather's timestamp
	 * @param temperature		weather's temperature
	 * @param windSpeed			weather's wind speed
	 * @param windDirection		weather's wind direction
	 * @param solarRadiation	weather's solar radiation
	 */
	public WeatherData(String locality, String timestamp, int temperature, int windSpeed, int windDirection,
			int solarRadiation)
	{
		this.locality = locality;
		this.timestamp = timestamp;
		this.temperature = temperature;

		if ((temperature < 10) || (temperature > 29))
		{
			this.temperature = (int) (Math.random() * 20 + 10);
		}

		this.windSpeed = windSpeed;

		if ((windSpeed < 0) || (windSpeed > 14))
		{
			this.windSpeed = (int) (Math.random() * 15);
		}

		this.windDirection = windDirection;

		if ((windDirection < 0) || (windDirection > 359))
		{
			this.windDirection = (int) (Math.random() * 360);
		}

		this.solarRadiation = solarRadiation;

		if ((solarRadiation < 50) || (solarRadiation > 99))
		{
			this.solarRadiation = (int) (Math.random() * 50) + 50;
		}
	}

	public String getLocality()
	{
		return locality;
	}

	public void setLocality(String locality)
	{
		this.locality = locality;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	public int getTemperature()
	{
		return temperature;
	}

	/**
	 * Sets the weather's temperature.
	 * <ul>
	 * 	<li>
	 * 		If the integer argument is not between 10 and 29 inclusive, then the weather's temperature is
	 * 		unchanged.
	 * 	</li>
	 * </ul>
	 *
	 * @param temperature	weather's temperature
	 */
	public void setTemperature(int temperature)
	{
		if ((temperature >= 10) && (temperature < 30))
		{
			this.temperature = temperature;
		}
	}

	public int getWindSpeed()
	{
		return windSpeed;
	}

	/**
	 * Sets the weather's wind speed.
	 * <ul>
	 * 	<li>
	 * 		If the integer argument is not between 0 and 14 inclusive, then the weather's wind speed is
	 * 		unchanged.
	 * 	</li>
	 * </ul>
	 *
	 * @param windSpeed		weather's wind speed
	 */
	public void setWindSpeed(int windSpeed)
	{
		if ((windSpeed >= 0) && (windSpeed < 15))
		{
			this.windSpeed = windSpeed;
		}
	}

	public int getWindDirection()
	{
		return windDirection;
	}

	/**
	 * Sets the weather's wind direction.
	 * <ul>
	 * 	<li>
	 * 		If the integer argument is not between 0 and 359 inclusive, then the weather's wind direction is
	 * 		unchanged.
	 * 	</li>
	 * </ul>
	 *
	 * @param windDirection		weather's wind direction
	 */
	public void setWindDirection(int windDirection)
	{
		if ((windDirection >= 0) && (windDirection < 360))
		{
			this.windDirection = windDirection;
		}
	}

	public int getSolarRadiation()
	{
		return solarRadiation;
	}

	/**
	 * Sets the weather's solar radiation.
	 * <ul>
	 * 	<li>
	 * 		If the integer argument is not between 50 and 99 inclusive, then the weather's solar radiation is
	 * 		unchanged.
	 * 	</li>
	 * </ul>
	 *
	 * @param solarRadiation	weather's solar radiation
	 */
	public void setSolarRadiation(int solarRadiation)
	{
		if ((solarRadiation >= 50) && (solarRadiation < 100))
		{
			this.solarRadiation = solarRadiation;
		}
	}

	public String toSqlEntry()
	{
		return "Insert Into WeatherData Values('" + locality + "', " + "STR_TO_DATE('" + timestamp +
				"', '%Y-%m-%d %H:%i:%s')" + ", " + temperature + ", " + windSpeed + ", " + windDirection + ", " +
				solarRadiation + ");";
	}

	@Override
	public String toString()
	{
		return "Locality: " + locality + ", Timestamp: " + timestamp + ", Temperature: " + temperature +
				", Windspeed: " + windSpeed + ", Wind Direction: " + windDirection + ", Solar Radiation: " +
				solarRadiation;
	}
}