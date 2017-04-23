package types;

/**
 * @author Michael Holloway, William Klein
 *
 */
public class GridData
{
	private int capacity;
	private int demand;
	private String locality;
	private String timestamp;

	private static final String TimestampStringFormat = "%s-%s-%s %s:00:00";

	/**
	 * Creates a new GridData given the grid's day and hour.
	 * <p>
	 * The grid's locality defaults to "Los Angeles". The grid's capacity and demand are randomly generated.
	 * <ul>
	 * 	<li>
	 * 		If the grid's day is not between 1 and 31 inclusive, then the grid's day defaults to 1.
	 * 	</li>
	 * 	<li>
	 * 		If the grid's hour is not between 0 and 23 inclusive, then the grid's hour defaults to 0.
	 * 	</li>
	 * </ul>
	 *
	 * @param day	grid's day
	 * @param hour	grid's hour
	 */
	public GridData(int day, int hour)
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
		capacity = (int) ((Math.random() * 5000) + 47500);
		demand = (int) ((Math.random() * 25000) + 25000);
	}

	/**
	 * Creates a new GridData given the grid's month, day, year, and hour.
	 * <p>
	 * The grid's locality defaults to "Los Angeles". The grid's capacity and demand are randomly generated.
	 * <ul>
	 * 	<li>
	 * 		If the grid's month is not between 1 and 12 inclusive, then the grid's month defaults to 1.
	 * 	</li>
	 * 	<li>
	 * 		If the grid's day is not between 1 and 31 inclusive, then the grid's day defaults to 1.
	 * 	</li>
	 * 	<li>
	 * 		If the grid's year is negative, then the grid's year defaults to 0.
	 * 	</li>
	 * 	<li>
	 * 		If the grid's hour is not between 0 and 23 inclusive, then the grid's hour defaults to 0.
	 * 	</li>
	 * </ul>
	 *
	 * @param month		grid's month
	 * @param day		grid's day
	 * @param year		grid's year
	 * @param hour		grid's hour
	 */
	public GridData(int month, int day, int year, int hour)
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
		capacity = (int) ((Math.random() * 5000) + 47500);
		demand = (int) ((Math.random() * 25000) + 25000);
	}

	/**
	 * Creates a new GridData given the grid's locality, timestamp, capacity, and demand.
	 * <ul>
	 * 	<li>
	 * 		If the grid's capacity is not between 47500 and 52499 inclusive, then the grid's capacity is randomly
	 * 		generated.
	 * 	</li>
	 * 	<li>
	 * 		If the grid's demand is not between 25000 and 49999 inclusive, then the grid's demand is randomly
	 * 		generated.
	 * 	</li>
	 * </ul>
	 *
	 * @param locality		grid's locality
	 * @param timestamp		grid's timestamp
	 * @param capacity		grid's capacity
	 * @param demand		grid's demand
	 */
	public GridData(String locality, String timestamp, int capacity, int demand)
	{
		this.locality = locality;
		this.timestamp = timestamp;
		this.capacity = capacity;

		if ((capacity < 47500) || (capacity > 52499))
		{
			this.capacity = (int) ((Math.random() * 5000) + 47500);
		}

		this.demand = demand;

		if ((demand < 25000) || (demand > 49999))
		{
			this.demand = (int) ((Math.random() * 25000) + 25000);
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

	public int getCapacity()
	{
		return capacity;
	}

	/**
	 * Sets the grid's capacity.
	 * <ul>
	 * 	<li>
	 * 		If the integer argument is not between 47500 and 52499 inclusive, then the grid's capacity is
	 * 		unchanged.
	 * 	</li>
	 * </ul>
	 *
	 * @param capacity		grid's capacity
	 */
	public void setCapacity(int capacity)
	{
		if ((capacity >= 47500) && (capacity < 52500))
		{
			this.capacity = capacity;
		}
	}

	public int getDemand()
	{
		return demand;
	}

	/**
	 * Sets the grid's demand.
	 * <ul>
	 * 	<li>
	 * 		If the integer argument is not between 25000 and 49999 inclusive, then the grid's demand is unchanged.
	 * 	</li>
	 * </ul>
	 *
	 * @param demand	grid's demand
	 */
	public void setDemand(int demand)
	{
		if ((demand >= 25000) && (demand < 50000))
		{
			this.demand = demand;
		}
	}

	public String toSqlEntry()
	{
		return "Insert Into GridData Values('" + locality + "', " + "STR_TO_DATE('" + timestamp +
				"', '%Y-%m-%d %H:%i:%s')" + ", " + capacity + ", " + demand + ");";
	}

	@Override
	public String toString()
	{
		return "Locality: " + locality + ", Timestamp: " + timestamp + ", Capacity: " + capacity + ", Demand: " +
				demand;
	}
}