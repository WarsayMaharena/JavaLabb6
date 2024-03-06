package SimState;

import Event.Event;
import Time.ExponentialRandomStream;
import Time.UniformRandomStream;

public class SimState {
	   private SnabbkopsState snabbkopsstate;
	   
	   private boolean simRunning;
	   
	   private UniformRandomStream pickTime;
	   private UniformRandomStream payTime;
	   private ExponentialRandomStream arrivalTime;
	   
	   private Event currentEvent;
	   private Customer currentCustomer;
	   
	   private long seed;
	   private double minPickTime;
	   private double maxPickTime;
	   private double minPayTime;
	   private double maxPayTime;
	   private double lambda;
	   
	   public SnabbkopsState getSnabbKopsState () {
		   return snabbkopsstate;
	   }

}
