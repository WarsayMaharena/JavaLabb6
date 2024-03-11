package Simulator;

import Event.*;
import SimState.SimState;
import View.*;

/**
 * Hanterar körningen av simuleringen genom att kontinuerligt hämta och exekvera
 * händelser från en händelsekö till dess att ett stoppvillkor uppnås.
 * 
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */
public class Simulator {

	private SimState state; // Tillståndet av simuleringen som innehåller all relevant data.
	private EventQueue eventQueue; // Kö som hanterar schemaläggningen och exekveringen av händelser.

	/**
	 * Skapar en ny instans av Simulator med ett specifikt tillstånd och en vy.
	 * Observera att vyn inte används direkt i denna implementation men kan vara
	 * till för framtida utvidgningar.
	 * 
	 * @param state Den initiala tillståndskonfigurationen för simuleringen.
	 * @param view  Vyn som presenterar simuleringens resultat (används inte direkt
	 *              här).
	 */
	public Simulator(SimState state, View view) {
		this.state = state;
		this.eventQueue = new EventQueue(); // Initierar händelsekön.
	}

	/**
	 * Startar och kör simuleringen. Lägger till start- och stopphändelser i
	 * händelsekön och bearbetar sedan händelser sekventiellt tills simuleringen är
	 * över.
	 */
	public void run() {
		// Lägger till initiala händelser för att starta och stoppa simuleringen.
		eventQueue.addEvent(new SimStart(state, eventQueue));
		eventQueue.addEvent(new SimStop(state, eventQueue));

		// Bearbetar händelser tills simuleringen inte längre är aktiv.
		while (state.getSimRunning()) {
			Event event = eventQueue.getFirst(); // Hämtar nästa händelse att exekvera.
			eventQueue.removeFirst(); // Tar bort händelsen från kön.
			event.doMe(); // Utför händelsen.
		}
	}
}
