import java.io.Console;
import java.sql.*;
import java.util.Scanner;

public class UNQS {
	public static void main(String args[]) {

		Configuration config = new Configuration();
		Console sensitive = System.console();
		Scanner input = new Scanner( System.in );
		String choice, filename = null, s;
		int read = 0;

		config.setDefault();

		if (args.length == 0) {
			do {
				System.out.print("Continue with the default configuration? (Y/N) ");
				choice = input.nextLine();

				/* use default configuration */
				if ( choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes") ) {
					break;
				}

				else if (choice.equalsIgnoreCase("n") || choice.equalsIgnoreCase("no")) {
					System.out.print("Enter <filename>.conf (must be in same directory of UNQS): ");
					filename = input.nextLine();
					break;
				}

				else {
					System.out.println("Invalid choice. Try again.");
				}
			} while (true);
		}

		else if (args.length > 1) {
			System.out.println("Error. Too many arguments. Please re-run program.");
			return;
		}

		else if (args.length == 1) {
			filename = args[0];
		}

		/* configure using .conf file */
		if (filename != null) {
			read = config.readFile( filename );
			if (read == -1) {
				return;
			}
		}

		if ( config.hasPassword() ) {
			if ( sensitive == null ) return;
			System.out.print("MySQL password: ");
			s = String.valueOf( sensitive.readPassword() );
			config.setPassword( s );
		}

		config.show();

		/* connect to database */
		System.out.println("-------------------------------");
		System.out.print("\nConnecting to database...");
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://"+ config.getIpAddress() + ":" + config.getPortNumber() + "/" + config.getDbName(), config.getUsername(), config.getPassword());

			System.out.print("successfully connected.\n");
		} catch (Exception e) {
			System.out.print("error connecting.\n");
			System.out.println(e);
			e.printStackTrace();
		}
	}
}