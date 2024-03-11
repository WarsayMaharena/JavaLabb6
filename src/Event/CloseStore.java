package Event;

import SimState.Customer;
import SimState.SimState;

/**
 * Representerar händelsen när butiken stänger i simuleringen. Denna händelse
 * ansvarar för att sätta butikens tillstånd till stängt och uppdatera
 * simuleringens tillstånd därefter.
 *
 * När denna händelse utförs kommer inga fler kunder att tillåtas att komma in i
 * butiken.
 *
 * Denna klass är en del av simuleringssystemet för att modellera händelser i en
 * butik.
 * 
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */

public class CloseStore extends Event {
	private double time; // Tiden då butiken ska stängas

	/**
	 * Skapar en ny stängningshändelse.
	 * 
	 * @param state      Simuleringsstatet som händelsen påverkar.
	 * @param eventQueue Eventkön där händelsen kan läggas till för framtida
	 *                   exekvering.
	 * @param time       Tiden då händelsen ska inträffa.
	 */

	public CloseStore(SimState state, EventQueue eventQueue, double time) {
		super(state, eventQueue);
		this.time = time;
	}

	/**
	 * Utför stängningshändelsen genom att sätta butikens tillstånd till stängt.
	 */

	@Override
	public void doMe() {
		state.update(this);
		state.getStore().setStoreOpen(false);
	}

	/**
	 * Hämtar tiden då händelsen inträffar.
	 * 
	 * @return Tiden för händelsen.
	 */

	@Override
	public double getTime() {
		return time;
	}

	/**
	 * Hämtar namnet på händelsen.
	 * 
	 * @return En sträng som representerar händelsens namn.
	 */

	@Override
	public String getName() {
		return "Stänger";
	}

	/**
	 * Denna händelse är inte associerad med någon specifik kund.
	 * 
	 * @return null eftersom ingen kund är direkt associerad med denna händelse.
	 */

	@Override
	public Customer getCustomer() {
		return null;
	}

}
