package Simulator;

import SimState.SimState;
import View.PrintView;
import View.View;

/**
 * RunSim-klassen är huvudklassen för att köra simuleringar av snabbköp.
 * Beroende på vilken konfiguration som väljs skapar den en simulering med
 * specifika parametrar och kör den.
 * 
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */
public class RunSim {
	/**
	 * Startpunkten för programmet. Denna metod väljer och kör ett fördefinierat
	 * scenario baserat på värdet av variabeln 'e'.
	 * 
	 * @param args Kommandoradsargument, används inte i denna implementering.
	 */
	public static void main(String[] args) {
		int e = 2;

		if (e == 1) {

			SimState state = new SimState(1234, 5, 2, 10, 0.5, 1, 2, 3, 1);
			View view = new PrintView(state, state.getStore());

			Simulator sim = new Simulator(state, view);
			sim.run();
		}

		if (e == 2) {
			SimState state = new SimState(13, 7, 2, 8, 0.6, 0.9, 0.35, 0.6, 3);
			View view = new PrintView(state, state.getStore());

			Simulator sim = new Simulator(state, view);
			sim.run();
		}
	}

}
