package gmath.ext;

import gmath.Rational;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

public final class NumberExtension {

    //plus operator
    public static Rational plus(final Number self, final Rational num) {
        return num.plus(self);
    }

    // minus operator
    public static Rational minus(final Number self, final Rational num) {
        return num.negative().plus(self);
    }

    // multiply operator
    public static Rational multiply(final Number self, final Rational num) {
        return num.multiply(self);
    }

    // divide operator
    public static Rational div(final Number self, final Rational num) {
        return num.inverse().multiply(self);
    }

    // power operator
    public static double power(final Number self, final Rational num) {
        return Math.pow(self.doubleValue(), num.doubleValue());
    }

    // power operator
    public static double mod(final Number self, final Rational num) {
        return self.doubleValue() % num.doubleValue();
    }

    // as operator
    public static <T> T asType(Number self, Class<T> clazz) {
        if (clazz == Rational.class) return (T) new Rational(self);
        else return DefaultGroovyMethods.asType(self, clazz);
    }
}
