package gmath

import spock.lang.Specification

class RationalSpec extends Specification {

    def "To String"() {
        expect:
        (a as Rational).toString() == b

        where:
        a             || b
        3 / 4         || "3/4"
        10            || "10"
        1 / 2         || "1/2"
        3.0 as double || "3"
//        "7/10"        || "7/10"
    }

    def "Equals"() {
        expect:
        new Rational(a).equals(b)

        where:
        a             | b
        3 / 4         | new Rational(3, 4)
        10            | 10
        1 / 2         | 0.5
        3.0 as double | 3
    }

    def "Less Than"() {
        expect:
        a < b

        where:
        a                   | b
        new Rational(1, 10) | new Rational(2, 10)
        new Rational(10, 5) | new Rational(10, 2)
        new Rational(5, 4) | new Rational(20, 3)
    }

    def "Plus"() {
        expect:
        a + b == c

        where:
        a                  | b                  || c
        new Rational(3, 4) | new Rational(3, 4) || new Rational(3, 2)
        new Rational(3, 4) | 2                  || new Rational(11, 4)
        2                  | new Rational(3, 4) || new Rational(11, 4)
        1L                 | new Rational(3, 4) || new Rational(7, 4)
        2 as Byte          | new Rational(3, 4) || new Rational(11, 4)
        1 as Short         | new Rational(3, 4) || new Rational(7, 4)
        3 / 4              | new Rational(3, 4) || new Rational(3, 2)
        0.75 as double     | new Rational(3, 4) || new Rational(3, 2)
        0.75 as float      | new Rational(3, 4) || new Rational(3, 2)
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
        5                  | new Rational(1, 4) || Math.pow(5, 0.25)
    }

    def "Negative"() {
        expect:
        -a == b

        where:
        a                  || b
        new Rational(3, 4) || new Rational(-3, 4)
    }
}
