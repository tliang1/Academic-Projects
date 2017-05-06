package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import enums.JobStatus;
import enums.PayType;
import enums.Sex;
import types.Employee;

/**
 * @author Tony Liang
 *
 */
public class DatabaseManager
{
	private String url;
	private String username;
	private String password;

	private Connection connection;

	/**
	 * Creates a new DatabaseManager using the default url, username, and password.
	 * <p>
	 * url: jdbc:mysql://localhost:3306/records
	 * <br>
	 * username: root
	 * <br>
	 * password: (No password)
	 *
	 * @throws SQLException		If a database access error occurs or the url is null.
	 */
	public DatabaseManager() throws SQLException
	{
		url = "jdbc:mysql://localhost:3306/records";
		username = "root";
		password = "";

		connection = DriverManager.getConnection(url, username, password);
	}

	/**
	 * Creates a new DatabaseManager given the url, username, and password for the database.
	 *
	 * @param url				database url
	 * @param username			username
	 * @param password			password
	 * @throws SQLException		If a database access error occurs or the url is null.
	 */
	public DatabaseManager(String url, String username, String password) throws SQLException
	{
		this.url = url;
		this.username = username;
		this.password = password;

		connection = DriverManager.getConnection(url, username, password);
	}

	/**
	 * Closes the connection to the database.
	 *
	 * @throws SQLException 	If a database access error occurs.
	 */
	public void close() throws SQLException
	{
		connection.close();
	}

