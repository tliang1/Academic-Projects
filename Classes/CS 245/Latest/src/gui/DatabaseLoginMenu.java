package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import sql.DatabaseManager;

/**
 * @author Tony Liang
 *
 */
public class DatabaseLoginMenu
{
	private JLabel urlLabel = new JLabel("URL: ");
	private JLabel usernameLabel = new JLabel("Username: ");
	private JLabel passwordLabel = new JLabel("Password: ");
	private JTextField urlTextField = new JTextField("jdbc:mysql://localhost:3306/records");
	private JTextField usernameTextField = new JTextField("root");
	private JPasswordField passwordTextField = new JPasswordField();
	private JButton loginButton = new JButton("Login");

	/**
	 * Creates a new DatabaseLoginMenu.
	 */
	public DatabaseLoginMenu() {}

	/**
	 * Displays the database login menu.
	 */
	public void display()
	{
		JFrame frame = new JFrame("Database Login");

		JPanel inputPanel = new JPanel(new GridLayout(3, 2, 0, 0));
		inputPanel.add(urlLabel);
		inputPanel.add(urlTextField);
		inputPanel.add(usernameLabel);
		inputPanel.add(usernameTextField);
		inputPanel.add(passwordLabel);
		inputPanel.add(passwordTextField);

		loginButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				try
				{
					DatabaseManager dm = new DatabaseManager(urlTextField.getText(), usernameTextField.getText(),
							String.valueOf(passwordTextField.getPassword()));

					frame.dispose();

					dm.createEmployeeTable();

					JFileChooser chooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma-Separated Values " +
							"(CSV) Files", "csv");
					chooser.setFileFilter(filter);
					chooser.setAcceptAllFileFilterUsed(false);

					int returnState = chooser.showOpenDialog(null);

					if (returnState == JFileChooser.APPROVE_OPTION)
					{
						dm.loadEmployeeData(chooser.getSelectedFile().getAbsolutePath());
					}

					new MainMenu(dm).display();
				}
				catch (SQLException sqle)
				{
					JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		frame.add(inputPanel, BorderLayout.NORTH);
		frame.add(loginButton, BorderLayout.SOUTH);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}