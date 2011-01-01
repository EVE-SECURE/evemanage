package lv.odylab.evemanage.client.rpc;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RationalNumberTest {
    private RationalNumber ZERO = RationalNumber.parse("0");
    private RationalNumber ONE = RationalNumber.parse("1");
    private RationalNumber TWO = RationalNumber.parse("2");
    private RationalNumber FIVE_THIRDS = RationalNumber.parse("5/3");
    private RationalNumber TEN_THIRDS = RationalNumber.parse("10/3");
    private RationalNumber THREE_TENTHS = RationalNumber.parse("3/10");
    private RationalNumber FIVE_TENTHS = RationalNumber.parse("5/10");

    @Test
    public void testParse() {
        assertThat(ONE.getNumerator(), equalTo(1L));
        assertThat(ONE.getDenominator(), equalTo(1L));
        assertThat(TWO.getNumerator(), equalTo(2L));
        assertThat(TWO.getDenominator(), equalTo(1L));
        assertThat(ZERO.getNumerator(), equalTo(0L));
        assertThat(ZERO.getDenominator(), equalTo(1L));
        assertThat(TEN_THIRDS.getNumerator(), equalTo(10L));
        assertThat(TEN_THIRDS.getDenominator(), equalTo(3L));
        assertThat(THREE_TENTHS.getNumerator(), equalTo(3L));
        assertThat(THREE_TENTHS.getDenominator(), equalTo(10L));
    }

    @Test
    public void testToString() {
        assertThat(ONE.toString(), equalTo("1"));
        assertThat(TWO.toString(), equalTo("2"));
        assertThat(ZERO.toString(), equalTo("0"));
        assertThat(FIVE_TENTHS.toString(), equalTo("1/2"));
        assertThat(TEN_THIRDS.toString(), equalTo("10/3"));
    }

    @Test
    public void testMultiply() {
        assertThat(ONE.multiply(10L).toString(), equalTo("10"));
        assertThat(TWO.multiply(10L).toString(), equalTo("20"));
        assertThat(ZERO.multiply(10L).toString(), equalTo("0"));
        assertThat(FIVE_THIRDS.multiply(1L).toString(), equalTo("5/3"));
        assertThat(FIVE_THIRDS.multiply(3L).toString(), equalTo("5"));
        assertThat(FIVE_THIRDS.multiply(FIVE_THIRDS).toString(), equalTo("25/9"));
    }

    @Test
    public void testAdd() {
        assertThat(ONE.add(ONE).toString(), equalTo("2"));
        assertThat(TWO.add(TWO).toString(), equalTo("4"));
        assertThat(ZERO.add(ONE).toString(), equalTo("1"));
        assertThat(ZERO.add(ONE).add(TWO).toString(), equalTo("3"));
        assertThat(ONE.add(FIVE_THIRDS).toString(), equalTo("8/3"));
        assertThat(FIVE_THIRDS.add(FIVE_THIRDS).toString(), equalTo("10/3"));
        assertThat(FIVE_THIRDS.add(TEN_THIRDS).toString(), equalTo("5"));
        assertThat(FIVE_THIRDS.add(THREE_TENTHS).toString(), equalTo("59/30"));
    }

    @Test
    public void testEvaluate() {
        assertThat(ONE.evaluate(), equalTo(new BigDecimal("1.00")));
        assertThat(TWO.evaluate(), equalTo(new BigDecimal("2.00")));
        assertThat(ZERO.evaluate(), equalTo(new BigDecimal("0.00")));
        assertThat(FIVE_TENTHS.evaluate(), equalTo(new BigDecimal("0.50")));
        assertThat(FIVE_THIRDS.evaluate(), equalTo(new BigDecimal("1.67")));
        assertThat(TEN_THIRDS.evaluate(), equalTo(new BigDecimal("3.33")));
    }

    @Test
    public void testEvaluateToLong() {
        assertThat(ONE.evaluateToLong(), equalTo(1L));
        assertThat(TWO.evaluateToLong(), equalTo(2L));
        assertThat(ZERO.evaluateToLong(), equalTo(0L));
        assertThat(FIVE_TENTHS.evaluateToLong(), equalTo(1L));
        assertThat(FIVE_THIRDS.evaluateToLong(), equalTo(2L));
        assertThat(TEN_THIRDS.evaluateToLong(), equalTo(4L));
    }
}