	/**
	 * Creates the Employee table in the database.
	 * <ul>
	 * 	<li>
	 * 		If the table is already created for the database, it will not be created again.
	 * 	</li>
	 * </ul>
	 */
	public void createEmployeeTable()
	{
		try
		{
			Statement stmt = connection.createStatement();
			String query = "CREATE TABLE IF NOT EXISTS Employee(" +
					"Employee_ID integer NOT NULL PRIMARY KEY," +
					"Employee_Last_Name varchar(10)," +
					"Employee_Hire_Date date," +
					"Employee_Birthdate date," +
					"Employee_Sex varchar(1)," +
					"Employee_Job_Status varchar(2)," +
					"Employee_Pay_Type varchar(1)," +
					"Employee_Annual_Salary double," +
					"Employee_Years_Of_Service integer" +
					")";
			stmt.executeUpdate(query);
		}
		catch (SQLException sqle)
		{
			JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Loads data from a file into the Employee table in the database given the file path.
	 *
	 * @param filePath	file path of the data
	 */
	public void loadEmployeeData(String filePath)
	{
		try
		{
			String query = "LOAD DATA LOCAL INFILE ? INTO TABLE Employee FIELDS TERMINATED " +
					"BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\r\n' (Employee_ID, Employee_Last_Name, " +
					"@Employee_Hire_Date, @Employee_Birthdate, Employee_Sex, Employee_Job_Status, " +
					"Employee_Pay_Type, Employee_Annual_Salary, Employee_Years_Of_Service) SET " +
					"Employee_Hire_Date = STR_TO_DATE(@Employee_Hire_Date, '%m/%d/%Y'), " +
					"Employee_Birthdate = STR_TO_DATE(@Employee_Birthdate, '%m/%d/%Y')";

			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, filePath.trim());
	        ps.executeUpdate();
		}
		catch (SQLException sqle)
		{
			JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Returns a list containing the employee's data given the employee's ID.
	 * <ul>
	 * 	<li>
	 * 		If the employee's ID isn't greater than 0, an empty list is returned.
	 * 	</li>
	 * </ul>
	 *
	 * @param id	employee's ID
	 * @return		list containing the employee's data
	 */
	public List<Employee> getEmployeeRecord(int id)
	{
		List<Employee> record = new ArrayList<Employee>();

		if (id > 0)
		{
			try
			{
				Statement stmt = connection.createStatement();
				String query = "SELECT * FROM Employee WHERE Employee_ID = " + id;
				ResultSet rs = stmt.executeQuery(query);

				if (!rs.next())
				{
					return record;
				}

				record.add(new Employee(rs.getInt(1), rs.getString(2), rs.getDate(3).toLocalDate(),
					rs.getDate(4).toLocalDate(), Sex.valueOf(rs.getString(5)), JobStatus.valueOf(rs.getString(6)),
					PayType.valueOf(rs.getString(7)), rs.getDouble(8), rs.getInt(9)));

				return record;

			}
			catch (SQLException sqle)
			{
				JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		return record;
	}

	/**
	 * Returns if the employee's ID isn't in the database.
	 * <ul>
	 * 	<li>
	 * 		If the employee's ID isn't greater than 0, this function returns false.
	 * 	</li>
	 * </ul>
	 *
	 * @param id	employee's ID
	 * @return		true if the employee's ID isn't in the database. Otherwise, false.
	 */
	public boolean isNewEmployee(int id)
	{
		if (id > 0)
		{
			if (getEmployeeRecord(id).isEmpty())
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns if the employee record is successfully added into the database given the Employee object.
	 * <ul>
	 * 	<li>
	 * 		If the ID is invalid, then the record isn't successfully added.
	 * 	</li>
	 * </ul>
	 *
	 * @param employee	employee
	 * @return			true if the record is successfully added. Otherwise, false.
	 */
	public boolean addEmployee(Employee employee)
	{
		if (isNewEmployee(employee.getID()))
		{
			try
			{
				String query = "INSERT INTO Employee VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement ps = connection.prepareStatement(query);
				ps.setInt(1, employee.getID());
				ps.setString(2, employee.getLastName());
				ps.setString(3, employee.getHireDate().toString());
				ps.setString(4, employee.getBirthdate().toString());
				ps.setString(5, String.valueOf(employee.getSex()));
				ps.setString(6, String.valueOf(employee.getJobStatus()));
				ps.setString(7, String.valueOf(employee.getPayType()));
				ps.setDouble(8, employee.getAnnualSalary());
				ps.setInt(9, employee.getYearsOfService());
				ps.executeUpdate();

				return true;
			}
			catch (SQLException sqle)
			{
				JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		return false;
	}

	/**
	 * Returns if the employee record is successfully updated into the database given the Employee object.
	 * <ul>
	 * 	<li>
	 * 		If the ID is invalid, then the record isn't successfully updated.
	 * 	</li>
	 * </ul>
	 *
	 * @param employee	employee
	 * @return			true if the record is successfully updated. Otherwise, false.
	 */
	public boolean updateEmployee(Employee employee)
	{
		if (!isNewEmployee(employee.getID()))
		{
			try
			{
				String query = "UPDATE Employee SET " + "Employee_Last_Name = ?, Employee_Hire_Date = ?, " +
						"Employee_Birthdate = ?, Employee_Sex = ?, Employee_Job_Status = ?, " +
						"Employee_Pay_Type = ?, Employee_Annual_Salary = ?, Employee_Years_Of_Service = ? " +
						"WHERE Employee_ID = ?";

				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, employee.getLastName());
				ps.setString(2, employee.getHireDate().toString());
				ps.setString(3, employee.getBirthdate().toString());
				ps.setString(4, String.valueOf(employee.getSex()));
				ps.setString(5, String.valueOf(employee.getJobStatus()));
				ps.setString(6, String.valueOf(employee.getPayType()));
				ps.setDouble(7, employee.getAnnualSalary());
				ps.setInt(8, employee.getYearsOfService());
				ps.setInt(9, employee.getID());
				ps.executeUpdate();

				return true;
			}
			catch (SQLException sqle)
			{
				JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		return false;
	}

	/**
	 * Returns if the employee record is successfully deleted from the database given the employee's ID.
	 * <ul>
	 * 	<li>
	 * 		If the employee's ID isn't greater than 0 and it isn't in the database, then the record isn't
	 * 		successfully deleted.
	 * 	</li>
	 * </ul>
	 * @param id	employee's ID
	 * @return		true if the employee record is successfully deleted. Otherwise, false.
	 */
	public boolean deleteEmployee(int id)
	{
		if ((id > 0) && !isNewEmployee(id))
		{
			try
			{
				Statement stmt = connection.createStatement();
				String query = "DELETE FROM Employee WHERE Employee_ID = " + id;
				stmt.executeUpdate(query);

				return true;
			}
			catch (SQLException sqle)
			{
				JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		return false;
	}

	/**
	 * Returns a list of employees with the given conditions.
	 * <ul>
	 * 	<li>
	 * 		If the conditions are invalid, an empty list is returned.
	 * 	</li>
	 * </ul>
	 *
	 * @param conditions	conditions
	 * @return				list of employees with the given conditions
	 */
	private List<Employee> getListOfEmployeeData(String conditions)
	{
		String conditionsTrim = conditions.trim();

		List<Employee> records = new ArrayList<Employee>();

		try
		{
			Statement stmt = connection.createStatement();
			String query = "SELECT COUNT(*) FROM Employee WHERE " + conditionsTrim;
			ResultSet rs = stmt.executeQuery(query);

			if (!rs.next())
			{
				return records;
			}

			query = "SELECT * FROM Employee WHERE " + conditionsTrim;
			rs = stmt.executeQuery(query);

			if (!rs.next())
			{
				return records;
			}

			while (!rs.isAfterLast())
			{
				records.add(new Employee(rs.getInt(1), rs.getString(2), rs.getDate(3).toLocalDate(),
						rs.getDate(4).toLocalDate(), Sex.valueOf(rs.getString(5)),
						JobStatus.valueOf(rs.getString(6)), PayType.valueOf(rs.getString(7)), rs.getDouble(8),
						rs.getInt(9)));

				rs.next();
			}

			return records;
		}
		catch (SQLException sqle)
		{
			JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		return records;
	}

	/**
	 * Returns a list of all employees.
	 *
	 * @return	list of all employees
	 */
	public List<Employee> getListOfAllEmployeeData()
	{
		return getListOfEmployeeData("TRUE");
	}

	/**
	 * Returns a list of all employees whose age is over 40, working full-time, and over 3 years in service, and
	 * their annual salaries are equal to the given salary.
	 *
	 * @param salary	annual salary
	 * @return			list of all employees whose age is over 40, working full-time, and over 3 years in
	 * 					service, and their annual salaries are equal to the given salary
	 */
	public List<Employee> searchEmployeesByOlderThan40FullTimeSalaryANDOver3YearsService(double salary)
	{
		return getListOfEmployeeData("CEIL(datediff(CURDATE(), Employee_Birthdate) / 365) > 40 AND " +
				"Employee_Job_Status = 'FT' AND Employee_Annual_Salary = " + salary + "AND " +
				"Employee_Years_Of_Service > 3");
	}

	/**
	 * Returns a list of all employees whose sex is female and their annual salaries are greater than $40,000.00.
	 *
	 * @return	list of all employees whose sex is female and their annual salaries are greater than $40,000.00
	 */
	public List<Employee> searchEmployeesByFemaleAndAbove40K()
	{
		return getListOfEmployeeData("Employee_Sex = 'F' AND Employee_Annual_Salary > 40000.00");
	}

	/**
	 * Returns a list of all employees whose last names are the same and their annual salaries are greater than
	 * $100,000.00 given the employee's last name.
	 *
	 * @param lastName	last name
	 * @return			list of all employees whose last names are the same and their annual salaries are greater
	 * 					than $100,000.00
	 */
	public List<Employee> searchEmployeesByLastNameAndAbove100K(String lastName)
	{
		String lastNameTrim = lastName.trim();

		return getListOfEmployeeData("Employee_Last_Name = '" + lastNameTrim + "' AND Employee_Annual_Salary > " +
				"100000.00");
	}
}