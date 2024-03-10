package View;

import Event.*;
import SimState.*;

import java.util.Observable;

/**
 * Klass som representerar en konsolbaserad vy för att visa simuleringens
 * förlopp och resultat. Denna klass är ansvarig för att skriva ut information
 * om händelser, parametrar och resultat av simuleringen.
 * 
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */
@SuppressWarnings("deprecation")
public class PrintView extends View {
	private SimState state;
	private SnabbkopsState store;

	/**
	 * Konstruktor som skapar en ny PrintView.
	 * 
	 * @param state Simuleringens tillstånd.
	 * @param store Butikens tillstånd.
	 */
	public PrintView(SimState state, SnabbkopsState store) {
		super(state, store);
		this.state = state;
		this.store = store;
	}

	/**
	 * Overloader formatNumber nedan med en default width
	 * 
	 * @param time
	 * @return
	 */
	private String formatNumber(double time) {
		return formatNumber(time, 7);
	}

	/**
	 * Formaterar ett tal till en sträng med angiven bredd och två decimaler.
	 * 
	 * @param number Talet som ska formateras.
	 * @param width  Bredden på formaterad sträng.
	 * @return En strängrepresentation av talet med angiven bredd.
	 */
	private String formatNumber(double number, int width) {
		String formattedNumber = String.format("%,.2f", number).replace('.', ',');
		return String.format("%" + width + "s", formattedNumber);
	}

	/**
	 * Skriver ut initial information om simuleringens parametrar. Denna metod
	 * presenterar en översikt av de viktigaste parametrarna som styr simuleringen,
	 * inklusive antalet kassor, maximalt antal kunder som ryms i butiken,
	 * ankomsthastighet (lambda), plocktider och betaltider. Dessutom visas
	 * frövärdet som används för att generera slumpmässiga händelser. Metoden
	 * avslutas med att skriva ut en rubrik för den efterföljande händelseloggen.
	 * 
	 * Parametrar som visas är: - Antal kassor (N) - Max antal kunder som ryms (M) -
	 * Ankomsthastighet (lambda) - Plocktider [P_min..P_max] - Betaltider
	 * [K_min..K_max] - Simuleringens frö (f)
	 * 
	 * Efter att parametrarna presenterats skrivs en rubrik ut för den detaljerade
	 * händelseloggen som följer under simuleringens gång. Denna rubrik innehåller
	 * kolumnnamn för tidpunkt, händelsetyp, kundnummer och ytterligare detaljer
	 * relevant för simuleringens fortsatta förlopp.
	 */
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

	/**
	 * Skriver ut information om en specifik händelse under simuleringen. Denna
	 * metod använder formaterad utskrift för att visa detaljerad information om den
	 * aktuella händelsen, inklusive tidpunkt, händelsetyp, kundnummer, butikens
	 * status (öppen eller stängd), antal lediga kassor, sammanlagd ledig tid för
	 * kassor, antal kunder i butiken, totalt antal betalningar, antal kunder som
	 * avvisats, antal kunder i kö, sammanlagd kötid och kösituationen vid kassorna.
	 * 
	 * Parametrar som visas är: - Tidpunkt för händelsen - Händelsetyp (namn på
	 * händelsen) - Kundnummer (eller '---' om inte tillämpligt) - Butikens status
	 * (Öppen 'Ö' eller Stängd 'S') - Antal lediga kassor - Sammanlagd ledig tid för
	 * kassor - Antal kunder i butiken - Totalt antal betalningar (intäkter) - Antal
	 * kunder som avvisats - Antal kunder i kö - Sammanlagd kötid - Kösituationen
	 * vid kassorna (visar kundernas position i kön)
	 * 
	 * Information presenteras i en tabellform där varje händelse loggas på en ny
	 * rad med relevant information som gör det möjligt för användaren att följa med
	 * i simuleringens gång och utfall.
	 */
	private void printEvent() {
		String eventName = state.getCurrentEvent().getName();
		String customerInfo = state.getCurrentCustomer() != null
				? String.valueOf(state.getCurrentCustomer().getCustomerID())
				: "---";
		String storeStatus = store.isOpen() ? "Ö" : "S";

		System.out.printf("%6s\t%-9s\t%4s\t%1s\t%3s\t%4s\t%1s\t%1s\t%2s\t%3s\t%5s\t%4s\t%s%n",
				formatNumber(state.getCurrentTime(), 6), eventName, customerInfo, storeStatus, store.getFreeRegisters(),
				formatNumber(store.getRegisterFreeTime(), 4), store.getCustomersInStore(), store.getTotalMoney(),
				store.getCustomersTurnedAway(), store.getTotalCustomersInQueue(),
				formatNumber(store.getCustomerQueueTime(), 5), store.getRegisterLine().size(),
				store.getRegisterLine().toString());
	}

