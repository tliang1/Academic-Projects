package functions;

import java.sql.SQLException;
import java.util.Scanner;

import functions.cf.CF;
import functions.dmf.DMF;
import functions.pf.PF;
import functions.rf.NRGResponse;
import misc.DummyDataMaker;
import types.Device;

public class Demo
{
	public static void main(String[] args) throws SQLException
	{
		Scanner sc = new Scanner(System.in);
		char input = 'a';

		while (input != 'q')
		{
			System.out.println("*****************************************");
			System.out.println("*****        NRG Demo System        *****");
			System.out.println("*****************************************\n");
			System.out.println("1) Data Management Function Demo");
			System.out.println("2) Predictive Function Demo");
			System.out.println("3) Response Function Demo");
			System.out.println("4) Control Function Demo\n");
			System.out.println("q) Exit");

			input = sc.next().charAt(0);

			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
					"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
					"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

			switch (input)
			{
			case '1':
				generateDevices(20);
				DMF.mainDemo();
				break;
			case '2':
				generateDevices(20);
				PF.mainDemo();
				break;
			case '3':
				generateDevices(20);
				NRGResponse.mainDemo();
				break;
			case '4':
				generateDevices(20);
				CF.mainDemo();
				break;
			case 'q':
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid input");
				sc.nextLine();
				break;
			}
		}

		sc.close();
	}

	/**
	 * Generates the given number of devices and stores them in the database.
	 * <ul>
	 * 	<li>
	 * 		The database clears all devices from the database before generating the new devices.
	 * 	</li>
	 * </ul>
	 *
	 * @param numberOfDevices	number of devices
	 * @throws SQLException
	 */
	public static void generateDevices(int numberOfDevices) throws SQLException
	{
		DMF dmf = new DMF();
		dmf.purgeOldDevices();
	    DummyDataMaker ddm = new DummyDataMaker(numberOfDevices);

	    for (Device device : ddm.getDevices())
	    {
	    	dmf.insertDevice(device);
	    }

	    dmf.close();
	}
}