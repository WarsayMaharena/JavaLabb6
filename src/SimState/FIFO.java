package SimState;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Hanterar en kö av kundobjekt i en först in, först ut ordning. Denna klass
 * implementerar en FIFO-kö (First In, First Out) som används för att hantera
 * kunder i simuleringen. Den bygger på en LinkedList för att lagra kundobjekt
 * och tillhandahåller metoder för att lägga till, ta bort och inspektera kunder
 * i kön.
 * 
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */
public class FIFO {
	private LinkedList<Customer> elements;
	private int maxSize;

	/**
	 * Skapar en ny, tom FIFO-kö.
	 */
	public FIFO() {
		elements = new LinkedList<Customer>();
	}

	/**
	 * Returnerar antalet kunder i kön.
	 * 
	 * @return Storleken på kön.
	 */
	public int size() {
		return elements.size();
	}

	/**
	 * Returnerar den största storleken som kön har haft.
	 * 
	 * @return Den maximala storleken på kön.
	 */
	public int maxSize() {
		return maxSize;
	}

	/**
	 * Kontrollerar om kön är tom.
	 * 
	 * @return {@code true} om kön är tom, annars {@code false}.
	 */
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	/**
	 * Returnerar det första elementet i kön utan att ta bort det.
	 * 
	 * @return Det första kundobjektet i kön.
	 * @throws NoSuchElementException om kön är tom.
	 */
	public Customer first() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("Kön är tom.");
		}
		return elements.getFirst();
	}

	/**
	 * Tar bort och returnerar det första elementet i kön.
	 * 
	 * @return Det första kundobjektet som avlägsnades från kön.
	 * @throws NoSuchElementException om kön är tom.
	 */
	public Customer poll() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("Kön är tom.");
		}
		return elements.poll();
	}

	/**
	 * Lägger till ett kundobjekt i slutet av kön.
	 * 
	 * @param item Kundobjektet som ska läggas till i kön.
	 */
	public void add(Customer item) {
		elements.add(item);
		maxSize = Math.max(maxSize, elements.size());
	}

	/**
	 * Tar bort det första elementet i kön.
	 * 
	 * @throws NoSuchElementException om kön är tom.
	 */
	public void removeFirst() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("Kön är tom.");
		}
		elements.removeFirst();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < elements.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(elements.get(i));
		}
		sb.append("]");
		return sb.toString();
	}
}
