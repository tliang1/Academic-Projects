package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import enums.JobStatus;
import enums.PayType;
import enums.Sex;
import sql.DatabaseManager;
import types.Employee;

/**
 * @author Tony Liang
 *
 */
public class AddMenu
{
	private JLabel lastNameLabel = new JLabel("Employee's Last Name:");
	private JLabel hireDateLabel = new JLabel("Employee's Hire Date (Format: Month/Day/Year):");
	private JLabel birthdateLabel = new JLabel("Employee's Birthdate (Format: Month/Day/Year):");
	private JLabel sexLabel = new JLabel("Employee's Sex (Format: M = Male or F = Female):");
	private JLabel jobStatusLabel =
			new JLabel("Employee's Job Status (Format: FT = Full-time or PT = Part-time):");
	private JLabel payTypeLabel = new JLabel("Employee's Pay Type (Format: S = Salary or H = Hourly):");
	private JLabel annualSalaryLabel = new JLabel("Employee's Annual Salary (Format: No dollar sign.):");
	private JLabel yearsOfServiceLabel = new JLabel("Employee's Years of Service:");

	private JTextField lastNameTextField = new JTextField();
	private JTextField hireDateTextField = new JTextField();
	private JTextField birthdateTextField = new JTextField();
	private JTextField annualSalaryTextField = new JTextField();
	private JTextField yearsOfServiceTextField = new JTextField();

	private JButton submitButton = new JButton("Add");
	private JButton cancelButton = new JButton("Cancel");

	private JComboBox<Sex> sexes = new JComboBox<Sex>(Sex.values());
	private JComboBox<PayType> payTypes = new JComboBox<PayType>(PayType.values());
	private JComboBox<JobStatus> jobStatus = new JComboBox<JobStatus>(JobStatus.values());

	private int id;

	private DatabaseManager dm;

	/**
	 * Creates a new AddMenu given the DatabaseManager object and the employee's ID.
	 *
	 * @param dm			database manager
	 * @param employeeID	employee's ID
	 */
	public AddMenu(DatabaseManager dm, int employeeID)
	{
		id = employeeID;
		this.dm = dm;
	}

	/**
	 * Displays the add menu.
	 */
	public void display()
	{
		JFrame frame = new JFrame("Add Employee");

		JPanel inputPanel = new JPanel(new GridLayout(8, 2, 0, 0));
		inputPanel.add(lastNameLabel);
		inputPanel.add(lastNameTextField);
		inputPanel.add(hireDateLabel);
		inputPanel.add(hireDateTextField);
		inputPanel.add(birthdateLabel);
		inputPanel.add(birthdateTextField);
		inputPanel.add(sexLabel);
		inputPanel.add(sexes);
		inputPanel.add(jobStatusLabel);
		inputPanel.add(jobStatus);
		inputPanel.add(payTypeLabel);
		inputPanel.add(payTypes);
		inputPanel.add(annualSalaryLabel);
		inputPanel.add(annualSalaryTextField);
		inputPanel.add(yearsOfServiceLabel);
		inputPanel.add(yearsOfServiceTextField);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		buttonPanel.add(submitButton);
		buttonPanel.add(cancelButton);

		submitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				boolean added = false;

				try
				{
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/y");
					LocalDate hireDate = LocalDate.parse(hireDateTextField.getText().trim().subSequence(0,
							hireDateTextField.getText().trim().length()), dtf);
					LocalDate birthDate = LocalDate.parse(birthdateTextField.getText().trim().subSequence(0,
							birthdateTextField.getText().trim().length()), dtf);

					Employee record = new Employee(id, lastNameTextField.getText().trim(), hireDate, birthDate,
							Sex.valueOf(String.valueOf(sexes.getSelectedItem())),
							JobStatus.valueOf(String.valueOf(jobStatus.getSelectedItem())),
							PayType.valueOf(String.valueOf(payTypes.getSelectedItem())),
							Double.parseDouble(annualSalaryTextField.getText().trim()),
							Integer.parseInt(yearsOfServiceTextField.getText().trim()));

					added = dm.addEmployee(record);

					if (added)
					{
						JOptionPane.showMessageDialog(null, "Record added to Employee database successfully.",
								"Success", JOptionPane.INFORMATION_MESSAGE);
						frame.dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "One or more of the fields are incorrect. Please" +
								" enter in the fields correctly.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch (DateTimeParseException dtpe)
				{
					JOptionPane.showMessageDialog(null, "The hire date and/or birthdate fields are empty or " +
							"incorrect. Please enter in the fields correctly.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				catch (IllegalArgumentException iae)
				{
					JOptionPane.showMessageDialog(null, "The last name, hire date, birthdate, annual salary " +
							"and/or years of service fields are empty or incorrect. The birthdate must be on " +
							"or before the hire date.\nThe annual salary must be greater than $0.00. The years " +
							"of services must be a positive integer. Please enter in the fields correctly.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		cancelButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				frame.dispose();
			}
		});

		frame.add(inputPanel, BorderLayout.NORTH);
		frame.add(buttonPanel, BorderLayout.SOUTH);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}