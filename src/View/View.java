package View;

import java.util.Observable;
import java.util.Observer;

import State.State;
import State.Store;

/**
 * En abstrakt bas för alla vy-klasser i simuleringen som implementerar Observer
 * gränssnittet. Denna klass fungerar som en brygga mellan simuleringens
 * tillstånd och dess presentation till användaren, genom att lyssna på
 * förändringar i simuleringens tillstånd och uppdatera användargränssnittet
 * därefter. Varje konkret subklass av View måste implementera update() metoden
 * för att definiera hur vy-uppdateringar hanteras när observerade objekt
 * notifierar om förändringar. Konstruktorn tar emot en referens till
 * simuleringens tillstånd och en butik, och registrerar vy-objektet som en
 * observatör till tillståndet, vilket möjliggör automatiska uppdateringar vid
 * förändringar.
 * 
 * @param state En referens till simuleringens tillstånd som denna vy kommer att
 *              observera.
 * @param store En referens till butiken som denna vy kommer att presentera
 *              information om.
 */
@SuppressWarnings("deprecation")
public abstract class View implements Observer {

	State state;
	Store store;

	public View(State state, Store store) {
		state.addObserver(this);
	}

	/**
	 * Hanterar notifieringar om uppdateringar från observerade objekt. Konkreta
	 * implementationer av denna metod ska definiera hur vy-uppdateringar genomförs
	 * baserat på förändringar i simuleringens tillstånd.
	 * 
	 * @param o   Det observerade objektet som notifierar om förändring.
	 * @param arg Ett argument skickat av det observerade objektet, kan användas för
	 *            att föra över specifik information.
	 */
	@Override
	abstract public void update(Observable o, Object arg);

}
