package Event;

import SimState.Customer;
import SimState.SimState;

/**
 * Representerar händelsen för att stoppa simuleringen. Denna händelse markerar
 * slutet på simuleringen. När denna händelse utförs kommer simuleringen att
 * upphöra att köra genom att sätta simuleringskörningens tillstånd till
 * avstängt.
 *
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */
public class SimStop extends Event {
	private double time;

	/**
	 * Skapar en ny SimStop-händelse.
	 * 
	 * @param state      Simuleringens tillstånd.
	 * @param eventQueue Händelsekön där händelsen ska läggas till.
	 */
	public SimStop(SimState state, EventQueue eventQueue) {
		super(state, eventQueue);
		this.time = 999.0; // Standardtid satt för att markera slutet på simuleringen.
	}

	/**
	 * Utför stopphändelsen. Stänger av simuleringen genom att uppdatera dess
	 * körningstillstånd.
	 */
	@Override
	public void doMe() {
		state.update(this);
		state.turnOffSimRunning(); // Sätter simuleringskörningstillståndet till avstängt.
	}

	/**
	 * Hämtar tiden för händelsen.
	 * 
	 * @return Tiden då simuleringen stoppas.
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
		return "Stop";
	}

	/**
	 * Hämtar kunden som är involverad i händelsen. Eftersom SimStop-händelsen inte
	 * är associerad med någon specifik kund, returnerar denna metod null.
	 * 
	 * @return null, eftersom ingen specifik kund är involverad i denna händelse.
	 */
	@Override
	public Customer getCustomer() {
		return null;
	}
}
