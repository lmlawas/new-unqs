/*
	To compile:
		javac -cp ".;mysql-connector-java.jar;" *.java
	To run:
		java -cp .:mysql-connector-java.jar UNQS simulation.conf
*/

import java.io.Console;
import java.sql.*;
import java.util.LinkedList;
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

		/* get password if any */
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

			Statement stmt = con.createStatement();
			ResultSet flows = stmt.executeQuery("select BYTES, PACKETS, L4_DST_PORT, FIRST_SWITCHED from `flows-" + config.getDatestamp() + "v4_" + config.getInterfaceName() + "` ORDER BY FIRST_SWITCHED ASC;");
			Flow single_flow = new Flow();
			LinkedList<Packet> new_packets = new LinkedList<Packet>();
			while( flows.next() ){				
				single_flow.size = flows.getInt(1);
				single_flow.no_of_packets = flows.getInt(2);
				single_flow.protocol = flows.getInt(3);
				single_flow.start_time = flows.getInt(4);

				new_packets = single_flow.convertToPackets();
				int total = 0;
				for(Packet p: new_packets){
					System.out.println(p.size + "\t" + p.priority + "\t" + p.start_time);
					total += p.size;
				}

				System.out.print("Correct individual packet sizes?\t");
				if(total == single_flow.size){
					System.out.println("yes");
				}
				else{
					System.out.println("no");
					break;
				}

				System.out.println("____________________________________________________________\n");
			}	
			// int priority = Packet.getPriority(3335);
			// System.out.println("Trial priority of port 3335 = " +priority + "\n");
		} catch (Exception e) {
			System.out.print("error connecting.\n");
			System.out.println(e);
			e.printStackTrace();
		}
	}
}