package gmath;
// ============================================================================
//   Copyright 2006-2010 Daniel W. Dyer
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
// ============================================================================

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Immutable value object for representing a rational number (or vulgar fraction).
 * Instaces of this class provide a way to perform arithmetic on fractional values
 * without loss of precision.
 * This implementation automatically simplifies fractions (3/6 is stored as 1/2).
 * The implementation also requires that the denominator is positive.  The numerator
 * may be negative.
 *
 * @author Daniel Dyer
 * @since 1.2
 */
public final class Rational implements Comparable<Rational> {
    private final long numerator;
    private final long denominator;

    /**
     * Creates a vulgar fraction with the specified numerator and denominator.
     *
     * @param numerator   The fraction's numerator (may be negative).
     * @param denominator The fraction's denominator (must be greater than or
     *                    equal to 1).
     */
    public Rational(long numerator, long denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator must be non-zero.");
        }
        int aux = denominator > 0 ? 1 : -1;
        long gcd = greatestCommonDivisor(numerator, denominator);
        this.numerator = aux * (numerator / gcd);
        this.denominator = aux * (denominator / gcd);
    }

    /**
     * Creates a rational value equivalent to the specified integer value.
     *
     * @param value The value of this rational as an integer.
     */
    public Rational(long value) {
        this(value, 1);
    }

    /**
     * Creates a rational value equivalent to the specified decimal value.
     *
     * @param value The value of this rational as a fractional decimal.
     * @throws ArithmeticException If the BigDecimal value is too large to be
     *                             represented as a Rational.
     */
    public Rational(Number value) {
        if (value.getClass() == BigDecimal.class) {
            BigDecimal trimmedValue = ((BigDecimal) value).stripTrailingZeros();
            BigInteger denominator = BigInteger.TEN.pow(trimmedValue.scale());
            BigInteger numerator = trimmedValue.unscaledValue();
            BigInteger gcd = numerator.gcd(denominator);
            this.numerator = numerator.divide(gcd).longValue();
            this.denominator = denominator.divide(gcd).longValue();
        } else if (value.getClass() == Double.class || value.getClass() == Float.class) {
            BigDecimal trimmedValue = new BigDecimal(value.doubleValue()).stripTrailingZeros();
            BigInteger denominator = BigInteger.TEN.pow(trimmedValue.scale());
            BigInteger numerator = trimmedValue.unscaledValue();
            BigInteger gcd = numerator.gcd(denominator);
            this.numerator = numerator.divide(gcd).longValue();
            this.denominator = denominator.divide(gcd).longValue();
        } else {
            this.numerator = value.longValue();
            this.denominator = 1;
        }
    }

    /**
     * Returns the numerator of the fraction.
     *
     * @return The numerator.
     */
    public long getNumerator() {
        return numerator;
    }

    /**
     * Returns the denominator (divisor) of the fraction.
     *
     * @return The denominator.
     */
    public long getDenominator() {
        return denominator;
    }

    public Rational inverse() {
        return new Rational(denominator, numerator);
    }

    public Rational plus(Rational other) {
        if (denominator == other.denominator) {
            return new Rational(numerator + other.numerator, denominator);
        } else {
            return new Rational(numerator * other.denominator + other.numerator * denominator,
                    denominator * other.denominator);
        }
    }

    /**
     * Add the specified value to this value and return the result as a new object
     * (also a rational).  If the two values have different denominators, they will
     * first be converted so that they have common denominators.
     *
     * @param other The value to add to this rational.
     * @return A new rational value that is the sum of this value and the specified
     * value.
     */
    public Rational plus(Number other) {
        Rational value = new Rational(other);
        return this.plus(value);
    }

    public Rational minus(Rational other) {
        return plus(other.negative());
    }

    /**
     * Subtract the specified value from this value and return the result as a new object
     * (also a rational).  If the two values have different denominators, they will
     * first be converted so that they have common denominators.
     *
     * @param other The value to subtract from this rational.
     * @return A new rational value that is the result of subtracting the specified value
     * from this value.
     */
    public Rational minus(Number other) {
        Rational value = new Rational(other);
        return this.minus(value);
    }

    public Rational multiply(Rational other) {
        return new Rational(numerator * other.numerator,
                denominator * other.denominator);
    }

    /**
     * Multiply this rational by the specified value and return the result as a new
     * object (also a Rational).
     *
     * @param other The amount to multiply by.
     * @return A new rational value that is the result of multiplying this value by
     * the specified value.
     */
    public Rational multiply(Number other) {
        Rational value = new Rational(other);
        return this.multiply(value);
    }

    public Rational div(Rational other) {
        return new Rational(numerator * other.denominator,
                denominator * other.numerator);
    }

    /**
     * Divide this rational by the specified value and return the result as a new
     * object (also a Rational).
     *
     * @param other The amount to divide by.
     * @return A new rational value that is the result of dividing this value by
     * the specified value.
     */
    public Rational div(Number other) {
        Rational value = new Rational(other);
        return this.div(value);
    }

    public double power(Rational other) {
        return Math.pow(this.doubleValue(), other.doubleValue());
    }

    public double power(Number other) {
        return Math.pow(this.doubleValue(), other.doubleValue());
    }

    public Rational mod(Rational other) {
        Rational div = this.div(other);
        return this.minus(other.multiply(div.longValue()));
    }

    public Rational mod(Number other) {
        Rational value = new Rational(other);
        return this.mod(value);
    }

    public Rational negative() {
        return this.multiply(-1);
    }

    public Rational positive() {
        return new Rational(this.numerator, this.denominator);
    }

    /**
     * Returns the integer equivalent of this rational number.  If
     * there is no exact integer representation, it returns the closest
     * integer that is lower than the rational value (effectively the
     * truncated version of calling {@link #doubleValue()}.
     *
     * @return The (truncated) integer value of this rational.
     */
    public int intValue() {
        return (int) longValue();
    }

    /**
     * Returns the integer equivalent of this rational number as a long.
     * If there is no exact integer representation, it returns the closest
     * long that is lower than the rational value (effectively the
     * truncated version of calling {@link #doubleValue()}.
     *
     * @return The (truncated) long value of this rational.
     */
    public long longValue() {
        return (long) doubleValue();
    }

    /**
     * Returns the result of dividing the numerator by the denominator.
     * Will result in a loss of precision for fractions that have no
     * exact float representation.
     *
     * @return The closest double-precision floating point equivalent of
     * the fraction represented by this object.
     */
    public float floatValue() {
        return (float) doubleValue();
    }

    /**
     * Returns the result of dividing the numerator by the denominator.
     * Will result in a loss of precision for fractions that have no
     * exact double representation.
     *
     * @return The closest double-precision floating point equivalent of
     * the fraction represented by this object.
     */
    public double doubleValue() {
        return (double) numerator / denominator;
    }

    /**
     * Determines the greatest common divisor of a pair of natural numbers
     * using the Euclidean algorithm.  This method only works with natural
     * numbers.  If negative integers are passed in, the absolute values will
     * be used.  The return value is always positive.
     *
     * @param a The first value.
     * @param b The second value.
     * @return The greatest common divisor.
     * @since 1.2
     */
    public long greatestCommonDivisor(long a, long b) {
        long absA = Math.abs(a);
        long absB = Math.abs(b);
        while (absB != 0) {
            long temp = absB;
            absB = absA % absB;
            absA = temp;
        }
        return absA;
    }

    /**
     * Determines whether this rational value is equal to some other object.
     * To be considered equal the other object must also be a Rational object
     * with an indentical numerator and denominator.
     *
     * @param other The object to compare against.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        Rational rational = other instanceof Number ? new Rational((Number) other) : (Rational) other;

        return denominator == rational.denominator && numerator == rational.numerator;
    }

    /**
     * Over-ridden to be consistent with {@link #equals(Object)}.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        int result = (int) (numerator ^ (numerator >>> 32));
        result = 31 * result + (int) (denominator ^ (denominator >>> 32));
        return result;
    }

    /**
     * Returns a String representation of the rational number, expressed as
     * a vulgar fraction (i.e. 1 and 1/3 is shown as 4/3).  If the rational
     * is equal to an integer, the value is simply displayed as that integer
     * with no fractional part (i.e. 2/1 is shown as 2).
     *
     * @return A string representation of this rational value.
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(numerator);
        if (denominator != 1) {
            buffer.append('/');
            buffer.append(denominator);
        }
        return buffer.toString();
    }

    /**
     * Compares this value with the specified object for order. Returns a negative
     * integer, zero, or a positive integer as this value is less than, equal to, or
     * greater than the specified value.
     *
     * @param other Another Rational value.
     * @return A negative integer, zero, or a positive integer as this value is less
     * than, equal to, or greater than the specified value.
     */
    public int compareTo(Rational other) {
        if (denominator == other.denominator) {
            return ((Long) numerator).compareTo(other.numerator);
        } else {
            Long adjustedNumerator = numerator * other.denominator;
            Long otherAdjustedNumerator = other.numerator * denominator;
            return adjustedNumerator.compareTo(otherAdjustedNumerator);
        }
    }
}
