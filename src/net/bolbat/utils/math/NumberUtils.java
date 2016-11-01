package net.bolbat.utils.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility with some helper functionality related to numbers.
 *
 * @author Alexandr Bolbat
 */
public final class NumberUtils {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private NumberUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Compare two instances of {@link Number} between each other.
	 *
	 * @param first
	 * 		first {@link Number}, can't be <code>null</code>
	 * @param second
	 * 		second {@link Number}, can't be <code>null</code>
	 * @return -1, 0, or 1 as first {@link Number} is less than, equal to, or greater than second {@link Number}
	 */
	public static int compare(final Number first, final Number second) {
		if (first == null)
			throw new IllegalArgumentException("first argument is null.");
		if (second == null)
			throw new IllegalArgumentException("second argument is null.");

		// if first and second numbers not the same type we use BigDecimal type to compare it's
		if (!first.getClass().equals(second.getClass()))
			return new BigDecimal(first.doubleValue()).compareTo(new BigDecimal(second.doubleValue()));

		// if both numbers are the same type
		if (first instanceof Byte && second instanceof Byte) { // byte
			return ((Byte) first).compareTo((Byte) second);
		} else if (first instanceof Short && second instanceof Short) { // short
			return ((Short) first).compareTo((Short) second);
		} else if (first instanceof Integer && second instanceof Integer) { // integer
			return ((Integer) first).compareTo((Integer) second);
		} else if (first instanceof AtomicInteger && second instanceof AtomicInteger) { // atomic integer
			return Integer.compare(first.intValue(), second.intValue());
		} else if (first instanceof BigInteger && second instanceof BigInteger) { // big integer
			return ((BigInteger) first).compareTo((BigInteger) second);
		} else if (first instanceof Long && second instanceof Long) { // long
			return ((Long) first).compareTo((Long) second);
		} else if (first instanceof AtomicLong && second instanceof AtomicLong) { // atomic long
			return Long.compare(first.longValue(), second.longValue());
		} else if (first instanceof Float && second instanceof Float) { // float
			return Float.compare(first.floatValue(), second.floatValue());
		} else if (first instanceof Double && second instanceof Double) { // double
			return Double.compare(first.doubleValue(), second.doubleValue());
		} else if (first instanceof BigDecimal && second instanceof BigDecimal) { // big decimal
			return ((BigDecimal) first).compareTo((BigDecimal) second);
		} else {
			// if other sub-types of Number we use BigDecimal type to compare it's
			return new BigDecimal(first.doubleValue()).compareTo(new BigDecimal(second.doubleValue()));
		}
	}

	/**
	 * Return sum of  incoming parameters.
	 * Returned result type will be calculated using next scheme, by highest argument type, but there is special prio for Atomics.
	 *  result type priorities :
	 *   - BigDecimal - in case if any argument is instance of it;
	 *   - Double - in case if any argument is instance of it;
	 *   - Float - in case if any argument is instance of it;
	 *   - Long - in case if any argument is instance of it;
	 *   - BigInteger - in case if any argument is instance of it;
	 *   - Integer - in case if any argument is instance of it;
	 *   - Short - in case if any argument is instance of it, or  byte;
	 *   - Double  - for all other cases.
	 *
	 * @param first
	 * 		first {@link Number}, can't be <code>null</code>
	 * @param second
	 * 		second {@link Number}, can't be <code>null</code>
	 * @return sum of two numbers
	 */
	public static Number add(final Number first, final Number second) {
		if (first == null)
			throw new IllegalArgumentException("first argument is null.");
		if (second == null)
			throw new IllegalArgumentException("second argument is null.");

		if (first instanceof BigDecimal || second instanceof BigDecimal)
			return first.doubleValue() + second.doubleValue();

		if (first instanceof Double || second instanceof Double)
			return first.doubleValue() + second.doubleValue();

		if (first instanceof Float || second instanceof Float)
			return first.floatValue() + second.floatValue();

		if (first instanceof Long || second instanceof Long)
			return first.longValue() + second.longValue();

		if (first instanceof BigInteger || second instanceof BigInteger)
			return first.longValue() + second.longValue();

		if (first instanceof Integer || second instanceof Integer)
			return first.intValue() + second.intValue();

		if (first instanceof Short || second instanceof Short)
			return first.shortValue() + second.shortValue();

		if (first instanceof Byte || second instanceof Byte)
			return first.shortValue() + second.shortValue();

		// in other  cases for now  lets  stick to double, afterwards  may be improved - but no sense now
		return first.doubleValue() + second.doubleValue();
	}

}
