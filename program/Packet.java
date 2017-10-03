import java.io.BufferedReader;
import java.io.FileReader;

public class Packet {

	/* Attributes */
	public double size;
	public int priority;
	public int start_time;

	/* Constructors */
	public Packet() {
	}

	public Packet(double size, int protocol, int start_time) {
		this.size = size;
		this.priority = getPriority(protocol);
		this.start_time = start_time;
	}

	/* Methods */
	public static int getPriority(int protocol) {
		try {
			int i;
			for (i = 1; i < 3; i++) {
				String file_location = "../priority/" + Integer.toString(i) + ".txt";
				FileReader fp = new FileReader(file_location);
				BufferedReader br = new BufferedReader(fp);
				String line = null;

				while ((line = br.readLine()) != null) {
					System.out.println(line);
					String[] tokens = line.split(",");
					int port = Integer.parseInt(tokens[1]);
					if (protocol == port) {
						return i;
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

}