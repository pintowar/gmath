package gmath;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class NumberExtension {

    public static Number asType(final Number self, final Class<? extends Rational> c) {
        return new Rational(self);
    }

    //plus operator
    public static Rational plus(final Byte self, final Rational num) {
        return num.plus(self);
    }

    public static Rational plus(final Short self, final Rational num) {
        return num.plus(self);
    }

    public static Rational plus(final Integer self, final Rational num) {
        return num.plus(self);
    }

    public static Rational plus(final Long self, final Rational num) {
        return num.plus(self);
    }

    public static Rational plus(final Float self, final Rational num) {
        return num.plus(self);
    }

    public static Rational plus(final Double self, final Rational num) {
        return num.plus(self);
    }

    public static Rational plus(final BigInteger self, final Rational num) {
        return num.plus(self);
    }

    public static Rational plus(final BigDecimal self, final Rational num) {
        return num.plus(self);
    }

    // minus operator
    public static Rational minus(final Byte self, final Rational num) {
        return num.negative().plus(self);
    }

    public static Rational minus(final Short self, final Rational num) {
        return num.negative().plus(self);
    }

    public static Rational minus(final Integer self, final Rational num) {
        return num.negative().plus(self);
    }

    public static Rational minus(final Long self, final Rational num) {
        return num.negative().plus(self);
    }

    public static Rational minus(final Float self, final Rational num) {
        return num.negative().plus(self);
    }

    public static Rational minus(final Double self, final Rational num) {
        return num.negative().plus(self);
    }

    public static Rational minus(final BigInteger self, final Rational num) {
        return num.negative().plus(self);
    }

    public static Rational minus(final BigDecimal self, final Rational num) {
        return num.negative().plus(self);
    }

    // multiply operator
    public static Rational multiply(final Byte self, final Rational num) {
        return num.multiply(self);
    }

    public static Rational multiply(final Short self, final Rational num) {
        return num.multiply(self);
    }

    public static Rational multiply(final Integer self, final Rational num) {
        return num.multiply(self);
    }

    public static Rational multiply(final Long self, final Rational num) {
        return num.multiply(self);
    }

    public static Rational multiply(final Float self, final Rational num) {
        return num.multiply(self);
    }

    public static Rational multiply(final Double self, final Rational num) {
        return num.multiply(self);
    }

    public static Rational multiply(final BigInteger self, final Rational num) {
        return num.multiply(self);
    }

    public static Rational multiply(final BigDecimal self, final Rational num) {
        return num.multiply(self);
    }

    // multiply operator
    public static Rational div(final Byte self, final Rational num) {
        return num.inverse().multiply(self);
    }

    public static Rational div(final Short self, final Rational num) {
        return num.inverse().multiply(self);
    }

    public static Rational div(final Integer self, final Rational num) {
        return num.inverse().multiply(self);
    }

    public static Rational div(final Long self, final Rational num) {
        return num.inverse().multiply(self);
    }

    public static Rational div(final Float self, final Rational num) {
        return num.inverse().multiply(self);
    }

    public static Rational div(final Double self, final Rational num) {
        return num.inverse().multiply(self);
    }

    public static Rational div(final BigInteger self, final Rational num) {
        return num.inverse().multiply(self);
    }

    public static Rational div(final BigDecimal self, final Rational num) {
        return num.inverse().multiply(self);
    }
}
