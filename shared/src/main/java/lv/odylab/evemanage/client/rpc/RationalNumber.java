package lv.odylab.evemanage.client.rpc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class RationalNumber implements Serializable, Comparable<RationalNumber> {
    private Long numerator;
    private Long denominator;

    public RationalNumber() {
    }

    public RationalNumber(Long numerator) {
        this.numerator = numerator;
        this.denominator = 1L;
    }

    public RationalNumber(Long numerator, Long denominator) {
        Long greatestCommonDivisor = findGreatestCommonDivisor(numerator, denominator);
        this.numerator = numerator / greatestCommonDivisor;
        this.denominator = denominator / greatestCommonDivisor;
    }

    public Long getNumerator() {
        return numerator;
    }

    public Long getDenominator() {
        return denominator;
    }

    @Override
    public String toString() {
        if (denominator == 1) {
            return String.valueOf(numerator);
        } else {
            return new StringBuilder().append(numerator).append("/").append(denominator).toString();
        }
    }

    @Override
    public int compareTo(RationalNumber rationalNumber) {
        Long a = numerator * rationalNumber.getDenominator();
        Long b = rationalNumber.getNumerator() * denominator;
        return a.compareTo(b);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        RationalNumber rationalNumber = (RationalNumber) object;
        return compareTo(rationalNumber) == 0;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    // http://en.wikipedia.org/wiki/Greatest_common_divisor#Using_Euclid.27s_algorithm
    private Long findGreatestCommonDivisor(Long a, Long b) {
        if (b == 0) {
            return a;
        } else {
            return findGreatestCommonDivisor(b, a % b);
        }
    }

    public RationalNumber multiply(Long multiplier) {
        return new RationalNumber(numerator * multiplier, denominator);
    }

    public RationalNumber multiply(RationalNumber multiplier) {
        return new RationalNumber(numerator * multiplier.getNumerator(), denominator * multiplier.getDenominator());
    }

    public RationalNumber divide(Long multiplier) {
        return new RationalNumber(numerator, denominator * multiplier);
    }

    public BigDecimal multiplyDecimal(BigDecimal multiplier) {
        if (denominator == null) {
            return BigDecimal.ZERO.setScale(2);
        } else {
            return multiplier.multiply(BigDecimal.valueOf(numerator)).divide(BigDecimal.valueOf(denominator), RoundingMode.HALF_UP);
        }
    }

    public RationalNumber add(RationalNumber number) {
        if (denominator.equals(number.getDenominator())) {
            return new RationalNumber(numerator + number.getNumerator(), denominator);
        } else {
            return new RationalNumber(numerator * number.getDenominator() + number.getNumerator() * denominator, denominator * number.getDenominator());
        }
    }

    public BigDecimal evaluate() {
        return BigDecimal.valueOf(numerator).setScale(2).divide(BigDecimal.valueOf(denominator), RoundingMode.HALF_UP);
    }

    public Long evaluateToLong() {
        return evaluate().setScale(0, RoundingMode.CEILING).longValue();
    }

    public Boolean equalOne() {
        return numerator == 1 && denominator == 1;
    }

    public Boolean hasFraction() {
        return denominator != 1;
    }

    public static RationalNumber parse(String string) {
        String[] tokens = string.split("\\/");
        if (tokens.length == 1) {
            return new RationalNumber(Long.valueOf(tokens[0]), 1L);
        } else {
            return new RationalNumber(Long.valueOf(tokens[0]), Long.valueOf(tokens[1]));
        }
    }
}
