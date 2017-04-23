# Networked Redistribution Grid (NRG) Software Engineering Project by William Klein, Michael Holloway, Tony Liang, and Laura Mann

Made using Java SE 1.8, MySQL, JCommon 1.0.23, and JFreeChart 1.0.19.

Networked Redistribution Grid (NRG) demo which includes the Data Management Function, Predictive Function, Response Function, and Control Function demos.

# Project Setup

### MySQL, JFreeChart and JCommon
1. Download, install, and setup MySQL.
2. Execute the NRG.sql file in MySQL.
3. Execute the HistoricalDummyData.sql file in MySQL. If you don't want to execute the provided sql file for whatever reason, you can skip this step and create a new one later. See the Generate Historical Dummy Data 
(Optional) section below. (Note: Some Insert statements might generate an error because of the daylight savings time issue. Those insert statements must be skipped.)
4. Download the latest MySQL Java Database Connectivity (JDBC) driver from here: https://dev.mysql.com/downloads/connector/j/
5. Extract the zip file.
6. Download the latest JFreeChart, JCommon library, and installation instructions (jfreechart-x.x.x-install.pdf) from here: https://sourceforge.net/projects/jfreechart/files/
7. Extract the JFreeChart and JCommon zip files.
8. Open the installation instructions pdf file.

### Eclipse IDE Instructions
1. Open Eclipse.
2. In the pdf file, go to the Eclipse section of the Configuring IDEs for JFreeChart section.
3. Follow all the steps of the Configuration Steps section.
4. Create a new project.
5. Right click the folder of the project and click Import.
6. Drop down the General folder and click File System.
7. Click Next.
8. Click Browse and click the location of the folder that contains the src folder and the doc folder and their files to import.
9. On the left side, drop down the selected folder, select the src folder and the doc folder, and click Finish.
10. In the Package Explorer tab, right click the project folder and click Properties. Keyboard Shortcut: Alt+Enter
11. Click Javadoc Location on the left side of the window.
12. Click Browse and select the docs folder location.
13. Click Java Build Path on the left side of the window.
14. Click the Libraries tab on the top side of the window.
15. Click Add External JARs... on the right side of the window.
16. Navigate to the location of the mysql-connector-java-x.x.x-bin.jar file which is in the folder extracted from the MySQL JDBC driver zip file.
17. Select the mysql-connector-java-x.x.x-bin.jar file and click Open.
18. Click Add Library... on the right side of the window and select User Library.
19. Click Next and select both JFreeChart and JCommon libraries.
20. Click Finish.
21. Click OK at the bottom right of the window.

### Important Note
For the DMF.java file in the src/functions/dmf/ directory:
1. The host, username, and password for the nrg database are localhost:3306/nrg, root, and (No password) respectively.
2. If the nrg database is not located there and/or the username and password are different, they must be manually changed in the constructor of the DMF.java file.

### Generate Historical Dummy Data (Optional)
1. In the Package Explorer tab, drop down the project folder, the src folder, and the misc package and click HistoricalDummyDataMaker.java.
2. Run the program.
3. The generated txt file, HistoricalDummyData.txt, is located at the desktop directory.
4. Rename the file extension to sql.
5. Execute the HistoricalDummyData.sql file in MySQL. (Note: Some Insert statements might generate an error because of the daylight savings time issue. Those insert statements must be skipped.)

#### Run Program
1. In the Package Explorer tab, drop down the project folder, the src folder, and the functions package and click Demo.java.
2. Run the program.

#### View Javadoc
1. In the Package Explorer tab, click the project folder.
2. In the menu bar, select Navigate and click Open Attached Javadoc. Keyboard Shortcut: Shift+F2

