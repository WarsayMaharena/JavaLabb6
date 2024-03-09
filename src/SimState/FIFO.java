// Amadeus Färdig

package SimState;

import java.util.LinkedList;

import java.util.NoSuchElementException;

public class FIFO {
	private LinkedList<Customer> elements;
	private int maxSize;

	public FIFO() {
		elements = new LinkedList<Customer>();
	}

	public int size() {
		return elements.size();
	}

	public int maxSize() {
		return maxSize;
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}

	public Object first() {
		if (elements.isEmpty()) {
			throw new NoSuchElementException("Kön är tom");
		}
		return elements.get(0);
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (!(o instanceof FIFO))
			throw new ClassCastException("Inte FIFO");

		FIFO fifo = (FIFO) o;

		if (this.size() != fifo.size())
			return false;

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

	public Customer poll() {
		return elements.poll();
	}

	public void add(Customer item) {
		elements.add(item);
		maxSize = Math.max(maxSize, elements.size());
	}

	public void removeFirst() {
		if (elements.isEmpty()) {
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
