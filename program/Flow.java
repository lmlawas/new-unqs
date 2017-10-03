import java.util.LinkedList;

public class Flow {

	/* Attributes */
	public double size;
	public int no_of_packets;
	public int protocol;
	public int start_time;

	/* Constructors */
	public Flow() {
	}

	public Flow(double size, int no_of_packets, int protocol, int start_time){
		this.size = size;
		this.no_of_packets = no_of_packets;
		this.protocol = protocol;
		this.start_time = start_time;
	}

	/* Methods */
	public LinkedList<Packet> convertToPackets() {
		LinkedList<Packet> packets = new LinkedList<Packet>();
		int i, quotient = (int)size / no_of_packets;
		Packet p = new Packet(quotient, protocol, start_time);

		for (i = 0; i < no_of_packets; i++) {
			if (i != no_of_packets - 1) {
				packets.add(p);
				size = size - quotient;
			} else {
				p.size = size;
				packets.add(p);
			}
		}

		return packets;
	}
}