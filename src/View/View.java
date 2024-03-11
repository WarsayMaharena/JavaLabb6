package View;

import java.util.Observable;
import java.util.Observer;

import SimState.SimState;
import SimState.SnabbkopsState;

/**
 * Abstrakt klass som representerar en vy. Denna klass observerar ändringar i
 * simuleringstillståndet och uppdateras därefter. Varje konkret vyklass ska
 * implementera metoden update för att hantera uppdateringar specifikt.
 * 
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */
@SuppressWarnings("deprecation")
public abstract class View implements Observer {

	/**
	 * Simuleringsstatus som denna vy observerar.
	 */
	protected SimState state;

	/**
	 * Specifikt tillstånd för snabbköpet som observeras av denna vy.
	 */
	protected SnabbkopsState store;

	/**
	 * Konstruktor som initierar en ny vy och registrerar den som en observatör för
	 * det angivna simuleringstillståndet.
	 * 
	 * @param state Simuleringsstatus som denna vy ska observera.
	 * @param store Specifikt tillstånd för snabbköpet som denna vy observerar.
	 */
	public View(SimState state, SnabbkopsState store) {
		this.state = state;
		this.store = store;
		state.addObserver(this); // Registrera denna vy som en observatör för simuleringstillståndet.
	}

	/**
	 * Uppdaterar vyn baserat på ändringar i det observerade objektet. Denna metod
	 * måste implementeras av alla konkreta vyklasser.
	 * 
	 * @param o   Det observerade objektet.
	 * @param arg Argument som skickas vid notifikation om uppdatering.
	 */
	@Override
	abstract public void update(Observable o, Object arg);
}
