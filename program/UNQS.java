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
			Connection con = DriverManager.getConnection("jdbc:mysql://" + config.getIpAddress() + ":" + config.getPortNumber() + "/" + config.getDbName(), config.getUsername(), config.getPassword());

			System.out.print("successfully connected.\n");

			Statement stmt = con.createStatement();
			ResultSet times = stmt.executeQuery("select MIN(FIRST_SWITCHED), MAX(FIRST_SWITCHED) from `flows-" + config.getDatestamp() + "v4_" + config.getInterfaceName() + "`;");
			ResultSet flows;
			Flow single_flow = new Flow();
			LinkedList<Packet> new_packets = new LinkedList<Packet>();
			LinkedList<Queue> priority_queues = new LinkedList<Queue>();
			int priority;

			// create priority queues
			for(int i=0; i<3; i++){
				priority_queues.add(new Queue());
				if(config.getSchedule()==Schedule.FIFO){
					break;
				}
			}

			int t = times.getInt(1); //first switch time

			// while there are flows between the earliest and latest switch times
			while (t < times.getInt(2)) {
				flows = stmt.executeQuery("select BYTES, PACKETS, L4_DST_PORT, FIRST_SWITCHED from `flows-" + config.getDatestamp() + "v4_" + config.getInterfaceName() + "` ORDER BY FIRST_SWITCHED ASC;");

				// while there are flows at time t								
				while ( flows.next() ) {
					single_flow.size = flows.getInt(1);
					single_flow.no_of_packets = flows.getInt(2);
					single_flow.protocol = flows.getInt(3);
					single_flow.start_time = flows.getInt(4);

					new_packets = single_flow.convertToPackets(config.getSchedule());
					priority = Packet.getPriority(single_flow.protocol, config.getSchedule());

					// add new_packets to appropriate queue
					priority_queues.get(priority-1).addAll(new_packets);

					// empty list before the next iteration
					new_packets.clear();

				}

				// process queue
				Schedule.process(priority_queues, config.getSchedule());

				t++;
			}

		} catch (Exception e) {
			System.out.print("error connecting.\n");
			System.out.println(e);
			e.printStackTrace();
		}
	}
}