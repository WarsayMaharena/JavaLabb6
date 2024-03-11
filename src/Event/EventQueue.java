package Event;

import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * Hanterar en prioritetskö av händelser i simuleringen.
 *
 * Denna klass använder en PriorityQueue för att hålla reda på och ordna
 * händelser baserat på när de ska inträffa. Händelser med tidigare tidpunkter
 * kommer att hanteras före de som inträffar senare, vilket möjliggör en
 * sekventiell simulering av händelseförloppet.
 *
 * Klassen tillhandahåller metoder för att lägga till, hämta och ta bort
 * händelser från kön.
 * 
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */
public class EventQueue {
	private PriorityQueue<Event> eventQueue;

	/**
	 * Skapar en ny händelsekö.
	 *
	 * Prioritetsordningen baseras på händelsernas tidpunkter, där händelser som ska
	 * inträffa tidigare ges högre prioritet.
	 */
	public EventQueue() {
		this.eventQueue = new PriorityQueue<>(new Comparator<Event>() {
			@Override
			public int compare(Event e1, Event e2) {
				return Double.compare(e1.getTime(), e2.getTime());
			}
		});
	}

	/**
	 * Hämtar storleken på händelsekön.
	 *
	 * @return Antalet händelser i kön.
	 */
	public int size() {
		return eventQueue.size();
	}

	/**
	 * Hämtar den första händelsen i kön utan att ta bort den.
	 *
	 * @return Den händelse som inträffar härnäst, eller null om kön är tom.
	 */
	public Event getFirst() {
		return eventQueue.peek();
	}

	/**
	 * Tar bort och returnerar den första händelsen i kön.
	 */
	public void removeFirst() {
		eventQueue.poll();
	}

	/**
	 * Lägger till en ny händelse i kön.
	 *
	 * @param newEvent Den händelse som ska läggas till.
	 */
	public void addEvent(Event newEvent) {
		eventQueue.add(newEvent);
	}

	/**
	 * Ger en strängrepresentation av händelsekön.
	 *
	 * @return En sträng som representerar händelsekön.
	 */
	@Override
	public String toString() {
		return eventQueue.toString();
	}
}
