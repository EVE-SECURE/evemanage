package lv.odylab.evemanage.client.rpc;

import java.io.Serializable;

public class RationalNumber implements Serializable {
    private Long numerator;
    private Long denominator;

    public RationalNumber() {
    }

    public RationalNumber(Long numerator, Long denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Long getNumerator() {
        return numerator;
    }

    public void setNumerator(Long numerator) {
        this.numerator = numerator;
    }

    public Long getDenominator() {
        return denominator;
    }

    public void setDenominator(Long denominator) {
        this.denominator = denominator;
    }
}
