package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sql.DatabaseManager;

/**
 * @author Tony Liang
 *
 */
public class MainMenu
{
	private JButton addButton = new JButton("Add");
	private JButton updateButton = new JButton("Update");
	private JButton deleteButton = new JButton("Delete");
	private JButton displayButton = new JButton("Display");
	private JButton searchButton = new JButton("Search");
	private JButton exitButton = new JButton("Exit");

	/**
	 * Creates a new MainMenu given the DatabaseManager object.
	 *
	 * @param dm	database manager
	 */
	public MainMenu(DatabaseManager dm)
	{
		addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				new AddIDMenu(dm).display();
			}
		});

		updateButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				new UpdateIDMenu(dm).display();
			}
		});

		deleteButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				new DeleteMenu(dm).display();
			}
		});

		displayButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				new DisplayMenu().display(dm.getListOfAllEmployeeData());
			}
		});

		searchButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				new SearchMenu(dm).display();
			}
		});

		exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				try
				{
					dm.close();
				}
				catch (SQLException sqle)
				{
					JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

				System.exit(0);
			}
		});
	}

	/**
	 * Displays the main menu.
	 */
	public void display()
	{
		JFrame frame = new JFrame("Main Menu");
		frame.setLayout(new BorderLayout());

		JPanel panel = new JPanel(new GridLayout(6, 1, 0, 10));
		panel.add(addButton);
		panel.add(updateButton);
		panel.add(deleteButton);
		panel.add(displayButton);
		panel.add(searchButton);
		panel.add(exitButton);

		frame.add(panel, BorderLayout.CENTER);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}