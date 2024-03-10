package Event;

import SimState.Customer;
import SimState.SimState;

public class Pick extends Event{
	
	double time;
	Pay payEvent;
	Customer customer;

	public Pick(SimState state, EventQueue eventqueue, double time) {
		   super(state,eventqueue);
		   this.time = time;
		   
		   	}

	@Override
	public void doMe() {
		// TODO Auto-generated method stub
	      state.update(this);
	      
	      if(state.getStore().getFreeRegisters() > 0)
	      {
	         double payTime = this.time + state.getPayTime().next();
	         
	         payEvent = new Pay(this.state, this.eventQueue, payTime, customer);
	         eventQueue.addEvent(payEvent);
	         state.getStore().changeRegisterAvailability(true);
	      }
	      
	      else 
	      {
	         state.getStore().getRegisterLine().add(customer);
	         state.getStore().addTotalCustomersInQueue();
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
