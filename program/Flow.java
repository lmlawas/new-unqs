import java.util.LinkedList;

public class Flow {

	/* Attributes */
	public int size;
	public int no_of_packets;
	public int protocol;
	public int start_time;

	/* Constructors */
	public Flow() {
	}

	public Flow(int size, int no_of_packets, int protocol, int start_time) {
		this.size = size;
		this.no_of_packets = no_of_packets;
		this.protocol = protocol;
		this.start_time = start_time;
	}

	/* Methods */
	public LinkedList<Packet> convertToPackets() {
		LinkedList<Packet> packets = new LinkedList<Packet>();
		int i, quotient = size / no_of_packets, remainder = size % no_of_packets;

		for (i = 0; i < no_of_packets; i++) {
			if (size % no_of_packets > 0 && i == no_of_packets - 1) {
				packets.add(new Packet(quotient + remainder, protocol, start_time));
			} else {
				packets.add(new Packet(quotient, protocol, start_time));
			}
		}

		return packets;
	}
}