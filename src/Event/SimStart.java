package Event;

import SimState.Customer;
import SimState.SimState;

public class SimStart extends Event {
	   public SimStart(SimState state, EventQueue eventQueue) {
		super(state, eventQueue);
		// TODO Auto-generated constructor stub
	}

	private CloseStore closing; 
	   private Arrival arrival;
	   
	   private double closeTime;
	   private double time; 
	   
	   public void doMe() {
		      state.update(this);
		      
		      state.getStore().setOpen(true);
		      closing = new CloseStore(this.state, eventQueue, closeTime);
		      eventQueue.addEvent(closing);
		      
		      double arrivalTime = 0;
		      while(closeTime >  arrivalTime){
			         arrivalTime = arrivalTime + state.getArrivalTime().next();
			         arrival = new Arrival(this.state, this.eventQueue, arrivalTime);
			         eventQueue.addEvent(arrival);
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
