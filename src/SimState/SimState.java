package SimState;

import java.util.Observable;

import Event.*;

/**
 * Hanterar det övergripande tillståndet för simuleringen av en
 * snabbköpssituation. Denna klass ansvarar för att hålla reda på aktuell tid,
 * generera händelser baserat på slumpmässiga tidsintervaller, och uppdatera
 * snabbköpstillståndet baserat på dessa händelser. Den använder sig av olika
 * slumpgeneratorer för att simulera ankomst, plockning och betalningstider.
 * 
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */
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

	/**
	 * * Konstruktorn för SimState som initialiserar simuleringens tillstånd med
	 * angivna parametrar. Denna metod initierar simuleringen genom att sätta
	 * startvärden för tid, definiera slumpmässiga tidsintervaller för
	 * kundaktiviteter och skapa ett nytt snabbköpstillstånd baserat på de angivna
	 * parametrarna. Den använder sig av UniformRandomStream för att generera tider
	 * för plockning och betalning samt ExponentialRandomStream för att generera
	 * ankomsttider för kunderna.
	 *
	 * 
	 * @param seed         Startvärdet för slumpgeneratorn.
	 * @param maxCustomers Maximala antalet kunder som snabbköpet kan hantera.
	 * @param registers    Antalet kassor i snabbköpet.
	 * @param closingTime  Tiden då snabbköpet stänger.
	 * @param minPickTime  Minsta tiden det tar för en kund att plocka varor.
	 * @param maxPickTime  Maximala tiden det tar för en kund att plocka varor.
	 * @param minPayTime   Minsta tiden det tar för en kund att betala.
	 * @param maxPayTime   Maximala tiden det tar för en kund att betala.
	 * @param lambda       Parametern för exponentialfördelningen av kundankomster.
	 */

	public SimState(long seed, int maxCustomers, int registers, double closingTime, double minPickTime,
			double maxPickTime, double minPayTime, double maxPayTime, double lambda) {

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

	/**
	 * Hämtar det aktuella snabbköpstillståndet. Denna metod ger åtkomst till det
	 * aktuella snabbköpstillståndet som hanterar alla relevanta data och händelser
	 * inom snabbköpet.
	 * 
	 * @return Det aktuella snabbköpstillståndet.
	 */
	public SnabbkopsState getStore() {
		return snabbkopsstate;
	}

	/**
	 * Kontrollerar om simuleringen är igång. Denna metod returnerar en boolesk
	 * värde som indikerar om simuleringen för närvarande körs.
	 * 
	 * @return true om simuleringen är igång, annars false.
	 */
	public boolean getSimRunning() {
		return simRunning;
	}

	/**
	 * Stänger av simuleringens körning. Denna metod stänger av simuleringens
	 * körning genom att sätta simRunning-variabeln till false.
	 */
	public void turnOffSimRunning() {
		simRunning = false;
	}

	/**
	 * Hämtar slumpgeneratorn för plocktiden. Denna metod returnerar en
	 * slumpgenerator som används för att generera slumpmässiga tider för hur länge
	 * kunder plockar varor.
	 * 
	 * @return En slumpgenerator för plocktiden.
	 */
	public UniformRandomStream getPickTime() {
		return pickTime;
	}

	/**
	 * Hämtar slumpgeneratorn för betalningstiden. Denna metod returnerar en
	 * slumpgenerator som används för att generera slumpmässiga tider för hur länge
	 * kunder betalar vid kassan.
	 * 
	 * @return En slumpgenerator för betalningstiden.
	 */
	public UniformRandomStream getPayTime() {
		return payTime;
	}

	/**
	 * Hämtar slumpgeneratorn för ankomsttiden. Denna metod returnerar en
	 * slumpgenerator som används för att generera slumpmässiga tider för när nya
	 * kunder anländer till snabbköpet.
	 * 
	 * @return En slumpgenerator för ankomsttiden.
	 */
	public ExponentialRandomStream getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Hämtar den nuvarande tiden i simuleringen. Denna metod returnerar den
	 * nuvarande tiden i simuleringen, vilket är tiden för den senast genomförda
	 * händelsen.
	 * 
	 * @return Den nuvarande tiden i simuleringen.
	 */
	public double getCurrentTime() {
		return currentTime;
	}

	/**
	 * Uppdaterar simuleringstillståndet baserat på en händelse. Denna metod
	 * uppdaterar simuleringens nuvarande tillstånd baserat på den givna händelsen.
	 * Den hanterar uppdatering av tid, kund, och simuleringslogik beroende på
	 * händelsetyp.
	 * 
	 * @param thisEvent Den händelse som ska bearbetas.
	 */
	public void update(Event thisEvent) {
		currentEvent = thisEvent;
		currentCustomer = thisEvent.getCustomer();

		lastEventTime = currentTime;
		currentTime = thisEvent.getTime();

		if (thisEvent.getClass() != SimStop.class) {
			snabbkopsstate.setRegisterFreeTime(
					snabbkopsstate.getRegisterFreeTime() + (timeBetweenEvent() * snabbkopsstate.getFreeRegisters()));
			snabbkopsstate.setCustomerQueueTime(snabbkopsstate.getCustomerQueueTime()
					+ (timeBetweenEvent() * snabbkopsstate.getRegisterLine().size()));

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

	/**
	 * Hämtar den nuvarande händelsen i simuleringen. Returnerar den händelse som
	 * senast bearbetades eller som för närvarande bearbetas i simuleringen.
	 * 
	 * @return Den nuvarande händelsen.
	 */
	public Event getCurrentEvent() {
		return currentEvent;
	}

	/**
	 * Hämtar den nuvarande kunden i den nuvarande händelsen. Returnerar kunden
	 * associerad med den nuvarande händelsen i simuleringen.
	 * 
	 * @return Den nuvarande kunden, eller null om ingen kund är associerad med
	 *         händelsen.
	 */
	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	/**
	 * Beräknar tiden mellan nuvarande händelse och den föregående händelsen.
	 * Används för att justera simuleringstid och andra tidsberoende variabler.
	 * 
	 * @return Tiden mellan den nuvarande och den föregående händelsen.
	 */
	private double timeBetweenEvent() {
		return currentTime - lastEventTime;
	}

	/**
	 * Hämtar minimiplocktiden. Returnerar den kortaste tiden det tar för en kund
	 * att plocka sina varor.
	 * 
	 * @return Minimiplocktiden.
	 */
	public double getMinPickTime() {
		return minPickTime;
	}

	/**
	 * Hämtar maximiplocktiden. Returnerar den längsta tiden det tar för en kund att
	 * plocka sina varor.
	 * 
	 * @return Maximiplocktiden.
	 */
	public double getMaxPickTime() {
		return maxPickTime;
	}

	/**
	 * Hämtar minimibetaltiden. Returnerar den kortaste tiden det tar för en kund
	 * att betala vid kassan.
	 * 
	 * @return Minimibetaltiden.
	 */
	public double getMinPayTime() {
		return minPayTime;
	}

	/**
	 * Hämtar maximibetaltiden. Returnerar den längsta tiden det tar för en kund att
	 * betala vid kassan.
	 * 
	 * @return Maximibetaltiden.
	 */
	public double getMaxPayTime() {
		return maxPayTime;
	}

	/**
	 * Hämtar ankomsthastigheten (lambda). Returnerar värdet på lambda som används
	 * för att generera ankomsttider för kunder.
	 * 
	 * @return Ankomsthastigheten.
	 */
	public double getLambda() {
		return lambda;
	}

	/**
	 * Hämtar simuleringsfröet. Returnerar det frö som används för att initiera alla
	 * slumpmässiga processer i simuleringen för reproducerbarhet.
	 * 
	 * @return Simuleringsfröet.
	 */
	public long getSeed() {
		return seed;
	}

}
