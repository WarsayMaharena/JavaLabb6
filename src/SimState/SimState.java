package SimState;

import java.util.Observable;

import Event.*;


@SuppressWarnings("deprecation")
public class SimState extends Observable {
	private SnabbkopsState snabbkopsstate;

	private boolean simRunning;

	private double currentTime;
	private double lastEventTime;

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

	public SimState(long seed, int maxCustomers, int registers, double closingTime, double minPickTime, double maxPickTime,
			double minPayTime, double maxPayTime, double lambda) {

		simRunning = true;

		currentTime = 0;
		lastEventTime = 0;

		pickTime = new UniformRandomStream(minPickTime, maxPickTime, seed);
		payTime = new UniformRandomStream(minPayTime, maxPayTime, seed);
		arrivalTime = new ExponentialRandomStream(lambda, seed);


		snabbkopsstate = new SnabbkopsState(maxCustomers, registers, closingTime);

		this.seed = seed;
		this.minPickTime = minPickTime;
		this.maxPickTime = maxPickTime;
		this.minPayTime = minPayTime;
		this.maxPayTime = maxPayTime;
		this.lambda = lambda;
	}


	public SnabbkopsState getStore() {
		return snabbkopsstate;
	}

	
	public boolean getSimRunning() {
		return simRunning;
	}


	public void turnOffSimRunning() {
		simRunning = false;
	}

	public UniformRandomStream getPickTime() {
		return pickTime;
	}


	public UniformRandomStream getPayTime() {
		return payTime;
	}


	public ExponentialRandomStream getArrivalTime() {
		return arrivalTime;
	}


	public double getCurrentTime() {
		return currentTime;
	}


	public void update(Event thisEvent) {
		currentEvent = thisEvent;
		currentCustomer = thisEvent.getCustomer();

		lastEventTime = currentTime;
		currentTime = thisEvent.getTime();

		if (thisEvent.getClass() != SimStop.class) {
			snabbkopsstate.setRegisterFreeTime(snabbkopsstate.getRegisterFreeTime() + (timeBetweenEvent() * snabbkopsstate.getFreeRegisters()));
			snabbkopsstate.setCustomerQueueTime(snabbkopsstate.getCustomerQueueTime() + (timeBetweenEvent() * snabbkopsstate.getRegisterLine().size()));

			if (thisEvent.getClass() == Pay.class) {
				snabbkopsstate.setLastPaymentTime(thisEvent.getTime());
			}

		} else {
			snabbkopsstate.setRegisterFreeTime(snabbkopsstate.getRegisterFreeTime()
					- ((lastEventTime - snabbkopsstate.getLastPaymentTime()) * snabbkopsstate.getFreeRegisters()));
		}

		setChanged();
		notifyObservers();
	}


	public Event getCurrentEvent() {
		return currentEvent;
	}


	public Customer getCurrentCustomer() {
		return currentCustomer;
	}


	private double timeBetweenEvent() {
		return currentTime - lastEventTime;
	}


	public double getMinPickTime() {
		return minPickTime;
	}


	public double getMaxPickTime() {
		return maxPickTime;
	}


	public double getMinPayTime() {
		return minPayTime;
	}


	public double getMaxPayTime() {
		return maxPayTime;
	}


	public double getLambda() {
		return lambda;
	}


	public long getSeed() {
		return seed;
	}

}
