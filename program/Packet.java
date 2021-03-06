import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;

public class Packet {

	/* Attributes */
	public int size;
	public int priority;
	public int start_time;

	/* Constructors */
	public Packet() {
	}

	public Packet(int size, int protocol, int start_time, int schedule_type) {
		this.size = size;
		this.priority = getPriority(protocol, schedule_type);
		this.start_time = start_time;
	}

	/* Methods */
	public static int getPriority(int protocol, int schedule_type) {		
		if(schedule_type == Schedule.FIFO){
			return 1;
		}
		try {
			int i;
			for (i = 1; i < 3; i++) {
				String file_location = "../priority/" + Integer.toString(i) + ".txt";
				FileReader fp = new FileReader(file_location);
				BufferedReader br = new BufferedReader(fp);
				String line = null;

				while ((line = br.readLine()) != null) {
					if (matchCommas(line)) {
						String[] tokens = line.split(",");
						int port = Integer.parseInt(tokens[1]);
						if (protocol == port) {
							return i;
						}
					}
				}
				br.close();
				fp.close();
			}
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static boolean matchCommas(String line) {
		boolean match = false;

		match = Pattern.matches("(.*),([0-9]+)(,.*)*", line);
		return match;
	}

	public void info(){
		System.out.println("packet size:" + this.size);
		System.out.println("packet priority:" + this.priority);
		System.out.println("packet start_time:" + this.start_time + "\n");
	}

}