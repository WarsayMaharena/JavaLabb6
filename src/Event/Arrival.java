package Event;

import SimState.Customer;
import SimState.SimState;
import SimState.Customer.*;


public class Arrival extends Event{
	   Pick pickEvent;
	   double time;
	   Customer customer;
	   
	   
	   public Arrival(SimState state, EventQueue eventqueue, double time){
		   super(state,eventqueue);
		   this.time = time;
		   customer = state.getStore().createNewCustomer();
	   }
	   
	   public void doMe() {
		   
		      state.update(this);
		       
		      this.state.getStore().addCustomer(customer);
		      
		      if(customer.getState() == CustomerState.inStore)
		      {
		         double pickTime = this.time + state.getPickTime().next();
		         
		         pickEvent = new Pick(this.state, this.eventQueue, pickTime);
		         
		         eventQueue.addEvent(pickEvent);
		      }
	   }

	@Override
	public double getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

}
