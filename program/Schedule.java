import java.io.FileWriter;
import java.util.LinkedList;

public class Schedule {

	/* Constants */
	final public static int FIFO = 0;
	final public static int PQ = 1;
	final public static int WFQ = 2;


	public static int process(LinkedList<Queue> priority_queues, int schedule_type, int bandwidth) {

		// if (schedule_type == FIFO) {
		// 	firstInFirstOut();
		// } else if (schedule_type == PQ) {
		// 	priorityQueue();
		// } else if (schedule_type == WFQ) {
		// 	weightedFairQueue();
		// } else {
		// 	return -1;
		// }
		return 1;
	}

	public static void firstInFirstOut(Queue q, int bandwidth) {
		// test
		// one queue
		// bandwidth

		int current_buffer = 0;		

		while(current_buffer < bandwidth){
			Packet p = new Packet();

			if(q.peek() != null){
				p = q.removeFirst();
				current_buffer = current_buffer + p.size;
			}
		}
	}

	public static void priorityQueue() {

	}

	public static void weightedFairQueue() {

	}
}