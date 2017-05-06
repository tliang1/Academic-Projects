package gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import types.Employee;

/**
 * @author Tony Liang
 *
 */
public class DisplayMenu
{
	private String[] columnLabels = {"Employee_ID", "Employee_Last_Name", "Employee_Hire_Date",
			"Employee_Birthdate", "Employee_Sex", "Employee_Job_Status", "Employee_Pay_Type",
			"Employee_Annual_salary", "Employee_Years_Of_Service"};

	/**
	 * Creates a new DisplayMenu.
	 */
	public DisplayMenu() {}

	/**
	 * Displays the display menu given the employees' data.
	 *
	 * @param employeesData		employees' data to display
	 */
	public void display(List<Employee> employeesData)
	{
		JFrame frame = new JFrame("Display Employee");
		JPanel panel = new JPanel(new BorderLayout());

		// Add the records to the table
		DefaultTableModel dtm = new DefaultTableModel(columnLabels, 0);
		JTable employeeDataTable = new JTable(dtm);

		for (Employee employee : employeesData)
		{
			dtm.addRow(employee.toStringArray());
		}

		employeeDataTable.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(employeeDataTable);

		panel.add(scrollPane);
		frame.add(panel);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}