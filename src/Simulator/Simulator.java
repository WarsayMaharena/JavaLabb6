package Simulator;

import Event.*;
import SimState.SimState;
import View.*;

public class Simulator {

	private SimState state;
	private EventQueue eventQueue;
	public Simulator(SimState state, View view) {
		
		this.state = state;
		this.eventQueue = new EventQueue();
	}

	public void run() {

		eventQueue.addEvent(new SimStart(state, eventQueue));
		eventQueue.addEvent(new SimStop(state, eventQueue));

		while (state.getSimRunning()) {
			Event event = eventQueue.getFirst();
			eventQueue.removeFirst();
			event.doMe();
		}
	}
}
