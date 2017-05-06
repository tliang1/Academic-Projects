# Employee Records Final Project by Tony Liang

Made using Java SE 1.8 and MySQL.

A database system which keeps records of employees' data and features adding, updating, deleting, displaying, and searching options.

# Project Setup

### MySQL Instructions
1. Download, install, and setup MySQL.
2. Create a database called records or a name of your choice to use for this program.
3. Download the latest MySQL Java Database Connectivity (JDBC) driver from here: https://dev.mysql.com/downloads/connector/j/
4. Extract the zip file.

### Eclipse IDE Instructions
1. Open Eclipse.
2. Create a new project.
3. Right click the folder of the project and click Import.
4. Drop down the General folder and click File System.
5. Click Next.
6. Click Browse and click the location of the folder that contains the src folder and the doc folder and their files to import.
7. On the left side, drop down the selected folder, select the src folder and the doc folder, and click Finish.
8. In the Package Explorer tab, right click the project folder and click Properties. Keyboard Shortcut: Alt+Enter
9. Click Javadoc Location on the left side of the window.
10. Click Browse and select the docs folder location.
11. Click Java Build Path on the left side of the window.
12. Click the Libraries tab on the top side of the window.
13. Click Add External JARs... on the right side of the window.
14. Navigate to the location of the mysql-connector-java-x.x.x-bin.jar file which is in the folder extracted from the MySQL JDBC driver zip file.
15. Select the mysql-connector-java-x.x.x-bin.jar file and click Open.
16. Click OK at the bottom right of the window.

#### Run Program
1. In the Package Explorer tab, drop down the project folder, the src folder, and the main package and click Main.java.
2. Run the program.

#### View Javadoc
1. In the Package Explorer tab, click the project folder.
2. In the menu bar, select Navigate and click Open Attached Javadoc. Keyboard Shortcut: Shift+F2

# How To Use
1. The Database Login Graphical User Interface (GUI) is opened.
![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/database_login_gui.png "Database Login GUI")
	1. The default database url, username, and password are displayed.
	2. If the database is not located at the url and/or the username and/or password are different, enter the correct url, username, and/or password,
2. Click the "Login" button.
3. The Comma-separated values (CSV) File Chooser GUI is displayed. (You can skip this step by clicking the "Cancel" button if you don't want or don't have a CSV file or you already selected 
   the same CSV file the first time you use the program.)
![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/csv_file_chooser_gui.png "Comma-separated values (CSV) File Chooser GUI")
	1. The Documents (My Documents) is the default directory.
	2. Browse and locate the CSV file containing the employees' data. (If you aren't using the provided CSV file, then your CSV file must have the employees' data listed in the same format
	   as the employees' data in the provided CSV file).
