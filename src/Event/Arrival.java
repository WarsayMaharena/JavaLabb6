package Event;

import SimState.Customer;
import SimState.SimState;

public class Arrival extends Event{
	   Pick pickEvent;
	   double time;
	   Customer customer;
	   
	   public Arrival(SimState state, EventQueue eventqueue, double time){
		   super(state,eventqueue);
		   this.time = time;
		   customer = state.getSnabbKopsState().createNewCustomer();
	   }
	   
	   public void doMe() {
		   
		   
		   
	   }

}
