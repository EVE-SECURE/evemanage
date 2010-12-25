package lv.odylab.evemanage.client.rpc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class RationalNumberProductExpression implements Serializable {
    private List<RationalNumber> rationalNumbers = new ArrayList<RationalNumber>();

    public RationalNumberProductExpression() {
    }

    public RationalNumberProductExpression(List<RationalNumber> rationalNumbers) {
        this.rationalNumbers = rationalNumbers;
    }

    public List<RationalNumber> getRationalNumbers() {
        return rationalNumbers;
    }

    public void setRationalNumbers(List<RationalNumber> rationalNumbers) {
        this.rationalNumbers = rationalNumbers;
    }

    public void addRationalNumber(RationalNumber rationalNumber) {
        rationalNumbers.add(rationalNumber);
    }

    public String getExpression() {
        StringBuilder stringBuilder = new StringBuilder();
        for (RationalNumber rationalNumber : rationalNumbers) {
            stringBuilder.append(rationalNumber.getNumerator()).append("/").append(rationalNumber.getDenominator()).append("*");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public static RationalNumberProductExpression parseExpression(String expressionString) {
        List<RationalNumber> rationalNumbers = new ArrayList<RationalNumber>();
        String[] rationalNumberTokens = expressionString.split("\\*");
        for (String rationalNumber : rationalNumberTokens) {
            String[] tokens = rationalNumber.split("\\/");
            rationalNumbers.add(new RationalNumber(Long.valueOf(tokens[0]), Long.valueOf(tokens[1])));
        }
        return new RationalNumberProductExpression(rationalNumbers);
    }

    public BigDecimal evaluate() {
        Long numeratorProduct = 1L;
        Long denominatorProduct = 1L;
        for (RationalNumber rationalNumber : rationalNumbers) {
            numeratorProduct *= rationalNumber.getNumerator();
            denominatorProduct *= rationalNumber.getDenominator();
        }
        return BigDecimal.valueOf(numeratorProduct).setScale(2).divide(BigDecimal.valueOf(denominatorProduct), RoundingMode.HALF_UP);
    }

    public BigDecimal evaluateAndMultiply(Long value) {
        Long numeratorProduct = value;
        Long denominatorProduct = 1L;
        for (RationalNumber rationalNumber : rationalNumbers) {
            numeratorProduct *= rationalNumber.getNumerator();
            denominatorProduct *= rationalNumber.getDenominator();
        }
        return BigDecimal.valueOf(numeratorProduct).setScale(2).divide(BigDecimal.valueOf(denominatorProduct), RoundingMode.HALF_UP);
    }

    public RationalNumberProductExpression multiply(RationalNumberProductExpression rationalNumberProductExpression) {
        rationalNumbers.addAll(rationalNumberProductExpression.getRationalNumbers());
        return this;
    }
}
