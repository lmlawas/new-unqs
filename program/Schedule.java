import java.io.FileWriter;
import java.util.LinkedList;

public class Schedule {

	/* Constants */
	final public static int FIFO = 0;
	final public static int PQ = 1;
	final public static int WFQ = 2;


	public int process(int schedule_type) {

		if (schedule_type == FIFO) {
			firstInFirstOut();
		} else if (schedule_type == PQ) {
			priorityQueue();
		} else if (schedule_type == WFQ) {
			weightedFairQueue();
		} else {
			return -1;
		}
		return 1;
	}

	public void firstInFirstOut( /* Queue */ ) {
		// test
	}

	public void priorityQueue() {

	}

	public void weightedFairQueue() {

	}
}