// Amadeus Färdig

package Event;

import SimState.Customer;
import SimState.SimState;

public class CloseStore extends Event {
	private double time;

	public CloseStore(SimState state, EventQueue eventQueue, double time) {
		super(state, eventQueue);
		this.time = time;
	}

	@Override
	public void doMe() {
		state.update(this);
		state.getStore().setOpen(false);
	}

	@Override
	public double getTime() {
		return time;
	}

	@Override
	public String getName() {
		return "Stänger";
	}

	@Override
	public Customer getCustomer() {
		return null;
	}

}
