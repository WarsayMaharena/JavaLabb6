package Event;

import SimState.*;

/**
 * Representerar en ankomsthändelse i simuleringen.
 * Denna händelse utlöses när en kund anländer till butiken.
 * Vid utförande uppdaterar den simuleringsstatusen, lägger till kunden i butiken,
 * och schemalägger en ny Pick-händelse om kunden befinner sig i butiken.
 * 
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén, Arian Asghari
 */

public class Arrival extends Event {


    /** Händelsen som representerar kundens nästa åtgärd att plocka varor. */
    Pick pickEvent;
    
    /** Simuleringstiden då händelsen inträffar. */
    double time;
    
    /** Kunden som är associerad med denna ankomsthändelse. */
    Customer customer;
    
    /**
     * Konstruerar en ankomsthändelse.
     *
     * @param state Det aktuella tillståndet i simuleringen.
     * @param eventQueue Händelsekön där nya händelser läggs till.
     * @param time Simuleringstiden då kunden anländer.
     */
	
	public Arrival(SimState state, EventQueue eventQueue, double time) {
		super(state, eventQueue);
		this.time = time;
		customer = state.getStore().createNewCustomer();
	}

    /**
     * Utför åtgärderna för ankomsthändelsen.
     * Uppdaterar simuleringsstatusen med denna händelse, lägger till den ankommande kunden i butiken,
     * och schemalägger en ny Pick-händelse för kunden om de befinner sig i butiken.
     */
	
	public void doMe() {

		state.update(this);

		this.state.getStore().addCustomer(customer);

		if (customer.getState() == Customer.CustomerState.inStore) {
			double pickTime = this.time + state.getPickTime().next();

			pickEvent = new Pick(this.state, this.eventQueue, customer, pickTime);

			eventQueue.addEvent(pickEvent);
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
	 * Hämtar kunden associerad med händelsen.
	 * 
	 * @return Kunden som utför plockningen.
	 */

	public Customer getCustomer() {
		return customer;
	}
	
	/**
	 * Hämtar händelsens namn.
	 * 
	 * @return En sträng som representerar händelsens namn.
	 */
	
	public String getName() {
		return "Ankomst";
	}
}
