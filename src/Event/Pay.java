
package Event;

import SimState.*;

/**
 * Representerar en betalningshändelse i simuleringen.
 *
 * Denna händelse inträffar när en kund genomför sin betalning i kassan. Det
 * uppdaterar simuleringens tillstånd genom att registrera betalningen, frigöra
 * en kassaregister och, om det finns kö, initiera nästa kunds betalning.
 * 
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */

public class Pay extends Event {
	private Customer customer;
	private double time;

	/**
	 * Skapar en ny betalningshändelse.
	 * 
	 * @param state      Simuleringens tillstånd.
	 * @param eventQueue Händelsekön.
	 * @param time       Tidpunkten då betalningen sker.
	 * @param customer   Kunden som betalar.
	 */

	public Pay(SimState state, EventQueue eventQueue, double time, Customer customer) {
		super(state, eventQueue);
		this.customer = customer;
		this.time = time;
	}

	/**
	 * Utför betalningshändelsen.
	 *
	 * Registrerar kundens betalning, frigör en kassaregister och, om det finns kö,
	 * initierar nästa kunds betalning.
	 *
	 */
	@Override
	public void doMe() {
		state.update(this);
		state.getStore().addMoney(); // Anta att varje betalning ökar butikens intäkter med 1 enhet.
		state.getStore().removeCustomer(customer);
		state.getStore().changeRegisterAvailability(false); // Ockupera en kassaregister.

		if (!state.getStore().getRegisterLine().isEmpty()) {
			Customer nextCustomer = state.getStore().getRegisterLine().poll();
			double nextPayTime = this.time + state.getPayTime().next();
			Pay nextPayEvent = new Pay(this.state, this.eventQueue, nextPayTime, nextCustomer);
			eventQueue.addEvent(nextPayEvent);
			state.getStore().changeRegisterAvailability(true); // Frigör en kassaregister för nästa kund.
		}
	}

	/**
	 * Hämtar tiden för händelsen.
	 * 
	 * @return Tiden då betalningen sker.
	 */
	@Override
	public double getTime() {
		return time;
	}

	/**
	 * Hämtar händelsens namn.
	 * 
	 * @return En sträng som representerar händelsens typ.
	 */
	@Override
	public String getName() {
		return "Betalning";
	}

	/**
	 * Hämtar kunden som är involverad i händelsen.
	 * 
	 * @return Kunden som betalar.
	 */
	@Override
	public Customer getCustomer() {
		return customer;
	}

}
