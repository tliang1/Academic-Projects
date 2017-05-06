package types;

import java.time.LocalDate;

import enums.JobStatus;
import enums.PayType;
import enums.Sex;

/**
 * @author Tony Liang
 *
 */
public class Employee
{
	private int id;
	private String lastName;
	private LocalDate hireDate;
	private LocalDate birthdate;
	private Sex sex;
	private JobStatus jobStatus;
	private PayType payType;
	private double annualSalary;
	private int yearsOfService;

	/**
	 * Creates a new Employee given the ID, last name, hire date, birthdate, sex, job status, pay type, annual
	 * salary, and years of service.
	 *
	 * @param id							ID
	 * @param lastName						last name
	 * @param hireDate						hire date
	 * @param birthdate						birthdate
	 * @param sex							sex
	 * @param jobStatus						job status
	 * @param payType						pay type
	 * @param annualSalary					annual salary
	 * @param yearsOfService				years of service
	 * @throws IllegalArgumentException		If the ID is less than 1, last name is empty, birth date is after the
	 * 										hire date, annual salary isn't a nonzero positive number, or years of
	 * 										service is negative.
	 */
	public Employee(int id, String lastName, LocalDate hireDate, LocalDate birthdate, Sex sex,
			JobStatus jobStatus, PayType payType, double annualSalary, int yearsOfService)
	{
		String lastNameTrim = lastName.trim();

		if ((id < 1) || (lastNameTrim.length() < 1) || (birthdate.compareTo(hireDate) > 0) ||
				(annualSalary <= 0.00) || (yearsOfService < 0))
		{
			throw new IllegalArgumentException();
		}

		this.id = id;
		this.lastName = lastNameTrim;
		this.hireDate = hireDate;
		this.birthdate = birthdate;
		this.sex = sex;
		this.jobStatus = jobStatus;
		this.payType = payType;
		this.annualSalary = annualSalary;
		this.yearsOfService = yearsOfService;
	}

	public int getID()
	{
		return id;
	}

	public String getLastName()
	{
		return lastName;
	}

	/**
	 * Sets the last name for the employee given the String argument.
	 * <ul>
	 * 	<li>
	 * 		If the String argument is empty, the employee's last name remains unchanged.
	 * 	</li>
	 * </ul>
	 *
	 * @param lastName	last name
	 */
	public void setLastName(String lastName)
	{
		String lastNameTrim = lastName.trim();

		if (lastNameTrim.length() > 0)
		{
			this.lastName = lastNameTrim;
		}
	}

	public LocalDate getHireDate()
	{
		return hireDate;
	}

	/**
	 * Sets the hire date for the employee given the hire date.
	 * <ul>
	 * 	<li>
	 * 		If the birthdate is after the hire date, the employee's hire date remains unchanged.
	 * 	</li>
	 * </ul>
	 *
	 * @param hireDate	hire date
	 */
	public void setHireDate(LocalDate hireDate)
	{
		if (birthdate.compareTo(hireDate) <= 0)
		{
			this.hireDate = hireDate;
		}
	}

	public LocalDate getBirthdate()
	{
		return birthdate;
	}

	/**
	 * Sets the birthdate for the employee given the birthdate.
	 * <ul>
	 * 	<li>
	 * 		If the birthdate is after the hire date, the employee's birthdate remains unchanged.
	 * 	</li>
	 * </ul>
	 *
	 * @param birthdate		birthdate
	 */
	public void setBirthdate(LocalDate birthdate)
	{
		if (birthdate.compareTo(hireDate) <= 0)
		{
			this.birthdate = birthdate;
		}
	}

	public Sex getSex()
	{
		return sex;
	}

	public void setSex(Sex sex)
	{
		this.sex = sex;
	}

	public JobStatus getJobStatus()
	{
		return jobStatus;
	}

	public void setJobStatus(JobStatus jobStatus)
	{
		this.jobStatus = jobStatus;
	}

	public PayType getPayType()
	{
		return payType;
	}

	public void setPayType(PayType payType)
	{
		this.payType = payType;
	}

	public double getAnnualSalary()
	{
		return annualSalary;
	}

	/**
	 * Sets the annual salary for the employee given the double argument.
	 * <ul>
	 * 	<li>
	 * 		If the double argument isn't a nonzero positive number, the employee's annual salary remains
	 * 		unchanged.
	 * 	</li>
	 * </ul>
	 *
	 * @param annualSalary	annual salary
	 */
	public void setAnnualSalary(double annualSalary)
	{
		if (annualSalary > 0.00)
		{
			this.annualSalary = annualSalary;
		}
	}

	public int getYearsOfService()
	{
		return yearsOfService;
	}

	/**
	 * Sets the years of service for the employee given the integer argument.
	 * <ul>
	 * 	<li>
	 * 		If the integer argument is negative, the employee's years of service remains unchanged.
	 * 	</li>
	 * </ul>
	 *
	 * @param yearsOfService	years of service
	 */
	public void setYearsOfService(int yearsOfService)
	{
		if (yearsOfService >= 0)
		{
			this.yearsOfService = yearsOfService;
		}
	}

	/**
	 * Returns the employee's data as an array of Strings.
	 *
	 * @return	the employee's data as an array of Strings
	 */
	public String[] toStringArray()
	{
		String[] employee = { String.valueOf(id), lastName, hireDate.toString(), birthdate.toString(),
				sex.toString(), jobStatus.toString(), payType.toString(), String.format("%.2f", annualSalary),
				String.valueOf(yearsOfService) };

		return employee;
	}
}