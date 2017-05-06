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
public class AddIDMenu
{
	private JLabel employeeIDLabel = new JLabel("Employee ID:");
	private JTextField employeeIDTextField = new JTextField(11);
	private JButton submitButton = new JButton("Submit");
	private JButton cancelButton = new JButton("Cancel");

	private DatabaseManager dm;

	/**
	 * Creates a new AddIDMenu given the DatabaseManager object.
	 *
	 * @param dm	database manager
	 */
	public AddIDMenu(DatabaseManager dm)
	{
		this.dm = dm;
	}

	/**
	 * Displays the add ID menu.
	 */
	public void display()
	{
		JFrame frame = new JFrame("Add Employee");

		JPanel inputPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		inputPanel.add(employeeIDLabel);
		inputPanel.add(employeeIDTextField);

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
					int id = Integer.parseInt(employeeIDTextField.getText().trim());

					if (id > 0)
					{
						if (dm.isNewEmployee(id))
						{
							new AddMenu(dm, id).display();
							frame.dispose();
						}
						else
						{
							JOptionPane.showMessageDialog(null,
									"Employee ID already exist. Please reenter a different employee ID.", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null,
								"Invalid employee ID. It must be a number greater than 0.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				catch (NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(null, "Invalid employee ID. It must be a number.", "Error",
							JOptionPane.ERROR_MESSAGE);
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