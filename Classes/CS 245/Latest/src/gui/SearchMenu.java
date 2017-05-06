package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sql.DatabaseManager;

/**
 * @author Tony Liang
 *
 */
public class SearchMenu
{
	private JLabel search1Label = new JLabel("Search for an employee by employee ID:");
	private JLabel search2Label = new JLabel("Search for employee(s) who are older than 40, full-time, are " +
			"based on salary, and have worked more than 3 years:");
	private JLabel search3Label = new JLabel("Search for employee(s) who are female and have annual salary " +
			"greater than 40k:");
	private JLabel search4Label = new JLabel("Search for employee(s) who have the same last name and make " +
			"more than 100k:");
	private JButton search1Button = new JButton("Search");
	private JButton search2Button = new JButton("Search");
	private JButton search3Button = new JButton("Search");
	private JButton search4Button = new JButton("Search");
	private JButton cancelButton = new JButton("Cancel");

	private DatabaseManager dm;

	/**
	 * Creates a new SearchMenu given the DatabaseManager object.
	 *
	 * @param dm	database manager
	 */
	public SearchMenu(DatabaseManager dm)
	{
		this.dm = dm;
	}

	/**
	 * Displays the search menu.
	 */
	public void display()
	{
		JFrame frame = new JFrame("Search Employee");

		JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 0, 0));
		buttonPanel.add(search1Label);
		buttonPanel.add(search1Button);
		buttonPanel.add(search2Label);
		buttonPanel.add(search2Button);
		buttonPanel.add(search3Label);
		buttonPanel.add(search3Button);
		buttonPanel.add(search4Label);
		buttonPanel.add(search4Button);

		search1Button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				new SearchIDMenu(dm).display();
			}
		});

		search2Button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				new SearchSalaryMenu(dm).display();
			}
		});

		search3Button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				new DisplayMenu().display(dm.searchEmployeesByFemaleAndAbove40K());
			}
		});

		search4Button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				new SearchLastNameMenu(dm).display();
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

		frame.add(buttonPanel, BorderLayout.NORTH);
		frame.add(cancelButton, BorderLayout.SOUTH);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}