	/**
	 * Skriver ut en summering av simuleringens resultat när simuleringen avslutas.
	 * Denna metod presenterar sammanfattande statistik över simuleringens gång,
	 * inklusive: - Totalt antal kunder som besökte butiken, antalet som genomförde
	 * ett köp och antalet som missades. - Total tid kassor varit lediga och den
	 * genomsnittliga lediga kassatiden som procentandel av totala öppettiden. -
	 * Total tid kunder tvingats köa och den genomsnittliga kötiden per kund.
	 * 
	 * Informationen är indelad i tre huvudkategorier: 1) Antal kunder som handlade
	 * versus antalet som missades. 2) Total och genomsnittlig ledig tid för kassor,
	 * samt denna tid som en procentandel av den totala öppettiden. 3) Total och
	 * genomsnittlig kötid för kunder som väntade i kö.
	 * 
	 * Denna metod ger en översikt över effektiviteten i hanteringen av kunder och
	 * kassors lediga tider, vilket kan användas för att utvärdera och förbättra
	 * butiksdriften.
	 */
	private void lastPrint() {
		System.out.println();
		System.out.println("RESULTAT");
		System.out.println("========");
		System.out.println();
		System.out.println("1) Av " + store.getTotalCustomers() + " kunder handlade " + store.getTotalMoney()
				+ " medan " + store.getCustomersTurnedAway() + " missades.");
		System.out.println();
		System.out.println("2) Total tid " + store.getRegisters() + " kassor varit lediga: "
				+ formatNumber(store.getRegisterFreeTime()).trim() + " te.");
		System.out.println("   Genomsnittlig ledig kassatid: "
				+ formatNumber(store.getRegisterFreeTime() / store.getRegisters()).trim() + " te (dvs "
				+ formatNumber(
						(store.getRegisterFreeTime() / store.getRegisters()) / (store.getLastPaymentTime()) * 100)
						.trim()
				+ "% av tiden från öppning tills sista kunden betalat).");
		System.out.println();
		System.out.println("3) Total tid " + store.getTotalCustomersInQueue() + " kunder tvingats köa: "
				+ formatNumber(store.getCustomerQueueTime()).trim() + " te.");
		System.out.println("   Genomsnittlig kötid: "
				+ formatNumber(store.getCustomerQueueTime() / store.getTotalCustomersInQueue()).trim() + " te.");
	}

	/**
	 * Hanterar uppdateringar från den observerade klassen och skriver ut
	 * händelseinformation baserat på den aktuella händelsen. Denna metod reagerar
	 * på notifikationer om händelseförändringar i simuleringen och skriver ut
	 * relevant information: - Vid simuleringens start skrivs initiala parametrar
	 * och "Start" meddelandet. - Vid simuleringens slut skrivs "Stop" och en
	 * sammanfattning av simuleringens resultat. - För alla andra händelser skrivs
	 * specifik händelseinformation.
	 * 
	 * Metoden använder sig av tre privata metoder för att hantera olika
	 * utskriftsscenario: - firstPrint() för att skriva ut initiala parametrar vid
	 * start. - astPrint() för att skriva ut en sammanfattning av simuleringens
	 * resultat vid slut. - printEvent() för att skriva ut information om pågående
	 * händelser.
	 * 
	 * Genom denna metod presenteras en dynamisk vy av simuleringens framsteg och
	 * resultat, vilket gör det möjligt för användaren att följa med i hur
	 * simuleringen utvecklar sig över tid.
	 * 
	 * @param o   Den observerade klassen som denna metod lyssnar på.
	 * @param arg Ett argument som skickas från den observerade klassen, används
	 *            inte här.
	 */
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
