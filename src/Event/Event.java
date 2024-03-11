package Event;

import SimState.*;

/**
 * Abstrakt klass som representerar grunden för en händelse inom simuleringen.
 *
 * Varje specifik händelse ska ärva från denna klass och implementera de
 * abstrakta metoderna för att definiera händelsens beteende, tidpunkt, namn och
 * associerad kund.
 *
 * Denna klass är en central del av händelsehanteringssystemet i simuleringen,
 * där varje händelse kan påverka simuleringens tillstånd på olika sätt.
 *
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */
abstract public class Event {
	protected SimState state;
	protected EventQueue eventQueue;

	/**
	 * Konstruktor för att skapa en händelse.
	 *
	 * @param state      Den aktuella simuleringens tillstånd.
	 * @param eventQueue Händelsekön där händelsen kan läggas till.
	 */
	public Event(SimState state, EventQueue eventQueue) {
		this.eventQueue = eventQueue;
		this.state = state;
	}

	/**
	 * Utför händelsen och dess effekter på simuleringens tillstånd.
	 */
	abstract public void doMe();

	/**
	 * Hämtar tiden då händelsen ska inträffa.
	 *
	 * @return Tiden då händelsen inträffar som en double.
	 */
	abstract public double getTime();

	/**
	 * Hämtar namnet på händelsen.
	 *
	 * @return En sträng som representerar händelsens namn.
	 */
	abstract public String getName();

	/**
	 * Hämtar den kund som är associerad med händelsen.
	 *
	 * @return Den kund som är associerad med händelsen, eller null om ingen kund är
	 *         associerad.
	 */
	abstract public Customer getCustomer();
}