4. Click the "Open" button.
5. The Main Menu GUI is displayed.  
![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/main_menu_gui.png "Main Menu GUI")
	1. Click the "Add" button.
		1. The Add Employee GUI is displayed.  
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/add_employee_gui.png "Add Employee GUI")
		2. Enter the employee's ID.
		3. Click the "Submit" button.
			1. If the employee's ID is not an integer, is less than 1, or is in the database, an error window is displayed.
				1. Click the "OK" button.
				2. Enter a valid employee's ID and click the "Submit" button.
		4. The Add Employee GUI is displayed.
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/add_employee_gui_2.png "Add Employee GUI 2")
			1. Add the employee's data.
			2. Click the "Add" button.
				1. If the last name is empty, the hire date and/or birthdate are empty or aren't in the correct format, the hire date is before the birthdate, the annual salary isn't a 
				   number and/or zero or negative, and the years of service isn't an integer and/or negative, an error window is displayed.
					1. Click the "OK" button.
					2. Enter the employee's data correctly and click the "Add" button.
		5. The information window is displayed. The record is successfully added into the database.
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/add_employee_successful_gui.png "Add Employee Successful GUI")
			1. Click the "OK" button.
	2. Click the "Update" button.
		1. The Update Employee GUI is displayed.  
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/update_employee_gui.png "Update Employee GUI")
		2. Enter the employee's ID.
		3. Click the "Submit" button.
			1. If the employee's ID is not an integer, is less than 1, or don't exist in the database, an error window is displayed.
				1. Click the "OK" button.
				2. Enter a valid employee's ID and click the "Submit" button.
		4. The Update Employee GUI is displayed.
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/update_employee_gui_2.png "Update Employee GUI 2")
			1. The employee's data is displayed.
			2. Update the employee's data.
			3. Click the "Update" button.
				1. If the last name is empty, the hire date and/or birthdate are empty or aren't in the correct format, the hire date is before the birthdate, the annual salary isn't a 
				   number and/or zero or negative, and the years of service isn't an integer and/or negative, an error window is displayed.
					1. Click the "OK" button.
					2. Enter the employee's data correctly and click the "Update" button.
		5. The information window is displayed. The record is successfully updated into the database.
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/update_employee_successful_gui.png "Update Employee Successful GUI")
			1. Click the "OK" button.
	3. Click the "Delete" button.
		1. The Delete Employee GUI is displayed.  
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/delete_employee_gui.png "Delete Employee GUI")
		2. Enter the employee's ID.
		3. Click the "Submit" button.
			1. If the employee's ID is not an integer, is less than 1, or don't exist in the database, an error window is displayed.
				1. Click the "OK" button.
				2. Enter a valid employee's ID and click the "Submit" button.
		4. The information window is displayed. The record is successfully deleted from the database.
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/delete_employee_successful_gui.png "Delete Employee Successful GUI")
			1. Click the "OK" button.
	4. Click the "Display" button.
		1. The Display Employee GUI is displayed.
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/display_employee_gui.png "Display Employee GUI")
		2. Every employee's data are displayed in the table.
		3. Click the X button at the top right to close this window.
	5. Click the "Search" button.
		1. The Search Employee GUI is displayed.
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/search_employee_gui.png "Search Employee GUI")
			1. Click the "Search" button for an employee by employee ID.
				1. The Search Employee's ID GUI is displayed.
				![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/search_employee_id_gui.png "Search Employee's ID GUI")
				2. Enter the employee's ID.
				3. Click the "Submit" button.
					1. If the employee's ID is not an integer, is less than 1, or don't exist in the database, an error window is displayed.
						1. Click the "OK" button.
						2. Enter a valid employee's ID and click the "Submit" button.
				4. The Display Employee GUI is displayed.
				![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/display_employee_with_id_gui.png "Display Employee With ID GUI")
				5. The employee's data are displayed in the table.
				6. Click the X button at the top right to close this window.
			2. Click the "Search" button for for employee(s) who are older than 40, full-time, are based on salary, and have worked more than 3 years.
				1. The Search Employee's Annual Salary With Over 40 Years Old, Full-time, And Over 3 Years Of Service GUI is displayed.
				![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/search_employee_annual_salary_over_40_years_old_full_time_over_3_years_of_service_gui.png 
				"Search Employee's Annual Salary With Over 40 Years Old, Full-time, And Over 3 Years Of Service GUI")
				2. Enter the annual salary.
				3. Click the "Submit" button.
					1. If the annual salary isn't a number and/or zero or negative, an error window is displayed.
						1. Click the "OK" button.
						2. Enter the annual salary correctly and click the "Submit" button.
				4. The Display Employee GUI is displayed.
				![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/display_employee_over_40_years_old_full_time_over_3_years_of_service_same_annual_salary_gui.png 
				"Display Employees Over 40 Years Old, Full-time, And Over 3 Years Of Service With The Same Annual Salary GUI")
				5. The employees' data are displayed in the table.
				6. Click the X button at the top right to close this window.
			3. Click the "Search" button for employee(s) who are female and have annual salary greater than 40k.
				1. The Display Employee GUI is displayed.
				![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/display_employee_female_and_greater_than_40K_gui.png 
				"Display Employees Female And Greater Than 40K GUI")
				2. The employees' data are displayed in the table.
				3. Click the X button at the top right to close this window.
			4. Click the "Search" button for who have the same last name and make more than 100k.
				1. The Search Employee's Last Name With Over $100K Annual Salary GUI is displayed.
				![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/search_employee_last_name_over_100K_annual_salary_gui.png 
				"Search Employee's Last Name With Over $100K Annual Salary GUI")
				2. Enter the last name.
				3. Click the "Submit" button.
				4. The Display Employee GUI is displayed.
				![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20245/Latest/images/instructions/display_employee_over_100K_same_last_name_gui.png 
				"Display Employees Over $100K Annual Salary With The Same Last Name GUI")
				5. The employees' data are displayed in the table.
				6. Click the X button at the top right to close this window.
	6. Click the "Exit" button to quit the program.