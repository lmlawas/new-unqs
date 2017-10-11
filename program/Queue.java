import java.util.LinkedList;

public class Queue extends LinkedList<Packet> {

	public int priority;

	/* Constructors */
	public Queue() {
		priority = 0;
	}

	public Queue(int priority) {
		this.priority = priority;
	}
}