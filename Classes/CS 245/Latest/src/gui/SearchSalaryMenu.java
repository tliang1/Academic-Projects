package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sql.DatabaseManager;

/**
 * @author Tony Liang
 *
 */
public class SearchSalaryMenu
{
	private JLabel annualSalaryLabel = new JLabel("Employee Annual Salary (Format: No dollar sign.):");
	private JTextField annualSalaryTextField = new JTextField(31);
	private JButton submitButton = new JButton("Submit");
	private JButton cancelButton = new JButton("Cancel");

	private DatabaseManager dm;

	/**
	 * Creates a new SearchSalaryMenu given the DatabaseManager object.
	 *
	 * @param dm	database manager
	 */
	public SearchSalaryMenu(DatabaseManager dm)
	{
		this.dm = dm;
	}

	/**
	 * Displays the search salary menu.
	 */
	public void display()
	{
		JFrame frame = new JFrame("Search Employee's Annual Salary With Over 40 Years Old, Full-time, And Over " +
				"3 Years Of Service");

		JPanel inputPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		inputPanel.add(annualSalaryLabel);
		inputPanel.add(annualSalaryTextField);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		buttonPanel.add(submitButton);
		buttonPanel.add(cancelButton);

		submitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				try
				{
					double salary = Double.parseDouble(annualSalaryTextField.getText().trim());

					if (salary > 0.0)
					{
						new DisplayMenu().display(
								dm.searchEmployeesByOlderThan40FullTimeSalaryANDOver3YearsService(salary));
						frame.dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Annual salary must be a nonzero positive number. " +
								"Please reenter a different employee annual salary.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				catch (NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(null, "Invalid employee annual salary. It must be a number.",
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