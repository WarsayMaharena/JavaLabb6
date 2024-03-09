// Amadeus Färdig

package View;

import Event.*;
import SimState.*;

import java.util.Observable;

public class PrintView extends View {
	private SimState state;
	private Store store;

	public PrintView(SimState state, Store store) {
		super(state, store);
		this.state = state;
		this.store = store;
	}

	private String formatNumber(double time) {
		return formatNumber(time, 1);
	}

	private String formatNumber(double number, int width) {
		String formattedNumber = String.format("%,.2f", number).replace('.', ',');
		return String.format("%" + width + "s", formattedNumber);
	}

	private void firstPrint() {
		System.out.println("PARAMETRAR");
		System.out.println("==========");
		System.out.println("Antal kassor, N..........: " + store.getRegisters());
		System.out.println("Max som ryms, M..........: " + store.getMaxCustomers());
		System.out.println("Ankomshastighet, lambda..: " + state.getLambda());
		System.out.println("Plocktider, [P_min..Pmax]: [" + formatNumber(state.getMinPickTime(), 3) + ".."
				+ formatNumber(state.getMaxPickTime(), 3) + "]");
		System.out.println("Betaltider, [K_min..Kmax]: [" + formatNumber(state.getMinPayTime(), 3) + ".."
				+ formatNumber(state.getMaxPayTime(), 3) + "]");
		System.out.println("Frö, f...................: " + state.getSeed());
		System.out.println();
		System.out.println("FÖRLOPP");
		System.out.println("=======");
		System.out.printf("%6s\t%-9s\t%4s\t%1s\t%3s\t%4s\t%1s\t%1s\t%3s\t%4s\t%5s\t%4s\t%s%n", "Tid", "Händelse",
				"Kund", "?", "led", "ledT", "I", "$", ":-(", "köat", "köT", "köar", "[Kassakö..]");
	}

	private void printEvent() {
		String eventName = state.getCurrentEvent().getName();
		String customerInfo = state.getCurrentCustomer() != null
				? String.valueOf(state.getCurrentCustomer().getCustomerID())
				: "---";
		String storeStatus = store.getIsOpen() ? "Ö" : "S";

		System.out.printf("%6s\t%-9s\t%4s\t%1s\t%3s\t%4s\t%1s\t%1s\t%2s\t%3s\t%5s\t%4s\t%s%n",
				formatNumber(state.getCurrentTime(), 6), eventName, customerInfo, storeStatus, store.getFreeRegisters(),
				formatNumber(store.getRegisterFreeTime(), 4), store.getCustomersInStore(), store.getCoinMade(),
				store.getCustomersTurnedAway(), store.getTotalCustomersInQueue(),
				formatNumber(store.getCustomerQueueTime(), 5), store.getFIFO().size(), store.getFIFO().toString());
	}

	private void lastPrint() {
		System.out.println();
		System.out.println("RESULTAT");
		System.out.println("========");
		System.out.println();
		System.out.println("1) Av " + store.getTotalCustomers() + " kunder handlade " + store.getCoinMade() + " medan "
				+ store.getCustomersTurnedAway() + " missades.");
		System.out.println();
		System.out.println("2) Total tid " + store.getRegisters() + " kassor varit lediga: "
				+ formatNumber(store.getRegisterFreeTime()).trim() + " te.");
		System.out.println("   Genomsnittlig ledig kassatid: "
				+ formatNumber(store.getRegisterFreeTime() / store.getRegisters()).trim() + " te (dvs"
				+ formatNumber(
						(store.getRegisterFreeTime() / store.getRegisters()) / (store.getLastPaymentTime()) * 100)
				+ "% av tiden från öppning tills sista kunden betalat).");
		System.out.println();
		System.out.println("3) Total tid " + store.getTotalCustomersInQueue() + " kunder tvingats köa: "
				+ formatNumber(store.getCustomerQueueTime()).trim() + " te.");
		System.out.println("   Genomsnittlig kötid: "
				+ formatNumber(store.getCustomerQueueTime() / store.getTotalCustomersInQueue()).trim() + " te.");
	}

	@Override
	public void update(Observable o, Object arg) {
		if (state.getCurrentEvent().getClass() == SimStart.class) {
			firstPrint();
			System.out.println(formatNumber(state.getCurrentTime(), 6) + "\tStart");
		} else if (state.getCurrentEvent().getClass() == SimStop.class) {
			System.out.println(formatNumber(state.getCurrentTime(), 6) + "\tStop");
			lastPrint();
		} else {
			printEvent();
		}
	}
}
