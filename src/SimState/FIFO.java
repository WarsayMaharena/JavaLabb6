package State;

import java.util.LinkedList;

import java.util.NoSuchElementException;

public class FIFO {
	// En lista för att hålla elementen i kön.
	private LinkedList<Object> elements;
	private int maxSize;

	// Konstruktören skapar en tom kö.
	public FIFO() {
		elements = new LinkedList<>();
		maxSize = 0;
	}

	// Metoden size returnerar antalet element i kön.
	public int size() {
		return elements.size();
	}

	// Metoden maxSize returnerar den största storleken som kön någonsin haft.
	public int maxSize() {
		return maxSize;
	}

	// Metoden isEmpty kontrollerar om kön är tom.
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	// Metoden first returnerar det första elementet i kön utan att ta bort det.
	public Object first() {
		if (elements.isEmpty()) {
			throw new NoSuchElementException("Kön är tom");
		}
		return elements.get(0);
	}

	// Metoden equals jämför detta FIFO-objekt med ett annat för att bestämma
	// likhet.
	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;


		if (!(o instanceof FIFO))
			throw new ClassCastException("Inte FIFO");

		FIFO fifo = (FIFO) o;

		if (this.size() != fifo.size())
			return false;

		// Iterera genom elementen i de två köerna för att jämföra dem ett och ett.
				for (int i = 0; i < this.size(); i++) {
					Object o1 = this.elements.get(i);
					Object o2 = fifo.elements.get(i);

					if (o1 == null) {
					    if (o2 != null) {

					        return false;
					    }

					} else if (!o1.equals(o2)) {

					    return false;
					}
				}

		return true;
	}

	// Metoden add lägger till ett element i slutet av kön.
	public void add(Object item) {
		elements.add(item);
		maxSize = Math.max(maxSize, elements.size());
	}

	// Metoden removeFirst tar bort det första elementet i kön.
	public void removeFirst() {
		if (elements.isEmpty()) {
			throw new NoSuchElementException("Kön är tom.");
		}
		elements.removeFirst();
	}

	// Metoden toString returnerar en strängrepresentation av kön.
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
