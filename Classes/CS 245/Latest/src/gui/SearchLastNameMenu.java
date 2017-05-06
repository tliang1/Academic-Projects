package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sql.DatabaseManager;

/**
 * @author Tony Liang
 *
 */
public class SearchLastNameMenu
{
	private JLabel employeeLastNameLabel = new JLabel("Employee Last Name:");
	private JTextField employeeLastNameTextField = new JTextField(22);
	private JButton submitButton = new JButton("Submit");
	private JButton cancelButton = new JButton("Cancel");

	private DatabaseManager dm;

	/**
	 * Creates a new SearchLastNameMenu given the DatabaseManager object.
	 *
	 * @param dm	database manager
	 */
	public SearchLastNameMenu(DatabaseManager dm)
	{
		this.dm = dm;
	}

	/**
	 * Displays the search last name menu.
	 */
	public void display()
	{
		JFrame frame = new JFrame("Search Employee's Last Name With Over $100K Annual Salary");

		JPanel inputPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		inputPanel.add(employeeLastNameLabel);
		inputPanel.add(employeeLastNameTextField);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		buttonPanel.add(submitButton);
		buttonPanel.add(cancelButton);

		submitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				new DisplayMenu().display(
						dm.searchEmployeesByLastNameAndAbove100K(employeeLastNameTextField.getText().trim()));
				frame.dispose();
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