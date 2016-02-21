package gmath

import spock.lang.Specification

class RationalSpec extends Specification {

    def "As Type"() {
        expect:
        a as Rational == b

        where:
        a       || b
        3 / 4   || new Rational(3, 4)
        1 / 3   || new Rational(1, 3)
        10      || new Rational(10, 1)
        11 / 57 || new Rational(11, 57)
    }

    def "Plus"() {
        expect:
        a + b == c

        where:
        a                  | b                  || c
        new Rational(3, 4) | new Rational(3, 4) || new Rational(3, 2)
        new Rational(3, 4) | 2                  || new Rational(11, 4)
        2                  | new Rational(3, 4) || new Rational(11, 4)
        1                  | new Rational(3, 4) || new Rational(7, 4)
    }

    def "Minus"() {
        expect:
        a - b == c

        where:
        a                  | b                  || c
        new Rational(3, 4) | new Rational(1, 4) || new Rational(1, 2)
        new Rational(3, 4) | 2                  || new Rational(-5, 4)
        2                  | new Rational(1, 4) || new Rational(7, 4)
        2L                 | new Rational(1, 4) || new Rational(7, 4)
    }

    def "Multiply"() {
        expect:
        a * b == c

        where:
        a                  | b                  || c
        new Rational(3, 4) | new Rational(2, 3) || new Rational(1, 2)
        new Rational(3, 4) | 2                  || new Rational(3, 2)
        2                  | new Rational(3, 4) || new Rational(3, 2)
    }

    def "Div"() {
        expect:
        a / b == c

        where:
        a                  | b                  || c
        new Rational(3, 4) | new Rational(3, 2) || new Rational(1, 2)
        new Rational(3, 4) | 3                  || new Rational(1, 4)
        2                  | new Rational(3, 4) || new Rational(8, 3)
    }

    def "Power"() {
        expect:
        a**b == c

        where:
        a                  | b                  || c
        new Rational(3, 4) | 2                  || new Rational(9, 16)
        4                  | new Rational(1, 2) || 2
        2                  | new Rational(1, 2) || Math.sqrt(2)
    }

    def "Negative"() {
        expect:
        -a == b

        where:
        a                  || b
        new Rational(3, 4) || new Rational(-3, 4)
    }
}
