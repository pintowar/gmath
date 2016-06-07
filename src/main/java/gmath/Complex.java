package gmath;

/**
 * Created by thiago on 07/06/16.
 */
public class Complex implements Comparable<Complex> {
    private final double real;
    private final double imaginary;

    public static final Complex I = new Complex(0, 1);
    public static final Complex NaN = new Complex(Double.NaN, Double.NaN);
    public static final Complex INF = new Complex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public Complex(double real) {
        this(real, 0.0);
    }

    public Complex(Number real, Number imaginary) {
        this.real = real.doubleValue();
        this.imaginary = imaginary.doubleValue();
    }

    public Complex(Number real) {
        this(real, 0);
    }

    /**
     * Returns the real value of the complex number.
     *
     * @return The real.
     */
    public double getReal() {
        return real;
    }

    /**
     * Returns the imaginary value of the complex number.
     *
     * @return The imaginary.
     */
    public double getImaginary() {
        return imaginary;
    }

    public Complex plus(Complex other) {
        if (isNaN() || other.isNaN()) {
            return NaN;
        }
        return new Complex(this.real + other.real, this.imaginary + other.imaginary);
    }

    public Complex plus(Number other) {
        return this.plus(new Complex(other));
    }

    public Complex minus(Complex other) {
        if (isNaN() || other.isNaN()) {
            return NaN;
        }
        return this.plus(other.negative());
    }

    public Complex minus(Number other) {
        return this.minus(new Complex(other));
    }

    public Complex multiply(Complex other) {
        if (isNaN() || other.isNaN()) {
            return NaN;
        }
        if (Double.isInfinite(real) ||
                Double.isInfinite(imaginary) ||
                Double.isInfinite(other.real) ||
                Double.isInfinite(other.imaginary)) {
            return INF;
        }

        return new Complex(real * other.real - imaginary * other.imaginary,
                real * other.imaginary + imaginary * other.real);
    }

    public Complex multiply(Number other) {
        return this.multiply(new Complex(other));
    }

    public Complex div(Complex other) {
        if (isNaN() || other.isNaN()) {
            return NaN;
        }

        final double c = other.getReal();
        final double d = other.getImaginary();

        if (c == 0.0 && d == 0.0) {
            return NaN;
        }

        if (other.isInfinite() && !isInfinite()) {
            return ZERO;
        }

        if (Math.abs(c) < Math.abs(d)) {
            double q = c / d;
            double denominator = c * q + d;
            return new Complex((real * q + imaginary) / denominator,
                    (imaginary * q - real) / denominator);
        } else {
            double q = d / c;
            double denominator = d * q + c;
            return new Complex((imaginary * q + real) / denominator,
                    (imaginary - real * q) / denominator);
        }
    }

    public Complex div(Number other) {
        return this.div(new Complex(other));
    }

    public Complex power(Complex other) {
        return this.log().multiply(other).exp();
    }

    public Complex power(Number other) {
        return this.power(new Complex(other));
    }

    public double abs() {
        if (isNaN()) {
            return Double.NaN;
        }
        if (isInfinite()) {
            return Double.POSITIVE_INFINITY;
        }
        if (Math.abs(real) < Math.abs(imaginary)) {
            if (imaginary == 0.0) {
                return Math.abs(real);
            }
            double q = real / imaginary;
            return Math.abs(imaginary) * Math.sqrt(1 + q * q);
        } else {
            if (real == 0.0) {
                return Math.abs(imaginary);
            }
            double q = imaginary / real;
            return Math.abs(real) * Math.sqrt(1 + q * q);
        }
    }

    public Complex log() {
        if (isNaN()) {
            return NaN;
        }

        return new Complex(Math.log(abs()),
                Math.atan2(imaginary, real));
    }

    public Complex exp() {
        if (isNaN()) {
            return NaN;
        }

        double expReal = Math.exp(real);
        return new Complex(expReal * Math.cos(imaginary),
                expReal * Math.sin(imaginary));
    }

    public Complex negative() {
        if (isNaN()) {
            return NaN;
        }
        return new Complex(-this.real, -this.imaginary);
    }

    public Complex positive() {
        return new Complex(this.real, this.imaginary);
    }

    public Complex reciprocal() {
        if (isNaN()) {
            return NaN;
        }

        if (real == 0.0 && imaginary == 0.0) {
            return INF;
        }

        if (isInfinite()) {
            return ZERO;
        }

        if (Math.abs(real) < Math.abs(imaginary)) {
            double q = real / imaginary;
            double scale = 1. / (real * q + imaginary);
            return new Complex(scale * q, -scale);
        } else {
            double q = imaginary / real;
            double scale = 1. / (imaginary * q + real);
            return new Complex(scale, -scale * q);
        }
    }

    public boolean isNaN() {
        return Double.isNaN(real) || Double.isNaN(imaginary);
    }

    public boolean isInfinite() {
        return !isNaN() && (Double.isInfinite(real) || Double.isInfinite(imaginary));
    }

    @Override
    public int compareTo(Complex o) {
        return 0;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Complex) {
            Complex c = (Complex) other;
            if (c.isNaN()) {
                return isNaN();
            } else {
                return real == c.real && imaginary == c.imaginary;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (isNaN()) {
            return 7;
        }
        return 37 * (17 * Double.valueOf(imaginary).hashCode() +
                Double.valueOf(real).hashCode());
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(real);
        if (imaginary >= 0) {
            buffer.append('+');
        }
        buffer.append(imaginary);
        buffer.append('i');
        return buffer.toString();
    }
}
