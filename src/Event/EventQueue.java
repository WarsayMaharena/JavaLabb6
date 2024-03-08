package Event;

import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * In this class, we will add and manage events so that they are triggered at
 * the right moment using a PriorityQueue.
 *
 * @author Jesper Frisk, Shahriar Chegini, Oscar Dahlberg, Folke Forshed.
 *
 */

public class EventQueue {
	private PriorityQueue<Event> eventQueue;

	public EventQueue() {
		this.eventQueue = new PriorityQueue<>(new Comparator<Event>() {
			@Override
			public int compare(Event e1, Event e2) {
				return Double.compare(e1.getTime(), e2.getTime());
			}
		});
	}

	public int size() {
		return eventQueue.size();
	}

	public Event getFirst() {
		return eventQueue.peek();
	}

	public void removeFirst() {
		eventQueue.poll();
	}

	public void addEvent(Event newEvent) {
		eventQueue.add(newEvent);
	}

	@Override
	public String toString() {
		return eventQueue.toString();
	}
}
