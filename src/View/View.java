// Amadeus Färdig

package View;

import java.util.Observable;
import java.util.Observer;

import SimState.SimState;
import SimState.Store;

public abstract class View implements Observer {

    protected SimState state;
    protected Store store;

    public View(SimState state, Store store) {
        this.state = state;
        this.store = store;
        state.addObserver(this);
    }


    @Override
    abstract public void update(Observable o, Object arg);
}
