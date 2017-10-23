import java.io.FileWriter;
import java.util.LinkedList;

public class Schedule {

	/* Constants */
	final public static int FIFO = 0;
	final public static int PQ = 1;
	final public static int WFQ = 2;


	public static int process(LinkedList<Queue> priority_queues, int schedule_type, int bandwidth) {

		if (schedule_type == FIFO) {
			firstInFirstOut(priority_queues.getFirst(), bandwidth);
		} else if (schedule_type == PQ) {
			priorityQueue(priority_queues, bandwidth);
		// } else if (schedule_type == WFQ) {
		// 	weightedFairQueue();
		} else {
			return -1;
		}
		return 1;
	}

	public static void firstInFirstOut(Queue q, int bandwidth) {

		int current_buffer = 0;

		for (Packet p : q) {
			if (current_buffer + p.size < bandwidth) {
				current_buffer += p.size;				
			} else {
				q.addFirst(p);
				break;
			}
		}
	}

	public static void priorityQueue(LinkedList<Queue> priority_queues, int bandwidth) {
		for(Queue q: priority_queues){
			if(!q.isEmpty()){
				firstInFirstOut(q, bandwidth);
			}
		}
	}

	public static void weightedFairQueue() {

	}
}