# How To Use
1. Enter one of the following:
	1. Enter 1 to run the Data Management Function (DMF) demo.
		1. Press Enter to see an example of inserting a new device into the database.
		2. Press Enter to see an example of deleting all weather data before the current time.
		3. Press Enter to note that the database is getting a list of all devices.
		4. Press Enter to see the list of all devices in the database.
		5. Press Enter to note that the database is getting a list of all grid data.
		6. Press Enter to see the list of all grid data in the database.
		7. Press Enter to note that the database is getting a list of all weather data.
		8. Press Enter to see the list of all weather data in the database.
		9. Press Enter to return to the demo selection menu.
	2. Enter 2 to run the Predictive Function (PF) demo.
		1. The demo displays the start time.
		2. Historical data is retrieved from the DMF.
		3. Weather data is mapped with grid data matching the same timestamp.
		4. Search the data with timestamp matching the current time's month and day and an hour after the current time.
		5. Total capacity and demand of the searched data is calculated.
		6. Average capacity and demand of the searched data is calculated.
		7. Predict a surplus/deficit.
		8. Send the prediction to the DMF.
		9. The demo displays the end time.
		10. Press Enter to return to the demo selection menu.
	3. Enter 3 to run the Response Function (RF) demo.
		1. The Devices Table Graphical User Interface (GUI) is opened.
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20437/Latest/images/instructions/response_function_devices_table_gui.png "Response Function Devices Table GUI")
			1. Every device's id, their power usages in watts, and their priority levels are displayed in the table.
			2. The total power usage and the number of priority levels are displayed above the table.
		2. Click the "Limit Power" button.
		3. A new Devices Table GUI is displayed.
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20437/Latest/images/instructions/response_function_devices_table_gui_2.png "Response Function Devices Table GUI 2")
			1. A random current grid deficit is generated and removed by lowering the power usage of the devices.
			2. Every device's id, their power usage in percentage, and their priority levels are displayed in the table.
			3. The current grid deficit removed, the total power usage, and the number of priority levels are displayed above the table.
		4. Close the GUI.
	4. Enter 4 to run the Control Function (CF) demo.
		1. The Control Panel GUI is opened.
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20437/Latest/images/instructions/control_function_control_panel_gui.png "Control Function Control Panel GUI")
			1. The temperature, wind speed, current deficit, and solar radiation graphs are displayed.
		2. Each graph has a "Add New Data Item" button which adds a random data to the graph when clicked.
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20437/Latest/images/instructions/control_function_control_panel_gui_2.png "Control Function Control Panel GUI 2")
		3. Click the "System Controls" button.
		4. The Instruction Interface GUI is displayed.
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20437/Latest/images/instructions/control_function_instruction_interface_gui.png "Control Function Instruction Interface GUI")
			1. At the center, the first row shows the list of all devices in the database.
			2. At the center, the second row shows the current instructions the devices.
			3. At the bottom, search the database for devices by type or priority level.
				1. For search by type, click the "Type" radio button.
					1. Enter the type name and click the "Search" button.
					![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20437/Latest/images/instructions/control_function_instruction_interface_gui_search_by_type.png "Control Function Instruction Interface GUI Search By Type")
				2. For search by priority level, click the "Priority" radio button.
					1. Enter the priority level and click the "Search" button.
					![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20437/Latest/images/instructions/control_function_instruction_interface_gui_search_by_priority.png "Control Function Instruction Interface GUI Search By Priority")
			4. At the bottom, right of the radio buttons is the "Add Instruction" button.
				1. Click the button to display the Add Instructions GUI.
			5. At the bottom, right of the "Add Instruction" button is the "Remove Instruction" button.
				1. Click the button to display the Remove Instructions GUI.
			6. At the bottom, at the right end is the "Return To Main Menu" button.
				1. Click the button to return to the Control Panel GUI.
		5. The Add Instructions GUI is displayed.
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20437/Latest/images/instructions/control_function_add_instructions_gui.png "Control Function Add Instructions GUI")
			1. At the center, the current instructions are displayed.
			2. At the bottom, select the timeframe for the instruction.
			![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20437/Latest/images/instructions/control_function_add_instructions_gui_select_timeframe.png "Control Function Add Instructions GUI Select Timeframe")
			3. Select the group or priority level of the instruction.
				1. Selecting the priority level will apply the instruction to all devices with the same priority level and higher.
				2. Enter the type name or priority level.
			4. Click the "Add" button to add the instruction.
			![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20437/Latest/images/instructions/control_function_add_instructions_gui_add_by_type.png "Control Function Add Instructions GUI Add By Type")
			![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20437/Latest/images/instructions/control_function_add_instructions_gui_add_by_priority.png "Control Function Add Instructions GUI Add By Priority")
			5. The new instruction is added to the current instructions.
			6. Continue adding more instructions or click the "Return To Main Menu" button when done.
		6. The Remove Instructions GUI is displayed.
		![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20437/Latest/images/instructions/control_function_remove_instructions_gui.png "Control Function Remove Instructions GUI")
			1. At the center, the current instructions are displayed.
			2. At the bottom, enter the instruction id to remove.
			![alt text](https://github.com/tliang1/Academic-Projects/raw/master/Classes/CS%20437/Latest/images/instructions/control_function_remove_instructions_gui_remove_by_id.png "Control Function Remove Instructions GUI Remove By Id")
			3. Click the "Remove" button to remove the instruction.
			4. The instruction is removed from the current instructions.
			5. Continue removing more instructions or click the "Return To Main Menu" button when done.
		7. Close the GUI.
	5. Enter q to exit the program.