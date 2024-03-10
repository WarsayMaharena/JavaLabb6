package SimState;

/**
 * Skapar nya kundobjekt för simuleringen. Denna fabriksklass ansvarar för att
 * generera nya kundinstanser med unika identifierare. Den säkerställer att
 * varje kund som skapas har ett unikt ID i hela simuleringens livscykel.
 * 
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */
public class CustomerFactory {
	/**
	 * Skapar och returnerar en ny kund. Varje anrop till denna metod genererar en
	 * ny kund med ett unikt ID. Kundens initialtillstånd är 'notInStore'.
	 * 
	 * @return En ny kundinstans med unikt ID.
	 */
	public Customer createCustomer() {
		return new Customer();
	}
}
