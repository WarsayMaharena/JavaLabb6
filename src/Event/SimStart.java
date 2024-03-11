package Event;

import SimState.*;

/**
 * Initierar starten av simuleringen genom att skapa och schemalägga de
 * inledande händelserna som öppnar snabbköpet och genererar ankomster av kunder
 * till snabbköpet.
 * 
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */
public class SimStart extends Event {
	private CloseStore closing;
	private Arrival arrival;
	private double closeTime;
	private double time;

	/**
	 * Skapar en start-händelse för simuleringen.
	 * 
	 * @param state      Den aktuella tillståndet av simuleringen.
	 * @param eventQueue Händelsekön där framtida händelser placeras.
	 */
	public SimStart(SimState state, EventQueue eventQueue) {
		super(state, eventQueue);
		this.time = 0d;
		this.closeTime = state.getStore().getClosingTime();
	}

	/**
	 * Utför start-händelsen genom att öppna snabbköpet och schemalägga den första
	 * stängningshändelsen samt generera ankomsthändelser för kunder.
	 */
	public void doMe() {
		state.update(this);

		state.getStore().setStoreOpen(true);
		closing = new CloseStore(this.state, eventQueue, closeTime);
		eventQueue.addEvent(closing);

		double arrivalTime = 0;
		while (closeTime > arrivalTime) {
			arrivalTime += state.getArrivalTime().next();
			arrival = new Arrival(this.state, this.eventQueue, arrivalTime);
			eventQueue.addEvent(arrival);
		}
	}

	/**
	 * Hämtar tiden för händelsen.
	 * 
	 * @return Tiden då händelsen inträffar.
	 */
	public double getTime() {
		return time;
	}

	/**
	 * Hämtar händelsens namn.
	 * 
	 * @return En sträng som representerar händelsens namn.
	 */
	public String getName() {
		return "Start";
	}

	/**
	 * Eftersom denna händelse inte är kopplad till någon specifik kund returneras
	 * null.
	 * 
	 * @return null eftersom ingen specifik kund är associerad med start-händelsen.
	 */
	public Customer getCustomer() {
		return null;
	}
}
