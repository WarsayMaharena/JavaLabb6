
package Simulator;

import java.util.Random;

import SimState.SimState;

/**
 * Optimize klassen innehåller metoder för att optimera antalet kassor i en
 * snabbköpssimulation för att minimera antalet missade kunder. Den använder
 * simuleringar baserade på olika antal kassor och olika frön för att hitta det
 * mest optimala antalet.
 * 
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */
public class Optimize {
	/**
	 * Huvudmetoden för att köra optimeringsprocessen. Den testar metod2 eller
	 * metod3
	 * 
	 * @param args kommandoradsargument används inte i denna implementering.
	 */
	public static void main(String[] args) {

		Optimize optimize = new Optimize();

		int o = 2;
		if (o == 2) {
			System.out.println("Minsta antal kassor som ger minimalt antal missade: "
					+ optimize.findOptimalNumberOfRegisters(K.SEED, K.M, K.END_TIME, K.LOW_COLLECTION_TIME,
							K.HIGH_COLLECTION_TIME, K.LOW_PAYMENT_TIME, K.HIGH_PAYMENT_TIME, K.L));

		}
		if (o == 3) {
			System.out.println(
					"Det mest troliga minimala antalet kassor: " + optimize.verifyOptimalRegistersAcrossSeeds(K.SEED));

		}

	}

	/**
	 * Kör en simulering baserad på angivna parametrar och returnerar antalet
	 * missade kunder.
	 * 
	 * @param registers    Antalet kassor i simulationen.
	 * @param seed         Fröet för slumptalsgeneratorn.
	 * @param maxCustomers Maximalt antal kunder som snabbköpet kan hantera.
	 * @param closingTime  Tiden då snabbköpet stänger.
	 * @param minPickTime  Minsta tid det tar för en kund att plocka varor.
	 * @param maxPickTime  Maximal tid det tar för en kund att plocka varor.
	 * @param minPayTime   Minsta tid det tar för en kund att betala.
	 * @param maxPayTime   Maximal tid det tar för en kund att betala.
	 * @param lambda       Ankomsthastigheten för kunder.
	 * @return Antalet missade kunder.
	 */
	public int metod1(int registers, long seed, int maxCustomers, double closingTime, double minPickTime,
			double maxPickTime, double minPayTime, double maxPayTime, double lambda) {
		SimState state = new SimState(seed, maxCustomers, registers, closingTime, minPickTime, maxPickTime, minPayTime,
				maxPayTime, lambda);

		Simulator sim = new Simulator(state, null);
		sim.run();

		return state.getStore().getCustomersTurnedAway();
	}

	/**
	 * Utför en binärsökning för att hitta det minimala antalet kassor som minimerar
	 * antalet missade kunder.
	 * 
	 * @param seed         Fröet för slumptalsgeneratorn.
	 * @param maxCustomers Maximalt antal kunder som snabbköpet kan hantera.
	 * @param closingTime  Tiden då snabbköpet stänger.
	 * @param minPickTime  Minsta tid det tar för en kund att plocka varor.
	 * @param maxPickTime  Maximal tid det tar för en kund att plocka varor.
	 * @param minPayTime   Minsta tid det tar för en kund att betala.
	 * @param maxPayTime   Maximal tid det tar för en kund att betala.
	 * @param lambda       Ankomsthastigheten för kunder.
	 * @return Det minimala antalet kassor som behövs.
	 */
	public int findOptimalNumberOfRegisters(long seed, int maxCustomers, double closingTime, double minPickTime,
			double maxPickTime, double minPayTime, double maxPayTime, double lambda) {

		int left = 1; // Minsta möjliga antal kassor.
		int right = maxCustomers; // Anta att det maximala antalet kassor är lika med max antal kunder som
									// snabbköpet kan ha.

		while (left < right) {
			int mid = left + (right - left) / 2;
			int missedCustomersWithMid = metod1(mid, seed, maxCustomers, closingTime, minPickTime, maxPickTime,
					minPayTime, maxPayTime, lambda);
			int missedCustomersWithMidPlusOne = metod1(mid + 1, seed, maxCustomers, closingTime, minPickTime,
					maxPickTime, minPayTime, maxPayTime, lambda);

			// Om antalet missade kunder inte minskar genom att lägga till ytterligare en
			// kassa, är vi på rätt spår och kan minska sökintervallet.
			if (missedCustomersWithMid <= missedCustomersWithMidPlusOne) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}

		// Vid slutet av loopen kommer left att vara lika med det minimala antalet
		// kassor som behövs för att minimera antalet missade kunder.
		return left;
	}

	/**
	 * Kör metod2 flera gånger med olika frön för att hitta det mest troliga
	 * minimala antalet kassor som minimerar antalet missade kunder.
	 * 
	 * @param seed Fröet för slumptalsgeneratorn.
	 * @return Resultatet innehållande det mest troliga minimala antalet kassor och
	 *         antalet missade kunder.
	 */
	public int verifyOptimalRegistersAcrossSeeds(long seed) {
		Random rand = new Random(seed);
		int stableCounter = 0;
		int highestMinimumRegisters = 0;

		while (stableCounter < 100) {
			long currentSeed = rand.nextLong();
			int currentMinRegisters = findOptimalNumberOfRegisters(currentSeed, K.M, K.END_TIME, K.LOW_COLLECTION_TIME,
					K.HIGH_COLLECTION_TIME, K.LOW_PAYMENT_TIME, K.HIGH_PAYMENT_TIME, K.L);

			if (currentMinRegisters > highestMinimumRegisters) {
				highestMinimumRegisters = currentMinRegisters;
				stableCounter = 0; // Nollställ räknaren eftersom vi hittade ett nytt högre värde
			} else {
				stableCounter++; // Öka räknaren eftersom det nuvarande minsta antalet stod sig
			}
		}

		return highestMinimumRegisters;
	}
}
